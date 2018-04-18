package com.roervik.tdt4100.gameproject.core.data.vertex;

import org.lwjgl.opengl.GL20;

import java.util.HashSet;
import java.util.Set;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArrayObject {
    private final IndexBuffer indexBuffer;
    private final int vertexArrayObjectId;
    private final Set<Integer> locations;

    public static class VertexArrayObjectBuilder {
        private final int vertexArrayObjectId;
        private IndexBuffer indexBuffer;
        private Set<Integer> locations;

        public VertexArrayObjectBuilder() {
            locations = new HashSet<>();
            vertexArrayObjectId = glGenVertexArrays();
            glBindVertexArray(vertexArrayObjectId);
        }

        public VertexArrayObjectBuilder indexBuffer(final int[] indexData) {
            this.indexBuffer = new IndexBuffer(indexData);
            return this;
        }

        public VertexArrayObjectBuilder indexBuffer(final IndexBuffer indexBuffer) {
            this.indexBuffer = indexBuffer;
            return this;
        }

        public VertexArrayObjectBuilder vertexBuffer(final float[] vertexData,
                                                     final int componentCount,
                                                     final int location) {
            locations.add(location);
            final int vertexBufferId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vertexBufferId);
            glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
            glVertexAttribPointer(location, componentCount, GL_FLOAT, false, 0, 0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            return this;
        }

        public VertexArrayObject build() {
            return new VertexArrayObject(vertexArrayObjectId, indexBuffer, locations);
        }
    }

    public static VertexArrayObjectBuilder builder() {
        return new VertexArrayObjectBuilder();
    }

    private VertexArrayObject(final int vertexArrayObjectId,
                              final IndexBuffer indexBuffer,
                              final Set<Integer> locations) {
        this.vertexArrayObjectId = vertexArrayObjectId;
        this.indexBuffer = indexBuffer;
        this.locations = locations;
    }

    public void render() {
        glBindVertexArray(vertexArrayObjectId);
        indexBuffer.bind();
        locations.forEach(GL20::glEnableVertexAttribArray);
        glDrawElements(GL_TRIANGLES, indexBuffer.vertexCount, GL_UNSIGNED_INT, 0);
        locations.forEach(GL20::glDisableVertexAttribArray);
        glBindVertexArray(0);
    }
}
