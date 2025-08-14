/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client;

import dev.dannytaylor.hero_statue.client.block.BlockRegistryClient;
import dev.dannytaylor.hero_statue.client.command.ClientCommandRegistry;
import dev.dannytaylor.hero_statue.client.config.HeroStatueClientConfig;
import dev.dannytaylor.hero_statue.client.event.ClientEvents;
import dev.dannytaylor.hero_statue.client.item_group.ItemGroupRegistry;
import dev.dannytaylor.hero_statue.client.network.ClientNetwork;
import dev.dannytaylor.hero_statue.client.render.Render;
import net.fabricmc.api.ClientModInitializer;

public class HeroStatueClient implements ClientModInitializer {
	public void onInitializeClient() {
		HeroStatueClientConfig.bootstrap();
		ClientCommandRegistry.bootstrap();
		ClientEvents.bootstrap();
		Render.bootstrap();
		BlockRegistryClient.bootstrap();
		ItemGroupRegistry.bootstrap();
		ClientNetwork.bootstrap();
	}
}
