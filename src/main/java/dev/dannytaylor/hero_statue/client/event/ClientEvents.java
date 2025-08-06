/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.event;

import dev.dannytaylor.hero_statue.common.event.CommonEvents;
import dev.dannytaylor.hero_statue.common.network.payloads.IdBooleanPayload;
import dev.dannytaylor.hero_statue.common.network.payloads.RequestPayload;
import dev.dannytaylor.hero_statue.common.network.payloads.S2CUpdateChunkStatuesPayload;
import dev.dannytaylor.hero_statue.common.network.payloads.S2CUpdateStatuePayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientEvents {
	public static class Network {
		public static final CommonEvents.Event<ClientPlayNetworking.PlayPayloadHandler<S2CUpdateChunkStatuesPayload>> updateChunkStatues;
		public static final CommonEvents.Event<ClientPlayNetworking.PlayPayloadHandler<S2CUpdateStatuePayload>> updateStatue;
		public static final CommonEvents.Event<ClientPlayNetworking.PlayPayloadHandler<IdBooleanPayload>> idBoolean;
		public static final CommonEvents.Event<ClientPlayNetworking.PlayPayloadHandler<RequestPayload>> request;
		static {
			updateChunkStatues = new CommonEvents.Event<>();
			updateStatue = new CommonEvents.Event<>();
			idBoolean = new CommonEvents.Event<>();
			request = new CommonEvents.Event<>();
		}
	}
	public static void bootstrap() {}
}
