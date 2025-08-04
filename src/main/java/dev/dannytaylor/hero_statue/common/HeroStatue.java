package dev.dannytaylor.hero_statue.common;

import dev.dannytaylor.hero_statue.common.block.BlockRegistry;
import dev.dannytaylor.hero_statue.common.item.ItemRegistry;
import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import dev.dannytaylor.hero_statue.common.sound.SoundRegistry;
import net.fabricmc.api.ModInitializer;

public class HeroStatue implements ModInitializer {
	@Override
	public void onInitialize() {
		SoundRegistry.bootstrap();
		CommonNetwork.bootstrap();
		BlockRegistry.bootstrap();
		ItemRegistry.bootstrap();
	}
}
