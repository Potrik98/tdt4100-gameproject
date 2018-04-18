package com.roervik.tdt4100.gameproject.gameproject.object;

import com.roervik.tdt4100.gameproject.core.data.texture.Texture;
import com.roervik.tdt4100.gameproject.core.data.vertex.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.entity.ModelEntity;
import com.roervik.tdt4100.gameproject.core.entity.TexturedEntity;
import com.roervik.tdt4100.gameproject.core.io.input.Controller;
import com.roervik.tdt4100.gameproject.core.io.input.Input;
import com.roervik.tdt4100.gameproject.core.math.Transformation;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.ProjectableShader;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class RotatingCube extends TexturedEntity {
    private static final float size = 2.0f;
    private static final float rotationSpeed = 0.02f;

    private float rotationProgress;
    private boolean isRotating;
    private Matrix4f modelMatrix;
    private Vector3f rotationAxis;
    private Quaternionf baseRotation;
    private Vector3f basePosition;

    private Vector4f centerToEdgeTranslation;
    private Vector4f edgeToCenterTranslation;

    public RotatingCube(final VertexArrayObject model,
                        final Texture texture,
                        final ProjectableShader shaderProgram) {
        super(model, texture, shaderProgram);

        rotationProgress = 0.0f;
        isRotating = false;
        centerToEdgeTranslation = new Vector4f();
        edgeToCenterTranslation = new Vector4f();
        modelMatrix = new Matrix4f();
        rotationAxis = new Vector3f();
        baseRotation = new Quaternionf();
        basePosition = new Vector3f();
    }

    @Override
    public void update() {
        handleInput();
        updateRotation();
        updatePosition();
        updateModelMatrix();
    }

    @Override
    public void render() {
        texture.bind();
        shaderProgram.setModelMatrix(modelMatrix);
        model.render();
    }

    public void handleInput() {
        final Input input = Controller.getInput();

        if (!isRotating) {
            if (input.isKeyPressed(GLFW_KEY_W)) startRotation(new Vector3f(1, 0, 0));
            if (input.isKeyPressed(GLFW_KEY_A)) startRotation(new Vector3f(0, 0, 1));
            if (input.isKeyPressed(GLFW_KEY_S)) startRotation(new Vector3f(-1, 0, 0));
            if (input.isKeyPressed(GLFW_KEY_D)) startRotation(new Vector3f(0, 0, -1));
        }
    }

    public boolean isRotating() {
        return isRotating;
    }

    private void updateRotation() {
        if (isRotating) {
            if (rotationProgress >= Math.PI / 2.0f) {
                stopRotation();
            } else {
                rotationProgress += rotationSpeed;
                rotation = new Quaternionf(new AxisAngle4f(rotationProgress, rotationAxis)).mul(baseRotation);
            }
        }
    }

    private void updatePosition() {
        if (isRotating) {
            final Matrix4f rotationMatrix = new Matrix4f().rotate(new AxisAngle4f(rotationProgress, rotationAxis));

            Vector4f edgeToCenterTranslationRotated = new Vector4f();
            edgeToCenterTranslation.mul(rotationMatrix, edgeToCenterTranslationRotated);

            Vector4f newCenterTranslation = new Vector4f();
            edgeToCenterTranslationRotated.add(centerToEdgeTranslation, newCenterTranslation);

            Vector4f newCenterTransform = new Vector4f();
            newCenterTranslation.rotate(rotation, newCenterTransform);
            position = new Vector3f(newCenterTranslation.x, newCenterTranslation.y, newCenterTranslation.z).add(basePosition);
        }
    }

    private void updateModelMatrix() {
        modelMatrix = Transformation.getModelMatrix(position, rotation, scale);
    }

    private void stopRotation() {
        rotation = new Quaternionf(new AxisAngle4f((float) (Math.PI / 2), rotationAxis)).mul(baseRotation);
        rotationProgress = 0;
        isRotating = false;
    }

    public void startRotation(final Vector3f rotationAxis) {
        isRotating = true;
        baseRotation = new Quaternionf(rotation);
        basePosition = new Vector3f(position);
        this.rotationAxis = rotationAxis;
        final Vector3f translation = new Vector3f(rotationAxis.z, 1, -rotationAxis.x).mul(0.5f * size);
        centerToEdgeTranslation = new Vector4f(translation.x, translation.y, translation.z, 0);
        edgeToCenterTranslation = new Vector4f(-translation.x, -translation.y, -translation.z, 0);
    }
}
