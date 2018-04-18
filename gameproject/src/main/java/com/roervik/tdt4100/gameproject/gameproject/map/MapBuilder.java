package com.roervik.tdt4100.gameproject.gameproject.map;

import com.roervik.tdt4100.gameproject.core.data.texture.Texture;
import com.roervik.tdt4100.gameproject.core.data.vertex.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.entity.TexturedEntity;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.ProjectableShader;
import org.joml.Vector2f;

import static com.roervik.tdt4100.gameproject.gameproject.map.BoardMap.textureMapSize;

public class MapBuilder {
    private final int width, height;
    private final BoardMap.TileType[] mapData;
    private final VertexArrayObject vertexArrayObject;
    private Texture texture;
    private ProjectableShader shader;

    private MapBuilder(final int width, final int height, final BoardMap.TileType[] mapData) {
        this.width = width;
        this.height= height;
        this.mapData = mapData;
        vertexArrayObject = generateVertexArrayObject();
    }

    public static MapBuilder ofResource(final String mapResource) {
        final MapLoader.MapData mapData = MapLoader.loadMapData(mapResource);
        return new MapBuilder(mapData.width, mapData.height, mapData.mapData);
    }

    public MapBuilder withTexture(final Texture texture) {
        this.texture = texture;
        return this;
    }

    public MapBuilder withShader(final ProjectableShader shader) {
        this.shader = shader;
        return this;
    }

    public BoardMap build() {
        return new BoardMap(new TexturedEntity(vertexArrayObject, texture, shader), width, height, mapData);
    }

    private Vector2f[] getTextureCoordinatesForTile(final int x, final int z) {
        final int textureIndex = mapData[x + z * width].textureIndex;
        final int zTextureOffset = textureIndex / textureMapSize;
        final int xTextureOffset = textureIndex - zTextureOffset * textureMapSize;

        return new Vector2f[] {
                new Vector2f(xTextureOffset, zTextureOffset).mul(1.0f / textureMapSize),
                new Vector2f(xTextureOffset, zTextureOffset + 1).mul(1.0f / textureMapSize),
                new Vector2f(xTextureOffset + 1, zTextureOffset).mul(1.0f / textureMapSize),
                new Vector2f(xTextureOffset + 1, zTextureOffset+ 1).mul(1.0f / textureMapSize)
        };
    }

    private VertexArrayObject generateVertexArrayObject() {
        int vertexCount = 4 * width * height;
        float[] vertexData = new float[3 * vertexCount];
        float[] normalVectorData = new float[3 * vertexCount];
        float[] textureCoordinateData = new float[2 * vertexCount];
        int[] indices = new int[6 * width * height];

        int vertexPointer = 0;
        int indexPointer = 0;
        for (int z = 0; z < height; z++) {
            for (int x = 0; x < width; x++) {
                final Vector2f[] textureCoordinates = getTextureCoordinatesForTile(x, z);

                vertexData[vertexPointer * 3] = x;
                vertexData[vertexPointer * 3 + 1] = 0;
                vertexData[vertexPointer * 3 + 2] = z;
                normalVectorData[vertexPointer * 3] = 0;
                normalVectorData[vertexPointer * 3 + 1] = 1;
                normalVectorData[vertexPointer * 3 + 2] = 0;
                textureCoordinateData[vertexPointer * 2] = textureCoordinates[0].x;
                textureCoordinateData[vertexPointer * 2 + 1] = textureCoordinates[0].y;
                final int topLeft = vertexPointer;
                vertexPointer++;

                vertexData[vertexPointer * 3] = x;
                vertexData[vertexPointer * 3 + 1] = 0;
                vertexData[vertexPointer * 3 + 2] = z + 1;
                normalVectorData[vertexPointer * 3] = 0;
                normalVectorData[vertexPointer * 3 + 1] = 1;
                normalVectorData[vertexPointer * 3 + 2] = 0;
                textureCoordinateData[vertexPointer * 2] = textureCoordinates[1].x;
                textureCoordinateData[vertexPointer * 2 + 1] = textureCoordinates[1].y;
                final int bottomLeft = vertexPointer;
                vertexPointer++;

                vertexData[vertexPointer * 3] = x + 1;
                vertexData[vertexPointer * 3 + 1] = 0;
                vertexData[vertexPointer * 3 + 2] = z;
                normalVectorData[vertexPointer * 3] = 0;
                normalVectorData[vertexPointer * 3 + 1] = 1;
                normalVectorData[vertexPointer * 3 + 2] = 0;
                textureCoordinateData[vertexPointer * 2] = textureCoordinates[2].x;
                textureCoordinateData[vertexPointer * 2 + 1] = textureCoordinates[2].y;
                final int topRight = vertexPointer;
                vertexPointer++;

                vertexData[vertexPointer * 3] = x + 1;
                vertexData[vertexPointer * 3 + 1] = 0;
                vertexData[vertexPointer * 3 + 2] = z + 1;
                normalVectorData[vertexPointer * 3] = 0;
                normalVectorData[vertexPointer * 3 + 1] = 1;
                normalVectorData[vertexPointer * 3 + 2] = 0;
                textureCoordinateData[vertexPointer * 2] = textureCoordinates[3].x;
                textureCoordinateData[vertexPointer * 2 + 1] = textureCoordinates[3].y;
                final int bottomRight = vertexPointer;
                vertexPointer++;

                indices[indexPointer++] = topLeft;
                indices[indexPointer++] = bottomLeft;
                indices[indexPointer++] = topRight;
                indices[indexPointer++] = topRight;
                indices[indexPointer++] = bottomLeft;
                indices[indexPointer++] = bottomRight;
            }
        }

        return VertexArrayObject.builder()
                .vertexBuffer(vertexData, 3, 0)
                .vertexBuffer(textureCoordinateData, 2, 1)
                .vertexBuffer(normalVectorData, 3, 2)
                .indexBuffer(indices)
                .build();
    }
}
