package dev.dannytaylor.hero_statue.client.gui.screen;

import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.client.gui.widget.InfoWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class InfoScreen extends Screen {
	private final Screen parent;
	public final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
	public final double scrollY;
	public InfoWidget info;

	public InfoScreen(Screen parent) {
		this(parent, 0);
	}

	public InfoScreen(Screen parent, double scrollY) {
		super(Text.translatable("hero-statue.title"));
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
		this.info = new InfoWidget(ClientData.minecraft, this.width, this.layout.getContentHeight(), this.layout.getHeaderHeight(), 11, this.scrollY);
		this.layout.addBody(this.info);
	}

	protected void initFooter() {
		this.layout.addFooter(ButtonWidget.builder(ScreenTexts.DONE, (button) -> this.close()).width(200).build());
	}

	public void close() {
		ClientData.minecraft.setScreen(this.parent);
	}

	public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);
	}

	@Override
	public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
		super.renderBackground(context, mouseX, mouseY, deltaTicks);
	}

	protected void refreshWidgetPositions() {
		this.layout.refreshPositions();
	}

	public void resize(MinecraftClient client, int width, int height) {
		super.resize(client, width, height);
		client.setScreen(new InfoScreen(this.parent, this.info != null ? this.info.getScrollY() : scrollY));
	}
}
