package com.roervik.tdt4100.gameproject.gameproject.map;

import com.roervik.tdt4100.gameproject.core.entity.TexturedEntity;
import com.roervik.tdt4100.gameproject.core.io.input.Controller;
import com.roervik.tdt4100.gameproject.core.io.input.Input;
import com.roervik.tdt4100.gameproject.core.world.Map;
import com.roervik.tdt4100.gameproject.gameproject.object.RotatingCube;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

public class BoardMap extends Map {
    public static final int textureMapSize = 2;
    private RotatingCube rotatingCube;
    private final int width, height;
    private final TileType[] mapData;
    private Vector2i currentTile;
    private CubeSide currentSide;
    private boolean completed;

    protected BoardMap(final TexturedEntity mapEntity, final int width, final int height, final TileType[] mapData) {
        super(mapEntity);
        mapEntity.scale = new Vector3f(RotatingCube.size);
        this.width = width;
        this.height = height;
        this.mapData = mapData;
        currentTile = getStartingTile();
        currentSide = CubeSide.UP;
        completed = false;
    }

    private enum CubeSide {
        UP, DOWN, LEFT, RIGHT, FRONT, BACK;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setRotatingCube(final RotatingCube rotatingCube) {
        this.rotatingCube = rotatingCube;
    }

    private Vector2i getStartingTile() {
        for (int z = 0; z < height; z++) {
            for (int x = 0; x < width; x++) {
                if (mapData[x + z * width] == TileType.START) {
                    return new Vector2i(x, z);
                }
            }
        }
        throw new IllegalStateException("No starting position on board!");
    }

    public Vector2f getStartingPosition() {
        return new Vector2f(getStartingTile().x + 0.5f, getStartingTile().y + 0.5f);
    }

    public boolean canMoveToTile(final int x, final int z) {
        if (x < 0 || x >= width || z < 0 || z >= height) return false;
        if (mapData[x + z * width] == TileType.EMPTY) return false;
        return true;
    }

    public void update() {
        handleInput();
        checkBoardState();
    }

    private void checkBoardState() {
        if (!rotatingCube.isRotating() && !completed) {
            if (mapData[currentTile.x + currentTile.y * width] == TileType.GOAL && currentSide == CubeSide.DOWN) {
                completed = true;
                System.out.println("Victory!");
            }
        }
    }

    private void rollForwards() {
        rotatingCube.startRotation(new Vector3f(1, 0, 0));
        if (currentSide == CubeSide.UP) currentSide = CubeSide.BACK;
        else if (currentSide == CubeSide.BACK) currentSide = CubeSide.DOWN;
        else if (currentSide == CubeSide.DOWN) currentSide = CubeSide.FRONT;
        else if (currentSide == CubeSide.FRONT) currentSide = CubeSide.UP;
    }

    private void rollBackWards() {
        rotatingCube.startRotation(new Vector3f(-1, 0, 0));
        if (currentSide == CubeSide.UP) currentSide = CubeSide.FRONT;
        else if (currentSide == CubeSide.FRONT) currentSide = CubeSide.DOWN;
        else if (currentSide == CubeSide.DOWN) currentSide = CubeSide.BACK;
        else if (currentSide == CubeSide.BACK) currentSide = CubeSide.UP;
    }

    private void rollLeft() {
        rotatingCube.startRotation(new Vector3f(0, 0, -1));
        if (currentSide == CubeSide.UP) currentSide = CubeSide.LEFT;
        else if (currentSide == CubeSide.LEFT) currentSide = CubeSide.DOWN;
        else if (currentSide == CubeSide.DOWN) currentSide = CubeSide.RIGHT;
        else if (currentSide == CubeSide.RIGHT) currentSide = CubeSide.UP;
    }

    private void rollRight() {
        rotatingCube.startRotation(new Vector3f(0, 0, 1));
        if (currentSide == CubeSide.UP) currentSide = CubeSide.RIGHT;
        else if (currentSide == CubeSide.RIGHT) currentSide = CubeSide.DOWN;
        else if (currentSide == CubeSide.DOWN) currentSide = CubeSide.LEFT;
        else if (currentSide == CubeSide.LEFT) currentSide = CubeSide.UP;
    }

    private void attemptMove(int dx, int dz) {
        if (canMoveToTile(currentTile.x + dx, currentTile.y + dz)) {
            if (dx == 0 && dz == -1) rollForwards();
            if (dx == 1 && dz == 0) rollRight();
            if (dx == 0 && dz == 1) rollBackWards();
            if (dx == -1 && dz == 0) rollLeft();
            currentTile.add(dx, dz);
        }
    }

    public void handleInput() {
        final Input input = Controller.getInput();

        if (!rotatingCube.isRotating()) {
            if (input.isKeyPressed(GLFW_KEY_W)) attemptMove(0, -1);
            if (input.isKeyPressed(GLFW_KEY_A)) attemptMove(1, 0);
            if (input.isKeyPressed(GLFW_KEY_S)) attemptMove(0, 1);
            if (input.isKeyPressed(GLFW_KEY_D)) attemptMove(-1, 0);
        }
    }

    public enum TileType {
        EMPTY('X', 0),
        GROUND(' ', 1),
        GOAL('G', 2),
        START('S', 1);
        public final char symbol;
        public final int textureIndex;
        TileType(final char symbol, final int textureIndex) {
            this.symbol = symbol;
            this.textureIndex = textureIndex;
        }
    }
}
