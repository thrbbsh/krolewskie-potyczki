package com.krolewskie_potyczki.model.factory;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.building.Inferno;
import com.krolewskie_potyczki.model.building.MainTower;
import com.krolewskie_potyczki.model.building.SideTower;
import com.krolewskie_potyczki.model.building.Tombstone;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.unit.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EntityFactory {
    public interface SpawnFunction {
        void spawn(boolean isPlayerEntity, Vector2 pos, Consumer<Entity> doSpawn);
    }

    private final Map<EntityType, SpawnFunction> registry = new HashMap<>();
    private final GameConfig gameConfig;

    public EntityFactory() {
        gameConfig = GameConfig.getInstance();

        register(EntityType.MAIN_TOWER, (isP, pos, doSpawn) -> {
            MainTower tower = new MainTower(isP, pos);
            doSpawn.accept(tower);
        });
        register(EntityType.SIDE_TOWER, (isP, pos, doSpawn) -> {
            SideTower tower = new SideTower(isP, pos);
            doSpawn.accept(tower);
        });
        register(EntityType.INFERNO, (isP, pos, doSpawn) -> {
            Inferno inferno = new Inferno(isP, pos);
            doSpawn.accept(inferno);
        });
        register(EntityType.TRIANGLE, (isP, pos, doSpawn) -> {
            TriangleUnit unit = new TriangleUnit(isP, pos);
            doSpawn.accept(unit);
        });
        register(EntityType.SQUARE, (isP, pos, doSpawn) -> {
            SquareUnit unit = new SquareUnit(isP, pos);
            doSpawn.accept(unit);
        });
        register(EntityType.TOMBSTONE, (isP, pos, doSpawn) -> {
            Tombstone tomb = new Tombstone(isP, pos, doSpawn);
            doSpawn.accept(tomb);
        });
        register(EntityType.SKELETON, (isP, pos, doSpawn) -> {
            SkeletonUnit sk = new SkeletonUnit(isP, pos);
            doSpawn.accept(sk);
        });

        register(EntityType.SKELETON_ARMY, (isP, pos, doSpawn) -> {
            CompositeUnit army = new SkeletonArmy(isP, pos);
            List<Entity> members = army.getEntities();
            for (Entity member : members) {
                doSpawn.accept(member);
            }
        });
    }

    private void register(EntityType type, SpawnFunction spawnFunction) {
        registry.put(type, spawnFunction);
    }

    public void spawnEntity(EntityType type, boolean isPlayerEntity, Vector2 pos, Consumer<Entity> doSpawn) {
        EntityConfig cfg = gameConfig.getEntityConfig(type);
        if (cfg == null) {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
        SpawnFunction fn = registry.get(type);
        if (fn == null) {
            throw new IllegalStateException("No spawn function for type: " + type);
        }
        fn.spawn(isPlayerEntity, pos, doSpawn);
    }
}
