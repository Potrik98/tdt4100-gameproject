package com.roervik.tdt4100.gameproject.gameproject;

import com.roervik.tdt4100.gameproject.core.data.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.io.file.OBJLoader;
import com.roervik.tdt4100.gameproject.core.math.Transformation;
import com.roervik.tdt4100.gameproject.core.shaders.ObjectShader;
import com.roervik.tdt4100.gameproject.core.shaders.ShaderLoader;
import com.roervik.tdt4100.gameproject.gameproject.object.RotatingCube;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

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
        //rotatingCube.startRotation(new Vector2f(0, 1));
        rotatingCube.position.z = -10;
    }

    public void update() {
        rotatingCube.update();

        if (!rotatingCube.isRotating()) {
            final int randDir = new java.util.Random().nextInt(4);
            if (randDir == 0) rotatingCube.startRotation(new Vector2f(0, 1));
            if (randDir == 1) rotatingCube.startRotation(new Vector2f(0, -1));
            if (randDir == 2) rotatingCube.startRotation(new Vector2f(1, 0));
            if (randDir == 3) rotatingCube.startRotation(new Vector2f(-1, 0));
        }

        /*Vector3f a = new Vector3f();
        rotatingCube.rotation.getEulerAnglesXYZ(a);
        Quaternionf q1 = new Quaternionf().rotateX(0.01f);
        Quaternionf q2 = new Quaternionf().rotateZ(0.01f);
        Quaternionf q3 = new Quaternionf();
        q2.rotateLocalX((float) (-Math.PI / 2), q3);
        if (a.x < Math.PI / 2) {
            rotatingCube.rotation.mul(q1);
        }
        else rotatingCube.rotation.mul(q3);*/
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
