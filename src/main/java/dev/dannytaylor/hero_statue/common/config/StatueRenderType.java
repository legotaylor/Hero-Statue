/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.config;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.encoding.StringEncoding;
import net.minecraft.util.StringIdentifiable;

import java.util.Arrays;
import java.util.Optional;

public enum StatueRenderType implements StringIdentifiable {
	FANCY("fancy"),
	FAST("fast"),
	FASTER("faster");

	public static final Codec<StatueRenderType> codec;
	public static final PacketCodec<ByteBuf, StatueRenderType> packetCodec;
	private final String id;

	StatueRenderType(String id) {
		this.id = id;
	}

	public String asString() {
		return this.id;
	}

	public StatueRenderType next() {
		StatueRenderType[] values = values();
		return values[(this.ordinal() + 1) % values.length];
	}

	public static Optional<StatueRenderType> fromId(String id) {
		for (StatueRenderType statueRenderType : Arrays.stream(StatueRenderType.values()).toList()) {
			if (statueRenderType.id.equals(id)) return Optional.of(statueRenderType);
		}
		return Optional.empty();
	}

	static {
		codec = StringIdentifiable.createCodec(StatueRenderType::values);
		packetCodec = new PacketCodec<>() {
			public StatueRenderType decode(ByteBuf byteBuf) {
				return StatueRenderType.fromId(StringEncoding.decode(byteBuf, 32767)).orElseThrow();
			}
			public void encode(ByteBuf byteBuf, StatueRenderType statueRenderType) {
				StringEncoding.encode(byteBuf, statueRenderType.asString(), 32767);
			}
		};
	}
}
