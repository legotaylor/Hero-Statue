/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;

public class StatuePoseModel extends Model {
	public ModelPart base;
	public ModelPart body;
	public ModelPart leftArm;
	public ModelPart rightArm;
	public ModelPart leftHand;
	public ModelPart rightHand;
	public ModelPart head;
	public ModelPart cape;
	public ModelPart leftLeg;
	public ModelPart rightLeg;
	public StatuePoseModel(ModelPart root) {
		super(root, RenderLayer::getEntityTranslucent);
		this.base = root.getChild("base");
		this.body = this.base.getChild("body");
		this.leftArm = this.body.getChild("left_arm");
		this.rightArm = this.body.getChild("right_arm");
		this.leftHand = this.leftArm.getChild("left_hand");
		this.rightHand = this.rightArm.getChild("right_hand");
		this.head = this.body.getChild("head");
		this.cape = this.body.getChild("cape");
		this.leftLeg = this.base.getChild("left_leg");
		this.rightLeg = this.base.getChild("right_leg");
	}
}
