package dev.dannytaylor.hero_statue.mixin;

import net.minecraft.client.render.block.BlockModels;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockModels.class)
public class ExampleMixin {
	// TODO: Check if textureId is hero-statue:material and replace it with texture of current material, with a fallback.
//	@Inject(method = "init", at = @At("HEAD"))
//	private void herostatue$init(CallbackInfo ci) {
//		this.splashText = new SplashTextRenderer("Happy ModFest!");
//	}
}
