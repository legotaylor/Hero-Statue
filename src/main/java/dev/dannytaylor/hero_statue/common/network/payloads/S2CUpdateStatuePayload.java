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
import net.minecraft.network.packet.CustomPayload;

public record S2CUpdateStatuePayload(StatueData statueData) implements CustomPayload {
	public static final Id<S2CUpdateStatuePayload> id = new Id<>(CommonNetwork.s2cStatue);
	public static final PacketCodec<RegistryByteBuf, S2CUpdateStatuePayload> packetCodec = PacketCodec.tuple(StatueData.packetCodec, S2CUpdateStatuePayload::statueData, S2CUpdateStatuePayload::new);
	@Override
	public Id<? extends CustomPayload> getId() {
		return id;
	}
}
