package com.roervik.tdt4100.gameproject.entity;

import com.roervik.tdt4100.gameproject.game.Updateable;
import lombok.Builder;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Builder(toBuilder = true)
public class Entity implements Updateable {
    protected Vector3f position;
    protected Quaternionf rotation;
    protected Vector3f scale;

    public void update() {

    }

    public static class EntityBuilder {
        public EntityBuilder() {
            position = new Vector3f();
            rotation = new Quaternionf();
            scale = new Vector3f();
        }
    }
}
