package com.roervik.tdt4100.gameproject;

import com.roervik.tdt4100.gameproject.data.IndexBuffer;
import com.roervik.tdt4100.gameproject.data.VertexArrayObject;
import com.roervik.tdt4100.gameproject.data.VertexBuffer;
import com.roervik.tdt4100.gameproject.entity.ModelEntity;
import com.roervik.tdt4100.gameproject.math.Transformation;
import com.roervik.tdt4100.gameproject.shaders.ObjectShader;
import com.roervik.tdt4100.gameproject.shaders.ShaderLoader;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

public class TestGameRotatingCube extends TestGame {
    private ObjectShader shaderProgram;
    private ModelEntity modelEntity;

    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;

    public TestGameRotatingCube() {
        super();
        viewMatrix = new Matrix4f();
        projectionMatrix = Transformation.getProjectionMatrix(70, width, height, 0.1f, 100.0f);
    }

    public void init() {
        super.init();

        glEnable(GL_DEPTH_TEST);

        shaderProgram = new ObjectShader(ShaderLoader.createShaderProgramFromResources(
                "testShaderObjectVertex.glsl",
                "testShaderBasicFragment.glsl"));

        final float[] vertices = {
                 1.0f,  1.0f,  1.0f,
                -1.0f,  1.0f,  1.0f,
                -1.0f, -1.0f,  1.0f,
                 1.0f, -1.0f,  1.0f,
                 1.0f,  1.0f, -1.0f,
                -1.0f,  1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                 1.0f, -1.0f, -1.0f
        };

        final int[] indices = {
                // Front face
                0, 1, 2,
                2, 3, 0,

                // Back face
                6, 5, 4,
                4, 7, 6,

                // Top face
                3, 7, 6,
                6, 2, 3,

                // Bottom face
                0, 1, 5,
                5, 4, 0,

                // Right face
                0, 3, 7,
                7, 4, 0,

                // Left face
                1, 2, 6,
                6, 5, 1
        };

        final VertexArrayObject vertexArrayObject = new VertexArrayObject(
                new IndexBuffer(indices),
                new VertexBuffer(vertices, 3));

        modelEntity = new ModelEntity(vertexArrayObject, shaderProgram);
        modelEntity.position.z = -5.0f;
    }

    public void render() {
        window.clear();
        shaderProgram.enable();
        shaderProgram.setProjectionMatrix(projectionMatrix);
        shaderProgram.setViewMatrix(viewMatrix);
        modelEntity.render();
        window.update();
    }

    public static void main(String[] args) {
        start(new TestGameRotatingCube());
    }
}
