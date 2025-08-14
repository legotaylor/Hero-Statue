/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.gui.screen;

import dev.dannytaylor.hero_statue.client.config.HeroStatueClientConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class OverlaysScreen extends ConfigScreen {
	public OverlaysScreen(Screen parent) {
		this(parent, 0);
	}

	public OverlaysScreen(Screen parent, double scrollY) {
		super(Text.translatable("hero-statue.overlays"), parent, scrollY);
	}

	public List<ClickableWidget> getWidgets() {
		List<ClickableWidget> options = new ArrayList<>();
		options.add(ButtonWidget.builder(Text.translatable("hero-statue.overlays.eyes.enabled", HeroStatueClientConfig.instance.renderEyes.value()), (button) -> {
			HeroStatueClientConfig.instance.renderEyes.setValue(!HeroStatueClientConfig.instance.renderEyes.value());
			button.setMessage(Text.translatable("hero-statue.overlays.eyes.enabled", HeroStatueClientConfig.instance.renderEyes.value()));
		}).build());
		return options;
	}

	public Screen getRefreshScreen() {
		return new OverlaysScreen(this.parent, this.config != null ? this.config.getScrollY() : scrollY);
	}
}
