
#version 430 core

layout (location = 0) in vec4 vertex;
layout (location = 1) in vec2 textureCoordinate;

out vec2 texture_coordinate;

uniform mat4 projection_matrix;
uniform mat4 view_matrix;
uniform mat4 model_matrix;

void main() {
    texture_coordinate = textureCoordinate;
    gl_Position = projection_matrix * view_matrix * model_matrix * vertex;
}
