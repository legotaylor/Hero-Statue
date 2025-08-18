/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.render.renderer;

import dev.dannytaylor.hero_statue.client.block.StatueRenderState;
import dev.dannytaylor.hero_statue.client.compatibility.HeroStatueIris;
import dev.dannytaylor.hero_statue.client.config.HeroStatueClientConfig;
import dev.dannytaylor.hero_statue.common.config.StatueRenderType;
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

public class StatueBlockEntityRenderer<T extends StatueBlockEntity> implements BlockEntityRenderer<T> {
	private final ItemDisplayContext itemDisplayContextLeft = ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
	private final ItemDisplayContext itemDisplayContextRight = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
	private static final List<String> eeFlipUpsideDown = List.of("Dinnerbone", "Grumm", "legotaylor", "dannnytaylor");
	private static final List<String> eeRainbowMode = List.of("jeb_", "RAINBOW MODE");
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
			new StatuePoseEightModel(context.getLayerModelPart(EntityModelRegistry.statuePoseEight)),
			new StatuePoseNineModel(context.getLayerModelPart(EntityModelRegistry.statuePoseNine)),
			new StatuePoseTenModel(context.getLayerModelPart(EntityModelRegistry.statuePoseTen)),
			new StatuePoseElevenModel(context.getLayerModelPart(EntityModelRegistry.statuePoseEleven)),
			new StatuePoseTwelveModel(context.getLayerModelPart(EntityModelRegistry.statuePoseTwelve)),
			new StatuePoseThirteenModel(context.getLayerModelPart(EntityModelRegistry.statuePoseThirteen)),
			new StatuePoseFourteenModel(context.getLayerModelPart(EntityModelRegistry.statuePoseFourteen))
		);
	}

	@Override
	public void render(StatueBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
		if (entity == null || entity.getWorld() == null) return;
		StatueRenderType renderType = HeroStatueClientConfig.instance.renderType.value();
		int pose = entity.getCachedState().get(StatueBlock.pose);
		StatuePoseModel model = this.models.get(renderType.equals(StatueRenderType.FASTER) ? 0 : pose);
		StatueRenderState renderState = getRenderState(entity);
		ItemStack stack = entity.getStack();
		boolean isRightHanded = pose % 2 == 0;
		matrices.push();
		matrices.translate(0.5F, 0.75F, 0.5F);
		fixRotation(entity, matrices);
		if (isPosableRenderType(renderType)) {
			rotateIfUpsideDown(entity, matrices);
			matrices.push();
			matrices.scale(0.5F, 0.5F, 0.5F);
			renderModel(entity, model, matrices, vertexConsumers, light, overlay, renderState);
			renderEyes(entity, model, matrices, vertexConsumers, light, overlay, renderState);
			matrices.pop();
		}
		if (!stack.isEmpty()) {
			matrices.push();
			matrices.scale(0.5F, 0.5F, 0.5F);
			if (renderType.equals(StatueRenderType.FASTER)) matrices.translate(0.0F, 0.25F, 0.0F);
			applyItemTransformations(model, matrices, isRightHanded);
			ClientData.minecraft.getItemRenderer().renderItem(stack, isRightHanded ? itemDisplayContextRight : itemDisplayContextLeft, light, overlay, matrices, vertexConsumers, entity.getWorld(), 1);
			matrices.pop();
		}
		matrices.pop();
	}

	private static void applyItemTransformations(StatuePoseModel model, MatrixStack matrices, boolean isRightHanded) {
		model.base.applyTransform(matrices);
		model.body.applyTransform(matrices);
		(isRightHanded ? model.rightArm : model.leftArm).applyTransform(matrices);
		(isRightHanded ? model.rightHand : model.leftHand).applyTransform(matrices);
		matrices.translate(0.0F, 0.0F, -0.05F);
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
	}

	private static void fixRotation(StatueBlockEntity entity, MatrixStack matrices) {
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(getYawFromDirection(entity.getCachedState().get(StatueBlock.facing))));
	}
	private static void rotateIfUpsideDown(StatueBlockEntity entity, MatrixStack matrices) {
		if (shouldFlipModelUpsideDown(entity)) {
			matrices.translate(0.0F, 0.37F, 0.0F);
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
		return HeroStatueClientConfig.instance.renderType.value().equals(StatueRenderType.FANCY) || (HeroStatueClientConfig.instance.renderType.value().equals(StatueRenderType.FAST) && HeroStatueIris.isIrisActive()) ? RenderLayerRegistry.getStatue(texture, renderState) : RenderLayer.getEntityCutout(texture);
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
		return HeroStatueClientConfig.instance.renderType.value().equals(StatueRenderType.FANCY) || (HeroStatueClientConfig.instance.renderType.value().equals(StatueRenderType.FAST) && HeroStatueIris.isIrisActive()) ? RenderLayerRegistry.getStatueEyes(texture, renderState) : RenderLayer.getEyes(texture);
	}

	private Identifier getTexture(StatueBlockEntity entity, String type) {
		return CommonData.idOf("textures/block/hero_statue/hero_statue" + type + (entity.getCachedState().get(StatueBlock.power) > 0 ? "_powered" : "") + ".png");
	}

	private static boolean shouldFlipModelUpsideDown(StatueBlockEntity entity) {
		Text stackName = entity.getStack().get(DataComponentTypes.CUSTOM_NAME);
		if (stackName != null) {
			String string = Formatting.strip(stackName.getString());
			return eeFlipUpsideDown.contains(string);
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
			if (eeRainbowMode.contains(string)) {
				rainbowMode = !rainbowMode;
			}
		}
		return new StatueRenderState(state.get(StatueBlock.pose), state.get(StatueBlock.facing), state.get(StatueBlock.power), state.get(StatueBlock.waterlogged), rainbowMode, shouldFlipModelUpsideDown(entity));
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

	private boolean isPosableRenderType(StatueRenderType renderType) {
		return renderType.equals(StatueRenderType.FANCY) || renderType.equals(StatueRenderType.FAST);
	}
}
