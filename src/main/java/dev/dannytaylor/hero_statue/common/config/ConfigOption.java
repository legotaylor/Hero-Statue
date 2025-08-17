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

public enum ConfigOption implements StringIdentifiable {
	statueRenderType("statue_render_type"),
	renderEyes("render_eyes"),
	rainbowMode("rainbow_mode");

	public static final Codec<ConfigOption> codec;
	public static final PacketCodec<ByteBuf, ConfigOption> packetCodec;
	private final String id;

	ConfigOption(String id) {
		this.id = id;
	}

	public String asString() {
		return this.id;
	}

	public static Optional<ConfigOption> fromId(String id) {
		for (ConfigOption configOption : Arrays.stream(ConfigOption.values()).toList()) {
			if (configOption.id.equals(id)) return Optional.of(configOption);
		}
		return Optional.empty();
	}

	static {
		codec = StringIdentifiable.createCodec(ConfigOption::values);
		packetCodec = new PacketCodec<>() {
			public ConfigOption decode(ByteBuf byteBuf) {
				return valueOf(StringEncoding.decode(byteBuf, 32767));
			}
			public void encode(ByteBuf byteBuf, ConfigOption ConfigOption) {
				StringEncoding.encode(byteBuf, ConfigOption.asString(), 32767);
			}
		};
	}
}
