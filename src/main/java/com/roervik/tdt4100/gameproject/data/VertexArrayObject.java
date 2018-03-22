package com.roervik.tdt4100.gameproject.data;

import java.util.stream.IntStream;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;

public class VertexArrayObject {
    private final VertexArray vertexArray;
    private final IndexBuffer indexBuffer;

    public VertexArrayObject(IndexBuffer indexBuffer, VertexBuffer... vertexBuffers) {
        this.indexBuffer = indexBuffer;
        vertexArray = new VertexArray();
        IntStream.range(0, vertexBuffers.length).forEach(i -> vertexArray.addBuffer(vertexBuffers[i], i));
    }

    public void render() {
        vertexArray.bind();
        indexBuffer.bind();
        glDrawElements(GL_TRIANGLES, indexBuffer.vertexCount, GL_UNSIGNED_INT, 0);
        indexBuffer.unbind();
        vertexArray.unbind();
    }
}
