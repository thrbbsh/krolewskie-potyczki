package com.krolewskie_potyczki.model.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.EntityConfig;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.config.GameConfig;

public class PhysicsWorld {
    public static final float PPM = GameConfig.getInstance().getGameConstants().PPM;

    public static final float RIVER_X_START = GameConfig.getInstance().getZonePointsConstantsConfig().riverXStart;
    public static final float RIVER_WIDTH = GameConfig.getInstance().getZonePointsConstantsConfig().riverWidth;
    public static final float RIVER_Y_UPPER_PART_START = GameConfig.getInstance().getZonePointsConstantsConfig().riverYUpperPartStart;
    public static final float RIVER_Y_UPPER_PART_HEIGHT = GameConfig.getInstance().getZonePointsConstantsConfig().riverYUpperPartHeight;
    public static final float RIVER_Y_MID_PART_START = GameConfig.getInstance().getZonePointsConstantsConfig().riverYMidPartStart;
    public static final float RIVER_Y_MID_PART_HEIGHT = GameConfig.getInstance().getZonePointsConstantsConfig().riverYMidPartHeight;
    public static final float RIVER_Y_LOWER_PART_START = GameConfig.getInstance().getZonePointsConstantsConfig().riverYLowerPartStart;
    public static final float RIVER_Y_LOWER_PART_HEIGHT = GameConfig.getInstance().getZonePointsConstantsConfig().riverYLowerPartHeight;
    public static final float LEFT_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().leftBorder;
    public static final float RIGHT_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().rightBorder;
    public static final float UP_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().upBorder;
    public static final float DOWN_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().downBorder;
    public static final float SCREEN_WIDTH = GameConfig.getInstance().getZonePointsConstantsConfig().screenWidth;
    public static final float SCREEN_HEIGHT = GameConfig.getInstance().getZonePointsConstantsConfig().screenHeight;
    public static final float BASIC_WALL_WIDTH = GameConfig.getInstance().getZonePointsConstantsConfig().basicWallWidth;
    public static final float BASIC_WALL_HEIGHT = GameConfig.getInstance().getZonePointsConstantsConfig().basicWallHeight;

    private final static int VELOCITY_ITERATIONS = GameConfig.getInstance().getPhysicsWorldConstants().velocityIterations;
    private final static int POSITION_ITERATIONS = GameConfig.getInstance().getPhysicsWorldConstants().positionIterations;
    private final static int LINEAR_DAMPING = GameConfig.getInstance().getPhysicsWorldConstants().linearDamping;
    private final static int ANGULAR_DAMPING = GameConfig.getInstance().getPhysicsWorldConstants().angularDamping;

    private final World world;
    private final Box2DDebugRenderer debugRenderer =
        new Box2DDebugRenderer(true, true, true, true, true, true);

    private final OrthographicCamera debugCamera;

    public PhysicsWorld() {
        this.world = new World(new Vector2(0, 0), true);
        setWalls();

        float w = Gdx.graphics.getWidth()  / PPM;
        float h = Gdx.graphics.getHeight() / PPM;
        debugCamera = new OrthographicCamera(w, h);
        debugCamera.position.set(w/2f, h/2f, 0);
        debugCamera.update();
    }

    public void step(float delta) {
        world.step(delta, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
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

    public Body createRectangleBody(EntityConfig cf, float x, float y, float radius, float hitboxOffsetY, BodyDef.BodyType type) {
        BodyDef bd = new BodyDef();
        bd.type = type;
        bd.position.set(x, y);
        Body body = world.createBody(bd);

        body.setFixedRotation(true);
        body.setLinearDamping(LINEAR_DAMPING);
        body.setAngularDamping(ANGULAR_DAMPING);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        shape.setPosition(new Vector2(0, hitboxOffsetY));

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;

        fd.density = cf.density;
        fd.friction = 0;
        fd.restitution = cf.restitution;
        if (cf.type == EntityType.SHURIKEN) fd.filter.maskBits = 0;

        body.createFixture(fd);
        shape.dispose();

        return body;
    }

    public void createWall(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x + width / 2, y + height / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    private void setWalls() {
        createWall(RIVER_X_START / PhysicsWorld.PPM, RIVER_Y_UPPER_PART_START / PhysicsWorld.PPM, RIVER_WIDTH / PhysicsWorld.PPM, RIVER_Y_UPPER_PART_HEIGHT / PhysicsWorld.PPM);
        createWall(RIVER_X_START / PhysicsWorld.PPM, RIVER_Y_MID_PART_START / PhysicsWorld.PPM, RIVER_WIDTH / PhysicsWorld.PPM, RIVER_Y_MID_PART_HEIGHT / PhysicsWorld.PPM);
        createWall(RIVER_X_START / PhysicsWorld.PPM, RIVER_Y_LOWER_PART_START / PhysicsWorld.PPM, RIVER_WIDTH / PhysicsWorld.PPM, RIVER_Y_LOWER_PART_HEIGHT / PhysicsWorld.PPM);
        createWall(0 / PhysicsWorld.PPM, 0 / PhysicsWorld.PPM, SCREEN_WIDTH / PhysicsWorld.PPM, DOWN_BORDER / PhysicsWorld.PPM);
        createWall(0 / PhysicsWorld.PPM, UP_BORDER / PhysicsWorld.PPM, SCREEN_WIDTH / PhysicsWorld.PPM, BASIC_WALL_HEIGHT / PhysicsWorld.PPM);
        createWall(0 / PhysicsWorld.PPM, 0 / PhysicsWorld.PPM, LEFT_BORDER / PhysicsWorld.PPM, SCREEN_HEIGHT / PhysicsWorld.PPM);
        createWall(RIGHT_BORDER / PhysicsWorld.PPM, 0 / PhysicsWorld.PPM, BASIC_WALL_WIDTH / PhysicsWorld.PPM, SCREEN_HEIGHT / PhysicsWorld.PPM);
    }

    public void destroyBody(Body body) {
        world.destroyBody(body);
    }

    public void dispose() {
        debugRenderer.dispose();
        world.dispose();
    }
}
