package com.roervik.tdt4100.gameproject.gameproject;

import com.roervik.tdt4100.gameproject.core.gfx.Window;
import com.roervik.tdt4100.gameproject.core.game.GameLogicComponent;
import com.roervik.tdt4100.gameproject.core.game.GameLoop;

import static org.lwjgl.opengl.GL11.glClearColor;

public class Game implements GameLogicComponent {
    private static final String title = "GameProject v1.0";
    private static final int width = 800;
    private static final int height = 600;
    private static final int updateRate = 60;

    private final Window window;

    public Game() {
        window = new Window(title, width, height);
    }

    public void init() {
        window.init();

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
        return updateRate;
    }

    public static void main(String[] args) {
        final GameLoop gameLoop = new GameLoop(new Game());
        gameLoop.start();
    }
}
