package com.krolewskie_potyczki.model.projectile;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.entity.Entity;

import java.util.Comparator;
import java.util.List;

public class Projectile extends Entity {
    boolean targetNotSet = true;
    public Projectile(EntityConfig config, TeamType teamType, Vector2 pos) {
        super(config, teamType, pos);
        HP = null;
    }
    @Override
    public boolean canBeAttackedBy(EntityType type) {
        return false;
    }
    void setTarget(List<Entity> activeEntities) {
        currentTarget = activeEntities.stream()
            .filter(e -> e.getTeamType() != this.getTeamType())
            .filter(e -> e.canBeAttackedBy(config.type))
            .min(Comparator.comparingDouble(this::directDistance))
            .orElse(null);
        if (currentTarget != null) movePath.add(currentTarget.getHitboxPos());
            else HP = 0f;
    }
    @Override
    public void updateCurrentTarget(List<Entity> activeEntities) {
        if (targetNotSet) {
            setTarget(activeEntities);
            targetNotSet = false;
        }
        else if (currentTarget == null || currentTarget.isDead() || !activeEntities.contains(currentTarget)) HP = 0f;
        else {
            movePath.clear();
            movePath.add(currentTarget.getHitboxPos());
        }
    }

    @Override
    protected void attack(List<Entity> activeEntities) {
        HP = 0f;
        if (currentTarget != null) {
            currentTarget.receiveDamage(config.damage);
        }
    }
}
