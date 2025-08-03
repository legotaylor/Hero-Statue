package dev.dannytaylor.hero_statue.common.item;

import dev.dannytaylor.hero_statue.common.block.BlockRegistry;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ItemRegistry {
	public static final Item heroStatue;
	private static RegistryKey<Item> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(CommonData.id, id));
	}

	private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
		return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
	}

	public static Item register(Block block) {
		return register(block, BlockItem::new);
	}

	public static Item register(Block block, Item.Settings settings) {
		return register(block, BlockItem::new, settings);
	}

	public static Item register(Block block, UnaryOperator<Item.Settings> settingsOperator) {
		return register(block, (BiFunction)((blockx, settings) -> new BlockItem((Block) blockx, settingsOperator.apply((Item.Settings) settings))));
	}

	public static Item register(Block block, Block... blocks) {
		Item item = register(block);

		for(Block block2 : blocks) {
			Item.BLOCK_ITEMS.put(block2, item);
		}

		return item;
	}

	public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory) {
		return register(block, factory, new Item.Settings());
	}

	public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings) {
		return register(keyOf(block.getRegistryEntry().registryKey()), (Function)((itemSettings) -> (Item)factory.apply(block, (Item.Settings) itemSettings)), settings.useBlockPrefixedTranslationKey());
	}

	public static Item register(String id, Function<Item.Settings, Item> factory) {
		return register(keyOf(id), factory, new Item.Settings());
	}

	public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
		return register(keyOf(id), factory, settings);
	}

	public static Item register(String id, Item.Settings settings) {
		return register(keyOf(id), Item::new, settings);
	}

	public static Item register(String id) {
		return register(keyOf(id), Item::new, new Item.Settings());
	}

	public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory) {
		return register(key, factory, new Item.Settings());
	}

	public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
		Item item = factory.apply(settings.registryKey(key));
		if (item instanceof BlockItem blockItem) {
			blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
		}

		return Registry.register(Registries.ITEM, key, item);
	}

	public static void bootstrap() {
	}

	static {
		heroStatue = register(BlockRegistry.heroStatue);
	}
}
