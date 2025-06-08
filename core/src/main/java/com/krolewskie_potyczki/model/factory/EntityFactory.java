package com.krolewskie_potyczki.model.factory;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.TeamType;
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
        void spawn(TeamType teamType, Vector2 pos, Consumer<Entity> doSpawn);
    }

    private final Map<EntityType, SpawnFunction> registry = new HashMap<>();
    private final GameConfig gameConfig;

    public EntityFactory() {
        gameConfig = GameConfig.getInstance();

        register(EntityType.MAIN_TOWER, (teamType, pos, doSpawn) -> {
            MainTower tower = new MainTower(teamType, pos);
            doSpawn.accept(tower);
        });
        register(EntityType.SIDE_TOWER, (teamType, pos, doSpawn) -> {
            SideTower tower = new SideTower(teamType, pos);
            doSpawn.accept(tower);
        });
        register(EntityType.INFERNO, (teamType, pos, doSpawn) -> {
            Inferno inferno = new Inferno(teamType, pos);
            doSpawn.accept(inferno);
        });
        register(EntityType.TRIANGLE, (teamType, pos, doSpawn) -> {
            TriangleUnit unit = new TriangleUnit(teamType, pos);
            doSpawn.accept(unit);
        });
        register(EntityType.SQUARE, (teamType, pos, doSpawn) -> {
            SquareUnit unit = new SquareUnit(teamType, pos);
            doSpawn.accept(unit);
        });
        register(EntityType.TOMBSTONE, (teamType, pos, doSpawn) -> {
            Tombstone tomb = new Tombstone(teamType, pos, doSpawn);
            doSpawn.accept(tomb);
        });
        register(EntityType.SKELETON, (teamType, pos, doSpawn) -> {
            SkeletonUnit unit = new SkeletonUnit(teamType, pos);
            doSpawn.accept(unit);
        });
        register(EntityType.ARCHER, (teamType, pos, doSpawn) -> {
            ArcherUnit unit = new ArcherUnit(teamType, pos);
            doSpawn.accept(unit);
        });
        register(EntityType.VALKYRIE, (teamType, pos, doSpawn) -> {
            ValkyrieUnit unit = new ValkyrieUnit(teamType, pos);
            doSpawn.accept(unit);
        });

        register(EntityType.SKELETON_ARMY, (teamType, pos, doSpawn) -> {
            CompositeUnit army = new SkeletonArmy(teamType, pos);
            List<Entity> members = army.getEntities();
            for (Entity member : members) {
                doSpawn.accept(member);
            }
        });
        register(EntityType.ARCHER_ARMY, (teamType, pos, doSpawn) -> {
            CompositeUnit army = new ArcherArmy(teamType, pos);
            List<Entity> members = army.getEntities();
            for (Entity member : members) {
                doSpawn.accept(member);
            }
        });
    }

    private void register(EntityType type, SpawnFunction spawnFunction) {
        registry.put(type, spawnFunction);
    }

    public void spawnEntity(EntityType type, TeamType teamType, Vector2 pos, Consumer<Entity> doSpawn) {
        EntityConfig cfg = gameConfig.getEntityConfig(type);
        if (cfg == null) {
            throw new IllegalArgumentException("Unknown entity type: " + type);
        }
        SpawnFunction fn = registry.get(type);
        if (fn == null) {
            throw new IllegalStateException("No spawn function for type: " + type);
        }
        fn.spawn(teamType, pos, doSpawn);
    }
}
