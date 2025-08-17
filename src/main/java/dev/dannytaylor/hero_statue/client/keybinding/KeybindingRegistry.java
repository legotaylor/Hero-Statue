/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.keybinding;

import dev.dannytaylor.hero_statue.client.config.HeroStatueClientConfig;
import dev.dannytaylor.hero_statue.client.gui.screen.ConfigScreen;
import dev.dannytaylor.hero_statue.client.gui.screen.HeroStatueScreen;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.EditBox;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class KeybindingRegistry {
	public static final KeyBinding cycleRenderType;
	public static final KeyBinding openConfig;
	public static final KeyBinding toggleEyeOverlay;
	public static final KeyBinding toggleRainbowMode;
	private static final List<KeyBinding> isPressed = new ArrayList<>();

	public static void bootstrap() {
		ClientTickEvents.START_CLIENT_TICK.register(KeybindingRegistry::tick);
	}

	public static void tick(MinecraftClient client) {
		if (wasPressed(client, cycleRenderType, true)) HeroStatueClientConfig.instance.renderType.setValue(HeroStatueClientConfig.instance.renderType.value().next());
		if (wasPressed(client, openConfig) && !(client.currentScreen instanceof HeroStatueScreen) && !isTyping(client)) client.setScreen(new ConfigScreen(client.currentScreen));
		if (wasPressed(client, toggleEyeOverlay, true)) HeroStatueClientConfig.instance.renderEyes.setValue(!HeroStatueClientConfig.instance.renderEyes.value());
		if (wasPressed(client, toggleRainbowMode, true)) HeroStatueClientConfig.instance.rainbowMode.setValue(!HeroStatueClientConfig.instance.rainbowMode.value());
		checkIfStillPressed(client);
	}

	private static void checkIfStillPressed(MinecraftClient client) {
		List<KeyBinding> notPressed = new ArrayList<>();
		for (KeyBinding keyBinding : isPressed) {
			if (!isPressed(client, keyBinding, false)) notPressed.add(keyBinding);
		}
		isPressed.removeAll(notPressed);
	}

	private static boolean isPressed(MinecraftClient client, KeyBinding keyBinding) {
		return isPressed(client, keyBinding, false);
	}

	private static boolean isPressed(MinecraftClient client, KeyBinding keyBinding, boolean inGame) {
		if (inGame) return keyBinding.isPressed();
		boolean keyPressed = false;
		if (keyBinding != null) {
			InputUtil.Key key = KeyBindingHelper.getBoundKeyOf(keyBinding);
			if (key.getCode() != GLFW.GLFW_KEY_UNKNOWN) keyPressed = InputUtil.isKeyPressed(client.getWindow().getHandle(), key.getCode());
		}
		return keyPressed;
	}

	private static boolean wasPressed(MinecraftClient client, KeyBinding keyBinding) {
		return wasPressed(client, keyBinding, false);
	}

	private static boolean wasPressed(MinecraftClient client, KeyBinding keyBinding, boolean inGame) {
		if (inGame) keyBinding.wasPressed();
		boolean keyPressed = false;
		if (keyBinding != null) {
			if (isPressed(client, keyBinding) && !isPressed.contains(keyBinding)) {
				isPressed.add(keyBinding);
				keyPressed = true;
			}
		}
		return keyPressed;
	}

	public static boolean isTyping(MinecraftClient client) {
		Screen screen = client.currentScreen;
		boolean isTyping = false;
		if (screen != null) {
			for (Element element : screen.children().stream().filter(element -> element instanceof EditBox || element instanceof TextFieldWidget).toList()) {
				if (element.isFocused()) isTyping = true;
			}
		}
		return isTyping;
	}

	public static KeyBinding register(String namespace, String category, String key, int keyCode) {
		return KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + namespace + "." + key, InputUtil.Type.KEYSYM, keyCode, "key.categories." + namespace + "." + category));
	}

	static {
		cycleRenderType = register(CommonData.id, "config", "cycle_render_type", GLFW.GLFW_KEY_UNKNOWN);
		openConfig = register(CommonData.id, "config", "open_config", GLFW.GLFW_KEY_HOME);
		toggleEyeOverlay = register(CommonData.id, "config", "toggle_eye_overlay", GLFW.GLFW_KEY_UNKNOWN);
		toggleRainbowMode = register(CommonData.id, "config", "toggle_rainbow_mode", GLFW.GLFW_KEY_UNKNOWN);
	}
}
