package com.roervik.tdt4100.gameproject.core.shaders;

import org.joml.Matrix4f;

public interface ProjectableShader {
    void setProjectionMatrix(final Matrix4f projectionMatrix);
    void setViewMatrix(final Matrix4f viewMatrix);
    void setModelMatrix(final Matrix4f modelMatrix);
}
