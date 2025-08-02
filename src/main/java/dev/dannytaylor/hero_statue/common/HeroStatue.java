package dev.dannytaylor.hero_statue.common;

import dev.dannytaylor.hero_statue.common.data.HeroStatueData;
import net.fabricmc.api.ModInitializer;

public class HeroStatue implements ModInitializer {
	@Override
	public void onInitialize() {
		HeroStatueData.logger.info("What if the real hero was the statues we met along the way?");
	}
}
