/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.gui.widget;

import dev.dannytaylor.hero_statue.client.data.ClientData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.EntryListWidget;

import java.util.List;
import java.util.Objects;

public class ConfigWidget extends EntryListWidget<ConfigWidget.ConfigEntry> {
	public ConfigWidget(MinecraftClient client, int width, int height, int y, int lineHeight, List<ClickableWidget> widgets, double scrollY) {
		super(client, width, height, y, lineHeight);
		for (ClickableWidget widget : widgets) addEntry(new ConfigEntry(widget));
		setScrollY(scrollY);
	}

	public int getRowWidth() {
		return this.width - 24;
	}

	protected void appendClickableNarrations(NarrationMessageBuilder builder) {
	}

	protected void renderEntry(DrawContext context, int mouseX, int mouseY, float delta, int index, int x, int y, int entryWidth, int entryHeight) {
		ConfigEntry entry = this.getEntry(index);
		entry.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, Objects.equals(this.getHoveredEntry(), entry), delta);
	}

	public static class ConfigEntry extends Entry<ConfigEntry> {
		private final ClickableWidget widget;

		public ConfigEntry(ClickableWidget widget) {
			this.widget = widget;
		}

		public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
			this.widget.setDimensions(entryWidth, entryHeight);
			this.widget.setPosition(x, y);
			this.widget.render(context, mouseX, mouseY, ClientData.minecraft.getRenderTickCounter().getDynamicDeltaTicks());
		}

		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			return this.widget.mouseClicked(mouseX, mouseY, button);
		}

		public boolean mouseReleased(double mouseX, double mouseY, int button) {
			return this.widget.mouseReleased(mouseX, mouseY, button);
		}

		public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
			return this.widget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
		}

		public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
			return this.widget.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
		}

		public boolean isMouseOver(double mouseX, double mouseY) {
			return this.widget.isMouseOver(mouseX, mouseY);
		}

		public void mouseMoved(double mouseX, double mouseY) {
			this.widget.mouseMoved(mouseX, mouseY);
		}

		public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
			return this.widget.keyPressed(keyCode, scanCode, modifiers);
		}

		public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
			return this.widget.keyReleased(keyCode, scanCode, modifiers);
		}

		public boolean charTyped(char chr, int modifiers) {
			return this.widget.charTyped(chr, modifiers);
		}
	}
}

