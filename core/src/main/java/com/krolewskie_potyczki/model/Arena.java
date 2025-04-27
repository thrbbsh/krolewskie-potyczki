package com.krolewskie_potyczki.model;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    List<Entity> activeEntities;
    public Arena() {
        activeEntities = new ArrayList<>();
        createEntity(EntityType.TOWER, true, 100, 400);
        createEntity(EntityType.TOWER, false, 700, 400);
    }
    void createEntity(EntityType entityType, boolean isPlayersEntity, float x, float y) {
        Entity entity;
        switch (entityType) {
            case TOWER:
                entity = new Tower(isPlayersEntity, x, y);
                break;
            case SQUARE:
                entity = new SquareUnit(isPlayersEntity, x, y);
                break;
            default:
                throw new IllegalArgumentException("Wrong entity type: " + entityType);
        }
        activeEntities.add(entity);
    }
    public List<Entity> getActiveEntities() {
        return activeEntities;
    }
}
