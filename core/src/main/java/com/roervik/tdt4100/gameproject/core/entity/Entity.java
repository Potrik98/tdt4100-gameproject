package com.roervik.tdt4100.gameproject.core.entity;

import com.roervik.tdt4100.gameproject.core.game.Updateable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Entity implements Updateable {
    public Vector3f position;
    public Quaternionf rotation;
    public Vector3f scale;

    public Entity() {
        position = new Vector3f();
        rotation = new Quaternionf();
        scale = new Vector3f(1.0f, 1.0f, 1.0f);
    }

    public void update() {

    }
}
