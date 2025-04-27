package com.krolewskie_potyczki.model;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    List<Entity> activeEntities;
    public Arena() {
        activeEntities = new ArrayList<>();
        addEntity(createEntity(EntityType.TOWER, true, 100, 600));
        addEntity(createEntity(EntityType.TOWER, false, 2300, 600));
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
    public void update() {
        for (Entity entity : activeEntities)
            entity.updateCurrentTarget(activeEntities);
    }
    public List<Entity> getActiveEntities() {
        return activeEntities;
    }

    public void addEntity(Entity entity) {
        activeEntities.add(entity);
    }
}
