/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.data;

import net.minecraft.client.MinecraftClient;

public class ClientData {
	public static final MinecraftClient minecraft;
	static {
		minecraft = MinecraftClient.getInstance();
	}
}
