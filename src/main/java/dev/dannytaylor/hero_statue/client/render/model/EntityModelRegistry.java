/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.render.model;

import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class EntityModelRegistry {
	public static final EntityModelLayer statuePoseZero;
	public static final EntityModelLayer statuePoseOne;
	public static final EntityModelLayer statuePoseTwo;
	public static final EntityModelLayer statuePoseThree;
	public static final EntityModelLayer statuePoseFour;
	public static final EntityModelLayer statuePoseFive;
	public static final EntityModelLayer statuePoseSix;
	public static final EntityModelLayer statuePoseSeven;
	public static final EntityModelLayer statuePoseEight;
	public static final EntityModelLayer statuePoseNine;
	public static final EntityModelLayer statuePoseTen;
	public static final EntityModelLayer statuePoseEleven;
	public static final EntityModelLayer statuePoseTwelve;
	public static final EntityModelLayer statuePoseThirteen;
	public static final EntityModelLayer statuePoseFourteen;
	public static void bootstrap() {
		EntityModelLayerRegistry.registerModelLayer(statuePoseZero, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseOne, StatuePoseOneModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseTwo, StatuePoseTwoModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseThree, StatuePoseThreeModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseFour, StatuePoseFourModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseFive, StatuePoseFiveModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseSix, StatuePoseSixModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseSeven, StatuePoseSevenModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseEight, StatuePoseEightModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseNine, StatuePoseNineModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseTen, StatuePoseTenModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseEleven, StatuePoseElevenModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseTwelve, StatuePoseTwelveModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseThirteen, StatuePoseThirteenModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseFourteen, StatuePoseFourteenModel::getTexturedModelData);
	}
	static {
		statuePoseZero = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_0");
		statuePoseOne = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_1");
		statuePoseTwo = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_2");
		statuePoseThree = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_3");
		statuePoseFour = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_4");
		statuePoseFive = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_5");
		statuePoseSix = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_6");
		statuePoseSeven = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_7");
		statuePoseEight = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_8");
		statuePoseNine = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_9");
		statuePoseTen = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_10");
		statuePoseEleven = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_11");
		statuePoseTwelve = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_12");
		statuePoseThirteen = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_13");
		statuePoseFourteen = new EntityModelLayer(CommonData.idOf("hero_statue"), "pose_14");
	}
}
