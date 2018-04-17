package com.roervik.tdt4100.gameproject.gameproject.object;

import com.roervik.tdt4100.gameproject.core.data.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.entity.ModelEntity;
import com.roervik.tdt4100.gameproject.core.math.Transformation;
import com.roervik.tdt4100.gameproject.core.shaders.ProjectableShader;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class RotatingCube extends ModelEntity {
    private static final float size = 2.0f;
    private static final float rotationSpeed = 0.02f;

    private float rotationProgress;
    private boolean isRotating;
    private Vector3f rotationDirection;
    private Matrix4f modelMatrix;

    private Vector4f centerToEdgeTranslation;
    private Vector4f edgeToCenterTranslation;

    public RotatingCube(final VertexArrayObject model,
                        final ProjectableShader shaderProgram) {
        super(model, shaderProgram);

        rotationProgress = 0.0f;
        isRotating = false;
        centerToEdgeTranslation = new Vector4f();
        edgeToCenterTranslation = new Vector4f();
        modelMatrix = new Matrix4f();
        rotationDirection = new Vector3f();
    }

    @Override
    public void update() {
        if (isRotating) {
            rotation.rotate(rotationDirection.x, rotationDirection.y, rotationDirection.z);
        }
        updateModelMatrix();
    }

    @Override
    public void render() {
        shaderProgram.setModelMatrix(modelMatrix);
        model.render();
    }

    private void updateModelMatrix() {
        modelMatrix = Transformation.getModelMatrix(position, rotation, scale);
        if (isRotating) {
            final Quaternionf inverse = new Quaternionf();
            rotation.invert(inverse);
            final Matrix4f rotationMatrix = new Matrix4f().rotate(inverse);

            Vector4f edgeToCenterTranslationRotated = new Vector4f();
            edgeToCenterTranslation.mul(rotationMatrix, edgeToCenterTranslationRotated);

            Vector4f newCenterTranslation = new Vector4f();
            centerToEdgeTranslation.mul(edgeToCenterTranslationRotated, newCenterTranslation);

            modelMatrix = new Matrix4f().translate(
                    new Vector3f(newCenterTranslation.x, newCenterTranslation.y, newCenterTranslation.z))
                    .mul(modelMatrix);
        }
    }

    public void startRotation(final Vector2f direction) {
        isRotating = true;
        final Vector2f xzOffset = direction.normalize().mul(-0.5f * size);
        final Vector3f translation = new Vector3f(xzOffset.x, 0.5f * size, xzOffset.y);
        centerToEdgeTranslation = new Vector4f(translation.x, translation.y, translation.z, 0);
        edgeToCenterTranslation = new Vector4f(-translation.x, -translation.y, -translation.z, 0);
        rotationDirection = new Vector3f(direction.y, 0, direction.x).mul(rotationSpeed);
    }
}
