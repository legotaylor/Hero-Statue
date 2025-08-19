/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.common.util;

@FunctionalInterface
public interface InputCallable<A, B> {
	A call(B b) throws Exception;
}
