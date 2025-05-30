package com.krolewskie_potyczki.model;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

import java.util.List;

public class Spawner extends Building {
    private float curInterval;
    private final float spawnInterval;
    private boolean readyToSpawn;
    private final EntityConfig spawnType;
    Spawner(EntityConfig config, boolean isPlayersEntity, Vector2 pos) {
        super(config, isPlayersEntity, pos);
        curInterval = 0;
        spawnInterval = config.spawnInterval;
        readyToSpawn = false;
        if (config.type == EntityType.TOMBSTONE) spawnType = GameConfig.getInstance().getEntityConfig(EntityType.SKELETON);
            else spawnType = null;
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
    public EntityConfig getSpawnType() {
        return spawnType;
    }
    public void setReadyToSpawn(boolean readyToSpawn) {
        this.readyToSpawn = readyToSpawn;
    }
}
