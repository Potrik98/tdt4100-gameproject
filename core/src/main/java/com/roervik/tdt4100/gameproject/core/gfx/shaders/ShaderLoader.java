package com.roervik.tdt4100.gameproject.core.gfx.shaders;

import com.roervik.tdt4100.gameproject.core.gfx.ShaderProgram;
import com.roervik.tdt4100.gameproject.core.io.file.FileUtils;

import java.io.IOException;

public class ShaderLoader {
    public static ShaderProgram createShaderProgramFromResources(final String vertexShaderResource,
                                                                 final String fragmentShaderResource) {
        try {
            final String vertexShaderSource = FileUtils.readInputStream(
                    ClassLoader.getSystemClassLoader().getResourceAsStream(vertexShaderResource));
            final String fragmentShaderSource = FileUtils.readInputStream(
                    ClassLoader.getSystemClassLoader().getResourceAsStream(fragmentShaderResource));
            return new ShaderProgram(vertexShaderSource, fragmentShaderSource);
        } catch (IOException e) {
            System.out.println("Failed to read shader resources.");
            System.out.println(e.getMessage());
        } catch (ShaderException e) {
            System.out.println("Failed to create shader.");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
