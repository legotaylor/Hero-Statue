package dev.dannytaylor.hero_statue.common.network.payloads;

import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.ChunkPos;

public record C2SStatueChunkPayload(ChunkPos chunkPos, boolean updateAll) implements CustomPayload {
	public static final Id<C2SStatueChunkPayload> id = new Id<>(CommonNetwork.c2sStatueChunk);
	public static final PacketCodec<RegistryByteBuf, C2SStatueChunkPayload> packetCodec = PacketCodec.tuple(ChunkPos.PACKET_CODEC, C2SStatueChunkPayload::chunkPos, PacketCodecs.BOOLEAN, C2SStatueChunkPayload::updateAll, C2SStatueChunkPayload::new);
	@Override
	public Id<? extends CustomPayload> getId() {
		return id;
	}
}
