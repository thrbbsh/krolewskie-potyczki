package com.krolewskie_potyczki.model;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;

import java.util.Comparator;
import java.util.List;

public class Entity {
    float HP;
    Vector2 pos;
    boolean isPlayersEntity;
    Entity currentTarget;
    Entity attackTarget;
    EntityConfig config;

    private float timeSinceLastAttack = 0f;

    Entity(EntityConfig config, boolean isPlayersEntity, Vector2 pos) {
        currentTarget = null;
        this.isPlayersEntity = isPlayersEntity;
        this.pos = pos;
        this.config = config;
        this.HP = config.totalHP;
    }

    float distance(Entity target) {
        if (target == null) return 0;
        return pos.dst(target.pos);
    }

    public void move(float delta) {
        if (currentTarget == null) return;
        float dx = currentTarget.pos.x - this.pos.x;
        float dy = currentTarget.pos.y - this.pos.y;
        float dist = distance(currentTarget);
        if (dist <= config.attackRadius) return;
        pos.add(dx / dist * delta * config.moveSpeed, dy / dist * delta * config.moveSpeed);
    }

    public boolean getIsPlayersEntity() {
        return isPlayersEntity;
    }

    public void update(float delta, List<Entity> activeEntities) {
        updateCurrentTarget(activeEntities);

        if (attackTarget == null && distance(currentTarget) > config.attackRadius) {
            move(delta);
            timeSinceLastAttack = 0f;
        } else {
            if (attackTarget != null && attackTarget != currentTarget) {
                attackTarget = null;
                timeSinceLastAttack = 0f;
            } else {
                attackTarget = currentTarget;
                timeSinceLastAttack += delta;
                if (timeSinceLastAttack >= config.attackInterval) {
                    attack();
                    timeSinceLastAttack -= config.attackInterval;
                    attackTarget = null;
                }
            }
        }
    }

    void updateHP(float change) {
        HP = Math.min(config.totalHP, Math.max(0F, HP + change));
    }

    public boolean isDead() {
        return (HP <= 0F);
    }

    void attack() {
        if (currentTarget == null)
            return;
        currentTarget.receiveDamage(config.damage);
    }

    void receiveDamage(float amount) {
        updateHP(-amount);
        if (isDead()) {
            onDeath();
        }
    }

    public void onDeath() {
        // TODO (animation, case when tower is "dead")
    }

    public Vector2 getPos() {
        return pos;
    }

    public float getHP() {
        return HP;
    }

    public void updateCurrentTarget(List<Entity> activeEntities) {
        currentTarget = activeEntities.stream()
            .filter(e -> e.isPlayersEntity != this.isPlayersEntity)
            .filter(e -> e.canBeAttackedBy(config.type))
            .min(Comparator.comparingDouble(this::distance))
            .orElse(null);
    }

    public EntityConfig getConfig() { return config; }

    public boolean canBeAttackedBy(EntityType type) {
        return true;
    }

    public boolean isTowerForPlayer(boolean isPlayer) {
        return false;
    }

    public boolean isReadyToSpawn() {
        return false;
    }
}
