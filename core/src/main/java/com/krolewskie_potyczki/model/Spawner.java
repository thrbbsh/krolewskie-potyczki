package com.krolewskie_potyczki.model;

public class Spawner extends Building {
    private float curInterval;
    private final float spawnInterval;
    private boolean readyToSpawn;
    private final EntityType spawnType;
    Spawner(EntityType type, boolean isPlayersEntity, float x, float y) {
        super(type, isPlayersEntity, x, y);
        curInterval = 0;
        spawnInterval = type.getSpawnInterval();
        readyToSpawn = false;
        if (this instanceof TombstoneUnit) spawnType = EntityType.Skeleton;
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
    public EntityType getSpawnType() {
        return spawnType;
    }
    public void setReadyToSpawn(boolean readyToSpawn) {
        this.readyToSpawn = readyToSpawn;
    }
}
