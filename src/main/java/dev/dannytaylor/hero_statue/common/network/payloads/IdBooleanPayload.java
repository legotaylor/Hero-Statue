package dev.dannytaylor.hero_statue.common.network.payloads;

import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record IdBooleanPayload(Identifier identifier, boolean value) implements CustomPayload {
	public static final Id<IdBooleanPayload> id = new Id<>(CommonNetwork.bidIdBoolean);
	public static final PacketCodec<RegistryByteBuf, IdBooleanPayload> packetCodec = PacketCodec.tuple(Identifier.PACKET_CODEC, IdBooleanPayload::identifier, PacketCodecs.BOOLEAN, IdBooleanPayload::value, IdBooleanPayload::new);
	@Override
	public Id<? extends CustomPayload> getId() {
		return id;
	}
}
