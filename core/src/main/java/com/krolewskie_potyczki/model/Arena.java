package com.krolewskie_potyczki.model;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    List<Entity> activeEntities;
    private float playerElixir = 0;
    private final float maxElixir = 10;
    private float elixirSpeed = 0.1F;
    private final float matchDuration = 10;
    private float timeLeft;

    public float getTimeLeft() {
        return timeLeft;
    }

    public String getFormattedTimeLeft() {
        return String.format("%01d:%02d", (int) Math.floor(timeLeft / 60), (int) Math.floor(timeLeft % 60));
    }

    public float getPlayerElixir() {
        return (float) Math.floor(playerElixir * 10) / 10;
    }

    public float getMaxElixir() {
        return maxElixir;
    }

    public Arena() {
        timeLeft = matchDuration;
        activeEntities = new ArrayList<>();
        addEntity(createEntity(EntityType.TOWER, true, 100, 500));
        addEntity(createEntity(EntityType.TOWER, false, 1720, 500));
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
        timeLeft -= delta;
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
