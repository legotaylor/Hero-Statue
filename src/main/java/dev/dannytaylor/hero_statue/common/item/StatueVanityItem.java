/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.item;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.component.type.FireworkExplosionComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class StatueVanityItem extends Item {
	private final ItemConvertible itemConvertible;
	public StatueVanityItem(Settings settings, ItemConvertible itemConvertible) {
		super(settings);
		this.itemConvertible = itemConvertible;
	}

	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		world.addImportantParticleClient(ParticleTypes.HEART, user.getX(), user.getY() + user.getHeight(), user.getZ(), 0.0F, 1.0F, 0.0F);
		world.addFireworkParticle(user.getX(), user.getEyeY(), user.getZ(), 0.0F, 1.0F, 0.0F, List.of(new FireworkExplosionComponent(FireworkExplosionComponent.Type.SMALL_BALL, IntList.of(0x341216, 0x1f060c), IntList.of(), false, true)));
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		user.setStackInHand(hand, new ItemStack(this.itemConvertible, stack.getCount()));
		return ActionResult.SUCCESS;
	}
}
