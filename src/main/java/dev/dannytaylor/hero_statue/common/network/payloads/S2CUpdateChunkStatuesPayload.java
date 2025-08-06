/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.network.payloads;

import dev.dannytaylor.hero_statue.common.block.StatueData;
import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

import java.util.List;

public record S2CUpdateChunkStatuesPayload(List<StatueData> statueData) implements CustomPayload {
	public static final Id<S2CUpdateChunkStatuesPayload> id = new Id<>(CommonNetwork.s2cStatueChunk);
	public static final PacketCodec<RegistryByteBuf, S2CUpdateChunkStatuesPayload> packetCodec = PacketCodec.tuple(StatueData.packetCodec.collect(PacketCodecs.toList()), S2CUpdateChunkStatuesPayload::statueData, S2CUpdateChunkStatuesPayload::new);
	@Override
	public Id<? extends CustomPayload> getId() {
		return id;
	}
}
