package com.krolewskie_potyczki.model;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;

public class Tower extends Entity {
    Tower(EntityConfig entityConfig, boolean isPlayersEntity, Vector2 pos) { super(entityConfig, isPlayersEntity, pos); }

    @Override
    public boolean isTowerForPlayer(boolean isPlayer) {
        return getIsPlayersEntity() == isPlayer;
    }
}
