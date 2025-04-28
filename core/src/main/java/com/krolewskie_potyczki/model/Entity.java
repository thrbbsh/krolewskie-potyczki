package com.krolewskie_potyczki.model;

import java.util.List;

public abstract class Entity {
    float HP, x, y;
    boolean isPlayersEntity;
    Entity currentTarget;
    EntityType type;

    private float timeSinceLastAttack = 0f;

    Entity(EntityType type, boolean isPlayersEntity, float x, float y) {
        currentTarget = null;
        this.isPlayersEntity = isPlayersEntity;
        this.x = x;
        this.y = y;
        this.type = type;
        HP = type.getTotalHP();
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
        if (dist <= type.getAttackRadius()) return;
        x += dx / dist * delta * type.getMoveSpeed();
        y += dy / dist * delta * type.getMoveSpeed();
    }

    public boolean getIsPlayersEntity() {
        return isPlayersEntity;
    }

    public void update(float delta, List<Entity> activeEntities) {
        updateCurrentTarget(activeEntities);
        move(delta);

        timeSinceLastAttack += delta;

        if (currentTarget != null && distance(currentTarget) <= type.getAttackRadius() &&
            timeSinceLastAttack >= type.getAttackInterval()) {
            attack();
            timeSinceLastAttack = 0f;
        }
    }


    void updateHP(float change) {
        HP = Math.min(type.getTotalHP(), Math.max(0F, HP + change));
    }

    boolean isDead() {
        return (HP <= 0F);
    }

    void attack() {
        currentTarget.receiveDamage(type.getDamage());
    }

    void receiveDamage(float amount) {
        updateHP(-amount);
        if (isDead()) {
            onDeath();
        }
    }

    void onDeath() {
        System.out.println("I am dead.");
        // TODO (animation, case when tower is "dead")
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public float getHP() {
        return HP;
    }

    public void updateCurrentTarget(List<Entity> activeEntities) {
        currentTarget = null;
        for (Entity e : activeEntities) {
            if (e.isPlayersEntity == this.isPlayersEntity) continue;
            if (currentTarget == null) currentTarget = e;
            else if (distance(currentTarget) > distance(e)) currentTarget = e;
        }
    }
}
