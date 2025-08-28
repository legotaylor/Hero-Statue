/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.gui.widget;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.StringIdentifiable;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InfoWidget extends EntryListWidget<InfoWidget.InfoEntry> {
	private final TextRenderer textRenderer;

	public InfoWidget(MinecraftClient client, int width, int height, int y, int lineHeight) {
		this(client, width, height, y, lineHeight, 0);
	}

	public InfoWidget(MinecraftClient client, int width, int height, int y, int lineHeight, double scrollY) {
		super(client, width, height, y, lineHeight);
		this.textRenderer = client.textRenderer;
		for (OrderedText row : this.textRenderer.wrapLines(load(CommonData.idOf("texts/info.json"), InfoWidget::read), this.getRowWidth())) addEntry(new InfoEntry(row));
		setScrollY(scrollY);
	}

	protected void renderEntry(DrawContext context, int mouseX, int mouseY, float delta, int index, int x, int y, int entryWidth, int entryHeight) {
		InfoEntry entry = this.getEntry(index);
		entry.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, Objects.equals(this.getHoveredEntry(), entry), delta);
	}

	public int getRowWidth() {
		return this.width - 32;
	}

	protected void appendClickableNarrations(NarrationMessageBuilder builder) {
	}

	public class InfoEntry extends Entry<InfoEntry> {
		private final OrderedText text;
		public InfoEntry(OrderedText text) {
			this.text = text;
		}
		public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
			context.drawTextWithShadow(textRenderer, this.text, x, y, 0xFFAAAAAA);
		}
	}

	private static Text load(Identifier id, InfoReader infoReader) {
		try (Reader reader = ClientData.minecraft.getResourceManager().openAsReader(id)) {
			return infoReader.read(reader);
		} catch (Exception exception) {
			CommonData.logger.error("Couldn't load info from file {}: {}", id, exception.getLocalizedMessage());
		}
		return Text.empty();
	}

	private static Text read(Reader reader) {
		JsonObject root = JsonHelper.deserialize(reader).getAsJsonObject();
		MutableText text = Text.empty();
		if (root.has("values")) {
			for (JsonElement element : root.getAsJsonArray("values")) {
				text.append(read(Text.empty(), element));
				if (root.has("line_breaks") && root.get("line_breaks").getAsBoolean()) text.append("\n");
			}
		}
		return text;
	}

	private static Text read(MutableText text, JsonElement element) {
		JsonObject jsonObject = element.getAsJsonObject();
		if (jsonObject.has("indent")) {
			int indents = jsonObject.get("indent").getAsInt();
			if (indents > 0) text.append(Text.literal(" ".repeat(indents)));
		}
		List<Text> args = new ArrayList<>();
		if (jsonObject.has("args")) {
			for (JsonElement argElement : jsonObject.getAsJsonArray("args")) {
				args.add(read(Text.empty(), argElement));
			}
		}
		if (jsonObject.has("value")) {
			text.append(switch (jsonObject.has("type") ? TextType.valueOf(jsonObject.get("type").getAsString()) : TextType.literal) {
				case literal -> Text.literal(jsonObject.get("value").getAsString());
				case translatable -> Text.translatable(jsonObject.get("value").getAsString(), args.toArray(new Object[0]));
				case variable -> switch (jsonObject.get("value").getAsString()) {
					case "id" -> Text.literal(CommonData.id);
					case "name" -> Text.translatable(CommonData.id + ".title");
					case "version" -> {
						Optional<ModContainer> mod = FabricLoader.getInstance().getModContainer(CommonData.id);
						yield Text.literal(mod.isPresent() ? mod.get().getMetadata().getVersion().getFriendlyString() : "UNKNOWN");
					}
					case null, default -> Text.empty();
				};
			});
		}
		return text;
	}

	interface InfoReader {
		Text read(Reader reader) throws IOException;
	}

	private enum TextType implements StringIdentifiable {
		literal("literal"),
		translatable("translatable"),
		variable("variable");

		final String id;

		TextType(String id) {
			this.id = id;
		}

		public String asString() {
			return this.id;
		}
	}
}

