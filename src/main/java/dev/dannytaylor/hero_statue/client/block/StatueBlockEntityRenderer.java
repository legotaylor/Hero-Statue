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
			int pose = entity.getCachedState().get(StatueBlock.pose);
			switch (pose) {
				case 0 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(5.0F));
				}
				case 1 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(350.0F));
				}
				case 2 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(325.0F));
				}
				case 3 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(300.0F));
				}
				case 4 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(275.0F));
				}
				case 5 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(250.0F));
				}
				case 6 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(225.0F));
				}
				case 7 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(200.0F));
				}
				case 8 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(175.0F));
				}
				case 9 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(150.0F));
				}
				case 10 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(125.0F));
				}
				case 11 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(100.0F));
				}
				case 12 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(75.0F));
				}
				case 13 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(50.0F));
				}
				case 14 -> {
					matrices.multiply(RotationAxis.POSITIVE_Y.rotation(25.0F));
				}
			}
			itemRenderer.renderItem(stack, pose % 2 == 0 ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND, getLight(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
			matrices.pop();
		}
	}

	private int getLight(World world, BlockPos pos) {
		int blockLight = world.getLightLevel(LightType.BLOCK, pos);
		for (Direction direction : Direction.values()) blockLight = Math.max(blockLight, world.getLightLevel(pos.offset(direction)));
		return LightmapTextureManager.pack(blockLight, world.getLightLevel(LightType.SKY, pos));
	}
}
