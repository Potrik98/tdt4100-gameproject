package com.roervik.tdt4100.gameproject.entity;

import com.roervik.tdt4100.gameproject.data.VertexArrayObject;
import com.roervik.tdt4100.gameproject.game.Renderable;
import com.roervik.tdt4100.gameproject.math.Transformation;
import com.roervik.tdt4100.gameproject.shaders.ProjectableShader;

public class ModelEntity extends Entity implements Renderable {
    public final VertexArrayObject model;
    public final ProjectableShader shaderProgram;

    public ModelEntity(final VertexArrayObject model, final ProjectableShader shaderProgram) {
        super();
        this.shaderProgram = shaderProgram;
        this.model = model;
    }

    public void render() {
        shaderProgram.setModelMatrix(Transformation.getModelMatrix(position, rotation, scale));
        model.render();
    }
}
