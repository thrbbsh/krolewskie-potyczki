package com.krolewskie_potyczki.model.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.unit.ValkyrieUnit;
import com.krolewskie_potyczki.model.Zone;
import com.krolewskie_potyczki.model.building.Tower;
import com.krolewskie_potyczki.model.pathfinder.PathFinder;

import java.util.ArrayList;
import java.util.List;

import static com.krolewskie_potyczki.model.physics.PhysicsWorld.PPM;

public class Entity {
    protected Float HP;
    private final Vector2 viewPos;
    private float hitboxRadius;
    private float hitboxOffsetY;
    private Zone zone;
    private final TeamType teamType;
    protected Entity currentTarget;
    protected Entity attackTarget;
    protected EntityConfig config;
    protected List<Vector2> movePath = new ArrayList<>();

    protected float timeSinceLastAttack = 0f;
    protected float timeSinceFirstAttack = 0f;

    private Body body;

    public Entity(EntityConfig config, TeamType teamType, Vector2 pos) {
        currentTarget = null;
        this.teamType = teamType;
        this.viewPos = pos;
        this.zone = Zone.getZone(pos);
        this.config = config;
        this.HP = config.totalHP;
    }

    protected float directDistance(Entity target) {
        if (target == null) return 0;
        return getHitboxPos().dst(target.getHitboxPos()) - hitboxRadius - target.hitboxRadius;
    }

    public void setBody(Body body) {
        this.body = body;
        this.body.setUserData(this);
        updatePosFromBody();
    }

    public void setHitbox(float hitboxRadius, float hitboxOffsetY) {
        this.hitboxRadius = hitboxRadius;
        this.hitboxOffsetY = hitboxOffsetY;
    }

    public Vector2 getHitboxPos() {
        return viewPos.cpy().add(0, hitboxOffsetY);
    }

    public Body getBody() {
        return body;
    }

    protected void updatePosFromBody() {
        if (body == null) return;
        Vector2 worldPos = body.getPosition();
        viewPos.set(worldPos.x * PPM, worldPos.y * PPM);
    }

    public void move(float ignoredDelta) {
        if (currentTarget == null || directDistance(currentTarget) <= config.attackRadius || movePath.isEmpty()) {
            body.setLinearVelocity(0, 0);
            return;
        }
        Vector2 targetPos = movePath.get(0);
        Vector2 dir = new Vector2(
            (targetPos.x - getHitboxPos().x) / PPM,
            (targetPos.y - getHitboxPos().y) / PPM
        ).nor();
        float velocity = config.moveSpeed / PPM;
        body.setLinearVelocity(dir.scl(velocity));
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public void update(float delta, List<Entity> activeEntities) {
        updatePosFromBody();
        zone = Zone.getZone(viewPos);
        updateCurrentTarget(activeEntities);

        if (attackTarget == null && directDistance(currentTarget) > config.attackRadius) {
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
                    if (!(this instanceof ValkyrieUnit)) attack();
                        else ((ValkyrieUnit) this).attack(activeEntities);
                    timeSinceLastAttack -= config.attackInterval;
                    attackTarget = null;
                }
            }
        }
    }

    void updateHP(float change) {
        if (HP == null) return;
        HP = Math.min(config.totalHP, Math.max(0F, HP + change));
    }

    public boolean isDead() {
        return (HP != null && HP <= 0F);
    }

    protected void attack() {
        if (currentTarget == null)
            return;
        currentTarget.receiveDamage(config.damage);
    }

    public void receiveDamage(float amount) {
        if (isDead()) return;
        updateHP(-amount);
        if (isDead()) {
            onDeath();
        }
    }

    public void onDeath() {
        // TODO (animation, case when tower is "dead")
    }

    public Vector2 getViewPos() {
        updatePosFromBody();
        return viewPos;
    }

    public Float getHP() {
        return HP;
    }

    public void updateCurrentTarget(List<Entity> activeEntities) {
        Entity previousTarget = currentTarget;
        currentTarget = null;
        for (Entity entity : activeEntities) {
            if (teamType == entity.teamType || !entity.canBeAttackedBy(config.type) || !(entity instanceof Tower || directDistance(entity) <= 250)) continue;
            List<Vector2> curPath = PathFinder.findShortestPath(getHitboxPos(), this.zone, entity.getHitboxPos(), entity.zone);
            if (currentTarget == null || (PathFinder.routeDistance(getHitboxPos(), curPath) < PathFinder.routeDistance(getHitboxPos(), movePath))) {
                currentTarget = entity;
                movePath = curPath;
            }
        }
        if (previousTarget != currentTarget) timeSinceFirstAttack = 0;
    }

    public EntityConfig getConfig() { return config; }

    public boolean canBeAttackedBy(EntityType type) {
        return true;
    }
}
