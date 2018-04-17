package com.roervik.tdt4100.gameproject.core.math;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transformation {
    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    public static Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRatio = width / height;
        return new Matrix4f().identity().perspective(fov, aspectRatio, zNear, zFar);
    }

    public static Matrix4f getOrthographicMatrix(float left, float right, float bottom, float top, float near, float far) {
        return new Matrix4f().identity().ortho(left, right, bottom, top, near, far);
    }

    public static Matrix4f getModelMatrix(Vector3f position, Quaternionf rotation, Vector3f scale) {
        return new Matrix4f().identity().translate(position)
                .rotate(rotation)
                .scale(scale);
    }
}
