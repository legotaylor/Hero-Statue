/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.render.pipeline;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.dannytaylor.hero_statue.client.compatibility.HeroStatueIris;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.VertexFormats;

public class RenderPipelineRegistry {
	public static final RenderPipeline statueEyes;
	public static void bootstrap() {
		RenderLayerRegistry.bootstrap();
		HeroStatueIris.registerPipelines();
	}
	static {
		statueEyes = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.TRANSFORMS_PROJECTION_FOG_SNIPPET).withLocation(CommonData.idOf("pipeline/statue_eyes")).withVertexShader(CommonData.idOf("core/statue_eyes")).withFragmentShader(CommonData.idOf("core/statue_eyes")).withSampler("Sampler0").withBlend(BlendFunction.ADDITIVE).withDepthWrite(false).withVertexFormat(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS).withDepthBias(-2.0F, -20.0F).build());
	}
}
