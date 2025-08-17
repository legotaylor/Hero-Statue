/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.block;

import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class StatueBlockEntity extends BlockEntity implements SingleStackInventory.SingleStackBlockEntityInventory {
	private ItemStack stack = ItemStack.EMPTY;
	protected boolean needsSync;

	public StatueBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegistry.heroStatue, pos, state);
	}

	public void setStack(ItemStack newStack) {
		if (!ItemStack.areEqual(this.stack, newStack)) {
			this.stack = newStack;
			this.markDirty();
		}
	}

	public void clearStack() {
		setStack(ItemStack.EMPTY);
	}

	public ItemStack getStack() {
		return stack;
	}

	public boolean hasStack() {
		return !stack.isEmpty();
	}

	@Override
	public void writeData(WriteView nbt) {
		super.writeData(nbt);
		if (hasStack()) nbt.put("item", ItemStack.CODEC, stack);
	}

	@Override
	protected void readData(ReadView nbt) {
		super.readData(nbt);
		ItemStack newStack = nbt.read("item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
		if (!ItemStack.areEqual(stack, newStack)) {
			stack = newStack;
			needsSync = true;
		}
	}

	public void tick() {
		if (this.needsSync) {
			this.updateListeners();
			this.needsSync = false;
		}
	}

	@Override
	public void onBlockReplaced(BlockPos pos, BlockState oldState) {
		super.onBlockReplaced(pos, oldState);
		if (!this.stack.isEmpty() && this.world != null) {
			ItemScatterer.spawn(this.world, pos.getX(), pos.getY(), pos.getZ(), this.stack);
		}
	}

	@Override
	public void markDirty() {
		super.markDirty();
		this.needsSync = true;
	}

	public void dropStack() {
		if (!this.stack.isEmpty() && this.world != null) {
			ItemStack dropStack = this.stack;
			clearStack();
			ItemScatterer.spawn(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), dropStack);
		}
	}

	public void updateListeners() {
		if (this.world instanceof ServerWorld serverWorld) {
			BlockPos blockPos = this.pos;
			BlockState blockState = getCachedState();
			ChunkPos chunkPos = this.world.getChunk(blockPos).getPos();
			for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, chunkPos)) {
				CommonNetwork.sendS2CStatue(player, serverWorld, blockPos);
			}
			this.world.updateListeners(blockPos, blockState, blockState, 3);
		}
	}

	@Override
	public BlockEntity asBlockEntity() {
		return this;
	}
}
