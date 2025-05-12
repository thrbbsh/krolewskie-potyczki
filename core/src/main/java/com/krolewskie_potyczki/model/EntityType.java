package com.krolewskie_potyczki.model;

public enum EntityType {
    TOWER( 0, 3000, 200, 50, 0.5f, false),
    SQUARE(300, 300, 80, 100, 0.5f, false),
    TRIANGLE( 140, 700, 80, 400, 1.5f, true);
    private final float moveSpeed;
    private final float totalHP;
    private final float attackRadius;
    private final float damage;
    private final float attackInterval;
    private final boolean ignoresUnits;

    EntityType(float moveSpeed, float hp, float attackRadius, float damage, float attackInterval, boolean ignoresUnits) {
        this.moveSpeed = moveSpeed;
        this.totalHP = hp;
        this.attackRadius = attackRadius;
        this.damage = damage;
        this.attackInterval = attackInterval;
        this.ignoresUnits = ignoresUnits;
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

    public boolean doesIgnoreUnits() {
        return ignoresUnits;
    }
}
