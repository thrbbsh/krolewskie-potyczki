package com.krolewskie_potyczki.model;

public enum EntityType {
    TOWER(0, 0, 1000, 100, 50, 0.5f, false),
    SQUARE(3, 300, 300, 20, 100, 0.5f, false),
    TRIANGLE(5, 100, 700, 20, 200, 1.5f, true);

    private final float elixirCost;
    private final float moveSpeed;
    private final float totalHP;
    private final float attackRadius;
    private final float damage;
    private final float attackInterval;
    private final boolean ignoresUnits;

    EntityType(float elixirCost, float moveSpeed, float hp, float attackRadius, float damage, float attackInterval, boolean ignoresUnits) {
        this.elixirCost = elixirCost;
        this.moveSpeed = moveSpeed;
        this.totalHP = hp;
        this.attackRadius = attackRadius;
        this.damage = damage;
        this.attackInterval = attackInterval;
        this.ignoresUnits = ignoresUnits;
    }

    public float getElixirCost() {
        return elixirCost;
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
