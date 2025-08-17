/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.network.payloads;

import dev.dannytaylor.hero_statue.common.config.StatueRenderType;
import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record IdStatueRenderTypePayload(Identifier identifier, StatueRenderType value) implements CustomPayload {
	public static final Id<IdStatueRenderTypePayload> id = new Id<>(CommonNetwork.bidIdStatueRenderType);
	public static final PacketCodec<RegistryByteBuf, IdStatueRenderTypePayload> packetCodec = PacketCodec.tuple(Identifier.PACKET_CODEC, IdStatueRenderTypePayload::identifier, StatueRenderType.packetCodec, IdStatueRenderTypePayload::value, IdStatueRenderTypePayload::new);
	@Override
	public Id<? extends CustomPayload> getId() {
		return id;
	}
}
