
#version 430 core

layout (location = 0) out vec4 color;

in vec2 texture_coordinate;

uniform sampler2D texture_sampler;

void main() {
    color = texture(texture_sampler, texture_coordinate);
}
