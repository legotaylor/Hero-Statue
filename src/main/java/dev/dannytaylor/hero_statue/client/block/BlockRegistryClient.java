/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.block;

import dev.dannytaylor.hero_statue.common.block.BlockRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

public class BlockRegistryClient {
	public static void bootstrap() {
		BlockRenderLayerMap.putBlock(BlockRegistry.heroStatue, BlockRenderLayer.TRANSLUCENT);
		BlockEntityRendererRegistry.bootstrap();
	}
}
