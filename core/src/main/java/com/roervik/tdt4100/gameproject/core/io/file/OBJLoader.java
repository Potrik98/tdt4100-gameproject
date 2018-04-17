package com.roervik.tdt4100.gameproject.core.io.file;

import com.roervik.tdt4100.gameproject.core.data.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.util.FileUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OBJLoader {
    public static VertexArrayObject loadModelFromObjFile(final String resourcePath) {
        final List<String> lines = FileUtils.readAllLines(resourcePath);

        final List<Vector3f> vertices = new ArrayList<>();
        final List<Vector2f> textures = new ArrayList<>();
        final List<Vector3f> normals = new ArrayList<>();
        final List<Face> faces = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v":
                    // Geometric vertex
                    Vector3f vec3f = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    vertices.add(vec3f);
                    break;
                case "vt":
                    // Texture coordinate
                    Vector2f vec2f = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]));
                    textures.add(vec2f);
                    break;
                case "vn":
                    // Vertex normal
                    Vector3f vec3fNorm = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    normals.add(vec3fNorm);
                    break;
                case "f":
                    Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    faces.add(face);
                    break;
                default:
                    // Ignore other lines
                    break;
            }
        }
        return getVertexArrayObjectFromDataLists(vertices, textures, normals, faces);
    }

    private static VertexArrayObject getVertexArrayObjectFromDataLists(final List<Vector3f> vertexList,
                                                                       final List<Vector2f> textureCoordinateList,
                                                                       final List<Vector3f> normalVectorList,
                                                                       final List<Face> faceList) {

        final List<Integer> indices = new ArrayList<>();

        // Create position array in the order it has been declared
        final float[] vertexArray = new float[vertexList.size() * 3];

        int i = 0;
        for (Vector3f pos : vertexList) {
            vertexArray[i * 3] = pos.x;
            vertexArray[i * 3 + 1] = pos.y;
            vertexArray[i * 3 + 2] = pos.z;
            i++;
        }

        final float[] textureCoordinatesData = new float[vertexList.size() * 2];
        final float[] normalVectorsData = new float[vertexList.size() * 3];

        faceList.stream().flatMap(face -> Arrays.stream(face.getFaceVertexIndices()))
                .forEach(indexGroup ->
                        processFaceVertex(
                                indexGroup,
                                textureCoordinateList,
                                normalVectorList,
                                indices,
                                textureCoordinatesData,
                                normalVectorsData));

        int[] indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
        return VertexArrayObject.builder()
                .indexBuffer(indicesArr)
                .vertexBuffer(vertexArray, 3, 0)
                .vertexBuffer(textureCoordinatesData, 2, 1)
                .vertexBuffer(normalVectorsData, 3, 2)
                .build();
    }

    private static void processFaceVertex(final IndexGroup indices,
                                          final List<Vector2f> textureCoordinateList,
                                          final List<Vector3f> normalVectorList,
                                          final List<Integer> indexList,
                                          final float[] textureCoordinatesData,
                                          final float[] normalVectorsData) {

        // Set index for vertex coordinates
        int posIndex = indices.indexPositionVertex;
        indexList.add(posIndex);

        // Reorder texture coordinates
        if (indices.indexTextureCoordinate >= 0) {
            final Vector2f textureCoordinate = textureCoordinateList.get(indices.indexTextureCoordinate);
            textureCoordinatesData[posIndex * 2] = textureCoordinate.x;
            textureCoordinatesData[posIndex * 2 + 1] = 1 - textureCoordinate.y;
        }
        if (indices.indexVertexNormal >= 0) {
            // Reorder normal vectors
            final Vector3f normalVector = normalVectorList.get(indices.indexVertexNormal);
            normalVectorsData[posIndex * 3] = normalVector.x;
            normalVectorsData[posIndex * 3 + 1] = normalVector.y;
            normalVectorsData[posIndex * 3 + 2] = normalVector.z;
        }
    }

    protected static class Face {
        /*
         * List of IndexGroup groups for a face triangle (3 vertices per face).
         */
        private IndexGroup[] indexGroups;

        Face(final String vertex1, final String vertex2, final String vertex3) {
            indexGroups = new IndexGroup[3];
            // Parse the lines
            indexGroups[0] = parseLine(vertex1);
            indexGroups[1] = parseLine(vertex2);
            indexGroups[2] = parseLine(vertex3);
        }

        private IndexGroup parseLine(String line) {
            final IndexGroup indexGroup = new IndexGroup();

            final String[] lineTokens = line.split("/");
            final int length = lineTokens.length;
            indexGroup.indexPositionVertex = Integer.parseInt(lineTokens[0]) - 1;
            if (length > 1) {
                // It can be empty if the obj does not define texture coordinates
                String textureCoordinate = lineTokens[1];
                indexGroup.indexTextureCoordinate = textureCoordinate.length() > 0 ? Integer.parseInt(textureCoordinate) - 1 : IndexGroup.NO_VALUE;
                if (length > 2) {
                    indexGroup.indexVertexNormal = Integer.parseInt(lineTokens[2]) - 1;
                }
            }

            return indexGroup;
        }

        IndexGroup[] getFaceVertexIndices() {
            return indexGroups;
        }
    }

    protected static class IndexGroup {
        public static final int NO_VALUE = -1;

        public int indexPositionVertex;
        public int indexTextureCoordinate;
        public int indexVertexNormal;

        IndexGroup() {
            indexPositionVertex = NO_VALUE;
            indexTextureCoordinate = NO_VALUE;
            indexVertexNormal = NO_VALUE;
        }
    }
}
