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
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.ChunkPos;

public record C2SUpdateChunkStatuesPayload(ChunkPos chunkPos, boolean updateAll) implements CustomPayload {
	public static final Id<C2SUpdateChunkStatuesPayload> id = new Id<>(CommonNetwork.c2sStatueChunk);
	public static final PacketCodec<RegistryByteBuf, C2SUpdateChunkStatuesPayload> packetCodec = PacketCodec.tuple(ChunkPos.PACKET_CODEC, C2SUpdateChunkStatuesPayload::chunkPos, PacketCodecs.BOOLEAN, C2SUpdateChunkStatuesPayload::updateAll, C2SUpdateChunkStatuesPayload::new);
	@Override
	public Id<? extends CustomPayload> getId() {
		return id;
	}
}
