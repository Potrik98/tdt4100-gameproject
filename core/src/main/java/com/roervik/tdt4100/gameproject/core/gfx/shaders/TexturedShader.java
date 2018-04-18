package com.roervik.tdt4100.gameproject.core.gfx.shaders;

import com.roervik.tdt4100.gameproject.core.gfx.ShaderProgram;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1i;

public class TexturedShader extends ObjectShader {
    private final int locationTextureSampler;

    public TexturedShader(String vertexSource, String fragmentSource) throws ShaderException {
        super(vertexSource, fragmentSource);

        locationTextureSampler = glGetUniformLocation(programId,"texture_sampler");
        setUniform1i(locationTextureSampler, 0);
    }

    public TexturedShader(ShaderProgram shaderProgram) {
        super(shaderProgram);

        locationTextureSampler = glGetUniformLocation(programId,"texture_sampler");
        setUniform1i(locationTextureSampler, 0);
    }

    public void setUniform1i(final int location, final int value) {
        glUniform1i(location, value);
    }
}
