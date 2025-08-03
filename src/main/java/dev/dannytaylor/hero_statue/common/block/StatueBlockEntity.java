package dev.dannytaylor.hero_statue.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class StatueBlockEntity extends BlockEntity {
	private ItemStack stack;

	public StatueBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.heroStatue, pos, state);
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
		markDirty();
	}

	public void clearStack() {
		setStack(ItemStack.EMPTY);
	}

	public ItemStack getStack() {
		return getStackData().orElse(ItemStack.EMPTY);
	}

	private Optional<ItemStack> getStackData() {
		return hasStack() ? Optional.of(this.stack) : Optional.empty();
	}

	public boolean hasStack() {
		return this.stack != null && !this.stack.isEmpty();
	}

	@Override
	public void writeData(WriteView nbt) {
		super.writeData(nbt);
		if (!getStack().isEmpty()) {
			nbt.put("item", ItemStack.CODEC, this.stack);
		}
	}

	@Override
	protected void readData(ReadView nbt) {
		super.readData(nbt);
		this.stack = nbt.read("item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
	}

	@Override
	public void onBlockReplaced(BlockPos pos, BlockState oldState) {
		super.onBlockReplaced(pos, oldState);
		if (this.world != null) ItemScatterer.spawn(this.world, pos.getX(), pos.getY(), pos.getZ(), getStack());
	}
}
