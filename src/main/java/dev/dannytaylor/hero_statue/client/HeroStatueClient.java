package dev.dannytaylor.hero_statue.client;

import dev.dannytaylor.hero_statue.client.block.BlockRegistryClient;
import dev.dannytaylor.hero_statue.client.item_group.ItemGroupRegistry;
import dev.dannytaylor.hero_statue.client.network.ClientNetwork;
import net.fabricmc.api.ClientModInitializer;

public class HeroStatueClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRegistryClient.bootstrap();
		ItemGroupRegistry.bootstrap();
		ClientNetwork.bootstrap();
	}
}
