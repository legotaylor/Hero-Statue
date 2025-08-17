/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.command;

import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;

public class CommandRegistry {
	public static void bootstrap() {
		registerCommands();
		registerArguments();
	}

	private static void register(CommandRegistrationCallback command) {
		CommandRegistrationCallback.EVENT.register(command);
	}

	private static void registerCommands() {
		register(RequestNetworkConfigUpdateCommand.register());
	}

	private static void registerArguments() {
		ArgumentTypeRegistry.registerArgumentType(CommonData.idOf("config_option"), ConfigOptionArgumentType.class, ConstantArgumentSerializer.of(ConfigOptionArgumentType::configOption));
		ArgumentTypeRegistry.registerArgumentType(CommonData.idOf("config_value"), ConfigValueArgumentType.class, ConstantArgumentSerializer.of(ConfigValueArgumentType::configValue));
	}
}
