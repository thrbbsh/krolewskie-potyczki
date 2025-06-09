package com.krolewskie_potyczki.model.building;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

import com.krolewskie_potyczki.model.projectile.ProjectileSpawnListener;

public class SideTower extends Tower implements UsesProjectiles {
    public SideTower(TeamType teamType, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.SIDE_TOWER), teamType, pos);
    }
    ProjectileSpawnListener listener;
    @Override
    public void setProjectileSpawnListener(ProjectileSpawnListener listener) {
        this.listener = listener;
    }
    @Override
    protected void attack() {
        if (currentTarget == null)
            return;
        listener.onProjectileSpawned(EntityType.SHURIKEN, getTeamType(), getViewPos());
    }
}
