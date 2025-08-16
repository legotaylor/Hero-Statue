package dev.dannytaylor.hero_statue.client.gui.screen;

import dev.dannytaylor.hero_statue.client.config.HeroStatueClientConfig;
import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.client.gui.widget.ConfigWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends Screen {
	public final Screen parent;
	public final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
	public final double scrollY;
	public ConfigWidget config;

	public ConfigScreen(Screen parent) {
		this(parent, 0);
	}

	public ConfigScreen(Screen parent, double scrollY) {
		super(Text.translatable("hero-statue.title"));
		this.parent = parent;
		this.scrollY = scrollY;
	}

	public ConfigScreen(Text title, Screen parent) {
		this(title, parent, 0);
	}

	public ConfigScreen(Text title, Screen parent, double scrollY) {
		super(Text.translatable("hero-statue.config_title", Text.translatable("hero-statue.title"), title));
		this.parent = parent;
		this.scrollY = scrollY;
	}

	protected void init() {
		super.init();
		this.initHeader();
		this.initBody();
		this.initFooter();
		this.layout.forEachChild(this::addDrawableChild);
		refreshWidgetPositions();
	}

	protected void initHeader() {
		this.layout.addHeader(this.title, this.textRenderer);
	}

	protected void initBody() {
		this.config = new ConfigWidget(ClientData.minecraft, this.width, this.layout.getContentHeight(), this.layout.getHeaderHeight(), 24, getWidgets(), this.scrollY);
		this.layout.addBody(this.config);
	}

	public List<ClickableWidget> getWidgets() {
		List<ClickableWidget> options = new ArrayList<>();
		options.add(ButtonWidget.builder(Text.translatable("hero-statue.about").append(Text.translatable("hero-statue.config.more")), (button) -> ClientData.minecraft.setScreen(new InfoScreen(getRefreshScreen()))).build());
		options.add(ButtonWidget.builder(Text.translatable("hero-statue.config_title", Text.translatable("hero-statue.render_type"), Text.translatable("hero-statue.render_type." + HeroStatueClientConfig.instance.renderType.value().getId())), (button) -> {
			HeroStatueClientConfig.instance.renderType.setValue(HeroStatueClientConfig.instance.renderType.value().next());
			button.setMessage(Text.translatable("hero-statue.config_title", Text.translatable("hero-statue.render_type"), Text.translatable("hero-statue.render_type." + HeroStatueClientConfig.instance.renderType.value().getId())));
			button.setTooltip(Tooltip.of(Text.translatable("hero-statue.render_type.hover", Text.translatable("hero-statue.render_type." + HeroStatueClientConfig.instance.renderType.value().getId() + ".hover"))));
		}).tooltip(Tooltip.of(Text.translatable("hero-statue.render_type.hover", Text.translatable("hero-statue.render_type." + HeroStatueClientConfig.instance.renderType.value().getId() + ".hover")))).build());
		options.add(ButtonWidget.builder(Text.translatable("hero-statue.config_title", Text.translatable("hero-statue.eye_overlay"), HeroStatueClientConfig.instance.renderEyes.value()), (button) -> {
			HeroStatueClientConfig.instance.renderEyes.setValue(!HeroStatueClientConfig.instance.renderEyes.value());
			button.setMessage(Text.translatable("hero-statue.config_title", Text.translatable("hero-statue.eye_overlay"), HeroStatueClientConfig.instance.renderEyes.value()));
		}).tooltip(Tooltip.of(Text.translatable("hero-statue.eye_overlay.hover"))).build());
		options.add(ButtonWidget.builder(Text.translatable("hero-statue.config_title", Text.translatable("hero-statue.rainbow_mode"), HeroStatueClientConfig.instance.rainbowMode.value()), (button) -> {
			HeroStatueClientConfig.instance.rainbowMode.setValue(!HeroStatueClientConfig.instance.rainbowMode.value());
			button.setMessage(Text.translatable("hero-statue.config_title", Text.translatable("hero-statue.rainbow_mode"), HeroStatueClientConfig.instance.rainbowMode.value()));
		}).tooltip(Tooltip.of(Text.translatable("hero-statue.rainbow_mode.hover"))).build());
		return options;
	}

	protected void initFooter() {
		this.layout.addFooter(ButtonWidget.builder(ScreenTexts.DONE, (button) -> this.close()).width(200).build());
	}

	public void close() {
		HeroStatueClientConfig.instance.save();
		ClientData.minecraft.setScreen(this.parent);
	}

	protected void refreshWidgetPositions() {
		this.layout.refreshPositions();
	}

	public void resize(MinecraftClient client, int width, int height) {
		super.resize(client, width, height);
		client.setScreen(getRefreshScreen());
	}

	public Screen getRefreshScreen() {
		return new ConfigScreen(this.parent, this.config != null ? this.config.getScrollY() : scrollY);
	}
}
