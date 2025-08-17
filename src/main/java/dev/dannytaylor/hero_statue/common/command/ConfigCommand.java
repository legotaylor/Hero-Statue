package dev.dannytaylor.hero_statue.common.command;

import com.mojang.datafixers.util.Either;
import dev.dannytaylor.hero_statue.common.config.ConfigOption;
import dev.dannytaylor.hero_statue.common.config.StatueRenderType;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

public class ConfigCommand {
	public static CommandRegistrationCallback register() {
		return (dispatcher, registryAccess, environment) -> {
			dispatcher.register((CommandManager.literal(CommonData.idOf("config").toString())
				.requires(CommandManager.requirePermissionLevel(2)))
				.then(CommandManager.argument("targets", EntityArgumentType.players())
					.then(CommandManager.argument("option", ConfigOptionArgumentType.configOption()).then(CommandManager.argument("value", ConfigValueArgumentType.configValue()).executes(context -> {
						Collection<? extends Entity> targets = EntityArgumentType.getEntities(context, "targets");
						Either<StatueRenderType, Boolean> value = ConfigValueArgumentType.valueOf(ConfigValueArgumentType.getConfigValue(context, "value"));
						int retVal = 1;
						ConfigOption option = ConfigOptionArgumentType.getConfigOption(context, "option");
						if (value != null) {
							switch (option) {
								case statueRenderType -> {
									if (value.left().isEmpty() || value.right().isPresent()) {
										retVal = 0;
									} else {
										value.ifLeft((statueRenderType) -> {
											for (Entity entity : targets) {
												if (entity instanceof ServerPlayerEntity player) {
													CommonNetwork.sendUpdateConfig(player, ConfigOption.statueRenderType, statueRenderType);
												}
											}
										});
									}
								}
								case renderEyes -> {
									if (value.left().isPresent() || value.right().isEmpty()) {
										retVal = 0;
									} else {
										value.ifRight((bool) -> {
											for (Entity entity : targets) {
												if (entity instanceof ServerPlayerEntity player) {
													CommonNetwork.sendUpdateConfig(player, ConfigOption.renderEyes, bool);
												}
											}
										});
									}
								}
								case rainbowMode -> {
									if (value.left().isPresent() || value.right().isEmpty()) {
										retVal = 0;
									} else {
										value.ifRight((bool) -> {
											for (Entity entity : targets) {
												if (entity instanceof ServerPlayerEntity player) {
													CommonNetwork.sendUpdateConfig(player, ConfigOption.rainbowMode, bool);
												}
											}
										});
									}
								}
								default -> retVal = 0;
							}
						} else {
							retVal = 0;
						}
						if (retVal == 0) context.getSource().sendFeedback(() -> Text.translatable("chat." + CommonData.id + ".update_config.fail" + (targets.size() > 1 ? ".multiple" : ""), Text.translatable("option." + CommonData.id + "." + option.asString()), (targets.size() > 1 ? targets.size() : targets.stream().toList().getFirst().getDisplayName()), value != null ? value.toString() : "null"), true);
						else {
							context.getSource().sendFeedback(() -> Text.translatable("chat." + CommonData.id + ".update_config.success" + (targets.size() > 1 ? ".multiple" : ""), Text.translatable("option." + CommonData.id + "." + option.asString()), (targets.size() > 1 ? targets.size() : targets.stream().toList().getFirst().getDisplayName())), true);
						}
						return retVal;
					})))
				)
			);
		};
	}
}
