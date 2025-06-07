package com.krolewskie_potyczki.model.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;

import java.util.Comparator;
import java.util.List;

import static com.krolewskie_potyczki.model.physics.PhysicsWorld.PPM;

public class Entity {
    private float HP;
    private final Vector2 pos;
    private final boolean isPlayersEntity;
    protected Entity currentTarget;
    private Entity attackTarget;
    protected EntityConfig config;

    private float timeSinceLastAttack = 0f;
    protected float timeSinceFirstAttack = 0f;

    private Body body;

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

    public void setBody(Body body) {
        this.body = body;
        this.body.setUserData(this);
        updatePosFromBody();
    }

    public Body getBody() {
        return body;
    }

    private void updatePosFromBody() {
        if (body == null) return;
        Vector2 worldPos = body.getPosition();
        pos.set(worldPos.x * PPM, worldPos.y * PPM);
    }

    public void move(float delta) {
        if (currentTarget == null || distance(currentTarget) <= config.attackRadius) {
            body.setLinearVelocity(0, 0);
            return;
        }
        Vector2 dir = new Vector2(
            (currentTarget.pos.x - pos.x) / PPM,
            (currentTarget.pos.y - pos.y) / PPM
        ).nor();
        float velocity = config.moveSpeed / PPM;
        body.setLinearVelocity(dir.scl(velocity));
    }

    public boolean getIsPlayersEntity() {
        return isPlayersEntity;
    }

    public void update(float delta, List<Entity> activeEntities) {
        updatePosFromBody();
        updateCurrentTarget(activeEntities);

        if (attackTarget == null && distance(currentTarget) > config.attackRadius) {
            move(delta);
            timeSinceFirstAttack = 0f;
            timeSinceLastAttack = 0f;
        } else {
            if (attackTarget != null && attackTarget != currentTarget) {
                attackTarget = null;
                timeSinceLastAttack = 0f;
            } else {
                attackTarget = currentTarget;
                timeSinceFirstAttack += delta;
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

    protected void attack() {
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
        updatePosFromBody();
        return pos;
    }

    public float getHP() {
        return HP;
    }

    public void updateCurrentTarget(List<Entity> activeEntities) {
        Entity previousTarget = currentTarget;
        currentTarget = activeEntities.stream()
            .filter(e -> e.isPlayersEntity != this.isPlayersEntity)
            .filter(e -> e.canBeAttackedBy(config.type))
            .min(Comparator.comparingDouble(this::distance))
            .orElse(null);
        if (previousTarget != currentTarget) timeSinceFirstAttack = 0;
    }

    public EntityConfig getConfig() { return config; }

    public boolean canBeAttackedBy(EntityType type) {
        return true;
    }
}
