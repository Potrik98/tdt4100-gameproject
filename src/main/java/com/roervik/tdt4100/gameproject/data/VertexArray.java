package com.roervik.tdt4100.gameproject.data;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArray {
    private final int vertexArrayId;

    public VertexArray() {
        vertexArrayId = glGenVertexArrays();
    }

    public void addBuffer(final VertexBuffer buffer, final int location) {
        bind();
        buffer.bind();
        glEnableVertexAttribArray(location);
        glVertexAttribPointer(location, buffer.componentCount, GL_FLOAT, false, 0, 0);
        buffer.unbind();
        unbind();
    }

    public void bind() {
        glBindVertexArray(vertexArrayId);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public void render() {
        glBindVertexArray(vertexArrayId);
        glDrawElements(GL_TRIANGLES, 0, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
