
#version 430 core

layout (location = 0) in vec4 vertex;
layout (location = 2) in vec4 normalVector;

out vec3 texture_coordinate;
out vec4 normal_vector;

uniform mat4 projection_matrix;
uniform mat4 view_matrix;
uniform mat4 model_matrix;

void main() {
    texture_coordinate = vertex.xyz;
    normal_vector = normalVector;
    gl_Position = projection_matrix * view_matrix * model_matrix * vertex;
}
