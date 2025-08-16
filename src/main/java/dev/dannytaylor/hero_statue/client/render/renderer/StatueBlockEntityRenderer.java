/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.render.renderer;

import dev.dannytaylor.hero_statue.client.block.StatueRenderState;
import dev.dannytaylor.hero_statue.client.config.HeroStatueClientConfig;
import dev.dannytaylor.hero_statue.client.data.ClientData;
import dev.dannytaylor.hero_statue.client.render.model.*;
import dev.dannytaylor.hero_statue.client.render.pipeline.RenderLayerRegistry;
import dev.dannytaylor.hero_statue.common.block.StatueBlock;
import dev.dannytaylor.hero_statue.common.block.StatueBlockEntity;
import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class StatueBlockEntityRenderer implements BlockEntityRenderer<StatueBlockEntity> {
	private final List<StatuePoseModel> models;
	public StatueBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		this.models = List.of(
			new StatuePoseZeroModel(context.getLayerModelPart(EntityModelRegistry.statuePoseZero)),
			new StatuePoseOneModel(context.getLayerModelPart(EntityModelRegistry.statuePoseOne)),
			new StatuePoseTwoModel(context.getLayerModelPart(EntityModelRegistry.statuePoseTwo)),
			new StatuePoseThreeModel(context.getLayerModelPart(EntityModelRegistry.statuePoseThree)),
			new StatuePoseFourModel(context.getLayerModelPart(EntityModelRegistry.statuePoseFour)),
			new StatuePoseFiveModel(context.getLayerModelPart(EntityModelRegistry.statuePoseFive)),
			new StatuePoseSixModel(context.getLayerModelPart(EntityModelRegistry.statuePoseSix)),
			new StatuePoseSevenModel(context.getLayerModelPart(EntityModelRegistry.statuePoseSeven)),
			new StatuePoseModel(context.getLayerModelPart(EntityModelRegistry.statuePoseEight)),
			new StatuePoseModel(context.getLayerModelPart(EntityModelRegistry.statuePoseNine)),
			new StatuePoseModel(context.getLayerModelPart(EntityModelRegistry.statuePoseTen)),
			new StatuePoseModel(context.getLayerModelPart(EntityModelRegistry.statuePoseEleven)),
			new StatuePoseModel(context.getLayerModelPart(EntityModelRegistry.statuePoseTwelve)),
			new StatuePoseModel(context.getLayerModelPart(EntityModelRegistry.statuePoseThirteen)),
			new StatuePoseFourteenModel(context.getLayerModelPart(EntityModelRegistry.statuePoseFourteen))
		);
	}

	@Override
	public void render(StatueBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
		if (entity == null || entity.getWorld() == null) return;
		int pose = entity.getCachedState().get(StatueBlock.pose);
		StatuePoseModel model = this.models.get(pose);
		StatueRenderState renderState = getRenderState(entity);
		ItemStack stack = entity.getStack();
		boolean isRightHanded = pose % 2 == 0;
		matrices.push();
		matrices.translate(0.5F, 0.75F, 0.5F);
		fixRotation(entity, matrices);
		rotateIfUpsideDown(entity, matrices);
		matrices.push();
		switch (HeroStatueClientConfig.instance.renderType.value()) {
			case FAST -> {
				matrices.translate(0.0F, shouldFlipModelUpsideDown(entity) ? 1.31F : 1.16F, 0.0F);
				matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
				ClientData.minecraft.getItemRenderer().renderItem(entity.getCachedState().getBlock().asItem().getDefaultStack(), ItemDisplayContext.HEAD, light, overlay, matrices, vertexConsumers, entity.getWorld(), 1);
			}
			case FANCY -> {
				matrices.scale(0.5F, 0.5F, 0.5F);
				renderModel(entity, model, matrices, vertexConsumers, light, overlay, renderState);
				renderEyes(entity, model, matrices, vertexConsumers, light, overlay, renderState);
			}
			case OFF -> {}
		}
		matrices.pop();
		if (!stack.isEmpty()) {
			matrices.push();
			matrices.scale(0.5F, 0.5F, 0.5F);
			model.base.applyTransform(matrices);
			model.body.applyTransform(matrices);
			(isRightHanded ? model.rightArm : model.leftArm).applyTransform(matrices);
			(isRightHanded ? model.rightHand : model.leftHand).applyTransform(matrices);
			matrices.translate(0.0F, 0.0F, -0.05F);
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
			ClientData.minecraft.getItemRenderer().renderItem(stack, isRightHanded ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 1);
			matrices.pop();
		}
		matrices.pop();
	}

	private static void fixRotation(StatueBlockEntity entity, MatrixStack matrices) {
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(getYawFromDirection(entity.getCachedState().get(StatueBlock.facing))));
	}
	private static void rotateIfUpsideDown(StatueBlockEntity entity, MatrixStack matrices) {
		if (shouldFlipModelUpsideDown(entity)) {
			matrices.translate(0.0F, 0.749F, 0.0F);
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
		}
	}

	public static float getYawFromDirection(Direction direction) {
		return switch (direction) {
			case NORTH -> 180.0F;
			case WEST -> 90.0F;
			case EAST -> -90.0F;
			default -> 0.0F;
		};
	}

	private void renderModel(StatueBlockEntity entity, StatuePoseModel model, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, StatueRenderState renderState) {
		matrices.push();
		model.render(matrices, vertexConsumers.getBuffer(getModelLayer(entity, renderState)), light, overlay, -1);
		matrices.pop();
	}

	private RenderLayer getModelLayer(StatueBlockEntity entity, StatueRenderState renderState) {
		Identifier texture = getTexture(entity, "");
		return HeroStatueClientConfig.instance.useVanillaShaders.value() ? RenderLayer.getEntityCutout(texture) : RenderLayerRegistry.getStatue(texture, renderState);
	}

	private void renderEyes(StatueBlockEntity entity, StatuePoseModel model, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, StatueRenderState renderState) {
		if (HeroStatueClientConfig.instance.renderEyes.value()) {
			matrices.push();
			model.render(matrices, vertexConsumers.getBuffer(getEyeLayer(entity, renderState)), light, overlay, -1);
			matrices.pop();
		}
	}

	private RenderLayer getEyeLayer(StatueBlockEntity entity, StatueRenderState renderState) {
		Identifier texture = getTexture(entity, "_eyes");
		return HeroStatueClientConfig.instance.useVanillaShaders.value() ? RenderLayer.getEyes(texture) : RenderLayerRegistry.getStatueEyes(texture, renderState);
	}

	private Identifier getTexture(StatueBlockEntity entity, String type) {
		return CommonData.idOf("textures/block/hero_statue/hero_statue" + type + (entity.getCachedState().get(StatueBlock.powered) ? "_powered" : "") + ".png");
	}

	private static boolean shouldFlipModelUpsideDown(StatueBlockEntity entity) {
		Text stackName = entity.getStack().get(DataComponentTypes.CUSTOM_NAME);
		if (stackName != null) {
			String string = Formatting.strip(stackName.getString());
			return List.of("Dinnerbone", "Grumm", "legotaylor", "dannnytaylor").contains(string);
		}
		return false;
	}

	public static StatueRenderState getRenderState(StatueBlockEntity entity) {
		BlockState state = entity.getCachedState();
		boolean rainbowMode = HeroStatueClientConfig.instance.rainbowMode.value();
		Text stackName = entity.getStack().get(DataComponentTypes.CUSTOM_NAME);
		if (state.get(StatueBlock.rainbow)) rainbowMode = !rainbowMode;
		if (stackName != null) {
			String string = Formatting.strip(stackName.getString());
			if (List.of("jeb_", "RAINBOW MODE").contains(string)) {
				rainbowMode = !rainbowMode;
			}
		}
		return new StatueRenderState(state.get(StatueBlock.pose), state.get(StatueBlock.facing), entity.getWorld() != null ? entity.getWorld().getReceivedRedstonePower(entity.getPos()) : 0, state.get(StatueBlock.waterlogged), rainbowMode, shouldFlipModelUpsideDown(entity));
	}

	public static List<Identifier> getKnownModelTextures() {
		return List.of(
			CommonData.idOf("textures/block/hero_statue/hero_statue.png"),
			CommonData.idOf("textures/block/hero_statue/hero_statue_powered.png")
		);
	}

	public static List<Identifier> getKnownEyeTextures() {
		return List.of(
			CommonData.idOf("textures/block/hero_statue/hero_statue_eyes.png"),
			CommonData.idOf("textures/block/hero_statue/hero_statue_eyes_powered.png")
		);
	}
}
