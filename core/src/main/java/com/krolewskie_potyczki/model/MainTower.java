package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.GameConfig;

public class MainTower extends Tower {
    public MainTower(boolean isPlayersEntity, float x, float y) {
        super(GameConfig.getInstance().getEntityConfig("MainTower"), isPlayersEntity, x, y);
    }
}
