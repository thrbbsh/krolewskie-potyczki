package com.krolewskie_potyczki.model;

public abstract class Entity {
    float HP, x, y, speed, damage;
    Lifebar lifebar;
    boolean isPlayersEntity;
    Entity currentTarget;
    Entity(boolean isPlayersEntity, float x, float y) {
        this.isPlayersEntity = isPlayersEntity;
        this.x = x;
        this.y = y;
    }

    void move(double delta) {

    }

    public boolean getIsPlayersEntity() {
        return isPlayersEntity;
    }

    public boolean isDead() {
        return (HP >= 0);
    }

    void attack() {

    }

    void getDamage(double damage) {
        lifebar.update(-damage);
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
}
