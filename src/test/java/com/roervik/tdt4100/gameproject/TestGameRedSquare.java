package com.roervik.tdt4100.gameproject;

import com.roervik.tdt4100.gameproject.data.IndexBuffer;
import com.roervik.tdt4100.gameproject.data.VertexArrayObject;
import com.roervik.tdt4100.gameproject.data.VertexBuffer;
import com.roervik.tdt4100.gameproject.gfx.ShaderProgram;
import com.roervik.tdt4100.gameproject.shaders.ShaderLoader;

public class TestGameRedSquare extends TestGame {
    private ShaderProgram shaderProgram;
    private VertexArrayObject vertexArrayObject;

    public TestGameRedSquare() {
        super();
    }

    public void init() {
        super.init();

        shaderProgram = ShaderLoader.createShaderProgramFromResources(
                "testShaderBasicVertex.glsl",
                "testShaderBasicFragment.glsl");

        final float[] vertices = {
                -0.5f, -0.5f, 1.0f,
                -0.5f,  0.5f, 1.0f,
                0.5f,  0.5f, 1.0f,
                0.5f, -0.5f, 1.0f
        };

        final int[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        vertexArrayObject = new VertexArrayObject(
                new IndexBuffer(indices),
                new VertexBuffer(vertices, 3));
    }

    public void render() {
        window.clear();
        shaderProgram.enable();
        vertexArrayObject.render();
        window.update();
    }

    public static void main(String[] args) {
        start(new TestGameRedSquare());
    }
}
