package com.roervik.tdt4100.gameproject.game;

public interface GameLogicComponent {
    int getUpdateRate();
    void update();
    void render();
    void init();
    boolean shouldStop();
}
