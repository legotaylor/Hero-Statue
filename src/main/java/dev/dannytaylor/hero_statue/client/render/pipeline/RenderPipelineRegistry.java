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

	private static final Map<StatueRenderState, RenderPipeline> statue;
	public static final RenderPipeline statueFallback;

	private static final Map<StatueRenderState, RenderPipeline> statueEyes;
	public static final RenderPipeline statueEyesFallback;

	public static void bootstrap() {
		RenderLayerRegistry.bootstrap();
		HeroStatueIris.registerPipelines();
	}

	public static RenderPipeline getStatue(StatueRenderState renderState) {
		return Optional.of(statue.get(renderState)).orElse(statueFallback);
	}

	public static List<RenderPipeline> getStatue() {
		return statue.values().stream().toList();
	}

	private static Map<StatueRenderState, RenderPipeline> registerStatue() {
		Map<StatueRenderState, RenderPipeline> pipelines = new HashMap<>();
		for (StatueRenderState state : getKnownStatueRenderStates()) pipelines.put(state, getStatueBuilder(state.pose(), state.facing(), state.powered(), state.waterlogged(), state.rainbowMode(), state.shouldFlipModelUpsideDown()).build());
		return pipelines;
	}

	private static RenderPipeline.Builder getStatueBuilder() {
		return getStatueBuilder("");
	}

	private static RenderPipeline.Builder getStatueBuilder(int pose, Direction facing, int powered, boolean waterlogged, boolean rainbowMode, boolean shouldFlipModelUpsideDown) {
		RenderPipeline.Builder builder = getStatueBuilder(pose + "_" + facing + "_" + powered + "_" + waterlogged).withShaderDefine("POSE", Math.clamp(pose, 0, 14)).withShaderDefine("YAW", StatueBlockEntityRenderer.getYawFromDirection(facing)).withShaderDefine("POWERED", Math.clamp(powered, 0, 15));
		if (waterlogged) builder.withShaderDefine("WATERLOGGED");
		if (rainbowMode) builder.withShaderDefine("RAINBOW_MODE");
		if (shouldFlipModelUpsideDown) builder.withShaderDefine("FLIP_MODEL");
		return builder;
	}

	private static RenderPipeline.Builder getStatueBuilder(String location) {
		return RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET).withLocation(CommonData.idOf("pipeline/statue" + (location.isEmpty() ? "" : "/" + location))).withVertexShader(CommonData.idOf("core/statue")).withFragmentShader(CommonData.idOf("core/statue")).withSampler("Sampler0").withSampler("Sampler1").withSampler("Sampler2").withVertexFormat(VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS);
	}

	public static RenderPipeline getStatueEyes(StatueRenderState renderState) {
		return Optional.of(statueEyes.get(renderState)).orElse(statueEyesFallback);
	}

	public static List<RenderPipeline> getStatueEyes() {
		return statueEyes.values().stream().toList();
	}

	private static Map<StatueRenderState, RenderPipeline> registerStatueEyes() {
		Map<StatueRenderState, RenderPipeline> pipelines = new HashMap<>();
		for (StatueRenderState state : getKnownStatueRenderStates()) pipelines.put(state, getStatueEyeBuilder(state.pose(), state.facing(), state.powered(), state.waterlogged(), state.rainbowMode(), state.shouldFlipModelUpsideDown()).build());
		return pipelines;
	}

	private static RenderPipeline.Builder getStatueEyeBuilder() {
		return getStatueEyeBuilder("");
	}

	private static RenderPipeline.Builder getStatueEyeBuilder(int pose, Direction facing, int powered, boolean waterlogged, boolean rainbowMode, boolean shouldFlipModelUpsideDown) {
		RenderPipeline.Builder builder = getStatueEyeBuilder(pose + "_" + facing + "_" + powered + "_" + waterlogged).withShaderDefine("POSE", Math.clamp(pose, 0, 14)).withShaderDefine("YAW", StatueBlockEntityRenderer.getYawFromDirection(facing)).withShaderDefine("POWERED", Math.clamp(powered, 0, 15));
		if (waterlogged) builder.withShaderDefine("WATERLOGGED");
		if (rainbowMode) builder.withShaderDefine("RAINBOW_MODE");
		if (shouldFlipModelUpsideDown) builder.withShaderDefine("FLIP_MODEL");
		return builder;
	}

	private static RenderPipeline.Builder getStatueEyeBuilder(String location) {
		return RenderPipeline.builder(RenderPipelines.TRANSFORMS_PROJECTION_FOG_SNIPPET).withLocation(CommonData.idOf("pipeline/statue_eyes" + (location.isEmpty() ? "" : "/" + location))).withVertexShader(CommonData.idOf("core/statue_eyes")).withFragmentShader(CommonData.idOf("core/statue_eyes")).withSampler("Sampler0").withBlend(new BlendFunction(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_COLOR)).withDepthWrite(false).withVertexFormat(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS).withDepthBias(-1.0F, -5.0F);
	}

	private static List<StatueRenderState> getKnownStatueRenderStates() {
		if (statueRenderStates == null) {
			statueRenderStates = new ArrayList<>();
			StatueBlock.facing.getValues().forEach((direction) -> {
				for (int pose = 0; pose < 15; pose++) {
					for (int power = 0; power < 16; power++) {
						for (int waterlogged = 0; waterlogged < 2; waterlogged++) {
							for (int rainbow = 0; rainbow < 2; rainbow++) {
								for (int flip = 0; flip < 2; flip++) {
									statueRenderStates.add(new StatueRenderState(pose, direction, power, waterlogged != 0, rainbow != 0, flip != 0));
								}
							}
						}
					}
				}
			});
		}
		return statueRenderStates;
	}

	static {
		statue = registerStatue();
		statueFallback = getStatueBuilder().build();

		statueEyes = registerStatueEyes();
		statueEyesFallback = getStatueEyeBuilder().build();
	}
}
