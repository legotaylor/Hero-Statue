package dev.dannytaylor.hero_statue.client.block;

import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.common.block.StatueBlock;
import dev.dannytaylor.hero_statue.common.block.StatueBlockEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class StatueBlockEntityRenderer implements BlockEntityRenderer<StatueBlockEntity> {
	public StatueBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
	}

	@Override
	public void render(StatueBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
		if (entity != null && entity.getWorld() != null) {
			ItemRenderer itemRenderer = ClientData.minecraft.getItemRenderer();
			ItemStack stack = entity.getStack();
			matrices.push();
			// TODO: Adjust where itemstack is based on pose. // Requires models.
			matrices.translate(0.5F, 1.5F, 0.5F);
			matrices.scale(0.5F, 0.5F, 0.5F);
			matrices.multiply(RotationAxis.POSITIVE_Y.rotation(360.0F / (entity.getCachedState().get(StatueBlock.pose) + 1)));
			itemRenderer.renderItem(stack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, getLight(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
			matrices.pop();
		}
	}

	private int getLight(World world, BlockPos pos) {
		int blockLight = world.getLightLevel(LightType.BLOCK, pos);
		for (Direction direction : Direction.values()) blockLight = Math.max(blockLight, world.getLightLevel(pos.offset(direction)));
		return LightmapTextureManager.pack(blockLight, world.getLightLevel(LightType.SKY, pos));
	}
}
