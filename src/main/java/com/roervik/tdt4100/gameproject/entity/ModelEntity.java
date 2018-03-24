package com.roervik.tdt4100.gameproject.entity;

import com.roervik.tdt4100.gameproject.data.VertexArrayObject;
import com.roervik.tdt4100.gameproject.game.Renderable;
import com.roervik.tdt4100.gameproject.math.Transformation;
import com.roervik.tdt4100.gameproject.shaders.ObjectShader;
import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
public class ModelEntity extends Entity implements Renderable {
    protected final VertexArrayObject model;
    protected final ObjectShader shaderProgram;

    public void render() {
        shaderProgram.enable();
        shaderProgram.setModelMatrix(Transformation.getModelMatrix(position, rotation, scale));
        model.render();
    }
}
