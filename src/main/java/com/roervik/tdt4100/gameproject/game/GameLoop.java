package com.roervik.tdt4100.gameproject.game;

public class GameLoop implements Runnable {
    private final GameLogicComponent gameLogicComponent;
    private boolean running;
    private Thread thread;

    public GameLoop(final GameLogicComponent gameLogicComponent) {
        this.gameLogicComponent = gameLogicComponent;
        gameLogicComponent.init();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        gameLogicComponent.init();
        long lastTime = System.nanoTime();
        double delta = 0.0;
        final double ns = 1000000000.0 / gameLogicComponent.getUpdateRate();
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) {
            final long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1.0) {
                gameLogicComponent.update();
                updates++;
                delta--;
            }
            gameLogicComponent.render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
                if (gameLogicComponent.shouldStop()) {
                    stop();
                }
            }
        }
    }
}
