package dev.dannytaylor.hero_statue.common;

import dev.dannytaylor.hero_statue.common.block.BlockEntityRegistry;
import dev.dannytaylor.hero_statue.common.block.BlockRegistry;
import dev.dannytaylor.hero_statue.common.item.ItemRegistry;
import net.fabricmc.api.ModInitializer;

public class HeroStatue implements ModInitializer {
	@Override
	public void onInitialize() {
		BlockRegistry.bootstrap();
		BlockEntityRegistry.bootstrap();
		ItemRegistry.bootstrap();
	}
}
