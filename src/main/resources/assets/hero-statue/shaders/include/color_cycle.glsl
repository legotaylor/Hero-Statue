#version 150

// Hero Statue
// Contributor(s): dannytaylor
// Github: https://github.com/legotaylor/hero-statue
// Licence: GNU LGPLv3

#moj_import <hero-statue:color_conversion.glsl>
#moj_import <minecraft:globals.glsl>

vec3 cycleColor(vec3 color, bool forward) {
	vec3 hsv = RGBtoHSV(color);
	float sumend = GameTime * 750.0;
	if (forward) hsv.x += sumend;
	else hsv.x -= sumend;
	return HSVtoRGB(hsv);
}

vec3 cycleColor(vec3 color) {
	return cycleColor(color, true);
}
