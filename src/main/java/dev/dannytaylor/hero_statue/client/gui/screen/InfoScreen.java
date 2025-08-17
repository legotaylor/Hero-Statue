/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.gui.screen;

import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.client.gui.widget.InfoWidget;
import net.minecraft.client.gui.screen.Screen;

public class InfoScreen extends HeroStatueScreen {
	public InfoWidget info;

	public InfoScreen(Screen parent) {
		super("about", parent);
	}

	public InfoScreen(Screen parent, double scrollY) {
		super("about", parent, scrollY);
	}

	public void initBody() {
		this.info = new InfoWidget(ClientData.minecraft, this.width, this.layout.getContentHeight(), this.layout.getHeaderHeight(), 11, this.scrollY);
		this.layout.addBody(this.info);
	}

	public Screen getRefreshScreen() {
		return new InfoScreen(this.parent, this.info != null ? this.info.getScrollY() : scrollY);
	}
}
