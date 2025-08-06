package dev.dannytaylor.hero_statue.common.block;

import com.mojang.serialization.MapCodec;
import dev.dannytaylor.hero_statue.client.gamerule.GameruleCache;
import dev.dannytaylor.hero_statue.common.gamerule.GameruleRegistry;
import dev.dannytaylor.hero_statue.common.sound.SoundRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

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
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (GameruleCache.allowPlayerChangeStatuePose || (world instanceof ServerWorld serverWorld && serverWorld.getGameRules().getBoolean(GameruleRegistry.allowPlayerChangeStatuePose))) {
			if (player.getAbilities().allowModifyWorld) {
				setPose(state, world, pos, (state.get(pose) + 1) % 15);
				return ActionResult.SUCCESS;
			}
		}
		return super.onUse(state, world, pos, player, hit);
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
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

	public void update(BlockState state, ServerWorld world, BlockPos pos) {
		if (world.getGameRules().getBoolean(GameruleRegistry.allowRedstoneChangeStatuePose)) {
			int powerLevel = world.getReceivedRedstonePower(pos);
			if (shouldSetPose(state, powerLevel)) {
				setPose(state, world, pos, getPose(powerLevel));
			}
		}
		if (!world.getBlockTickScheduler().isQueued(pos, this)) world.scheduleBlockTick(pos, this, 2);
	}

	public void setPose(BlockState state, World world, BlockPos pos, int value) {
		world.setBlockState(pos, state.with(pose, value), 3);
		world.playSound(null, pos, SoundRegistry.heroStatueUpdatePose, SoundCategory.BLOCKS, 0.5F, world.random.nextFloat() * 0.25F + 0.6F);
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
	protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
		if (!canPlaceAt(state, world, pos)) {
			dropStacks(state, world, pos);
			dropStack(world, pos);
			world.removeBlock(pos, false);
		}
		super.neighborUpdate(state, world, pos, sourceBlock, wireOrientation, notify);
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

	@Override
	protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
		ItemStack stack = new ItemStack(this);
		if (includeData) stack.set(DataComponentTypes.BLOCK_STATE, new BlockStateComponent(Map.of(pose.getName(), state.get(pose).toString())));
		return stack;
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return Block.createCuboidShape(3.0F, 0.0F, 3.0F, 13.0F, 16.0F, 13.0F);
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return super.canPlaceAt(state, world, pos) && world.getBlockState(pos.down()).isSideSolid(world, pos, Direction.UP, SideShapeType.CENTER);
	}

	static {
		pose = PropertyRegistry.pose;
		waterlogged = Properties.WATERLOGGED;
		facing = HorizontalFacingBlock.FACING;
	}
}
