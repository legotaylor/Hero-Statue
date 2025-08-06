/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common;

import dev.dannytaylor.hero_statue.common.block.BlockRegistry;
import dev.dannytaylor.hero_statue.common.event.CommonEvents;
import dev.dannytaylor.hero_statue.common.gamerule.GameruleRegistry;
import dev.dannytaylor.hero_statue.common.item.ItemRegistry;
import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import dev.dannytaylor.hero_statue.common.sound.SoundRegistry;
import net.fabricmc.api.ModInitializer;

public class HeroStatue implements ModInitializer {
	public void onInitialize() {
		CommonEvents.bootstrap();
		GameruleRegistry.bootstrap();
		SoundRegistry.bootstrap();
		CommonNetwork.bootstrap();
		BlockRegistry.bootstrap();
		ItemRegistry.bootstrap();
	}
}
