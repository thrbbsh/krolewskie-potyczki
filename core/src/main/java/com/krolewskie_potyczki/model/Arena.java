package com.krolewskie_potyczki.model;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    List<Entity> activeEntities;
    private float playerElixir = 0;
    private final float maxElixir = 10;
    private float elixirSpeed = 0.1F;

    public float getPlayerElixir() {
        return (float) Math.floor(playerElixir * 10) / 10F;
    }

    public float getMaxElixir() {
        return maxElixir;
    }

    public Arena() {
        activeEntities = new ArrayList<>();
        addEntity(createEntity(EntityType.TOWER, true, 100, 600));
        addEntity(createEntity(EntityType.TOWER, false, 2300, 600));
        playerElixir = 5;
    }
    public Entity createEntity(EntityType entityType, boolean isPlayersEntity, float x, float y) {
        switch (entityType) {
            case TOWER:
                return new Tower(isPlayersEntity, x, y);
            case SQUARE:
                return new SquareUnit(isPlayersEntity, x, y);
            default:
                throw new IllegalArgumentException("Wrong entity type: " + entityType);
        }
    }

    public void update(float delta) {
        updatePlayerElixir(delta);
        for (Entity entity : activeEntities) {
            entity.updateCurrentTarget(activeEntities);
            entity.move(delta);
        }
    }

    private void updatePlayerElixir(float delta) {
        playerElixir = Math.min(maxElixir, playerElixir + delta * elixirSpeed);
    }

    public List<Entity> getActiveEntities() {
        return activeEntities;
    }

    public void addEntity(Entity entity) {
        activeEntities.add(entity);
    }

    public void subtractElixir(float elixirCost) {
        playerElixir -= elixirCost;
    }
}
