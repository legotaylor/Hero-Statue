package dev.dannytaylor.hero_statue.client.entity;

import dev.dannytaylor.hero_statue.client.block.statue.model.StatuePoseZeroModel;
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
		EntityModelLayerRegistry.registerModelLayer(statuePoseOne, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseTwo, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseThree, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseFour, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseFive, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseSix, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseSeven, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseEight, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseNine, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseTen, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseEleven, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseTwelve, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseThirteen, StatuePoseZeroModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(statuePoseFourteen, StatuePoseZeroModel::getTexturedModelData);
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
