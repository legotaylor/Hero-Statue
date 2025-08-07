/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.render.renderer;

import dev.dannytaylor.hero_statue.client.config.HeroStatueClientConfig;
import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.client.render.model.*;
import dev.dannytaylor.hero_statue.client.render.pipeline.RenderLayerRegistry;
import dev.dannytaylor.hero_statue.common.block.StatueBlock;
import dev.dannytaylor.hero_statue.common.block.StatueBlockEntity;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
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
			new StatuePoseOneModel(context.getLayerModelPart(EntityModelRegistry.statuePoseOne)),
			new StatuePoseTwoModel(context.getLayerModelPart(EntityModelRegistry.statuePoseTwo)),
			new StatuePoseOneModel(context.getLayerModelPart(EntityModelRegistry.statuePoseThree)),
			new StatuePoseTwoModel(context.getLayerModelPart(EntityModelRegistry.statuePoseFour)),
			new StatuePoseOneModel(context.getLayerModelPart(EntityModelRegistry.statuePoseFive)),
			new StatuePoseTwoModel(context.getLayerModelPart(EntityModelRegistry.statuePoseSix)),
			new StatuePoseOneModel(context.getLayerModelPart(EntityModelRegistry.statuePoseSeven)),
			new StatuePoseTwoModel(context.getLayerModelPart(EntityModelRegistry.statuePoseEight)),
			new StatuePoseOneModel(context.getLayerModelPart(EntityModelRegistry.statuePoseNine)),
			new StatuePoseTwoModel(context.getLayerModelPart(EntityModelRegistry.statuePoseTen)),
			new StatuePoseOneModel(context.getLayerModelPart(EntityModelRegistry.statuePoseEleven)),
			new StatuePoseTwoModel(context.getLayerModelPart(EntityModelRegistry.statuePoseTwelve)),
			new StatuePoseOneModel(context.getLayerModelPart(EntityModelRegistry.statuePoseThirteen)),
			new StatuePoseTwoModel(context.getLayerModelPart(EntityModelRegistry.statuePoseFourteen))
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

			matrices.push();
			model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(CommonData.idOf("textures/block/hero_statue/hero_statue" + (entity.getCachedState().get(StatueBlock.powered) ? "_powered" : "") + ".png"))), light, overlay, -1);
			matrices.pop();

			renderEyes(entity, model, matrices, vertexConsumers, light, overlay, cameraPos);

			ItemStack stack = entity.getStack();
			if (!stack.isEmpty()) {
				matrices.push();
				boolean isRightHanded = pose % 2 == 0;
				model.base.applyTransform(matrices);
				model.body.applyTransform(matrices);
				(isRightHanded ? model.rightArm : model.leftArm).applyTransform(matrices);
				(isRightHanded ? model.rightHand : model.leftHand).applyTransform(matrices);
				matrices.translate(0.0F, 0.0F, -0.05F);
				matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-135));
				ClientData.minecraft.getItemRenderer().renderItem(entity.getStack(), isRightHanded ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND, getLight(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
				matrices.pop();
			}
			matrices.pop();
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

	private void renderEyes(StatueBlockEntity entity, StatuePoseModel model, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
		if (HeroStatueClientConfig.instance.renderEyes.value()) {
			matrices.push();
			model.render(matrices, vertexConsumers.getBuffer(getEyeLayer(entity)), light, overlay, -1);
			matrices.pop();
		}
	}

	private RenderLayer getEyeLayer(StatueBlockEntity entity) {
		Identifier texture = getTexture(entity, "_eyes");
		return RenderLayerRegistry.getStatueEyes(texture);
	}

	private Identifier getTexture(StatueBlockEntity entity, String type) {
		return CommonData.idOf("textures/block/hero_statue/hero_statue" + type + (entity.getCachedState().get(StatueBlock.powered) ? "_powered" : "") + ".png");
	}
}
