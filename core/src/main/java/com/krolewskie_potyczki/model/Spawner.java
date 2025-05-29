package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.GameConfig;

public class Spawner extends Building {
    private float curInterval;
    private final float spawnInterval;
    private boolean readyToSpawn;
    private final EntityConfig spawnType;
    Spawner(EntityConfig config, boolean isPlayersEntity, float x, float y) {
        super(config, isPlayersEntity, x, y);
        curInterval = 0;
        spawnInterval = config.spawnInterval;
        readyToSpawn = false;
        if (this instanceof TombstoneUnit) spawnType = GameConfig.getInstance().getEntityConfig("Skeleton");
            else spawnType = null;
    }
    public void updateSpawnUnit(float delta) {
        curInterval += delta;
        if (curInterval > spawnInterval) {
            curInterval -= spawnInterval;
            readyToSpawn = true;
        }
    }
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
