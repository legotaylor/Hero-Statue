package dev.dannytaylor.hero_statue.common.network;

import dev.dannytaylor.hero_statue.common.block.StatueBlockEntity;
import dev.dannytaylor.hero_statue.common.block.StatueData;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import dev.dannytaylor.hero_statue.common.network.payloads.C2SUpdateChunkStatuesPayload;
import dev.dannytaylor.hero_statue.common.network.payloads.C2SUpdateStatuePayload;
import dev.dannytaylor.hero_statue.common.network.payloads.S2CUpdateChunkStatuesPayload;
import dev.dannytaylor.hero_statue.common.network.payloads.S2CUpdateStatuePayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
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

	public static void bootstrap() {
		try {
			PayloadTypeRegistry.playC2S().register(C2SUpdateChunkStatuesPayload.id, C2SUpdateChunkStatuesPayload.packetCodec);
			PayloadTypeRegistry.playC2S().register(C2SUpdateStatuePayload.id, C2SUpdateStatuePayload.packetCodec);
			PayloadTypeRegistry.playS2C().register(S2CUpdateStatuePayload.id, S2CUpdateStatuePayload.packetCodec);
			PayloadTypeRegistry.playS2C().register(S2CUpdateChunkStatuesPayload.id, S2CUpdateChunkStatuesPayload.packetCodec);

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

	public static Identifier idOf(String path) {
		return Identifier.of(CommonData.id, path);
	}

	static {
		c2sStatue = idOf("c2s_update_statue");
		c2sStatueChunk = idOf("c2s_update_chunk_statues");
		s2cStatue = idOf("s2c_update_statue");
		s2cStatueChunk = idOf("s2c_update_chunk_statues");
	}
}
