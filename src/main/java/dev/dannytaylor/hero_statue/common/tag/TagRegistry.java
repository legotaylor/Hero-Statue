/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.tag;

import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class TagRegistry {
	public static final TagKey<Block> statueNonSolidPlaceable;
	public static TagKey<Block> ofBlock(Identifier id) {
		return TagKey.of(RegistryKeys.BLOCK, id);
	}
	public static TagKey<Block> ofBlock(String id) {
		return ofBlock(CommonData.idOf(id));
	}
	static {
		statueNonSolidPlaceable = ofBlock("statue_non_solid_placeable");
	}
}
