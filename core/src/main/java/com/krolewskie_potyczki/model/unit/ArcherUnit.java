package com.krolewskie_potyczki.model.unit;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.building.UsesProjectiles;
import com.krolewskie_potyczki.model.projectile.ProjectileSpawnListener;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class ArcherUnit extends Unit implements UsesProjectiles {
    public ArcherUnit(TeamType teamType, Vector2 pos) {
        super(GameConfig.getInstance().getEntityConfig(EntityType.ARCHER), teamType, pos);
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
        listener.onProjectileSpawned(EntityType.ARROW, getTeamType(), getViewPos());
    }
}
