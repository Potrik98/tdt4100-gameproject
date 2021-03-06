package com.roervik.tdt4100.gameproject.core;

import com.roervik.tdt4100.gameproject.core.io.file.OBJLoader;
import com.roervik.tdt4100.gameproject.core.data.vertex.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.entity.ModelEntity;
import com.roervik.tdt4100.gameproject.core.math.Transformation;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.ObjectShader;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.ShaderLoader;
import org.joml.Matrix4f;

public class TestGameRotatingCube extends TestGame {
    private ObjectShader shaderProgram;
    private ModelEntity modelEntity;

    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;

    public TestGameRotatingCube() {
        super();
        viewMatrix = new Matrix4f();
        projectionMatrix = Transformation.getProjectionMatrix((float) Math.toRadians(70.0f), width, height, 0.1f, 100.0f);
    }

    public void init() {
        super.init();

        //glEnable(GL_DEPTH_TEST);

        shaderProgram = new ObjectShader(ShaderLoader.createShaderProgramFromResources(
                "shaders/ObjectVertexShader.glsl",
                "shaders/BasicFragmentShader.glsl"));

        final VertexArrayObject vertexArrayObject = OBJLoader.loadModelFromObjFile("models/cube.obj");

        modelEntity = new ModelEntity(vertexArrayObject, shaderProgram);
    }

    public void update() {
        modelEntity.position.z = -10.0f;
        modelEntity.rotation.rotate(0, 0, 0.01f);
        modelEntity.rotation.rotate(0, 0.02f, 0);
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
