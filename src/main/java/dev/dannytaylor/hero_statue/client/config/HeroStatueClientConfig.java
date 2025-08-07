/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.config;

import dev.dannytaylor.hero_statue.common.data.CommonData;
import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

import java.nio.file.Paths;

public class HeroStatueClientConfig extends ReflectiveConfig {
	public static final HeroStatueClientConfig instance = HeroStatueClientConfig.createToml(Paths.get("config"), CommonData.id, "client", HeroStatueClientConfig.class);

	public final TrackedValue<Boolean> renderEyes = this.value(true);

	public static void bootstrap() {
	}
}
