package com.roervik.tdt4100.gameproject.core.entity;

import com.roervik.tdt4100.gameproject.core.gfx.shaders.ProjectableShader;
import com.roervik.tdt4100.gameproject.core.data.vertex.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.game.Renderable;
import com.roervik.tdt4100.gameproject.core.math.Transformation;

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
