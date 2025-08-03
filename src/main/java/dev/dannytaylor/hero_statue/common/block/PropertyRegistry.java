package dev.dannytaylor.hero_statue.common.block;

import net.minecraft.state.property.IntProperty;

public class PropertyRegistry {
	public static final IntProperty pose;
	static {
		pose = IntProperty.of("pose", 0, 14);
	}
}
