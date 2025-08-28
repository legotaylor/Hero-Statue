/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.config;

import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.common.config.StatueRenderType;

public class ClientConfigHelper {
	public static void cycleRenderType() {
		setStatueRenderType(HeroStatueClientConfig.instance.renderType.value().next());
	}
	public static void setStatueRenderType(StatueRenderType renderType) {
		StatueRenderType currentRenderType = HeroStatueClientConfig.instance.renderType.value();
		HeroStatueClientConfig.instance.renderType.setValue(renderType);
		if (currentRenderType.equals(StatueRenderType.FASTER) || renderType.equals(StatueRenderType.FASTER)) ClientData.minecraft.worldRenderer.reload();
	}
	public static void toggleRenderEyes() {
		setRenderEyes(!HeroStatueClientConfig.instance.renderEyes.value());
	}
	public static void setRenderEyes(boolean renderEyes) {
		HeroStatueClientConfig.instance.renderEyes.setValue(renderEyes);
	}
	public static void toggleRainbowMode() {
		setRainbowMode(!HeroStatueClientConfig.instance.rainbowMode.value());
	}
	public static void setRainbowMode(boolean rainbowMode) {
		HeroStatueClientConfig.instance.rainbowMode.setValue(rainbowMode);
	}
	public static void toggleUseConfigKeybindingAnywhere() {
		setUseConfigKeybindingAnywhere(!HeroStatueClientConfig.instance.useConfigKeybindingAnywhere.value());
	}
	public static void setUseConfigKeybindingAnywhere(boolean configAnywhere) {
		HeroStatueClientConfig.instance.useConfigKeybindingAnywhere.setValue(configAnywhere);
	}
	public static void toggleAllowNetworkConfigUpdates() {
		setAllowNetworkConfigUpdates(!HeroStatueClientConfig.instance.allowNetworkConfigUpdates.value());
	}
	public static void setAllowNetworkConfigUpdates(boolean allowNetworkConfigUpdates) {
		HeroStatueClientConfig.instance.allowNetworkConfigUpdates.setValue(allowNetworkConfigUpdates);
	}
}
