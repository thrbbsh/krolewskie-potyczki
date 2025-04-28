package com.krolewskie_potyczki.model;

public enum EntityType {
    TOWER(0, 0, 1000, 5, false),
    SQUARE(3, 300, 300, 1, false),
    TRIANGLE(5, 100, 700, 1, true);

    private final float elixirCost;
    private final float moveSpeed;
    private final float totalHP;
    private final float attackRadius;
    private final boolean ignoresUnits;

    EntityType(float elixirCost, float moveSpeed, float hp, float attackRadius, boolean ignoresUnits) {
        this.elixirCost = elixirCost;
        this.moveSpeed = moveSpeed;
        this.totalHP = hp;
        this.attackRadius = attackRadius;
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

    public boolean doesIgnoreUnits() {
        return ignoresUnits;
    }
}
