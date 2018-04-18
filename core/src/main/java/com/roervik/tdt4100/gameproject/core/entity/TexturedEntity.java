package com.roervik.tdt4100.gameproject.core.entity;

import com.roervik.tdt4100.gameproject.core.data.texture.Texture;
import com.roervik.tdt4100.gameproject.core.data.vertex.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.ProjectableShader;

public class TexturedEntity extends ModelEntity {
    protected final Texture texture;

    public TexturedEntity(final VertexArrayObject model,
                          final Texture texture,
                          final ProjectableShader shaderProgram) {
        super(model, shaderProgram);
        this.texture = texture;
    }

    @Override
    public void render() {
        texture.bind();
        super.render();
    }
}
