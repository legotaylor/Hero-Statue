package dev.dannytaylor.hero_statue.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StatueBlock extends BlockWithEntity {
	public static final MapCodec<StatueBlock> codec = createCodec(StatueBlock::new);
	public static final IntProperty pose;

	@Override
	public MapCodec<? extends StatueBlock> getCodec() {
		return codec;
	}

	public StatueBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(pose, 0));
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.getBlockEntity(pos) instanceof StatueBlockEntity statueBlockEntity) {
			if (!statueBlockEntity.hasMaterial()) {
				if (stack.getItem() instanceof BlockItem blockItem) {
					stack.decrement(1);
					// TODO: Check if full compatible block.
					try {
						statueBlockEntity.setMaterial(Identifier.of(Registries.BLOCK.getEntry(blockItem.getBlock()).getIdAsString()));
					} catch (Exception e) {}
					player.sendMessage(Text.literal("You've set the block material to " + statueBlockEntity.getMaterial() + "."), true);
				}
			} else {
				if (stack.isOf(Items.SHEARS)) {
					Identifier material = statueBlockEntity.getMaterial();
					statueBlockEntity.clearMaterial();
					world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Registries.BLOCK.get(material).asItem())));
					player.sendMessage(Text.literal("You've reset the block material."), true);
					if (stack.isDamageable()) stack.damage(1, player);
				}
			}
			return ActionResult.SUCCESS;
		}
		return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
	}

	@Nullable
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
			if (world instanceof ServerWorld serverWorld && !world.getBlockTickScheduler().isQueued(pos, this)) {
				this.update(state, serverWorld, pos);
			}
		}
	}

	public void update(BlockState state, ServerWorld world, BlockPos pos) {
		int powerLevel = world.getReceivedRedstonePower(pos);
		if (shouldSetPose(state, powerLevel)) world.setBlockState(pos, state.with(pose, getPose(powerLevel)), 3);
		if (!world.getBlockTickScheduler().isQueued(pos, this)) world.scheduleBlockTick(pos, this, 2);
	}

	public boolean shouldSetPose(BlockState state, int powerLevel) {
		return powerLevel > 0 && powerLevel != getPose(state.get(pose));
	}

	public int getPose(int powerLevel) {
		return powerLevel - 1;
	}

	@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.update(state, world, pos);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(pose);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return this.getDefaultState().with(pose, 0);
	}

	static {
		pose = PropertyRegistry.pose;
	}
}
