package com.roervik.tdt4100.gameproject.gameproject.object;

import com.roervik.tdt4100.gameproject.core.data.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.entity.ModelEntity;
import com.roervik.tdt4100.gameproject.core.math.Transformation;
import com.roervik.tdt4100.gameproject.core.shaders.ProjectableShader;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class RotatingCube extends ModelEntity {
    private static final float size = 2.0f;
    private static final float rotationSpeed = 0.02f;

    private float rotationProgress;
    private boolean isRotating;
    private Vector3f rotationDirection;
    private Matrix4f modelMatrix;

    private Matrix4f centerToEdgeTranslation;
    private Matrix4f edgeToCenterTranslation;

    public RotatingCube(final VertexArrayObject model,
                        final ProjectableShader shaderProgram) {
        super(model, shaderProgram);

        rotationProgress = 0.0f;
        isRotating = false;
        centerToEdgeTranslation = new Matrix4f();
        edgeToCenterTranslation = new Matrix4f();
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
        if (isRotating) {
            final Matrix4f rotationMatrix = new Matrix4f().rotate(rotation);

            Matrix4f edgeToCenterTransformRotated = new Matrix4f();
            rotationMatrix.mul(edgeToCenterTranslation, edgeToCenterTransformRotated);

            Matrix4f newCenterTransform = new Matrix4f();
            centerToEdgeTranslation.mul(edgeToCenterTransformRotated, newCenterTransform);

            Vector3f newCenterTranslation = new Vector3f();
            newCenterTransform.getTranslation(newCenterTranslation);

            modelMatrix = new Matrix4f().translate(newCenterTranslation).mul(Transformation.getModelMatrix(position, rotation, scale));
        } else {
            modelMatrix = Transformation.getModelMatrix(position, rotation, scale);
        }
    }

    public void startRotation(final Vector2f direction) {
        isRotating = true;
        final Vector2f xzOffset = direction.normalize().mul(-0.5f * size);
        final Vector3f translation = new Vector3f(xzOffset.x, 0.5f * size, xzOffset.y);
        centerToEdgeTranslation = new Matrix4f().translation(translation);
        edgeToCenterTranslation = new Matrix4f().translation(translation).invert();
        rotationDirection = new Vector3f(direction.y, 0, direction.x).mul(rotationSpeed);
    }
}
