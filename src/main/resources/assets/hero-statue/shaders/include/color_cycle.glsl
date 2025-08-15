#version 150

// Hero Statue
// Contributor(s): dannytaylor
// Github: https://github.com/legotaylor/hero-statue
// Licence: GNU LGPLv3

#moj_import <hero-statue:color_conversion.glsl>
#moj_import <minecraft:globals.glsl>

vec3 cycleColor(vec3 color) {
	vec3 hsv = RGBtoHSV(color);
	hsv.x += (GameTime * 375.0);
	return HSVtoRGB(hsv);
}
