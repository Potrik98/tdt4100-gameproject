package com.roervik.tdt4100.gameproject.core.gfx;

import org.joml.Vector3f;

public class Camera {
    protected Vector3f position;
    protected Vector3f rotation;

    public Camera() {
        position = new Vector3f();
        rotation = new Vector3f();
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
