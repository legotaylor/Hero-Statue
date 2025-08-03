package dev.dannytaylor.hero_statue.common.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonData {
	public static final String id;
	public static final Logger logger;
	static {
		id = "hero-statue";
		logger = LoggerFactory.getLogger(id);
	}
}
