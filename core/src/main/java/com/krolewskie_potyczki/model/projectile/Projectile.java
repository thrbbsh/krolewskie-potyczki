package com.krolewskie_potyczki.model.projectile;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.krolewskie_potyczki.model.building.Tower;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.pathfinder.PathFinder;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.entity.Entity;

import java.util.Comparator;
import java.util.List;

public class Projectile extends Entity {
    boolean targetNotSet = true;
    public Projectile(EntityConfig config, TeamType teamType, Vector2 pos) {
        super(config, teamType, pos);
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
            else HP = 0;
    }
    @Override
    public void updateCurrentTarget(List<Entity> activeEntities) {
        if (targetNotSet) {
            setTarget(activeEntities);
            targetNotSet = false;
        }
    }

    @Override
    protected void attack() {
        currentTarget.receiveDamage(config.damage);
        HP = 0;
    }
}
