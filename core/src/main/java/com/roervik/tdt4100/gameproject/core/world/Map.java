package com.roervik.tdt4100.gameproject.core.world;

import com.roervik.tdt4100.gameproject.core.entity.TexturedEntity;

public abstract class Map {
    protected final TexturedEntity mapEntity;

    protected Map(final TexturedEntity mapEntity) {
        this.mapEntity = mapEntity;
    }

    public void render() {
        mapEntity.render();
    }
}
