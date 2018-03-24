package com.roervik.tdt4100.gameproject.entity;

import com.roervik.tdt4100.gameproject.data.VertexArrayObject;
import com.roervik.tdt4100.gameproject.game.Renderable;
import com.roervik.tdt4100.gameproject.math.Transformation;
import com.roervik.tdt4100.gameproject.shaders.ObjectShader;

public class ModelEntity extends Entity implements Renderable {
    public final VertexArrayObject model;
    public final ObjectShader shaderProgram;

    public ModelEntity(final VertexArrayObject model, final ObjectShader shaderProgram) {
        super();
        this.shaderProgram = shaderProgram;
        this.model = model;
    }

    public void render() {
        shaderProgram.enable();
        shaderProgram.setModelMatrix(Transformation.getModelMatrix(position, rotation, scale));
        model.render();
    }
}
