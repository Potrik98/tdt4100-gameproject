package com.roervik.tdt4100.gameproject;

import com.roervik.tdt4100.gameproject.game.GameLogicComponent;
import com.roervik.tdt4100.gameproject.game.GameLoop;

public class Game implements GameLogicComponent {
    private static final String title = "GameProject v1.0";
    private static final int width = 800;
    private static final int height = 600;
    private static final int updateRate = 60;

    public void init() {

    }

    public void update() {

    }

    public void render() {

    }

    public int getUpdateRate() {
        return updateRate;
    }

    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop(new Game());
        gameLoop.start();
    }
}
