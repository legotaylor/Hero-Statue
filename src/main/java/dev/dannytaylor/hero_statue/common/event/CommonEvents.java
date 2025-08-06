/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.event;

import dev.dannytaylor.hero_statue.common.network.payloads.C2SUpdateChunkStatuesPayload;
import dev.dannytaylor.hero_statue.common.network.payloads.C2SUpdateStatuePayload;
import dev.dannytaylor.hero_statue.common.network.payloads.IdBooleanPayload;
import dev.dannytaylor.hero_statue.common.network.payloads.RequestPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class CommonEvents {
	public static class GenericEvent<K, V> {
		public final Map<K, V> registry = new HashMap<>();
		public void register(K key, V value) {
			if (!registry.containsKey(key)) registry.put(key, value);
		}
		public V get(K key) {
			return registry.get(key);
		}
		public void modify(K key, V value) {
			registry.replace(key, value);
		}
		public void remove(K key) {
			registry.remove(key);
		}
	}
	public static class Event<T> extends GenericEvent<Identifier, T> {}
	public static class Network {
		public static final CommonEvents.Event<ServerPlayNetworking.PlayPayloadHandler<C2SUpdateChunkStatuesPayload>> updateChunkStatues;
		public static final CommonEvents.Event<ServerPlayNetworking.PlayPayloadHandler<C2SUpdateStatuePayload>> updateStatue;
		public static final CommonEvents.Event<ServerPlayNetworking.PlayPayloadHandler<IdBooleanPayload>> idBoolean;
		public static final CommonEvents.Event<ServerPlayNetworking.PlayPayloadHandler<RequestPayload>> request;
		static {
			updateChunkStatues = new CommonEvents.Event<>();
			updateStatue = new CommonEvents.Event<>();
			idBoolean = new CommonEvents.Event<>();
			request = new CommonEvents.Event<>();
		}
	}
	public static void bootstrap() {}
}
