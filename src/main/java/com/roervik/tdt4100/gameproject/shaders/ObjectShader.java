package com.roervik.tdt4100.gameproject.shaders;

import com.roervik.tdt4100.gameproject.gfx.ShaderProgram;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class ObjectShader extends ShaderProgram implements ProjectableShader {
    private final int locationProjectionMatrix;
    private final int locationViewMatrix;
    private final int locationModelMatrix;

    public ObjectShader(final String vertexSource,
                        final String fragmentSource) throws ShaderException {
        super(vertexSource, fragmentSource);

        locationProjectionMatrix = glGetUniformLocation(programId,"projection_matrix");
        locationViewMatrix = glGetUniformLocation(programId,"view_matrix");
        locationModelMatrix = glGetUniformLocation(programId,"model_matrix");
    }

    public ObjectShader(final ShaderProgram shaderProgram) {
        super(shaderProgram);

        locationProjectionMatrix = glGetUniformLocation(programId,"projection_matrix");
        locationViewMatrix = glGetUniformLocation(programId,"view_matrix");
        locationModelMatrix = glGetUniformLocation(programId,"model_matrix");
    }

    private void setUniformMatrix4f(final Matrix4f matrix, final int location) {
        float[] data = new float[16];
        matrix.get(data);
        glUniformMatrix4fv(location, false, data);
    }

    public void setProjectionMatrix(final Matrix4f projectionMatrix) {
        setUniformMatrix4f(projectionMatrix, locationProjectionMatrix);
    }
    
    public void setModelMatrix(final Matrix4f modelMatrix) {
        setUniformMatrix4f(modelMatrix, locationModelMatrix);
    }
    
    public void setViewMatrix(final Matrix4f viewMatrix) {
        setUniformMatrix4f(viewMatrix, locationViewMatrix);
    }
}
