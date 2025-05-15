package com.krolewskie_potyczki.model;

public enum EntityType {
    SideTower( 0, 2000, 200, 70, 0.4f, 0, false),
    MainTower( 0, 3000, 200, 50, 0.5f, 0, false),
    Square(250, 300, 80, 100, 0.5f, 0, false),
    Triangle( 140, 700, 80, 400, 1.5f, 0,true),
    Tombstone( 0, 800, 0, 0, 0, 5, false),
    Skeleton( 200, 200, 80, 40, 0.5f, 0, false);
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
