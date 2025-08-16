/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

package dev.dannytaylor.hero_statue.client.config;

public enum StatueRenderType {
	FANCY("fancy"),
	FAST("fast"),
	OFF("off");

	private final String id;

	StatueRenderType(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public StatueRenderType next() {
		StatueRenderType[] values = values();
		return values[(this.ordinal() + 1) % values.length];
	}
}
