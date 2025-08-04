package dev.dannytaylor.hero_statue.common.block;

import com.mojang.serialization.MapCodec;
import dev.dannytaylor.hero_statue.common.sound.SoundRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class StatueBlock extends BlockWithEntity implements Waterloggable {
	public static final MapCodec<StatueBlock> codec = createCodec(StatueBlock::new);
	public static final IntProperty pose;
	public static final BooleanProperty waterlogged;
	public static final EnumProperty<Direction> facing;

	@Override
	public MapCodec<? extends StatueBlock> getCodec() {
		return codec;
	}

	public StatueBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(pose, 0).with(waterlogged, false).with(facing, Direction.NORTH));
	}

	@Override
	protected void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
		if (player.getAbilities().allowModifyWorld) {
			dropStack(world, pos);
		}
		super.onBlockBreakStart(state, world, pos, player);
	}

	@Override
	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		// Just in case it wasn't dropped already
		dropStack(world, pos);
		super.onBroken(world, pos, state);
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		// TODO: Area Lib compatibility?
		// https://github.com/Tomate0613/area-lib/wiki

		// If player IS NOT in adventure mode OR IS in modifiable area
		//AreaLib.getSavedData(world);

		if (player.getAbilities().allowModifyWorld) {
			if (world.getBlockEntity(pos) instanceof StatueBlockEntity statueBlockEntity) {
				if (!statueBlockEntity.hasStack() && !stack.isEmpty()) {
					ItemStack stack1 = stack.copyWithCount(1);
					stack.decrementUnlessCreative(1, player);
					statueBlockEntity.setStack(stack1);
					return ActionResult.SUCCESS;
				}
			}
		}
		return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new StatueBlockEntity(pos, state);
	}

	@Override
	protected boolean emitsRedstonePower(BlockState state) {
		return false;
	}

	@Override
	protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return 0;
	}

	@Override
	protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return 0;
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		// Comparator output should equal the redstone power required to set the current pose, regardless of input power level.
		return state.get(pose) + 1;
	}

	@Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		if (!state.isOf(oldState.getBlock())) {
			if (world instanceof ServerWorld serverWorld) {
				if (!world.getBlockTickScheduler().isQueued(pos, this)) {
					this.update(state, serverWorld, pos);
				}
			}
		}
	}

	public void update(BlockState state, World world, BlockPos pos) {
		int powerLevel = world.getReceivedRedstonePower(pos);
		if (shouldSetPose(state, powerLevel)) {
			world.setBlockState(pos, state.with(pose, getPose(powerLevel)), 3);
			world.playSound(null, pos, SoundRegistry.heroStatueUpdatePose, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
		}
		if (!world.getBlockTickScheduler().isQueued(pos, this)) world.scheduleBlockTick(pos, this, 2);
	}

	public boolean shouldSetPose(BlockState state, int powerLevel) {
		boolean hasPower = powerLevel > 0;
		boolean diffPower = powerLevel != getPower(state.get(pose));
		return hasPower && diffPower;
	}

	public int getPose(int powerLevel) {
		return powerLevel - 1;
	}

	public int getPower(int pose) {
		return pose + 1;
	}

	@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.update(state, world, pos);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(pose, waterlogged, facing);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
		return this.getDefaultState().with(pose, 0).with(waterlogged, fluidState.getFluid() == Fluids.WATER).with(facing, context.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, BlockEntityRegistry.heroStatue, StatueBlockEntity::tick);
	}

	@Override
	protected BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
		if (state.get(waterlogged)) {
			tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return state;
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.get(waterlogged) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	public void dropStack(WorldAccess world, BlockPos pos) {
		if (world.getBlockEntity(pos) instanceof StatueBlockEntity statueBlockEntity) {
			if (statueBlockEntity.hasStack()) {
				statueBlockEntity.dropStack();
			}
		}
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(facing, rotation.rotate(state.get(facing)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(facing)));
	}

	static {
		pose = PropertyRegistry.pose;
		waterlogged = Properties.WATERLOGGED;
		facing = HorizontalFacingBlock.FACING;
	}
}
