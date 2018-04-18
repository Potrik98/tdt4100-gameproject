package com.roervik.tdt4100.gameproject.gameproject.map;

import com.roervik.tdt4100.gameproject.core.io.file.FileUtils;

import java.util.Arrays;
import java.util.List;

public class MapLoader {
    public static class MapData {
        public final BoardMap.TileType[] mapData;
        public final int width, height;
        private MapData(final int width, final int height, final BoardMap.TileType[] mapData) {
            this.width = width;
            this.height = height;
            this.mapData = mapData;
        }
    }

    private static BoardMap.TileType getTileTypeForSymbol(final char symbol) {
        return Arrays.stream(BoardMap.TileType.values())
                .filter(tileType -> tileType.symbol == symbol)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unknown tile symbol: " + symbol));
    }

    public static MapData loadMapData(final String mapResource) {
        final List<String> lines = FileUtils.readAllLines(mapResource);
        return new MapData(
                lines.get(0).length(),
                lines.size(),
                lines.stream()
                        .flatMap(str -> str.chars()
                                .mapToObj(c -> getTileTypeForSymbol((char) c)))
                        .toArray(BoardMap.TileType[]::new)
        );
    }
}
