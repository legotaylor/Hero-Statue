package dev.dannytaylor.hero_statue.client.gui.screen;

import dev.dannytaylor.hero_statue.client.config.HeroStatueClientConfig;
import dev.dannytaylor.hero_statue.client.data.ClientData;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class OffsetsScreen extends ConfigScreen {
	public OffsetsScreen(Screen parent) {
		this(parent, 0);
	}

	public OffsetsScreen(Screen parent, double scrollY) {
		super(Text.translatable("hero-statue.offsets"), parent, scrollY);
	}

	public List<ClickableWidget> getWidgets() {
		List<ClickableWidget> options = new ArrayList<>();
		options.add(new SliderWidget(0, 0, 300, 20, Text.translatable("hero-statue.offsets.offset", String.format("%.3f", HeroStatueClientConfig.instance.offset.value())), HeroStatueClientConfig.instance.offset.value() / 0.2F) {
			@Override
			protected void updateMessage() {
				this.setMessage(Text.translatable("hero-statue.offsets.offset", String.format("%.3f", HeroStatueClientConfig.instance.offset.value())));
			}
			@Override
			protected void applyValue() {
				HeroStatueClientConfig.instance.offset.setValue(Math.round(((float) this.value * 0.2F) * 1000.0F) / 1000.0F);
			}
		});
		if (FabricLoader.getInstance().isModLoaded("iris")) {
			options.add(new TextWidget(Text.translatable("hero-statue.offsets.iris_z-fighting"), ClientData.minecraft.textRenderer));
			options.add(ButtonWidget.builder(Text.translatable("hero-statue.offsets.iris_z-fighting.enabled", HeroStatueClientConfig.instance.irisEyeZFightingFix.value()), (button) -> {
				HeroStatueClientConfig.instance.irisEyeZFightingFix.setValue(!HeroStatueClientConfig.instance.irisEyeZFightingFix.value());
				button.setMessage(Text.translatable("hero-statue.offsets.iris_z-fighting.enabled", HeroStatueClientConfig.instance.irisEyeZFightingFix.value()));
			}).build());
			options.add(new SliderWidget(0, 0, 300, 20, Text.translatable("hero-statue.offsets.iris_z-fighting.min_dist", String.format("%.3f", HeroStatueClientConfig.instance.irisEyeZFightingFix_MinDist.value())), HeroStatueClientConfig.instance.irisEyeZFightingFix_MinDist.value() / 32.0F) {
				@Override
				protected void updateMessage() {
					this.setMessage(Text.translatable("hero-statue.offsets.iris_z-fighting.min_dist", String.format("%.3f", HeroStatueClientConfig.instance.irisEyeZFightingFix_MinDist.value())));
				}
				@Override
				protected void applyValue() {
					HeroStatueClientConfig.instance.irisEyeZFightingFix_MinDist.setValue(Math.round(((float) this.value * 32.0F) * 1000.0F) / 1000.0F);
				}
			});
			options.add(new SliderWidget(0, 0, 300, 20, Text.translatable("hero-statue.offsets.iris_z-fighting.max_dist", String.format("%.3f", HeroStatueClientConfig.instance.irisEyeZFightingFix_MaxDist.value())), HeroStatueClientConfig.instance.irisEyeZFightingFix_MaxDist.value() / 32.0F) {
				@Override
				protected void updateMessage() {
					this.setMessage(Text.translatable("hero-statue.offsets.iris_z-fighting.max_dist", String.format("%.3f", HeroStatueClientConfig.instance.irisEyeZFightingFix_MaxDist.value())));
				}
				@Override
				protected void applyValue() {
					HeroStatueClientConfig.instance.irisEyeZFightingFix_MaxDist.setValue(Math.round(((float) this.value * 32.0F) * 1000.0F) / 1000.0F);
				}
			});
			options.add(new SliderWidget(0, 0, 300, 20, Text.translatable("hero-statue.offsets.iris_z-fighting.min_offset", String.format("%.3f", HeroStatueClientConfig.instance.irisEyeZFightingFix_MinOffset.value())), HeroStatueClientConfig.instance.irisEyeZFightingFix_MinOffset.value() / 0.2F) {
				@Override
				protected void updateMessage() {
					this.setMessage(Text.translatable("hero-statue.offsets.iris_z-fighting.min_offset", String.format("%.3f", HeroStatueClientConfig.instance.irisEyeZFightingFix_MinOffset.value())));
				}
				@Override
				protected void applyValue() {
					HeroStatueClientConfig.instance.irisEyeZFightingFix_MinOffset.setValue(Math.round(((float) this.value * 0.2F) * 1000.0F) / 1000.0F);
				}
			});
			options.add(new SliderWidget(0, 0, 300, 20, Text.translatable("hero-statue.offsets.iris_z-fighting.max_offset", String.format("%.3f", HeroStatueClientConfig.instance.irisEyeZFightingFix_MaxOffset.value())), HeroStatueClientConfig.instance.irisEyeZFightingFix_MaxOffset.value() / 0.2F) {
				@Override
				protected void updateMessage() {
					this.setMessage(Text.translatable("hero-statue.offsets.iris_z-fighting.max_offset", String.format("%.3f", HeroStatueClientConfig.instance.irisEyeZFightingFix_MaxOffset.value())));
				}
				@Override
				protected void applyValue() {
					HeroStatueClientConfig.instance.irisEyeZFightingFix_MaxOffset.setValue(Math.round(((float) this.value * 0.2F) * 1000.0F) / 1000.0F);
				}
			});
		}
		return options;
	}

	public Screen getRefreshScreen() {
		return new OffsetsScreen(this.parent, this.config != null ? this.config.getScrollY() : scrollY);
	}
}
