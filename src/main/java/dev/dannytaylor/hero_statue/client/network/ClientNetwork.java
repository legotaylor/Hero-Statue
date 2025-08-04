package dev.dannytaylor.hero_statue.client.network;

import dev.dannytaylor.hero_statue.common.block.StatueBlockEntity;
import dev.dannytaylor.hero_statue.common.block.StatueData;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import dev.dannytaylor.hero_statue.common.network.payloads.C2SUpdateChunkStatuesPayload;
import dev.dannytaylor.hero_statue.common.network.payloads.S2CUpdateChunkStatuesPayload;
import dev.dannytaylor.hero_statue.common.network.payloads.S2CUpdateStatuePayload;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class ClientNetwork {
	public static void bootstrap() {
		try {
			ClientPlayNetworking.registerGlobalReceiver(S2CUpdateStatuePayload.id, (payload, context) -> context.client().execute(() -> {
				World world = context.client().world;
				if (world != null) {
					if (world.getBlockEntity(payload.statueData().blockPos()) instanceof StatueBlockEntity statueBlockEntity) statueBlockEntity.setStack(payload.statueData().stack());
				}
			}));

			ClientPlayNetworking.registerGlobalReceiver(S2CUpdateChunkStatuesPayload.id, (payload, context) -> context.client().execute(() -> {
				World world = context.client().world;
				if (world != null) {
					for (StatueData data : payload.statueData()) {
						if (world.getBlockEntity(data.blockPos()) instanceof StatueBlockEntity statueBlockEntity) statueBlockEntity.setStack(data.stack());
					}
				}
			}));

			ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
				sendC2SStatueChunk(chunk.getPos(), false);
			});
		} catch (Exception error) {
			CommonData.logger.warn("Failed to init client network: {}", error.getLocalizedMessage());
		}
	}

	public static void sendC2SStatueChunk(ChunkPos chunkPos, boolean updateAll) {
		ClientPlayNetworking.send(new C2SUpdateChunkStatuesPayload(chunkPos, updateAll));
	}
}
