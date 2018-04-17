package com.roervik.tdt4100.gameproject.core.game;

public interface GameLogicComponent {
    int getUpdateRate();
    void update();
    void render();
    void init();
    boolean shouldStop();
}
