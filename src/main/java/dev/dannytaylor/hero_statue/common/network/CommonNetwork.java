package dev.dannytaylor.hero_statue.common.network;

import dev.dannytaylor.hero_statue.common.block.StatueBlockEntity;
import dev.dannytaylor.hero_statue.common.block.StatueData;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import dev.dannytaylor.hero_statue.common.gamerule.GameruleRegistry;
import dev.dannytaylor.hero_statue.common.network.payloads.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
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

	public static void bootstrap() {
		try {
			// C->S
			PayloadTypeRegistry.playC2S().register(C2SUpdateChunkStatuesPayload.id, C2SUpdateChunkStatuesPayload.packetCodec);
			PayloadTypeRegistry.playC2S().register(C2SUpdateStatuePayload.id, C2SUpdateStatuePayload.packetCodec);
			// C<-S
			PayloadTypeRegistry.playS2C().register(S2CUpdateStatuePayload.id, S2CUpdateStatuePayload.packetCodec);
			PayloadTypeRegistry.playS2C().register(S2CUpdateChunkStatuesPayload.id, S2CUpdateChunkStatuesPayload.packetCodec);
			// C<->S
			PayloadTypeRegistry.playC2S().register(IdBooleanPayload.id, IdBooleanPayload.packetCodec);
			PayloadTypeRegistry.playS2C().register(IdBooleanPayload.id, IdBooleanPayload.packetCodec);
			PayloadTypeRegistry.playC2S().register(RequestPayload.id, RequestPayload.packetCodec);
			PayloadTypeRegistry.playS2C().register(RequestPayload.id, RequestPayload.packetCodec);

			ServerPlayNetworking.registerGlobalReceiver(C2SUpdateStatuePayload.id, (payload, context) -> context.server().execute(() -> {
				ServerWorld world = context.player().getWorld();
				BlockPos blockPos = payload.blockPos();
				if (payload.updateAll()) {
					for (ServerPlayerEntity player : PlayerLookup.tracking(world, blockPos)) {
						sendS2CStatue(player, world, blockPos);
					}
				} else sendS2CStatue(context.player(), world, blockPos);
			}));

			ServerPlayNetworking.registerGlobalReceiver(C2SUpdateChunkStatuesPayload.id, (payload, context) -> context.server().execute(() -> {
				ServerWorld world = context.player().getWorld();
				ChunkPos chunkPos = payload.chunkPos();
				if (payload.updateAll()) {
					for (ServerPlayerEntity player : PlayerLookup.tracking(world, chunkPos)) {
						sendS2CStatueChunk(player, world, chunkPos);
					}
				} else sendS2CStatueChunk(context.player(), world, chunkPos);
			}));

			ServerPlayNetworking.registerGlobalReceiver(IdBooleanPayload.id, (payload, context) -> context.server().execute(() -> {
				// We don't currently expect the client to send anything, but if we ever do, this would be where it would go.
			}));

			ServerPlayNetworking.registerGlobalReceiver(RequestPayload.id, (payload, context) -> context.server().execute(() -> {
				if (payload.identifier().equals(request_gameRules)) sendGameRules(context.player(), context.server());
			}));

			ServerPlayConnectionEvents.JOIN.register((networkHandler, packetSender, server) -> {
				sendGameRules(networkHandler.player, server);
			});

			ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
				for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) sendGameRules(player, server);
			});
		} catch (Exception error) {
			CommonData.logger.warn("Failed to init common network: {}", error.getLocalizedMessage());
		}
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

	public static Identifier idOf(String path) {
		return Identifier.of(CommonData.id, path);
	}

	static {
		c2sStatue = idOf("c2s_update_statue");
		c2sStatueChunk = idOf("c2s_update_chunk_statues");
		s2cStatue = idOf("s2c_update_statue");
		s2cStatueChunk = idOf("s2c_update_chunk_statues");
		bidIdBoolean = idOf("bid_id_boolean");
		bidRequest = idOf("bid_request");

		request_gameRules = idOf("request/gamerules");
		gamerule_allowPlayerChangeStatuePose = idOf("gamerule/allow_player_change_statue_pose");
	}
}
