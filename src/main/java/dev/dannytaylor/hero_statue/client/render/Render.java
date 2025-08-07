/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.render;

import dev.dannytaylor.hero_statue.client.render.model.EntityModelRegistry;
import dev.dannytaylor.hero_statue.client.render.pipeline.RenderPipelineRegistry;
import dev.dannytaylor.hero_statue.client.render.renderer.BlockEntityRendererRegistry;

public class Render {
	public static void bootstrap() {
		RenderPipelineRegistry.bootstrap();
		BlockEntityRendererRegistry.bootstrap();
		EntityModelRegistry.bootstrap();
	}
}
