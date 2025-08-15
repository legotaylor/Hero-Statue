/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.block;

import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class PropertyRegistry {
	public static final IntProperty pose;
	public static final BooleanProperty rainbow;
	public static String of(String path) {
		return CommonData.idOf(path).toUnderscoreSeparatedString().replace("-", "_");
	}
	static {
		pose = IntProperty.of(of("pose"), 0, 14);
		rainbow = BooleanProperty.of(of("rainbow"));
	}
}
