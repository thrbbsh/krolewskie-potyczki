package com.krolewskie_potyczki.model;

public enum EntityType {
    SideTower( 0, 1400, 500, 50, 0.8f, 0, false),
    MainTower( 0, 2400, 300, 50, 1, 0, false),
    Square(60, 690, 80, 79, 1.2f, 0, false),
    Triangle( 45, 1933, 80, 119, 1.5f, 0,true),
    Tombstone( 0, 250, 0, 0, 0, 4, false),
    Skeleton( 100, 32, 80, 32, 1, 0, false),
    SkeletonArmy(-1, -1, -1, -1, -1, -1, false);
    private final float moveSpeed;
    private final float totalHP;
    private final float attackRadius;
    private final float damage;
    private final float attackInterval;
    private final float spawnInterval;
    private final boolean ignoresMovingUnits;
    EntityType(float moveSpeed, float hp, float attackRadius, float damage, float attackInterval, float spawnInterval, boolean ignoresUnits) {
        this.moveSpeed = moveSpeed;
        this.totalHP = hp;
        this.attackRadius = attackRadius;
        this.damage = damage;
        this.attackInterval = attackInterval;
        this.spawnInterval = spawnInterval;
        this.ignoresMovingUnits = ignoresUnits;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getTotalHP() {
        return totalHP;
    }

    public float getAttackRadius() {
        return attackRadius;
    }

    public float getDamage() {
        return damage;
    }

    public float getAttackInterval() {
        return attackInterval;
    }

    public float getSpawnInterval() { return spawnInterval; }

    public boolean doesIgnoreMovingUnits() {
        return ignoresMovingUnits;
    }
}
