package com.krolewskie_potyczki.model;

import java.util.List;

public class Entity {
    float HP, x, y;
    boolean isPlayersEntity;
    Entity currentTarget, attackTarget;
    EntityType type;

    private float timeSinceLastAttack = 0f;

    Entity(EntityType type, boolean isPlayersEntity, float x, float y) {
        currentTarget = null;
        this.isPlayersEntity = isPlayersEntity;
        this.x = x;
        this.y = y;
        this.type = type;
        this.HP = type.getTotalHP();
    }

    float distance(Entity target) {
        if (target == null) return 0;
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

        if (attackTarget == null && distance(currentTarget) > type.getAttackRadius()) {
            move(delta);
            timeSinceLastAttack = 0f;
        } else {
            if (attackTarget != null && attackTarget != currentTarget) {
                attackTarget = null;
                timeSinceLastAttack = 0f;
            } else {
                attackTarget = currentTarget;
                timeSinceLastAttack += delta;
                if (timeSinceLastAttack >= type.getAttackInterval()) {
                    attack();
                    timeSinceLastAttack -= type.getAttackInterval();
                    attackTarget = null;
                }
            }
        }

        if (this instanceof Spawner) {
            ((Spawner) this).updateSpawnUnit(delta);
            this.receiveDamage(delta * ((Spawner) this).getSpawnerBreakSpeed());
        }
    }


    void updateHP(float change) {
        HP = Math.min(type.getTotalHP(), Math.max(0F, HP + change));
    }

    public boolean isDead() {
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
            if (e.isPlayersEntity == this.isPlayersEntity || (this.type.doesIgnoreMovingUnits() && e instanceof MovingUnit)) continue;
            if (currentTarget == null) currentTarget = e;
            else if (distance(currentTarget) > distance(e)) currentTarget = e;
        }
    }

    public EntityType getType() {
        return type;
    }
}
