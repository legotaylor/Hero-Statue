/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.command;

import dev.dannytaylor.hero_statue.client.config.HeroStatueClientConfig;
import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class ClientCommandRegistry {
	public static void bootstrap() {
		register((dispatcher, registryAccess) -> {
			dispatcher.register(literal(CommonData.idOf("rainbow_mode").toString())
				.executes(context -> {
					HeroStatueClientConfig.instance.rainbowMode.setValue(!HeroStatueClientConfig.instance.rainbowMode.value(), true);
					if (ClientData.minecraft.player != null) ClientData.minecraft.player.sendMessage(Text.translatable("hero-statue.rainbow_mode.message", Text.translatable("hero-statue.rainbow_mode"), HeroStatueClientConfig.instance.rainbowMode.value()), false);
					return 1;
				}));
		});
	}
	private static void register(ClientCommandRegistrationCallback command) {
		ClientCommandRegistrationCallback.EVENT.register(command);
	}
}
