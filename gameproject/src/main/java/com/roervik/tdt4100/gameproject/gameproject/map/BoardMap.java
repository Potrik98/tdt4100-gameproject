package com.roervik.tdt4100.gameproject.gameproject.map;

import com.roervik.tdt4100.gameproject.core.entity.TexturedEntity;
import com.roervik.tdt4100.gameproject.core.world.Map;
import com.roervik.tdt4100.gameproject.gameproject.object.RotatingCube;
import org.joml.Vector3f;

public class BoardMap extends Map {
    public static final int textureMapSize = 2;
    private final int width, height;
    private final TileType[] mapData;

    protected BoardMap(final TexturedEntity mapEntity, final int width, final int height, final TileType[] mapData) {
        super(mapEntity);
        mapEntity.scale = new Vector3f(RotatingCube.size);
        this.width = width;
        this.height = height;
        this.mapData = mapData;
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
