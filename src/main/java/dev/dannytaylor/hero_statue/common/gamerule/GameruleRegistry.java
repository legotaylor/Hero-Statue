package dev.dannytaylor.hero_statue.common.gamerule;

import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class GameruleRegistry {
	public static final GameRules.Key<GameRules.BooleanRule> allowPlayerChangeStatuePose;
	public static final GameRules.Key<GameRules.BooleanRule> allowRedstoneChangeStatuePose;
	public static void bootstrap() {
	}
	private static String idOf(String name) {
		return CommonData.id + "$" + name;
	}
	static {
		allowPlayerChangeStatuePose = GameRuleRegistry.register(idOf("allowPlayerChangeStatuePose"), GameRules.Category.UPDATES, GameRuleFactory.createBooleanRule(false));
		allowRedstoneChangeStatuePose = GameRuleRegistry.register(idOf("allowRedstoneChangeStatuePose"), GameRules.Category.UPDATES, GameRuleFactory.createBooleanRule(true));
	}
}
