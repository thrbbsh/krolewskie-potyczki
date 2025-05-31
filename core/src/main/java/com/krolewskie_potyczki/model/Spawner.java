package com.krolewskie_potyczki.model;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;

import java.util.List;

public class Spawner extends Building {
    private float curInterval;
    private final float spawnInterval;
    private boolean readyToSpawn;
    Spawner(EntityConfig config, boolean isPlayersEntity, Vector2 pos) {
        super(config, isPlayersEntity, pos);
        curInterval = 0;
        spawnInterval = config.spawnInterval;
        readyToSpawn = false;
    }
    public void updateSpawnUnit(float delta) {
        curInterval += delta;
        if (curInterval > spawnInterval) {
            curInterval -= spawnInterval;
            readyToSpawn = true;
        }
    }
    @Override
    public void update(float delta, List<Entity> activeEntities) {
        super.update(delta, activeEntities);
        updateSpawnUnit(delta);
        receiveDamage(delta * config.totalHP / 30f);
    }
    @Override
    public boolean isReadyToSpawn() {
        return readyToSpawn;
    }
    public void setReadyToSpawn(boolean readyToSpawn) {
        this.readyToSpawn = readyToSpawn;
    }
}
