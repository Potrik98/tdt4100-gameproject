package com.roervik.tdt4100.gameproject.gameproject;

import com.roervik.tdt4100.gameproject.core.data.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.io.file.OBJLoader;
import com.roervik.tdt4100.gameproject.core.math.Transformation;
import com.roervik.tdt4100.gameproject.core.shaders.ObjectShader;
import com.roervik.tdt4100.gameproject.core.shaders.ShaderLoader;
import com.roervik.tdt4100.gameproject.gameproject.object.RotatingCube;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class TestGameRotatingRotatingCube extends TestGame {
    private ObjectShader shaderProgram;
    private RotatingCube rotatingCube;

    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;

    public TestGameRotatingRotatingCube() {
        super();
        viewMatrix = new Matrix4f();
        projectionMatrix = Transformation.getProjectionMatrix((float) Math.toRadians(70.0f), width, height, 0.1f, 100.0f);
    }

    public void init() {
        super.init();

        //glEnable(GL_DEPTH_TEST);

        shaderProgram = new ObjectShader(ShaderLoader.createShaderProgramFromResources(
                "shaders/ObjectVertex.glsl",
                "shaders/ObjectFragment.glsl"));

        final VertexArrayObject vertexArrayObject = OBJLoader.loadModelFromObjFile("models/cube.obj");

        rotatingCube = new RotatingCube(vertexArrayObject, shaderProgram);
        rotatingCube.startRotation(new Vector2f(1, 0));
        rotatingCube.position.z = -10;
        rotatingCube.position.x = -5;
    }

    public void update() {
        rotatingCube.update();
    }

    public void render() {
        window.clear();
        shaderProgram.enable();
        shaderProgram.setProjectionMatrix(projectionMatrix);
        shaderProgram.setViewMatrix(viewMatrix);
        rotatingCube.render();
        window.update();
    }

    public static void main(String[] args) {
        start(new TestGameRotatingRotatingCube());
    }
}
