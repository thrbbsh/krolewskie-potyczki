package com.krolewskie_potyczki.model;

public abstract class Entity {
    double HP, x, y, speed, damage;
    Lifebar lifebar;
    boolean isPlayersEntity;
    Entity currentTarget;
    Entity(boolean isPlayersEntity, double x, double y) {
        this.isPlayersEntity = isPlayersEntity;
        this.x = x;
        this.y = y;
    }

    void move(double delta) {

    }

    public boolean isDead() {
        return (HP >= 0);
    }

    void attack() {

    }

    void getDamage(double damage) {
        lifebar.update(-damage);
    }
}
