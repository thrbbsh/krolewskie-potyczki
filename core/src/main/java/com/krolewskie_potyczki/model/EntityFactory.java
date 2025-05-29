package com.krolewskie_potyczki.model;

import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.GameConfig;

import java.util.HashMap;
import java.util.Map;

public class EntityFactory {
    public interface SpawnFunction {
        Entity spawn(EntityConfig cfg, boolean isPlayerEntity, float x, float y);
    }

    private final Map<String, SpawnFunction> registry = new HashMap<>();
    private final GameConfig gameConfig;

    public EntityFactory() {
        gameConfig = GameConfig.getInstance();
        registry.put("MainTower", (cfg, isP, x, y) -> new MainTower(isP, x, y));
        registry.put("SideTower", (cfg, isP, x, y) -> new SideTower(isP, x, y));
        registry.put("Triangle", (cfg, isP, x, y) -> new TriangleUnit(isP, x, y));
        registry.put("Square",   (cfg, isP, x, y) -> new SquareUnit(isP, x, y));
        registry.put("Tombstone",(cfg, isP, x, y) -> new TombstoneUnit(isP, x, y));
        registry.put("Skeleton", (cfg, isP, x, y) -> new SkeletonUnit(isP, x, y));
    }

    public Entity spawnEntity(String type, boolean isPlayerEntity, float x, float y) {
        EntityConfig cfg = gameConfig.getEntityConfig(type);
        if (cfg == null) {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
        SpawnFunction fn = registry.get(type);
        if (fn == null) {
            throw new IllegalStateException("No spawn function for type: " + type);
        }
        return fn.spawn(cfg, isPlayerEntity, x, y);
    }
}
