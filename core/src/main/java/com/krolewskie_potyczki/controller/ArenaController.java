package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.model.entity.Entity;
import com.krolewskie_potyczki.model.factory.EntityFactory;
import com.krolewskie_potyczki.model.physics.PhysicsWorld;

import java.util.ArrayList;
import java.util.List;

public class ArenaController {
    private final Arena arena;
    private final EntityFactory entityFactory;
    private final PhysicsWorld physicsWorld;

//    private final Stage stage;

    public ArenaController() {
//        this.stage = stage;
        arena = new Arena();
        entityFactory = new EntityFactory();
        physicsWorld = new PhysicsWorld();
        spawnTowers();
    }

    void spawnTowers() {
        spawnEntity(EntityType.MAIN_TOWER, TeamType.PLAYER, new Vector2(380, 655));
        spawnEntity(EntityType.SIDE_TOWER, TeamType.PLAYER, new Vector2(470, 405));
        spawnEntity(EntityType.SIDE_TOWER, TeamType.PLAYER, new Vector2(470, 905));
        spawnEntity(EntityType.MAIN_TOWER, TeamType.BOT, new Vector2(1815, 655));
        spawnEntity(EntityType.SIDE_TOWER, TeamType.BOT, new Vector2(1725, 405));
        spawnEntity(EntityType.SIDE_TOWER, TeamType.BOT, new Vector2(1725, 905));
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

            BodyDef.BodyType bodyType = switch (entity.getConfig().type) {
                case MAIN_TOWER, SIDE_TOWER, TOMBSTONE, INFERNO -> BodyDef.BodyType.StaticBody;
                default -> BodyDef.BodyType.DynamicBody;
            };

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
        List<Entity> toRemove = new ArrayList<>();
        List<Entity> curActiveEntities = new ArrayList<>(arena.getActiveEntities());
        for (Entity e: curActiveEntities)
            if (e.isDead()) {
                toRemove.add(e);
            }

        for (Entity e : toRemove) {
            physicsWorld.destroyBody(e.getBody());
        }
        toRemove.forEach(arena::removeEntity);

        for (Entity e : new ArrayList<>(arena.getActiveEntities()))
            e.update(delta, arena.getActiveEntities());

//        physicsWorld.debugRender(stage.getViewport().getCamera());
    }

    public Arena getArena() {
        return arena;
    }
}
