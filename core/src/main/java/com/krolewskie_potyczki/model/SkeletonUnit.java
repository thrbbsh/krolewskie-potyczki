package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.GameConfig;

public class SkeletonUnit extends MovingUnit {
    public SkeletonUnit(boolean isPlayersEntity, float x, float y) {
        super(GameConfig.getInstance().getEntityConfig("Skeleton"), isPlayersEntity, x, y);
    }
}
