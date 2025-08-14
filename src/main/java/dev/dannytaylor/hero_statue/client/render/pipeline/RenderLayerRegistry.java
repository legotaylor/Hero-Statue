/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.render.pipeline;

import dev.dannytaylor.hero_statue.client.block.StatueRenderState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.BiFunction;

public class RenderLayerRegistry {
	private static final BiFunction<Identifier, StatueRenderState, RenderLayer> statueEyes;
	public static void bootstrap() {
	}
	public static RenderLayer getStatueEyes(Identifier texture, StatueRenderState renderState) {
		return statueEyes.apply(texture, renderState);
	}
	static {
		statueEyes = Util.memoize((texture, renderState) -> {
			RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder().texture(new RenderPhase.Texture(texture, false)).build(false);
			return RenderLayer.of("hero_statue_eyes", 1536, true, true, RenderPipelineRegistry.getStatueEyes(renderState), multiPhaseParameters);
		});
	}
}
