#version 150

// Hero Statue
// Contributor(s): dannytaylor
// Github: https://github.com/legotaylor/hero-statue
// Licence: GNU LGPLv3

#moj_import <minecraft:light.glsl>
#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:dynamictransforms.glsl>
#moj_import <minecraft:projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV1;
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

out float sphericalVertexDistance;
out float cylindricalVertexDistance;
out vec4 vertexColor;
out vec4 lightMapColor;
out vec4 overlayColor;
out vec2 texCoord0;

// You can check if these are defined using #ifdef <UNIFORM> #endif.
// POSE :: Returns the current pose. (0-14)
// YAW :: Returns the current direction in degrees. (0, 90, 180, 270)
// POWERED :: Returns the received redstone input. (0-15)
// WATERLOGGED :: If exists, the block is waterlogged.
// RAINBOW_MODE :: If exists, the jeb_/RAINBOW MODE easter egg is enabled for that block.
// FLIP_MODEL :: If exists, the Dinnerbone/Grumm/legotaylor/dannnytaylor easter egg is enabled for that block.

void main() {
	gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
	sphericalVertexDistance = fog_spherical_distance(Position);
	cylindricalVertexDistance = fog_cylindrical_distance(Position);
	vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color);
	lightMapColor = texelFetch(Sampler2, UV2 / 16, 0);
	overlayColor = texelFetch(Sampler1, UV1, 0);
	texCoord0 = UV0;
}
