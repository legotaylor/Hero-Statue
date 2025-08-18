/*
    Hero Statue
    Contributor(s): dannytaylor, Phantazap
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.render.model;

import net.minecraft.client.model.*;

public class StatuePoseEightModel extends StatuePoseModel {
	public StatuePoseEightModel(ModelPart root) {
		super(root);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create(), pivot(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData body = base.addChild("body", ModelPartBuilder.create(), pivot(0.0F, -12.0F, 1.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = body.addChild("cube_r1", ModelPartBuilder.create().uv(50, 13).cuboid(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), pivot(0.0F, -4.75F, -0.65F, 0.0873F, 0.0F, 0.0F));

		ModelPartData left_arm = body.addChild("left_arm", ModelPartBuilder.create().uv(32, 65).cuboid(-3.1642F, -1.4142F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
			.uv(68, 43).cuboid(-3.4142F, -2.4142F, -3.0F, 0.0F, 4.0F, 6.0F, new Dilation(0.0F))
			.uv(50, 37).cuboid(-3.4142F, -2.4142F, 3.0F, 5.0F, 4.0F, 0.0F, new Dilation(0.0F))
			.uv(64, 71).cuboid(1.5858F, -2.4142F, -3.0F, 0.0F, 4.0F, 6.0F, new Dilation(0.0F))
			.uv(70, 29).cuboid(-3.4142F, -2.4142F, -3.0F, 5.0F, 4.0F, 0.0F, new Dilation(0.0F))
			.uv(64, 65).cuboid(-3.4142F, -2.4142F, -3.0F, 5.0F, 0.0F, 6.0F, new Dilation(0.0F)), pivot(6.0F, -12.0F, 0.0F, 0.0F, -0.3054F, -2.3562F));

		ModelPartData left_hand = left_arm.addChild("left_hand", ModelPartBuilder.create(), pivot(0.25F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData left_hand_r1 = left_hand.addChild("left_hand_r1", ModelPartBuilder.create().uv(90, 4).cuboid(-1.4142F, -1.0F, -1.0F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), pivot(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ModelPartData right_arm = body.addChild("right_arm", ModelPartBuilder.create().uv(48, 65).cuboid(-0.8358F, -1.4142F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
			.uv(68, 53).cuboid(3.4142F, -2.4142F, -3.0F, 0.0F, 4.0F, 6.0F, new Dilation(0.0F))
			.uv(70, 33).cuboid(-1.5858F, -2.4142F, 3.0F, 5.0F, 4.0F, 0.0F, new Dilation(0.0F))
			.uv(74, 13).cuboid(-1.5858F, -2.4142F, -3.0F, 0.0F, 4.0F, 6.0F, new Dilation(0.0F))
			.uv(74, 23).cuboid(-1.5858F, -2.4142F, -3.0F, 5.0F, 4.0F, 0.0F, new Dilation(0.0F))
			.uv(68, 37).cuboid(-1.5858F, -2.4142F, -3.0F, 5.0F, 0.0F, 6.0F, new Dilation(0.0F)), pivot(-6.0F, -12.0F, 0.0F, 0.0F, 0.3054F, 2.3562F));

		ModelPartData right_hand = right_arm.addChild("right_hand", ModelPartBuilder.create(), pivot(-0.25F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData right_hand_r1 = right_hand.addChild("right_hand_r1", ModelPartBuilder.create().uv(86, 8).cuboid(1.4142F, -1.0F, -1.0F, 0.0F, 0.0F, 0.0F, new Dilation(0.0F)), pivot(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-6.5F, -8.5F, -5.5F, 13.0F, 11.0F, 11.0F, new Dilation(0.0F))
			.uv(0, 32).cuboid(-7.5F, 3.5F, -2.5F, 15.0F, 0.0F, 10.0F, new Dilation(0.0F))
			.uv(48, 0).cuboid(-7.5F, -9.5F, 7.5F, 15.0F, 13.0F, 0.0F, new Dilation(0.0F))
			.uv(48, 42).cuboid(-7.5F, -9.5F, -2.5F, 0.0F, 13.0F, 10.0F, new Dilation(0.0F))
			.uv(28, 42).cuboid(7.5F, -9.5F, -2.5F, 0.0F, 13.0F, 10.0F, new Dilation(0.0F))
			.uv(0, 22).cuboid(-7.5F, -9.5F, -2.5F, 15.0F, 0.0F, 10.0F, new Dilation(0.0F))
			.uv(50, 29).cuboid(-3.5F, -9.5F, 7.5F, 7.0F, 5.0F, 3.0F, new Dilation(0.0F))
			.uv(78, 5).cuboid(-6.5F, 2.5F, -5.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
			.uv(78, 9).cuboid(4.5F, 2.5F, -5.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), pivot(0.0F, -12.5F, -0.5F, -0.3054F, 0.0F, 0.0F));

		ModelPartData cape = body.addChild("cape", ModelPartBuilder.create(), pivot(-1.0F, -14.0F, 2.0F, 0.0F, 0.0F, 0.0F));

		ModelPartData cube_r2 = cape.addChild("cube_r2", ModelPartBuilder.create().uv(0, 42).cuboid(-7.0F, -6.0F, 0.0F, 14.0F, 21.0F, 0.0F, new Dilation(0.001F)), pivot(1.0F, 5.0F, 1.0F, 0.6981F, 0.0F, 0.0F));

		ModelPartData left_leg = base.addChild("left_leg", ModelPartBuilder.create().uv(0, 63).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), pivot(2.5F, -11.25F, 1.0F, -0.044F, 0.1308F, -0.6166F));

		ModelPartData cube_r3 = left_leg.addChild("cube_r3", ModelPartBuilder.create().uv(76, 71).cuboid(0.0F, 0.0F, -2.0F, 0.0F, 1.0F, 4.0F, new Dilation(0.0F)), pivot(-2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		ModelPartData cube_r4 = left_leg.addChild("cube_r4", ModelPartBuilder.create().uv(60, 37).cuboid(0.0F, 0.0F, -2.0F, 0.0F, 1.0F, 4.0F, new Dilation(0.0F)), pivot(2.0F, 3.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		ModelPartData cube_r5 = left_leg.addChild("cube_r5", ModelPartBuilder.create().uv(16, 63).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 0.0F, new Dilation(0.001F)), pivot(0.0F, 3.0F, 2.0F, 0.3927F, 0.0F, 0.0F));

		ModelPartData cube_r6 = left_leg.addChild("cube_r6", ModelPartBuilder.create().uv(50, 41).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 0.0F, new Dilation(0.001F)), pivot(0.0F, 3.0F, -2.0F, -0.3927F, 0.0F, 0.0F));

		ModelPartData right_leg = base.addChild("right_leg", ModelPartBuilder.create().uv(16, 65).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), pivot(-2.5F, -11.25F, 1.0F, 0.0443F, -0.1744F, 0.6032F));

		ModelPartData cube_r7 = right_leg.addChild("cube_r7", ModelPartBuilder.create().uv(78, 0).cuboid(0.0F, 0.0F, -2.0F, 0.0F, 1.0F, 4.0F, new Dilation(0.0F)), pivot(-2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

		ModelPartData cube_r8 = right_leg.addChild("cube_r8", ModelPartBuilder.create().uv(76, 76).cuboid(0.0F, 0.0F, -2.0F, 0.0F, 1.0F, 4.0F, new Dilation(0.0F)), pivot(2.0F, 3.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

		ModelPartData cube_r9 = right_leg.addChild("cube_r9", ModelPartBuilder.create().uv(68, 63).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 0.0F, new Dilation(0.001F)), pivot(0.0F, 3.0F, 2.0F, 0.3927F, 0.0F, 0.0F));

		ModelPartData cube_r10 = right_leg.addChild("cube_r10", ModelPartBuilder.create().uv(16, 64).cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 0.0F, new Dilation(0.001F)), pivot(0.0F, 3.0F, -2.0F, -0.3927F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
}
