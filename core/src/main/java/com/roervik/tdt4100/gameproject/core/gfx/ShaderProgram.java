package com.roervik.tdt4100.gameproject.core.gfx;

import com.roervik.tdt4100.gameproject.core.gfx.shaders.ShaderException;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

public class ShaderProgram {
    public final int programId;

    protected ShaderProgram(final ShaderProgram shaderProgram) {
        this.programId = shaderProgram.programId;
    }

    public ShaderProgram(final String vertexSource, final String fragmentSource) throws ShaderException {
        programId = glCreateProgram();
        enable();
        createShader(vertexSource, GL_VERTEX_SHADER);
        createShader(fragmentSource, GL_FRAGMENT_SHADER);
        link();
    }

    private int createShader(String shaderCode, int shaderType) throws ShaderException {
        final int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new ShaderException("Error creating shader.");
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new ShaderException("Error compiling Shader code:\n" + shaderCode + "\n"  + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    private void link() throws ShaderException {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new ShaderException("Error linking Shader code: " + glGetShaderInfoLog(programId, 1024));
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            throw new ShaderException("Error validating Shader code: " + glGetShaderInfoLog(programId, 1024));
        }
    }

    public void enable() {
        glUseProgram(programId);
    }

    public void disable() {
        glUseProgram(0);
    }
}