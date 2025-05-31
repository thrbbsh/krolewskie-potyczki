package com.krolewskie_potyczki.model.factory;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.unit.CompositeUnit;
import com.krolewskie_potyczki.model.unit.SkeletonArmy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompositeEntityFactory {
    public interface SpawnFunction {
        CompositeUnit spawn(EntityConfig cfg, boolean isPlayerEntity, Vector2 pos);
    }

    private final Map<EntityType, SpawnFunction> registry = new HashMap<>();
    private final GameConfig gameConfig;

    public CompositeEntityFactory() {
        gameConfig = GameConfig.getInstance();

        register(EntityType.SKELETON_ARMY, (cfg, isP, pos) -> new SkeletonArmy(isP, pos));
    }

    private void register(EntityType type, SpawnFunction spawnFunction) {
        registry.put(type, spawnFunction);
    }

    public List<Entity> spawnEntity(EntityType type, boolean isPlayerEntity, Vector2 pos) {
        EntityConfig cfg = gameConfig.getEntityConfig(type);
        if (cfg == null) {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
        SpawnFunction fn = registry.get(type);
        if (fn == null) {
            throw new IllegalStateException("No spawn function for type: " + type);
        }
        return fn.spawn(cfg, isPlayerEntity, pos).getEntities();
    }
}
