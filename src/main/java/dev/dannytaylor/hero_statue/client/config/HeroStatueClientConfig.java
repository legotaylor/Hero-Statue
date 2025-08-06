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

	public final TrackedValue<Boolean> renderLayers = this.value(true);
	public final TrackedValue<Float> offsets = this.value(0.001F);
	public final TrackedValue<Boolean> irisEyeZFightingFix = this.value(true);
	public final TrackedValue<Float> irisEyeZFightingFix_MinDist = this.value(0.0F);
	public final TrackedValue<Float> irisEyeZFightingFix_MaxDist = this.value(32.0F);
	public final TrackedValue<Float> irisEyeZFightingFix_MinOffset = this.value(0.001F);
	public final TrackedValue<Float> irisEyeZFightingFix_MaxOffset = this.value(0.075F);

	public static void bootstrap() {
	}
}
