package dev.dannytaylor.hero_statue.common.network.payloads;

import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record C2SUpdateStatuePayload(BlockPos blockPos, boolean updateAll) implements CustomPayload {
	public static final Id<C2SUpdateStatuePayload> id = new Id<>(CommonNetwork.c2sStatue);
	public static final PacketCodec<RegistryByteBuf, C2SUpdateStatuePayload> packetCodec = PacketCodec.tuple(BlockPos.PACKET_CODEC, C2SUpdateStatuePayload::blockPos, PacketCodecs.BOOLEAN, C2SUpdateStatuePayload::updateAll, C2SUpdateStatuePayload::new);
	@Override
	public Id<? extends CustomPayload> getId() {
		return id;
	}
}
