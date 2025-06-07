package com.krolewskie_potyczki.model.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityType;

public class PhysicsWorld {
    public static final float PPM = 100f;

    private final World world;
    private final Box2DDebugRenderer debugRenderer =
        new Box2DDebugRenderer(true, true, true, true, true, true);

    private final static int velocityIterations = 6;
    private final static int positionIterations = 2;

    private final OrthographicCamera debugCamera;

    public PhysicsWorld() {
        this.world = new World(new Vector2(0, 0), true);

        float w = Gdx.graphics.getWidth()  / PPM;
        float h = Gdx.graphics.getHeight() / PPM;
        debugCamera = new OrthographicCamera(w, h);
        debugCamera.position.set(w/2f, h/2f, 0);
        debugCamera.update();
    }

    public void step(float delta) {
        world.step(delta, velocityIterations, positionIterations);
    }

    public World getWorld() {
        return world;
    }

    public void debugRender(Camera stageCamera) {
        debugCamera.viewportWidth  = stageCamera.viewportWidth  / PPM;
        debugCamera.viewportHeight = stageCamera.viewportHeight / PPM;
        debugCamera.position.set(
            stageCamera.position.x / PPM,
            stageCamera.position.y / PPM,
            0
        );
        debugCamera.update();
        debugRenderer.render(world, debugCamera.combined);
    }

    public Body createRectangleBody(EntityType tp, float x, float y, float width, float height, BodyDef.BodyType type) {
        BodyDef bd = new BodyDef();
        bd.type = type;
        bd.position.set(x, y);
        Body body = world.createBody(bd);

        body.setFixedRotation(true);
        body.setLinearDamping(5f);
        body.setAngularDamping(5f);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2f, height/2f);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;

        // TODO: load weight from config.
        fd.density = 1000.0f;
        if (tp == EntityType.SKELETON) {
            fd.density = 0.1f;
        }
        fd.friction = 0.8f;
        fd.restitution = 0.0f;
        body.createFixture(fd);
        shape.dispose();

        return body;
    }

    public void destroyBody(Body body) {
        world.destroyBody(body);
    }

    public void dispose() {
        debugRenderer.dispose();
        world.dispose();
    }
}
