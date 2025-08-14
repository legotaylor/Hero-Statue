/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.block;

import net.minecraft.util.math.Direction;

public record StatueRenderState(Integer pose, Direction facing, Integer powered, Boolean waterlogged, Boolean rainbowMode) {
}
