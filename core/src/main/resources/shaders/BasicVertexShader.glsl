
#version 430 core

layout (location = 0) in vec4 vertex;

void main() {
    gl_Position = vertex;
}
