
#version 430 core

layout (location = 0) out vec4 color;

in vec3 texture_coordinate;
in vec4 normal_vector;

uniform samplerCube texture_sampler;

void main() {
    //color = texture(texture_sampler, texture_coordinate);
    color = normal_vector;
}
