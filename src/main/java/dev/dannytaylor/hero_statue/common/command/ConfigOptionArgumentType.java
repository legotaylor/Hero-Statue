/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.dannytaylor.hero_statue.common.config.ConfigOption;

import java.util.concurrent.CompletableFuture;

public class ConfigOptionArgumentType implements ArgumentType<ConfigOption> {
	private ConfigOptionArgumentType() {
	}

	public static ConfigOptionArgumentType configOption() {
		return new ConfigOptionArgumentType();
	}

	public static ConfigOption getConfigOption(final CommandContext<?> context, final String name) {
		return context.getArgument(name, ConfigOption.class);
	}

	@Override
	public ConfigOption parse(final StringReader reader) throws CommandSyntaxException {
		return ConfigOption.fromId(reader.readString()).orElseThrow();
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
		for (ConfigOption configOption : ConfigOption.values()) {
			if (configOption.asString().startsWith(builder.getRemainingLowerCase())) {
				builder.suggest(configOption.asString());
			}
		}
		return builder.buildFuture();
	}
}
