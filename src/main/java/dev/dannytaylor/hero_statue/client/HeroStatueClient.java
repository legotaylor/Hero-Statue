package dev.dannytaylor.hero_statue.client;

import dev.dannytaylor.hero_statue.common.data.HeroStatueData;
import net.fabricmc.api.ClientModInitializer;

public class HeroStatueClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HeroStatueData.logger.info("Meowth, that's right!");
	}
}
