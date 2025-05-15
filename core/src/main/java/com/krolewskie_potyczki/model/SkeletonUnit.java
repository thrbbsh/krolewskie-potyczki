package com.krolewskie_potyczki.model;

public class SkeletonUnit extends MovingUnit {
    public SkeletonUnit(boolean isPlayersEntity, float x, float y) {
        super(EntityType.Skeleton, isPlayersEntity, x, y);
    }
}
