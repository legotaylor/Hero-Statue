package dev.dannytaylor.hero_statue.common.network.payloads;

import dev.dannytaylor.hero_statue.common.block.StatueData;
import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record S2CStatuePayload(StatueData statueData) implements CustomPayload {
	public static final Id<S2CStatuePayload> id = new Id<>(CommonNetwork.s2cStatue);
	public static final PacketCodec<RegistryByteBuf, S2CStatuePayload> packetCodec = PacketCodec.tuple(StatueData.packetCodec, S2CStatuePayload::statueData, S2CStatuePayload::new);
	@Override
	public Id<? extends CustomPayload> getId() {
		return id;
	}
}
