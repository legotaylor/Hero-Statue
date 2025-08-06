/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.block;

import net.minecraft.state.property.IntProperty;

public class PropertyRegistry {
	public static final IntProperty pose;
	static {
		pose = IntProperty.of("pose", 0, 14);
	}
}
