package com.krolewskie_potyczki.model.pathfinder;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.GameConfig;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {
    public static final Vector2 UPPER_BRIDGE_LEFT_POINT  = GameConfig.getInstance().getArenaConstants().upperBridgeLeftPoint;
    public static final Vector2 UPPER_BRIDGE_RIGHT_POINT = GameConfig.getInstance().getArenaConstants().upperBridgeRightPoint;
    public static final Vector2 LOWER_BRIDGE_LEFT_POINT  = GameConfig.getInstance().getArenaConstants().lowerBridgeLeftPoint;
    public static final Vector2 LOWER_BRIDGE_RIGHT_POINT = GameConfig.getInstance().getArenaConstants().lowerBridgeRightPoint;

    public static List<Vector2> findShortestPath(Vector2 startPos, Zone startZone, Vector2 goalPos, Zone goalZone) {
        List<Vector2> cw  = findClockWisePath(startPos, startZone, goalPos, goalZone);
        List<Vector2> ccw = findCounterClockWisePath(startPos, startZone, goalPos, goalZone);
        return routeDistance(startPos, cw) < routeDistance(startPos, ccw) ? cw : ccw;
    }

    public static List<Vector2> findClockWisePath(Vector2 startPos, Zone startZone, Vector2 goalPos, Zone goalZone) {
        List<Vector2> path = new ArrayList<>();
        Zone zone = startZone;

        while (zone != goalZone) {
            zone = switch (zone) {
                case BLUE_BASE -> {
                    path.add(UPPER_BRIDGE_LEFT_POINT);
                    yield Zone.UPPER_BRIDGE;
                }
                case UPPER_BRIDGE -> {
                    path.add(UPPER_BRIDGE_RIGHT_POINT);
                    yield Zone.RED_BASE;
                }
                case RED_BASE -> {
                    path.add(LOWER_BRIDGE_RIGHT_POINT);
                    yield Zone.LOWER_BRIDGE;
                }
                case LOWER_BRIDGE -> {
                    path.add(LOWER_BRIDGE_LEFT_POINT);
                    yield Zone.BLUE_BASE;
                }
            };
        }
        path.add(goalPos);

        if (startPos.dst(path.get(0)) < 20) {
            path.remove(0);
        }
        return path;
    }

    public static List<Vector2> findCounterClockWisePath(Vector2 startPos, Zone startZone, Vector2 goalPos, Zone goalZone) {
        List<Vector2> path = new ArrayList<>();
        Zone zone = startZone;

        while (zone != goalZone) {
            zone = switch (zone) {
                case BLUE_BASE -> {
                    path.add(LOWER_BRIDGE_LEFT_POINT);
                    yield Zone.LOWER_BRIDGE;
                }
                case LOWER_BRIDGE -> {
                    path.add(LOWER_BRIDGE_RIGHT_POINT);
                    yield Zone.RED_BASE;
                }
                case RED_BASE -> {
                    path.add(UPPER_BRIDGE_RIGHT_POINT);
                    yield Zone.UPPER_BRIDGE;
                }
                case UPPER_BRIDGE -> {
                    path.add(UPPER_BRIDGE_LEFT_POINT);
                    yield Zone.BLUE_BASE;
                }
            };
        }
        path.add(goalPos);

        if (startPos.dst(path.get(0)) < 20) {
            path.remove(0);
        }
        return path;
    }

    public static float routeDistance(Vector2 startPos, List<Vector2> path) {
        float dist = 0f;
        Vector2 current = new Vector2(startPos);
        for (Vector2 pt : path) {
            dist += current.dst(pt);
            current.set(pt);
        }
        return dist;
    }
}
