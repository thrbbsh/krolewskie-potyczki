package com.krolewskie_potyczki.model.entity;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;

import java.util.Comparator;
import java.util.List;

public class Entity {
    private float HP;
    private final Vector2 pos;
    private final boolean isPlayersEntity;
    private Entity currentTarget;
    private Entity attackTarget;
    protected EntityConfig config;

    private float timeSinceLastAttack = 0f;

    public Entity(EntityConfig config, boolean isPlayersEntity, Vector2 pos) {
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
        if (currentTarget == null)
            return;
        if (distance(currentTarget) <= config.attackRadius)
            return;
        Vector2 dv = currentTarget.pos.cpy().sub(pos);
        pos.add(dv.scl(delta * config.moveSpeed / distance(currentTarget)));
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

    public void receiveDamage(float amount) {
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
}
