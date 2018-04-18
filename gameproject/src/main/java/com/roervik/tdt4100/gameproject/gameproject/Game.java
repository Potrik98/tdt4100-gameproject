package com.roervik.tdt4100.gameproject.gameproject;

import com.roervik.tdt4100.gameproject.core.data.texture.Texture;
import com.roervik.tdt4100.gameproject.core.data.vertex.VertexArrayObject;
import com.roervik.tdt4100.gameproject.core.game.GameLogicComponent;
import com.roervik.tdt4100.gameproject.core.game.GameLoop;
import com.roervik.tdt4100.gameproject.core.gfx.Window;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.ArchBallCamera;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.ShaderLoader;
import com.roervik.tdt4100.gameproject.core.gfx.shaders.TexturedShader;
import com.roervik.tdt4100.gameproject.core.io.file.OBJLoader;
import com.roervik.tdt4100.gameproject.core.io.input.Controller;
import com.roervik.tdt4100.gameproject.core.math.Transformation;
import com.roervik.tdt4100.gameproject.gameproject.map.BoardMap;
import com.roervik.tdt4100.gameproject.gameproject.map.MapBuilder;
import com.roervik.tdt4100.gameproject.gameproject.object.RotatingCube;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

public class Game implements GameLogicComponent {
    private static final String title = "GameProject v1.0";
    private static final int width = 800;
    private static final int height = 600;
    private static final int updateRate = 60;
    private static final float FOV = 70.0f;

    private final Window window;

    private TexturedShader shaderProgram;
    private RotatingCube rotatingCube;

    private Matrix4f projectionMatrix;
    private ArchBallCamera camera;

    private BoardMap boardMap;

    public Game() {
        window = new Window(title, width, height);
        projectionMatrix = Transformation.getProjectionMatrix((float) Math.toRadians(FOV), width, height, 0.1f, 100.0f);
    }

    public void init() {
        window.init();
        Controller.initInputController(window);
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        camera = new ArchBallCamera(window.getWindowHandle());

        shaderProgram = new TexturedShader(ShaderLoader.createShaderProgramFromResources(
                "shaders/TexturedVertexShader.glsl",
                "shaders/TexturedFragmentShader.glsl"));

        boardMap = MapBuilder.ofResource("maps/map1.txt")
                .withShader(shaderProgram)
                .withTexture(Texture.fromResource("textures/ground.png"))
                .build();

        final VertexArrayObject vertexArrayObject = OBJLoader.loadModelFromObjFile("models/cube.obj");

        rotatingCube = new RotatingCube(vertexArrayObject, Texture.fromResource("textures/scales.png"), shaderProgram);
        rotatingCube.position.z = -10;
    }

    public void update() {
        rotatingCube.update();
        camera.update(rotatingCube.position);
    }

    public void render() {
        window.clear();
        shaderProgram.enable();
        shaderProgram.setProjectionMatrix(projectionMatrix);
        shaderProgram.setViewMatrix(Transformation.getViewMatrix(camera));
        rotatingCube.render();
        boardMap.render();
        window.update();
    }

    public boolean shouldStop() {
        return window.shouldClose();
    }

    public int getUpdateRate() {
        return updateRate;
    }

    public static void main(String[] args) {
        final GameLoop gameLoop = new GameLoop(new Game());
        gameLoop.start();
    }
}
