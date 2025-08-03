package dev.dannytaylor.hero_statue.common.block;

import dev.dannytaylor.hero_statue.common.data.HeroStatueData;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class BlockRegistry {
	public static final Block heroStatue;
	private static RegistryKey<Block> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(HeroStatueData.id, id));
	}
	private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
		return Blocks.register(keyOf(id), factory, settings);
	}
	private static Block register(String id, AbstractBlock.Settings settings) {
		return register(id, Block::new, settings);
	}
	public static void bootstrap() {
	}
	static {
		heroStatue = register("hero_statue", StatueBlock::new, AbstractBlock.Settings.create());
	}
}
