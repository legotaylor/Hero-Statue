/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.gui.screen;

import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ThreePartsLayoutWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class HeroStatueScreen extends Screen {
	public final Screen parent;
	public final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
	public final double scrollY;

	public HeroStatueScreen(Screen parent) {
		this(parent, 0);
	}

	public HeroStatueScreen(Screen parent, double scrollY) {
		this(null, parent, scrollY);
	}

	public HeroStatueScreen(String id, Screen parent) {
		this(id, parent, 0);
	}

	public HeroStatueScreen(String id, Screen parent, double scrollY) {
		super(id != null ? getTitle(getSubtitle(id)) : getName(""));
		this.parent = parent;
		this.scrollY = scrollY;
	}

	public void init() {
		super.init();
		this.initHeader();
		this.initBody();
		this.initFooter();
		this.layout.forEachChild(this::addDrawableChild);
		refreshWidgetPositions();
	}

	public void initHeader() {
		this.layout.addHeader(this.title, this.textRenderer);
	}

	public void initBody() {
	}

	public void initFooter() {
		this.layout.addFooter(ButtonWidget.builder(ScreenTexts.DONE, (button) -> this.close()).width(200).build());
	}

	public void close() {
		ClientData.minecraft.setScreen(this.parent);
	}

	public void refreshWidgetPositions() {
		this.layout.refreshPositions();
	}

	public static Text getName(String id) {
		return Text.translatable(CommonData.id + ".title");
	}
	
	public static Text getTitle(Text title) {
		return Text.translatable(CommonData.id + ".config_title", getName(""), title);
	}

	public static Text getSubtitle(String id) {
		return Text.translatable(CommonData.id + "." + id);
	}

	public void resize(MinecraftClient client, int width, int height) {
		super.resize(client, width, height);
		client.setScreen(getRefreshScreen());
	}

	public Screen getRefreshScreen() {
		return null;
	}
}
