/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.compatibility;

import dev.dannytaylor.hero_statue.client.render.pipeline.RenderPipelineRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.irisshaders.iris.api.v0.IrisApi;
import net.irisshaders.iris.pipeline.IrisPipelines;
import net.minecraft.client.gl.RenderPipelines;

public class HeroStatueIris {
	public static boolean isIrisInstalled() {
		return FabricLoader.getInstance().isModLoaded("iris");
	}
	public static boolean isIrisActive() {
		return isIrisInstalled() && IrisApi.getInstance().isShaderPackInUse();
	}
	public static void registerPipelines() {
		if (isIrisInstalled()) {
			IrisPipelines.copyPipeline(RenderPipelines.ENTITY_EYES, RenderPipelineRegistry.statueEyes);
		}
	}
}
