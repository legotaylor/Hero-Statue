package dev.dannytaylor.hero_statue.common.block;

import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockEntityRegistry {
	public static final BlockEntityType<StatueBlockEntity> heroStatue;
	public static void bootstrap() {
	}
	private static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(CommonData.id, id), FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
	}
	static {
		heroStatue = register("hero_statue", StatueBlockEntity::new, BlockRegistry.heroStatue);
	}
}
