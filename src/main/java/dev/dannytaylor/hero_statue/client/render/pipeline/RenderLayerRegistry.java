/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.render.pipeline;

import dev.dannytaylor.hero_statue.client.block.StatueRenderState;
import dev.dannytaylor.hero_statue.client.render.renderer.StatueBlockEntityRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.BiFunction;

public class RenderLayerRegistry {
	private static final BiFunction<Identifier, StatueRenderState, RenderLayer> statue;
	private static final BiFunction<Identifier, StatueRenderState, RenderLayer> statueEyes;
	public static void bootstrap() {
		// pre-warm shaders so it doesn't lag when first seeing the statues.
		for (StatueRenderState renderState : RenderPipelineRegistry.getKnownStatueRenderStates()) {
			for (Identifier texture : StatueBlockEntityRenderer.getKnownModelTextures()) getStatue(texture, renderState);
			for (Identifier texture : StatueBlockEntityRenderer.getKnownEyeTextures()) getStatueEyes(texture, renderState);
		}
	}
	public static RenderLayer getStatue(Identifier texture, StatueRenderState renderState) {
		return statue.apply(texture, renderState);
	}
	public static RenderLayer getStatueEyes(Identifier texture, StatueRenderState renderState) {
		return statueEyes.apply(texture, renderState);
	}
	static {
		statue = Util.memoize((texture, renderState) -> {
			RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(texture, false)).lightmap(RenderLayer.ENABLE_LIGHTMAP).overlay(RenderLayer.ENABLE_OVERLAY_COLOR).build(true);
			return RenderLayer.of("hero_statue", 1536, true, false, RenderPipelineRegistry.getStatue(renderState), multiPhaseParameters);
		});
		statueEyes = Util.memoize((texture, renderState) -> {
			RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(texture, false)).build(false);
			return RenderLayer.of("hero_statue_eyes", 1536, true, true, RenderPipelineRegistry.getStatueEyes(renderState), multiPhaseParameters);
		});
	}
}
