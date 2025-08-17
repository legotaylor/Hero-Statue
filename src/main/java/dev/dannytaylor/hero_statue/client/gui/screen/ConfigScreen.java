/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.gui.screen;

import dev.dannytaylor.hero_statue.client.config.ClientConfigHelper;
import dev.dannytaylor.hero_statue.client.config.HeroStatueClientConfig;
import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.client.gui.widget.ConfigWidget;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends HeroStatueScreen {
	public ConfigWidget config;

	public ConfigScreen(Screen parent) {
		super(parent);
	}

	public ConfigScreen(Screen parent, double scrollY) {
		super(parent, scrollY);
	}

	public void initBody() {
		this.config = new ConfigWidget(ClientData.minecraft, this.width, this.layout.getContentHeight(), this.layout.getHeaderHeight(), 24, getWidgets(), this.scrollY);
		this.layout.addBody(this.config);
	}

	public List<ClickableWidget> getWidgets() {
		List<ClickableWidget> options = new ArrayList<>();
		options.add(ButtonWidget.builder(Text.translatable(CommonData.id + ".info").append(Text.translatable(CommonData.id + ".config.more")), (button) -> ClientData.minecraft.setScreen(new InfoScreen(getRefreshScreen()))).build());
		options.add(ButtonWidget.builder(Text.translatable(CommonData.id + ".config_title", Text.translatable(CommonData.id + ".render_type"), Text.translatable(CommonData.id + ".render_type." + HeroStatueClientConfig.instance.renderType.value().asString())), (button) -> {
			ClientConfigHelper.cycleRenderType();
			button.setMessage(Text.translatable(CommonData.id + ".config_title", Text.translatable(CommonData.id + ".render_type"), Text.translatable(CommonData.id + ".render_type." + HeroStatueClientConfig.instance.renderType.value().asString())));
			button.setTooltip(Tooltip.of(Text.translatable(CommonData.id + ".render_type.hover", Text.translatable(CommonData.id + ".render_type." + HeroStatueClientConfig.instance.renderType.value().asString() + ".hover"))));
		}).tooltip(Tooltip.of(Text.translatable(CommonData.id + ".render_type.hover", Text.translatable(CommonData.id + ".render_type." + HeroStatueClientConfig.instance.renderType.value().asString() + ".hover")))).build());
		options.add(ButtonWidget.builder(Text.translatable(CommonData.id + ".config_title", Text.translatable(CommonData.id + ".eye_overlay"), HeroStatueClientConfig.instance.renderEyes.value()), (button) -> {
			ClientConfigHelper.toggleRenderEyes();
			button.setMessage(Text.translatable(CommonData.id + ".config_title", Text.translatable(CommonData.id + ".eye_overlay"), HeroStatueClientConfig.instance.renderEyes.value()));
		}).tooltip(Tooltip.of(Text.translatable(CommonData.id + ".eye_overlay.hover"))).build());
		options.add(ButtonWidget.builder(Text.translatable(CommonData.id + ".config_title", Text.translatable(CommonData.id + ".rainbow_mode"), HeroStatueClientConfig.instance.rainbowMode.value()), (button) -> {
			ClientConfigHelper.toggleRainbowMode();
			button.setMessage(Text.translatable(CommonData.id + ".config_title", Text.translatable(CommonData.id + ".rainbow_mode"), HeroStatueClientConfig.instance.rainbowMode.value()));
		}).tooltip(Tooltip.of(Text.translatable(CommonData.id + ".rainbow_mode.hover"))).build());
		options.add(ButtonWidget.builder(Text.translatable(CommonData.id + ".config_title", Text.translatable(CommonData.id + ".allow_network_config_updates"), HeroStatueClientConfig.instance.allowNetworkConfigUpdates.value()), (button) -> {
			ClientConfigHelper.toggleAllowNetworkConfigUpdates();
			button.setMessage(Text.translatable(CommonData.id + ".config_title", Text.translatable(CommonData.id + ".allow_network_config_updates"), HeroStatueClientConfig.instance.allowNetworkConfigUpdates.value()));
		}).tooltip(Tooltip.of(Text.translatable(CommonData.id + ".allow_network_config_updates.hover"))).build());
		return options;
	}

	public Screen getRefreshScreen() {
		return new ConfigScreen(this.parent, this.config != null ? this.config.getScrollY() : scrollY);
	}
}
