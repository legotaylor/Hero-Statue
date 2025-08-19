/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.event;

import dev.dannytaylor.hero_statue.common.util.InputCallable;
import dev.dannytaylor.hero_statue.common.event.CommonEvents;
import dev.dannytaylor.hero_statue.common.network.payloads.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;

public class ClientEvents extends CommonEvents {
	public static class ClientNetwork {
		public static final Event<ClientPlayNetworking.PlayPayloadHandler<S2CUpdateChunkStatuesPayload>> updateChunkStatues;
		public static final Event<ClientPlayNetworking.PlayPayloadHandler<S2CUpdateStatuePayload>> updateStatue;
		public static final Event<ClientPlayNetworking.PlayPayloadHandler<IdBooleanPayload>> idBoolean;
		public static final Event<ClientPlayNetworking.PlayPayloadHandler<IdStatueRenderTypePayload>> idStatueRenderType;
		public static final Event<ClientPlayNetworking.PlayPayloadHandler<RequestPayload>> request;
		static {
			updateChunkStatues = new Event<>();
			updateStatue = new Event<>();
			idBoolean = new Event<>();
			idStatueRenderType = new Event<>();
			request = new Event<>();
		}
	}

	public static class Keybinding {
		public static final Event<InputCallable<Boolean, MinecraftClient>> isTyping;
		public static final Event<InputCallable<Boolean, MinecraftClient>> preventConfigKeybinding;
		static {
			isTyping = new Event<>();
			preventConfigKeybinding = new Event<>();
		}
	}

	public static void bootstrap() {}
}
