package com.krolewskie_potyczki.model;

public enum EntityType {
    TOWER(0, 0, 1000, 50, 50, 0.5f),
    SQUARE(3, 300, 300, 20, 100, 0.5f);

    private final float elixirCost;
    private final float moveSpeed;
    private final float totalHP;
    private final float attackRadius;
    private final float damage;
    private final float attackInterval;

    EntityType(float elixirCost, float moveSpeed, float hp, float attackRadius, float damage, float attackInterval) {
        this.elixirCost = elixirCost;
        this.moveSpeed = moveSpeed;
        this.totalHP = hp;
        this.attackRadius = attackRadius;
        this.damage = damage;
        this.attackInterval = attackInterval;
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
}
