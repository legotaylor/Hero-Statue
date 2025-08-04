package dev.dannytaylor.hero_statue.common.network.payloads;

import dev.dannytaylor.hero_statue.common.block.StatueData;
import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

import java.util.List;

public record S2CStatueChunkPayload(List<StatueData> statueData) implements CustomPayload {
	public static final Id<S2CStatueChunkPayload> id = new Id<>(CommonNetwork.s2cStatueChunk);
	public static final PacketCodec<RegistryByteBuf, S2CStatueChunkPayload> packetCodec = PacketCodec.tuple(StatueData.packetCodec.collect(PacketCodecs.toList()), S2CStatueChunkPayload::statueData, S2CStatueChunkPayload::new);
	@Override
	public Id<? extends CustomPayload> getId() {
		return id;
	}
}
