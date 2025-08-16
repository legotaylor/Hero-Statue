#version 150

// Hero Statue
// Contributor(s): dannytaylor
// Github: https://github.com/legotaylor/hero-statue
// Licence: GNU LGPLv3

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <hero-statue:color_cycle.glsl>

uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

// You can check if these are defined using #ifdef <UNIFORM> #endif.
// POSE :: Returns the current pose. (0-14)
// YAW :: Returns the current direction in degrees. (0, 90, 180, 270)
// POWERED :: Returns the received redstone input. (0-15)
// WATERLOGGED :: If exists, the block is waterlogged.
// RAINBOW_MODE :: If exists, the jeb_/RAINBOW MODE easter egg is enabled for that block.
// FLIP_MODEL :: If exists, the Dinnerbone/Grumm/legotaylor/dannnytaylor easter egg is enabled for that block.

void main() {
	vec4 color = texture(Sampler0, texCoord0);
	if (color.a < 0.1) discard;
	#ifdef POWERED
	#ifdef RAINBOW_MODE
	if (POWERED > 0) {
		bool forward = true;
		#ifdef FLIP_MODEL
		forward = false;
		#endif
		color.rgb = cycleColor(color.rgb, forward);
	}
	#endif
	#endif
	color *= (float(POWERED) / 15.0);
	color *= vertexColor * ColorModulator;
	fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
