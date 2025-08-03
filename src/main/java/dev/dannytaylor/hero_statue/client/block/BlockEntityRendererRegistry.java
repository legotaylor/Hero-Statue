package dev.dannytaylor.hero_statue.client.block;

import dev.dannytaylor.hero_statue.common.block.BlockEntityRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BlockEntityRendererRegistry {
	public static void bootstrap() {
		BlockEntityRendererFactories.register(BlockEntityRegistry.heroStatue, StatueBlockEntityRenderer::new);
	}
}
