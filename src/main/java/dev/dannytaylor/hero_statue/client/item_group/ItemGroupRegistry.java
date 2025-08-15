/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.item_group;

import dev.dannytaylor.hero_statue.common.item.ItemRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class ItemGroupRegistry {
	public static void bootstrap() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
			content.add(ItemRegistry.heroStatue);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(content -> {
			content.add(ItemRegistry.heroStatueVanity);
		});
	}
}
