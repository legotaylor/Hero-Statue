/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.block.statue;

import dev.dannytaylor.hero_statue.client.block.statue.model.StatuePoseModel;
import dev.dannytaylor.hero_statue.client.block.statue.model.StatuePoseZeroModel;
import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.client.entity.EntityModelRegistry;
import dev.dannytaylor.hero_statue.common.block.StatueBlock;
import dev.dannytaylor.hero_statue.common.block.StatueBlockEntity;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.List;

public class StatueBlockEntityRenderer implements BlockEntityRenderer<StatueBlockEntity> {
	private final List<StatuePoseModel> models;
	public StatueBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		this.models = List.of(
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseZero)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseOne)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseTwo)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseThree)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseFour)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseFive)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseSix)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseSeven)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseEight)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseNine)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseTen)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseEleven)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseTwelve)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseThirteen)),
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseFourteen))
		);
	}

	@Override
	public void render(StatueBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
		if (entity != null && entity.getWorld() != null) {
			matrices.push();
			matrices.translate(0.5F, 0.75F, 0.5F);
			matrices.scale(0.5F, 0.5F, 0.5F);
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(getYawFromDirection(entity.getCachedState().get(StatueBlock.facing))));
			int pose = entity.getCachedState().get(StatueBlock.pose);
			StatuePoseModel model = this.models.get(pose);
			model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(CommonData.idOf("textures/block/hero_statue/hero_statue.png"))), light, overlay, -1);
			matrices.pop();

			ItemStack stack = entity.getStack();
			if (!stack.isEmpty()) {
				matrices.push();
				boolean isRightHanded = pose % 2 == 0;
				ModelPart hand = (isRightHanded ? model.rightHand : model.leftHand);
				hand.applyTransform(matrices);
				ClientData.minecraft.getItemRenderer().renderItem(entity.getStack(), isRightHanded ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND, getLight(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
				matrices.pop();
			}
		}
	}

	private int getLight(World world, BlockPos pos) {
		int blockLight = world.getLightLevel(LightType.BLOCK, pos);
		for (Direction direction : Direction.values()) blockLight = Math.max(blockLight, world.getLightLevel(pos.offset(direction)));
		return LightmapTextureManager.pack(blockLight, world.getLightLevel(LightType.SKY, pos));
	}

	private static float getYawFromDirection(Direction direction) {
		return switch (direction) {
			case NORTH -> 180.0F;
			case WEST -> 90.0F;
			case EAST -> -90.0F;
			default -> 0.0F;
		};
	}
}
