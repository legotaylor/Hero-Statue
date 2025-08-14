/*
    Hero Statue
    Contributor(s): dannytaylor
    Github: https://github.com/legotaylor/hero-statue
    Licence: GNU LGPLv3
*/

#version 150

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:globals.glsl>

uniform sampler2D Sampler0;

in float sphericalVertexDistance;
in float cylindricalVertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec2 texCoord2;

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

// RGB <-> HSV conversion; https://gist.github.com/983/e170a24ae8eba2cd174f
vec3 RGBtoHSV(vec3 color) {
	vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
	vec4 p = mix(vec4(color.bg, K.wz), vec4(color.gb, K.xy), step(color.b, color.g));
	vec4 q = mix(vec4(p.xyw, color.r), vec4(color.r, p.yzx), step(p.x, color.r));

	float d = q.x - min(q.w, q.y);
	float e = 1.0e-10;
	return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 HSVtoRGB(vec3 color){
	vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
	vec3 p = abs(fract(color.xxx + K.xyz) * 6.0 - K.www);
	return color.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), color.y);
}

void main() {
	vec4 color = texture(Sampler0, texCoord0) * vertexColor;
	if (color.a < 0.1) discard;
	#ifdef RAINBOW_MODE
	vec3 hsv = RGBtoHSV(color.rgb);
	hsv.x += (GameTime * 375.0);
	color.rgb = HSVtoRGB(hsv);
	#endif
	color *= (float(POWERED) / 15.0);
	color *= ColorModulator;
	fragColor = apply_fog(color, sphericalVertexDistance, cylindricalVertexDistance, FogEnvironmentalStart, FogEnvironmentalEnd, FogRenderDistanceStart, FogRenderDistanceEnd, FogColor);
}
