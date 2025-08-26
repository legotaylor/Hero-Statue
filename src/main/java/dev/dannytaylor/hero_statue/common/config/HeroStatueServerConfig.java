/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.config;

import dev.dannytaylor.hero_statue.common.data.CommonData;
import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

import java.nio.file.Paths;

public class HeroStatueServerConfig extends ReflectiveConfig {
	public static final HeroStatueServerConfig instance = HeroStatueServerConfig.createToml(Paths.get("config"), CommonData.id, "server", HeroStatueServerConfig.class);

	public final TrackedValue<Boolean> updateChunkStatuesOnStatueUpdate = this.value(false);

	public static void bootstrap() {
	}
}
