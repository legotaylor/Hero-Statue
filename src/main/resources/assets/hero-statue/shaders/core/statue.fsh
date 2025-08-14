#version 150

// Hero Statue
// Contributor(s): dannytaylor
// Github: https://github.com/legotaylor/hero-statue
// Licence: GNU LGPLv3

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:globals.glsl>
#moj_import <hero-statue:color_conversion.glsl>

uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;

out vec4 fragColor;

#ifndef POSE
#define POSE 0
#endif

#ifndef YAW
#define YAW 0
#endif

#ifndef POWERED
#define POWERED 0
#endif

// You can also check waterlogged by using #ifdef WATERLOGGED #endif

void main() {
	vec4 color = texture(Sampler0, texCoord0);
	if (color.a < 0.1) discard;
	#ifdef RAINBOW_MODE
	if (POWERED > 0) {
		vec3 hsv = RGBtoHSV(color.rgb);
		hsv.x += (GameTime * 375.0);
		color.rgb = HSVtoRGB(hsv);

	}
	#endif
	color *= vertexColor * ColorModulator;
	color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
	color *= lightMapColor;
	fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
