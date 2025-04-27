package com.krolewskie_potyczki.model;

public enum EntityType {
    TOWER(0, 0, 1000, 5),
    SQUARE(3, 300, 300, 1);

    private final float elixirCost;
    private final float moveSpeed;
    private final float totalHP;
    private final float attackRadius;

    EntityType(float elixirCost, float moveSpeed, float hp, float attackRadius) {
        this.elixirCost = elixirCost;
        this.moveSpeed = moveSpeed;
        this.totalHP = hp;
        this.attackRadius = attackRadius;
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
}
