/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.block;

import dev.dannytaylor.hero_statue.common.network.CommonNetwork;
import dev.dannytaylor.hero_statue.common.sound.SoundRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;

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

	@Override
	public boolean isValid(int slot, ItemStack stack) {
		return slot == 0 && !this.hasStack();
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
		if (this.world != null && hasStack()) this.world.playSound(null, pos, SoundRegistry.heroStatueGiveItem, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
		this.needsSync = true;
	}

	public void dropStack() {
		if (this.world != null && !this.world.isClient) {
			ItemStack dropStack = this.getStack();
			clearStack();
			if (!dropStack.isEmpty()) {
				this.emptyStack();
				Vec3d vec3d = Vec3d.add(this.getPos(), 0.5F, 1.01, 0.5F).addRandom(this.world.random, 0.7F);
				ItemEntity itemEntity = new ItemEntity(this.world, vec3d.getX(), vec3d.getY(), vec3d.getZ(), dropStack);
				itemEntity.setToDefaultPickupDelay();
				this.world.spawnEntity(itemEntity);
			}
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

	@Override
	public int getMaxCountPerStack() {
		return 1;
	}
}
