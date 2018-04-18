package com.roervik.tdt4100.gameproject.core;

import com.roervik.tdt4100.gameproject.core.data.texture.Texture;
import com.roervik.tdt4100.gameproject.core.data.vertex.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.entity.TexturedEntity;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.ArchBallCamera;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.ObjectShader;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.ShaderLoader;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.TexturedShader;
import com.roervik.tdt4100.gameproject.core.io.file.OBJLoader;
import com.roervik.tdt4100.gameproject.core.math.Transformation;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

public class TestGameArchBallCamera extends TestGame {
    private ObjectShader shaderProgram;
    private TexturedEntity texturedEntity;

    private final Matrix4f projectionMatrix;
    private ArchBallCamera archBallCamera;

    public TestGameArchBallCamera() {
        super();
        projectionMatrix = Transformation.getProjectionMatrix((float) Math.toRadians(70.0f), width, height, 0.1f, 100.0f);
    }

    public void init() {
        super.init();

        archBallCamera = new ArchBallCamera(window.getWindowHandle());

        glEnable(GL_DEPTH_TEST);

        shaderProgram = new TexturedShader(ShaderLoader.createShaderProgramFromResources(
                "shaders/TexturedVertexShader.glsl",
                "shaders/TexturedFragmentShader.glsl"));

        final VertexArrayObject vertexArrayObject = OBJLoader.loadModelFromObjFile("models/cube.obj");

        texturedEntity = new TexturedEntity(vertexArrayObject, Texture.fromResource("textures/scales.png"), shaderProgram);
    }

    public void update() {
        texturedEntity.position.z = -10.0f;
        archBallCamera.update(texturedEntity.position);
    }

    public void render() {
        window.clear();
        shaderProgram.enable();
        shaderProgram.setProjectionMatrix(projectionMatrix);
        shaderProgram.setViewMatrix(Transformation.getViewMatrix(archBallCamera));
        texturedEntity.render();
        window.update();
    }

    public static void main(String[] args) {
        start(new TestGameArchBallCamera());
    }
}
