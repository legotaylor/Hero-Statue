package dev.dannytaylor.hero_statue.client.network;

import dev.dannytaylor.hero_statue.client.event.ClientEvents;
import dev.dannytaylor.hero_statue.client.gamerule.GameruleCache;
import dev.dannytaylor.hero_statue.common.block.StatueBlockEntity;
import dev.dannytaylor.hero_statue.common.block.StatueData;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import dev.dannytaylor.hero_statue.common.network.payloads.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class ClientNetwork {
	private static void registerEvents() {
		try {
			ClientEvents.Network.updateStatue.register(CommonNetwork.s2cStatue, (payload, context) -> {
				World world = context.client().world;
				if (world != null) {
					if (world.getBlockEntity(payload.statueData().blockPos()) instanceof StatueBlockEntity statueBlockEntity) statueBlockEntity.setStack(payload.statueData().stack());
				}
			});
			ClientEvents.Network.updateChunkStatues.register(CommonNetwork.s2cStatueChunk, (payload, context) -> {
				World world = context.client().world;
				if (world != null) {
					for (StatueData data : payload.statueData()) {
						if (world.getBlockEntity(data.blockPos()) instanceof StatueBlockEntity statueBlockEntity) statueBlockEntity.setStack(data.stack());
					}
				}
			});
			ClientEvents.Network.idBoolean.register(CommonNetwork.bidIdBoolean, (payload, context) -> {
				if (payload.identifier().equals(CommonNetwork.gamerule_allowPlayerChangeStatuePose)) {
					GameruleCache.allowPlayerChangeStatuePose = payload.value();
				}
			});
		} catch (Exception error) {
			CommonData.logger.warn("Failed to register client network events: {}", error.getLocalizedMessage());
		}
	}

	private static void registerReceivers() {
		try {
			ClientPlayNetworking.registerGlobalReceiver(S2CUpdateStatuePayload.id, (payload, context) -> context.client().execute(() -> {
				ClientEvents.Network.updateStatue.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
			ClientPlayNetworking.registerGlobalReceiver(S2CUpdateChunkStatuesPayload.id, (payload, context) -> context.client().execute(() -> {
				ClientEvents.Network.updateChunkStatues.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
			ClientPlayNetworking.registerGlobalReceiver(IdBooleanPayload.id, (payload, context) -> context.client().execute(() -> {
				ClientEvents.Network.idBoolean.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
			ClientPlayNetworking.registerGlobalReceiver(RequestPayload.id, (payload, context) -> context.client().execute(() -> {
				ClientEvents.Network.request.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
		} catch (Exception error) {
			CommonData.logger.warn("Failed to register client network receivers: {}", error.getLocalizedMessage());
		}
	}

	private static void registerSenders() {
		try {
			// Client to Server
			PayloadTypeRegistry.playC2S().register(C2SUpdateChunkStatuesPayload.id, C2SUpdateChunkStatuesPayload.packetCodec);
			PayloadTypeRegistry.playC2S().register(C2SUpdateStatuePayload.id, C2SUpdateStatuePayload.packetCodec);

			// Events
			ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
				sendC2SStatueChunk(chunk.getPos(), false);
			});
		} catch (Exception error) {
			CommonData.logger.warn("Failed to register client network senders: {}", error.getLocalizedMessage());
		}
	}

	public static void bootstrap() {
		registerEvents();
		registerSenders();
		registerReceivers();
	}

	public static void sendC2SStatueChunk(ChunkPos chunkPos, boolean updateAll) {
		ClientPlayNetworking.send(new C2SUpdateChunkStatuesPayload(chunkPos, updateAll));
	}

	public static void sendIdBoolean(Identifier identifier, Boolean value) {
		ClientPlayNetworking.send(new IdBooleanPayload(identifier, value));
	}

	public static void sendRequest(Identifier identifier) {
		ClientPlayNetworking.send(new RequestPayload(identifier));
	}
}
