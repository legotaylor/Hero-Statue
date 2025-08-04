package dev.dannytaylor.hero_statue.common.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.BlockPos;

public record StatueData(BlockPos blockPos, ItemStack stack) {
	public static final Codec<StatueData> codec;
	public static final net.minecraft.network.codec.PacketCodec<RegistryByteBuf, StatueData> packetCodec;
	static {
		codec = RecordCodecBuilder.create((instance) -> instance.group(BlockPos.CODEC.fieldOf("blockPos").forGetter(StatueData::blockPos), ItemStack.OPTIONAL_CODEC.fieldOf("stack").forGetter(StatueData::stack)).apply(instance, StatueData::new));
		packetCodec = PacketCodec.tuple(BlockPos.PACKET_CODEC, StatueData::blockPos, ItemStack.OPTIONAL_PACKET_CODEC, StatueData::stack, StatueData::new);
	}
}
