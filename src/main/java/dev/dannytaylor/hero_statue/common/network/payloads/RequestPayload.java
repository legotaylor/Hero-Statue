/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.network.payloads;

import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record RequestPayload(Identifier identifier) implements CustomPayload {
	public static final Id<RequestPayload> id = new Id<>(CommonNetwork.bidRequest);
	public static final PacketCodec<RegistryByteBuf, RequestPayload> packetCodec = PacketCodec.tuple(Identifier.PACKET_CODEC, RequestPayload::identifier, RequestPayload::new);
	@Override
	public Id<? extends CustomPayload> getId() {
		return id;
	}
}
