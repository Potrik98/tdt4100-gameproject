package com.roervik.tdt4100.gameproject.core.io.input;

import com.roervik.tdt4100.gameproject.core.gfx.Window;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;


public class Input {
    private final Vector2f currentMousePosition;
    private final Vector2f scrollVec;

    private boolean inWindow = false;
    private boolean leftMouseButtonPressed = false;
    private boolean rightMouseButtonPressed = false;

    private boolean[] isKeyPressed;

    public Input() {
        currentMousePosition = new Vector2f(0, 0);
        scrollVec = new Vector2f();
        isKeyPressed = new boolean[512];
    }

    public void init(final Window window) {
        glfwSetInputMode(window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwSetCursorPosCallback(window.getWindowHandle(), new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                currentMousePosition.x = (float) xpos;
                currentMousePosition.y = (float) ypos;
            }
        });
        glfwSetCursorEnterCallback(window.getWindowHandle(), new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long window, boolean entered) {
                inWindow = entered;
            }
        });
        glfwSetMouseButtonCallback(window.getWindowHandle(), new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                leftMouseButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
                rightMouseButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
            }
        });
        glfwSetKeyCallback(window.getWindowHandle(), new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                isKeyPressed[key] = action != GLFW_RELEASE;
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(window, true);
                }
            }
        });
        glfwSetScrollCallback(window.getWindowHandle(), new GLFWScrollCallback() {
            public void invoke(long window, double xoffset, double yoffset) {
                scrollVec.x = (float) xoffset;
                scrollVec.y = (float) yoffset;
            }
        });
    }

    public boolean isInWindow() {
        return inWindow;
    }

    public boolean isKeyPressed(final int keyCode) {
        return isKeyPressed[keyCode];
    }

    public Vector2f getCurrentMousePosition() {
        return currentMousePosition;
    }

    public Vector2f getScrollVec() {
        return scrollVec;
    }

    public boolean isLeftMouseButtonPressed() {
        return leftMouseButtonPressed;
    }

    public boolean isRightMouseButtonPressed() {
        return rightMouseButtonPressed;
    }
}