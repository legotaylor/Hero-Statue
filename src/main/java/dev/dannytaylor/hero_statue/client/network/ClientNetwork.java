/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.network;

import dev.dannytaylor.hero_statue.client.config.ClientConfigHelper;
import dev.dannytaylor.hero_statue.client.config.HeroStatueClientConfig;
import dev.dannytaylor.hero_statue.client.event.ClientEvents;
import dev.dannytaylor.hero_statue.client.gamerule.GameruleCache;
import dev.dannytaylor.hero_statue.common.block.StatueBlockEntity;
import dev.dannytaylor.hero_statue.common.block.StatueData;
import dev.dannytaylor.hero_statue.common.config.StatueRenderType;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import dev.dannytaylor.hero_statue.common.network.payloads.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;

public class ClientNetwork {
	private static void registerEvents() {
		try {
			ClientEvents.ClientNetwork.updateStatue.register(CommonNetwork.s2cStatue, (payload, context) -> {
				World world = context.client().world;
				if (world != null) {
					if (world.getBlockEntity(payload.statueData().blockPos()) instanceof StatueBlockEntity statueBlockEntity) statueBlockEntity.setStack(payload.statueData().stack());
				}
			});
			ClientEvents.ClientNetwork.updateChunkStatues.register(CommonNetwork.s2cStatueChunk, (payload, context) -> {
				World world = context.client().world;
				if (world != null) {
					for (StatueData data : payload.statueData()) {
						if (world.getBlockEntity(data.blockPos()) instanceof StatueBlockEntity statueBlockEntity) statueBlockEntity.setStack(data.stack());
					}
				}
			});
			ClientEvents.ClientNetwork.idBoolean.register(CommonNetwork.bidIdBoolean, (payload, context) -> {
				if (payload.identifier().equals(CommonNetwork.gamerule_allowPlayerChangeStatuePose)) {
					GameruleCache.allowPlayerChangeStatuePose = payload.value();
				} else {
					if (HeroStatueClientConfig.instance.allowNetworkConfigUpdates.value()) {
						if (payload.identifier().equals(CommonNetwork.config_updateRenderEyes)) {
							ClientConfigHelper.setRenderEyes(payload.value());
						} else if (payload.identifier().equals(CommonNetwork.config_updateRainbowMode)) {
							ClientConfigHelper.setRainbowMode(payload.value());
						} else if (payload.identifier().equals(CommonNetwork.config_updateUseConfigKeybindingAnywhere)) {
							ClientConfigHelper.setUseConfigKeybindingAnywhere(payload.value());
						}
					}
				}
			});
			ClientEvents.ClientNetwork.idStatueRenderType.register(CommonNetwork.bidIdStatueRenderType, (payload, context) -> {
				if (HeroStatueClientConfig.instance.allowNetworkConfigUpdates.value()) {
					if (payload.identifier().equals(CommonNetwork.config_updateStatueRenderType)) {
						ClientConfigHelper.setStatueRenderType(payload.value());
					}
				}
			});
		} catch (Exception error) {
			CommonData.logger.warn("Failed to register client network events: {}", error.getLocalizedMessage());
		}
	}

	private static void registerSenders() {
		try {
			ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
				sendC2SStatueChunk(chunk.getPos(), false);
			});
		} catch (Exception error) {
			CommonData.logger.warn("Failed to register client network senders: {}", error.getLocalizedMessage());
		}
	}

	private static void registerReceivers() {
		try {
			ClientPlayNetworking.registerGlobalReceiver(S2CUpdateStatuePayload.id, (payload, context) -> context.client().execute(() -> {
				ClientEvents.ClientNetwork.updateStatue.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
			ClientPlayNetworking.registerGlobalReceiver(S2CUpdateChunkStatuesPayload.id, (payload, context) -> context.client().execute(() -> {
				ClientEvents.ClientNetwork.updateChunkStatues.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
			ClientPlayNetworking.registerGlobalReceiver(IdBooleanPayload.id, (payload, context) -> context.client().execute(() -> {
				ClientEvents.ClientNetwork.idBoolean.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
			ClientPlayNetworking.registerGlobalReceiver(IdStatueRenderTypePayload.id, (payload, context) -> context.client().execute(() -> {
				ClientEvents.ClientNetwork.idStatueRenderType.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
			ClientPlayNetworking.registerGlobalReceiver(RequestPayload.id, (payload, context) -> context.client().execute(() -> {
				ClientEvents.ClientNetwork.request.registry.forEach((identifier, handler) -> handler.receive(payload, context));
			}));
		} catch (Exception error) {
			CommonData.logger.warn("Failed to register client network receivers: {}", error.getLocalizedMessage());
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

	public static void sendIdStatueRenderType(Identifier identifier, StatueRenderType value) {
		ClientPlayNetworking.send(new IdStatueRenderTypePayload(identifier, value));
	}

	public static void sendRequest(Identifier identifier) {
		ClientPlayNetworking.send(new RequestPayload(identifier));
	}
}
