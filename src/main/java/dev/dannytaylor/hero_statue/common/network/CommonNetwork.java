package dev.dannytaylor.hero_statue.common.network;

import dev.dannytaylor.hero_statue.common.block.StatueBlockEntity;
import dev.dannytaylor.hero_statue.common.block.StatueData;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import dev.dannytaylor.hero_statue.common.event.CommonEvents;
import dev.dannytaylor.hero_statue.common.gamerule.GameruleCache;
import dev.dannytaylor.hero_statue.common.gamerule.GameruleRegistry;
import dev.dannytaylor.hero_statue.common.network.payloads.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.List;

public class CommonNetwork {
	public static final Identifier c2sStatue;
	public static final Identifier c2sStatueChunk;
	public static final Identifier s2cStatue;
	public static final Identifier s2cStatueChunk;
	public static final Identifier bidIdBoolean;
	public static final Identifier bidRequest;

	public static final Identifier request_gameRules;
	public static final Identifier gamerule_allowPlayerChangeStatuePose;

	public static void registerEvents() {
		try {
			CommonEvents.Network.updateStatue.register(s2cStatue, (payload, context) -> {
				ServerWorld world = context.player().getWorld();
				BlockPos blockPos = payload.blockPos();
				if (payload.updateAll()) {
					for (ServerPlayerEntity player : PlayerLookup.tracking(world, blockPos)) {
						sendS2CStatue(player, world, blockPos);
					}
				} else sendS2CStatue(context.player(), world, blockPos);
			});
			CommonEvents.Network.updateChunkStatues.register(s2cStatue, (payload, context) -> {
				ServerWorld world = context.player().getWorld();
				ChunkPos chunkPos = payload.chunkPos();
				if (payload.updateAll()) {
					for (ServerPlayerEntity player : PlayerLookup.tracking(world, chunkPos)) {
						sendS2CStatueChunk(player, world, chunkPos);
					}
				} else sendS2CStatueChunk(context.player(), world, chunkPos);
			});
			CommonEvents.Network.request.register(bidRequest, (payload, context) -> {
				if (payload.identifier().equals(request_gameRules)) sendGameRules(context.player(), context.server());
			});
		} catch (Exception error) {
			CommonData.logger.warn("Failed to register common network events: {}", error.getLocalizedMessage());
		}
	}

	public static void registerReceivers() {
		try {
			ServerPlayNetworking.registerGlobalReceiver(C2SUpdateStatuePayload.id, (payload, context) -> context.server().execute(() -> {
				CommonEvents.Network.updateStatue.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
			ServerPlayNetworking.registerGlobalReceiver(C2SUpdateChunkStatuesPayload.id, (payload, context) -> context.server().execute(() -> {
				CommonEvents.Network.updateChunkStatues.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
			ServerPlayNetworking.registerGlobalReceiver(IdBooleanPayload.id, (payload, context) -> context.server().execute(() -> {
				CommonEvents.Network.idBoolean.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
			ServerPlayNetworking.registerGlobalReceiver(RequestPayload.id, (payload, context) -> context.server().execute(() -> {
				CommonEvents.Network.request.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
		} catch (Exception error) {
			CommonData.logger.warn("Failed to register common network receivers: {}", error.getLocalizedMessage());
		}
	}

	public static void registerSenders() {
		try {
			// Server to Client
			PayloadTypeRegistry.playS2C().register(S2CUpdateStatuePayload.id, S2CUpdateStatuePayload.packetCodec);
			PayloadTypeRegistry.playS2C().register(S2CUpdateChunkStatuesPayload.id, S2CUpdateChunkStatuesPayload.packetCodec);

			// Bi-directional
			PayloadTypeRegistry.playC2S().register(IdBooleanPayload.id, IdBooleanPayload.packetCodec);
			PayloadTypeRegistry.playS2C().register(IdBooleanPayload.id, IdBooleanPayload.packetCodec);
			PayloadTypeRegistry.playC2S().register(RequestPayload.id, RequestPayload.packetCodec);
			PayloadTypeRegistry.playS2C().register(RequestPayload.id, RequestPayload.packetCodec);

			// Events
			ServerPlayConnectionEvents.JOIN.register((networkHandler, packetSender, server) -> {
				sendGameRules(networkHandler.player, server);
			});
			ServerTickEvents.START_WORLD_TICK.register(world -> {
				boolean allowPlayerChangeStatuePose = world.getGameRules().getBoolean(GameruleRegistry.allowPlayerChangeStatuePose);
				if (GameruleCache.allowPlayerChangeStatuePose != allowPlayerChangeStatuePose) {
					GameruleCache.allowPlayerChangeStatuePose = allowPlayerChangeStatuePose;
					for (ServerPlayerEntity player : world.getServer().getPlayerManager().getPlayerList()) sendGameRules(player, world.getServer());
				}
			});
		} catch (Exception error) {
			CommonData.logger.warn("Failed to register common network senders: {}", error.getLocalizedMessage());
		}
	}

	public static void bootstrap() {
		registerEvents();
		registerSenders();
		registerReceivers();
	}

	public static void sendS2CStatue(ServerPlayerEntity player, ServerWorld world, BlockPos blockPos) {
		BlockEntity blockEntity = world.getBlockEntity(blockPos);
		if (blockEntity != null) {
			if (blockEntity instanceof StatueBlockEntity statueBlockEntity) {
				StatueData statueData = new StatueData(blockPos, statueBlockEntity.getStack());
				ServerPlayNetworking.send(player, new S2CUpdateStatuePayload(statueData));
			} else {
				CommonData.logger.error("Mismatched block entity, expected StatueBlockEntity found {}, client may experience de-sync!", blockEntity.getNameForReport());
			}
		}
	}

	public static void sendS2CStatueChunk(ServerPlayerEntity player, ServerWorld world, ChunkPos chunkPos) {
		List<StatueData> statueData = new ArrayList<>();
		world.getChunk(chunkPos.x, chunkPos.z).getBlockEntities().forEach((blockPos, blockEntity) -> {
			if (blockEntity instanceof StatueBlockEntity statueBlockEntity) {
				statueData.add(new StatueData(blockPos, statueBlockEntity.getStack()));
			}
		});
		ServerPlayNetworking.send(player, new S2CUpdateChunkStatuesPayload(statueData));
	}

	public static void sendRequest(ServerPlayerEntity player, Identifier identifier) {
		ServerPlayNetworking.send(player, new RequestPayload(identifier));
	}

	public static void sendIdBoolean(ServerPlayerEntity player, Identifier identifier, Boolean value) {
		ServerPlayNetworking.send(player, new IdBooleanPayload(identifier, value));
	}

	public static void sendGameRules(ServerPlayerEntity player, MinecraftServer server) {
		sendIdBoolean(player, gamerule_allowPlayerChangeStatuePose, server.getGameRules().getBoolean(GameruleRegistry.allowPlayerChangeStatuePose));
	}

	static {
		c2sStatue = CommonData.idOf("c2s_update_statue");
		c2sStatueChunk = CommonData.idOf("c2s_update_chunk_statues");
		s2cStatue = CommonData.idOf("s2c_update_statue");
		s2cStatueChunk = CommonData.idOf("s2c_update_chunk_statues");
		bidIdBoolean = CommonData.idOf("bid_id_boolean");
		bidRequest = CommonData.idOf("bid_request");

		request_gameRules = CommonData.idOf("request/gamerules");
		gamerule_allowPlayerChangeStatuePose = CommonData.idOf("gamerule/allow_player_change_statue_pose");
	}
}
