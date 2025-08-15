/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.item;

import dev.dannytaylor.hero_statue.common.sound.SoundRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StatueVanityItem extends Item {
	private final ItemConvertible itemConvertible;
	public StatueVanityItem(Settings settings, ItemConvertible itemConvertible) {
		super(settings);
		this.itemConvertible = itemConvertible;
	}

	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		for (int i = 0; i < (world.random.nextInt(22) + 8) * stack.getCount(); ++i) {
			world.addImportantParticleClient(ParticleTypes.HEART, user.getX() + ((world.random.nextDouble() - 0.5F) * 2.0F), user.getY() + (user.getHeight() / 2.0F) + ((world.random.nextDouble() - 0.5F) * 2.0F), user.getZ() + ((world.random.nextDouble() - 0.5F) * 2.0F), ((world.random.nextDouble() - 0.5F) * 2.0F), 0.05F, ((world.random.nextDouble() - 0.5F) * 2.0F));
		}
		world.playSound(user, user.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS);
		world.playSound(user, user.getBlockPos(), SoundRegistry.heroStatueUpdatePose, SoundCategory.PLAYERS);
		user.increaseStat(Stats.USED.getOrCreateStat(this), stack.getCount());
		user.setStackInHand(hand, stack.copyComponentsToNewStack(this.itemConvertible, stack.getCount()));
		return ActionResult.SUCCESS;
	}
}
