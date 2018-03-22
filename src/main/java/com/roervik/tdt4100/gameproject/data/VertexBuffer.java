package com.roervik.tdt4100.gameproject.data;

import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer {
    private final int bufferId;
    public final int componentCount;

    public VertexBuffer(final float[] data, final int componentCount) {
        this.componentCount = componentCount;
        bufferId = glGenBuffers();
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        unbind();
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
