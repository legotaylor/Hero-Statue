/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.data;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonData {
	public static final String id;
	public static final Logger logger;
	public static Identifier idOf(String path) {
		return Identifier.of(CommonData.id, path);
	}
	static {
		id = "hero-statue";
		logger = LoggerFactory.getLogger(id);
	}
}
