package com.krolewskie_potyczki.model;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class MainTower extends Tower {
    public MainTower(boolean isPlayersEntity, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.MAIN_TOWER), isPlayersEntity, pos);
    }
}
