package com.roervik.tdt4100.gameproject.core.gfx;

import com.roervik.tdt4100.gameproject.core.io.input.Controller;
import com.roervik.tdt4100.gameproject.core.io.input.Input;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

public class ArchBallCamera extends Camera {
    private static final float MOUSE_SENSITIVITY_HORIZONTAL = 0.02f;
    private static final float MOUSE_SENSITIVITY_VERTICAL = 0.025f;
    private static final float MOUSE_SENSITIVITY_ZOOM = 2.5f;

    private static final float MIN_ZOOM = 1.0f;
    private static final float MAX_ZOOM = 50.0f;

    private Vector2f lastMousePosition;

    private float distance;
    private float pitch;
    private float theta;
    private final long windowHandle;

    public ArchBallCamera(final long windowHandle) {
        super();
        this.windowHandle = windowHandle;
        distance = 25.0f;
        pitch = (float) (-Math.PI);
        theta = 0.0f;
        lastMousePosition = new Vector2f();

    }

    public void update(final Vector3f pivotPoint) {
        final Input input = Controller.getInput();

        if (!input.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            double[] mx = new double[1];
            double[] my = new double[1];
            glfwGetCursorPos(windowHandle, mx, my);
            final Vector2f currentMousePosition = new Vector2f((float) mx[0], (float) my[0]);
            Vector2f mouseDelta = new Vector2f();
            currentMousePosition.sub(lastMousePosition, mouseDelta);
            lastMousePosition = currentMousePosition;
            pitch -= mouseDelta.y * MOUSE_SENSITIVITY_VERTICAL;
            theta -= mouseDelta.x * MOUSE_SENSITIVITY_HORIZONTAL;

            if (pitch > 0) pitch = 0;
            else if (pitch < -Math.PI) pitch = (float) (-Math.PI);
        }

        rotation.y = (float) (Math.PI + theta);

        distance += input.getScrollVec().y * MOUSE_SENSITIVITY_ZOOM;
        input.getScrollVec().set(0, 0);
        if (distance > MAX_ZOOM) distance = MAX_ZOOM;
        else if (distance < MIN_ZOOM) distance = MIN_ZOOM;


        float dxz = (float) Math.cos(pitch) * distance;
        float dy = (float) Math.sin(pitch) * distance;

        float dx = -dxz * (float) Math.sin(theta);
        float dz = dxz * (float) Math.cos(theta);

        rotation.x = pitch;

        position.x = pivotPoint.x - dx;
        position.y = pivotPoint.y + dy;
        position.z = pivotPoint.z - dz;
    }
}
