package com.roervik.tdt4100.gameproject;

import com.roervik.tdt4100.gameproject.data.VertexArrayObject;
import com.roervik.tdt4100.gameproject.entity.ModelEntity;
import com.roervik.tdt4100.gameproject.io.file.OBJLoader;
import com.roervik.tdt4100.gameproject.math.Transformation;
import com.roervik.tdt4100.gameproject.shaders.ObjectShader;
import com.roervik.tdt4100.gameproject.shaders.ShaderLoader;
import org.joml.Matrix4f;

public class TestGameRotatingCube extends TestGame {
    private ObjectShader shaderProgram;
    private ModelEntity modelEntity;

    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;

    public TestGameRotatingCube() {
        super();
        viewMatrix = new Matrix4f();
        projectionMatrix = Transformation.getProjectionMatrix((float) Math.toRadians(70.0f), 4, 3, -0.1f, -100.0f);
    }

    public void init() {
        super.init();

        //glEnable(GL_DEPTH_TEST);

        shaderProgram = new ObjectShader(ShaderLoader.createShaderProgramFromResources(
                "testShaderObjectVertex.glsl",
                "testShaderBasicFragment.glsl"));

        final VertexArrayObject vertexArrayObject = OBJLoader.loadModelFromObjFile("models/cube.obj");

        modelEntity = new ModelEntity(vertexArrayObject, shaderProgram);
    }

    public void update() {
        modelEntity.position.x = (float) (100.0f * Math.sin(System.currentTimeMillis() / 100.0f));
        modelEntity.position.y = (float) (100.0f * Math.cos(System.currentTimeMillis() / 100.0f));
        modelEntity.position.z = (float) (100.0f * Math.sin(1.0 + System.currentTimeMillis() / 100.0f));
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
