package dev.dannytaylor.hero_statue.mixin;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ExampleMixin {
	@Shadow @Nullable private SplashTextRenderer splashText;
	@Inject(method = "init", at = @At("HEAD"))
	private void herostatue$init(CallbackInfo ci) {
		this.splashText = new SplashTextRenderer("Happy ModFest!");
	}
}
