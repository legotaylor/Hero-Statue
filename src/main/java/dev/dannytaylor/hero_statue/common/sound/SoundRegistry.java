/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.sound;

import dev.dannytaylor.hero_statue.common.data.CommonData;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {
	public static final SoundEvent heroStatueGiveItem;
	public static final SoundEvent heroStatueUpdatePose;
	public static void bootstrap() {
	}
	private static SoundEvent register(String id) {
		return register(Identifier.of(CommonData.id, id));
	}
	private static SoundEvent register(Identifier id) {
		return register(id, id);
	}
	private static SoundEvent register(Identifier id, Identifier soundId) {
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(soundId));
	}
	static {
		heroStatueGiveItem = register("block.hero_statue.give_item");
		heroStatueUpdatePose = register("block.hero_statue.update_pose");
	}
}
