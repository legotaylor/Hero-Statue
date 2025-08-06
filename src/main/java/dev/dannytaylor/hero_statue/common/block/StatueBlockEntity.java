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
import net.minecraft.world.World;

import java.util.Optional;

public class StatueBlockEntity extends BlockEntity implements SingleStackInventory.SingleStackBlockEntityInventory {
	private ItemStack stack;
	private boolean needsSync;

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
		this.needsSync = true;
	}

	public static void tick(World world, BlockPos blockPos, BlockState blockState, StatueBlockEntity entity) {
		if (entity.needsSync) {
			entity.updateListeners();
			entity.needsSync = false;
		}
	}

	@Override
	public void onBlockReplaced(BlockPos pos, BlockState oldState) {
		super.onBlockReplaced(pos, oldState);
		if (this.world != null) ItemScatterer.spawn(this.world, pos.getX(), pos.getY(), pos.getZ(), getStack());
	}

	@Override
	public void markDirty() {
		super.markDirty();
		this.needsSync = true;
	}

	@Override
	public BlockEntity asBlockEntity() {
		return this;
	}

	public void dropStack() {
		if (this.world != null) {
			if (this.hasStack()) {
				ItemStack stack = this.getStack();
				this.clearStack();
				ItemScatterer.spawn(this.world, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), stack);
			}
		}
	}

	public void updateListeners() {
		if (this.world != null) {
			updateStack();
			this.world.updateListeners(pos, getCachedState(), getCachedState(), 3);
		}
	}

	public void updateStack() {
		if (this.world instanceof ServerWorld serverWorld) {
			BlockPos blockPos = this.getPos();
			ChunkPos chunkPos = this.world.getChunk(blockPos).getPos();
			for (ServerPlayerEntity player : PlayerLookup.tracking(serverWorld, chunkPos)) CommonNetwork.sendS2CStatue(player, serverWorld, blockPos);
		}
	}
}
