package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.krolewskie_potyczki.model.bot.BotMoveLogic;
import com.krolewskie_potyczki.model.building.Building;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.factory.EntityFactory;
import com.krolewskie_potyczki.model.physics.PhysicsWorld;

import java.util.List;

public class ArenaController {
    public static final Vector2 PLAYER_MAIN_TOWER = GameConfig.getInstance().getArenaConstants().playerMainTower;
    public static final Vector2 PLAYER_SIDE1_TOWER = GameConfig.getInstance().getArenaConstants().playerSide1Tower;
    public static final Vector2 PLAYER_SIDE2_TOWER = GameConfig.getInstance().getArenaConstants().playerSide2Tower;
    public static final Vector2 BOT_MAIN_TOWER = GameConfig.getInstance().getArenaConstants().botMainTower;
    public static final Vector2 BOT_SIDE1_TOWER = GameConfig.getInstance().getArenaConstants().botSide1Tower;
    public static final Vector2 BOT_SIDE2_TOWER = GameConfig.getInstance().getArenaConstants().botSide2Tower;

    private final Arena arena;
    private final EntityFactory entityFactory;
    private final PhysicsWorld physicsWorld;
    private final BotMoveLogic botMoveLogic;

    public ArenaController() {
        arena = new Arena();
        botMoveLogic = new BotMoveLogic(arena);
        entityFactory = new EntityFactory();
        entityFactory.setProjectileSpawnListener(this::spawnEntity);
        physicsWorld = new PhysicsWorld();
        spawnTowers();
    }

    void spawnTowers() {
        spawnEntity(EntityType.MAIN_TOWER, TeamType.PLAYER, PLAYER_MAIN_TOWER);
        spawnEntity(EntityType.SIDE_TOWER, TeamType.PLAYER, PLAYER_SIDE1_TOWER);
        spawnEntity(EntityType.SIDE_TOWER, TeamType.PLAYER, PLAYER_SIDE2_TOWER);
        spawnEntity(EntityType.MAIN_TOWER, TeamType.BOT, BOT_MAIN_TOWER);
        spawnEntity(EntityType.SIDE_TOWER, TeamType.BOT, BOT_SIDE1_TOWER);
        spawnEntity(EntityType.SIDE_TOWER, TeamType.BOT, BOT_SIDE2_TOWER);
    }

    public void spawnEntity(EntityType type, TeamType teamType, Vector2 pos) {
        entityFactory.spawnEntity(type, teamType, pos.cpy(), entity -> {
            Texture tex = new Texture(String.format(
                "skins/%s/%s%s.png",
                teamType == TeamType.PLAYER ? "player" : "bot",
                teamType == TeamType.PLAYER ? "player" : "bot",
                entity.getConfig().type
            ));
            float wPx = tex.getWidth();
            float hPx = tex.getHeight();
            float hitboxRadiusPx = wPx / 2 * 0.8f;
            float hitboxOffsetYPx = (hitboxRadiusPx - hPx) / 2;


            float xM = entity.getViewPos().x / PhysicsWorld.PPM;
            float yM = entity.getViewPos().y / PhysicsWorld.PPM;

            float hitboxRadiusM = hitboxRadiusPx / PhysicsWorld.PPM;
            float hitboxOffsetYM = hitboxOffsetYPx / PhysicsWorld.PPM;

            BodyDef.BodyType bodyType = entity instanceof Building ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

            Body body = physicsWorld.createRectangleBody(
                entity.getConfig(),
                xM, yM,
                hitboxRadiusM,
                hitboxOffsetYM,
                bodyType
            );
            entity.setBody(body);
            entity.setHitbox(hitboxRadiusPx, hitboxOffsetYPx);
            arena.addEntity(entity);
        });
    }

    public void update(float delta) {
        physicsWorld.step(delta);
        arena.updateTimer(-delta);
        arena.updatePlayerElixir(delta);
        botMoveLogic.update(delta, this::spawnEntity);

        List<Entity> deadEntities = arena.getActiveEntities().stream().filter(Entity::isDead).toList();

        deadEntities.forEach(entity -> {
            physicsWorld.destroyBody(entity.getBody());
            arena.removeEntity(entity);
        });

        arena.getActiveEntities().stream().toList().forEach(entity -> entity.update(delta, arena.getActiveEntities()));
    }

    public Arena getArena() {
        return arena;
    }
}
