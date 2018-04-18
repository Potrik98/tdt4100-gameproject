package com.roervik.tdt4100.gameproject.core;

import com.roervik.tdt4100.gameproject.core.gfx.Window;
import com.roervik.tdt4100.gameproject.core.game.GameLogicComponent;
import com.roervik.tdt4100.gameproject.core.game.GameLoop;
import com.roervik.tdt4100.gameproject.core.io.input.Controller;

import static org.lwjgl.opengl.GL11.glClearColor;

public abstract class TestGame implements GameLogicComponent {
    protected final Window window;
    protected final int width = 800;
    protected final int height = 600;

    protected TestGame() {
        window = new Window("Test game", width, height);
    }

    public void init() {
        window.init();
        Controller.initInputController(window);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void update() {

    }

    public void render() {
        window.clear();

        window.update();
    }

    public boolean shouldStop() {
        return window.shouldClose();
    }

    public int getUpdateRate() {
        return 60;
    }

    public static void start(TestGame testGame) {
        final GameLoop gameLoop = new GameLoop(testGame);
        gameLoop.start();
    }
}
