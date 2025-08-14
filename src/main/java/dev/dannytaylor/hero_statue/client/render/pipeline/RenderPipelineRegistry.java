/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.render.pipeline;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.dannytaylor.hero_statue.client.block.StatueRenderState;
import dev.dannytaylor.hero_statue.client.compatibility.HeroStatueIris;
import dev.dannytaylor.hero_statue.client.render.renderer.StatueBlockEntityRenderer;
import dev.dannytaylor.hero_statue.common.block.StatueBlock;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.Direction;

import java.util.*;

public class RenderPipelineRegistry {
	private static List<StatueRenderState> statueRenderStates;
	private static final Map<StatueRenderState, RenderPipeline> statueEyes;
	public static final RenderPipeline statueEyesFallback;

	public static void bootstrap() {
		RenderLayerRegistry.bootstrap();
		HeroStatueIris.registerPipelines();
	}

	public static RenderPipeline getStatueEyes(StatueRenderState renderState) {
		return Optional.of(statueEyes.get(renderState)).orElse(statueEyesFallback);
	}

	public static List<RenderPipeline> getStatueEyes() {
		return statueEyes.values().stream().toList();
	}

	private static Map<StatueRenderState, RenderPipeline> registerStatueEyes() {
		Map<StatueRenderState, RenderPipeline> pipelines = new HashMap<>();
		for (StatueRenderState state : getKnownRenderStates()) pipelines.put(state, getStatueEyeBuilder(state.pose(), state.facing(), state.powered(), state.waterlogged(), state.rainbowMode()).build());
		return pipelines;
	}

	private static RenderPipeline.Builder getStatueEyeBuilder() {
		return getStatueEyeBuilder("");
	}

	private static RenderPipeline.Builder getStatueEyeBuilder(int pose, Direction facing, int powered, boolean waterlogged, boolean rainbowMode) {
		RenderPipeline.Builder builder = getStatueEyeBuilder(pose + "_" + facing + "_" + powered + "_" + waterlogged).withShaderDefine("POSE", Math.clamp(pose, 0, 14)).withShaderDefine("YAW", StatueBlockEntityRenderer.getYawFromDirection(facing)).withShaderDefine("POWERED", Math.clamp(powered, 0, 15));
		if (waterlogged) builder.withShaderDefine("WATERLOGGED");
		if (rainbowMode) builder.withShaderDefine("RAINBOW_MODE");
		return builder;
	}

	private static RenderPipeline.Builder getStatueEyeBuilder(String location) {
		return RenderPipeline.builder(RenderPipelines.TRANSFORMS_PROJECTION_FOG_SNIPPET).withLocation(CommonData.idOf("pipeline/statue_eyes" + (location.isEmpty() ? "" : "/" + location))).withVertexShader(CommonData.idOf("core/statue_eyes")).withFragmentShader(CommonData.idOf("core/statue_eyes")).withSampler("Sampler0").withBlend(new BlendFunction(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_COLOR)).withDepthWrite(false).withVertexFormat(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS).withDepthBias(-1.0F, -5.0F);
	}

	private static List<StatueRenderState> getKnownRenderStates() {
		if (statueRenderStates == null) {
			statueRenderStates = new ArrayList<>();
			StatueBlock.facing.getValues().forEach((direction) -> {
				for (int pose = 0; pose < 15; pose++) {
					for (int power = 0; power < 16; power++) {
						statueRenderStates.add(new StatueRenderState(pose, direction, power, false, false));
						statueRenderStates.add(new StatueRenderState(pose, direction, power, false, true));
						statueRenderStates.add(new StatueRenderState(pose, direction, power, true, false));
						statueRenderStates.add(new StatueRenderState(pose, direction, power, true, true));
					}
				}
			});
		}
		return statueRenderStates;
	}

	static {
		statueEyes = registerStatueEyes();
		statueEyesFallback = getStatueEyeBuilder().build();
	}
}
