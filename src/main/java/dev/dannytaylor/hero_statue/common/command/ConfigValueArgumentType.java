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
import com.mojang.datafixers.util.Either;
import dev.dannytaylor.hero_statue.common.config.StatueRenderType;
import dev.dannytaylor.hero_statue.common.data.CommonData;

import java.util.concurrent.CompletableFuture;

public class ConfigValueArgumentType implements ArgumentType<String> {
	private ConfigValueArgumentType() {
	}

	public static ConfigValueArgumentType configValue() {
		return new ConfigValueArgumentType();
	}

	public static String getConfigValue(final CommandContext<?> context, final String name) {
		return context.getArgument(name, String.class);
	}

	@Override
	public String parse(final StringReader reader) throws CommandSyntaxException {
		return reader.readString();
	}

	public static Either<StatueRenderType, Boolean> valueOf(String value) throws CommandSyntaxException {
		if (value.equalsIgnoreCase("true")) return Either.right(true);
		if (value.equalsIgnoreCase("false")) return Either.right(false);
		try {
			StatueRenderType type = StatueRenderType.fromId(value).orElseThrow();
			return Either.left(type);
		} catch (Exception exception) {
			CommonData.logger.error("Invalid config value: {}", exception.getLocalizedMessage());
		}
		return null;
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
		switch (ConfigOptionArgumentType.getConfigOption(context, "option")) {
			case statueRenderType -> {
				for (StatueRenderType statueRenderType : StatueRenderType.values()) {
					if (statueRenderType.asString().startsWith(builder.getRemainingLowerCase())) {
						builder.suggest(statueRenderType.asString());
					}
				}
			}
			default -> {
				if ("true".startsWith(builder.getRemainingLowerCase())) {
					builder.suggest("true");
				}
				if ("false".startsWith(builder.getRemainingLowerCase())) {
					builder.suggest("false");
				}
			}
		}
		return builder.buildFuture();
	}
}
