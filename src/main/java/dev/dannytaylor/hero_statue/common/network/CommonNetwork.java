package dev.dannytaylor.hero_statue.common.network;

import dev.dannytaylor.hero_statue.common.block.StatueBlockEntity;
import dev.dannytaylor.hero_statue.common.block.StatueData;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import dev.dannytaylor.hero_statue.common.network.payloads.C2SStatueChunkPayload;
import dev.dannytaylor.hero_statue.common.network.payloads.S2CStatueChunkPayload;
import dev.dannytaylor.hero_statue.common.network.payloads.S2CStatuePayload;
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
	public static final Identifier c2sStatueChunk;
	public static final Identifier s2cStatue;
	public static final Identifier s2cStatueChunk;

	public static void bootstrap() {
		try {
			PayloadTypeRegistry.playC2S().register(C2SStatueChunkPayload.id, C2SStatueChunkPayload.packetCodec);
			PayloadTypeRegistry.playS2C().register(S2CStatuePayload.id, S2CStatuePayload.packetCodec);
			PayloadTypeRegistry.playS2C().register(S2CStatueChunkPayload.id, S2CStatueChunkPayload.packetCodec);

			ServerPlayNetworking.registerGlobalReceiver(C2SStatueChunkPayload.id, (payload, context) -> context.server().execute(() -> {
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
				ServerPlayNetworking.send(player, new S2CStatuePayload(statueData));
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
		ServerPlayNetworking.send(player, new S2CStatueChunkPayload(statueData));
	}

	public static Identifier idOf(String path) {
		return Identifier.of(CommonData.id, path);
	}

	static {
		c2sStatueChunk = idOf("c2s_statue_chunk");
		s2cStatue = idOf("s2c_statue");
		s2cStatueChunk = idOf("s2c_statue_chunk");
	}
}
