package com.krolewskie_potyczki.model;

import java.util.List;

public abstract class Entity {
    float HP, totalHP, x, y, speed, damage, attackRadius;
    boolean isPlayersEntity;
    Entity currentTarget;
    Entity(boolean isPlayersEntity, float x, float y) {
        currentTarget = null;
        this.isPlayersEntity = isPlayersEntity;
        this.x = x;
        this.y = y;
    }

    float distance(Entity target) {
        float dx = this.x - target.x;
        float dy = this.y - target.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public void move(float delta) {
        if (currentTarget == null) return;
        float dx = currentTarget.x - this.x;
        float dy = currentTarget.y - this.y;
        float dist = distance(currentTarget);
        if (dist < attackRadius) return;
        x += dx / dist * delta * speed;
        y += dy / dist * delta * speed;
    }

    public boolean getIsPlayersEntity() {
        return isPlayersEntity;
    }

    void update(float change) {
        HP = Math.min(totalHP, Math.max(0F, HP + change));
    }

    boolean isDead() {
        return (HP >= 0F);
    }

    void attack() {

    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public void updateCurrentTarget(List<Entity> activeEntities) {
        currentTarget = null;
        for (Entity e : activeEntities) {
            if (e.isPlayersEntity == this.isPlayersEntity) continue;
            if (currentTarget == null) currentTarget = e;
            else if (distance(currentTarget) < distance(e)) currentTarget = e;
        }
    }
}
