package com.krolewskie_potyczki.controller;

import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.Entity;
import com.krolewskie_potyczki.model.EntityType;
import com.krolewskie_potyczki.screens.MenuScreen;

public class GameController {
    private final Arena arena;
    private final Main game;
    private final ArenaController arenaController;
    private boolean paused = false;
    private boolean ended = false;
    private float EnemySpawn = 7f + (float) (Math.random() * 3f);
    private float EnemySpawnTimer = 0f;

    public GameController(Arena arena, Main game) {
        this.arena = arena;
        this.game = game;
        arenaController = new ArenaController(arena);
    }

    public void update(float delta) {
        if (isEnded() || isPaused()) return;
        arenaController.update(delta);
        updateTimer(delta);
        enemyMove(delta);
        boolean pAlive = !arena.isPlayerTowerDestroyed();
        boolean eAlive = !arena.isEnemyTowerDestroyed();
        if (!pAlive || !eAlive || arena.getTimeLeft() <= 0f) ended = true;
    }

    private void enemyMove(float delta) {
        EnemySpawnTimer += delta;
        if (EnemySpawnTimer >= EnemySpawn) {
            EntityType type;
            if (Math.random() < 0.7) {
                type = EntityType.SQUARE;
            } else {
                type = EntityType.TRIANGLE;
            }
            float spawnX = 1200f + (float) (Math.random() * 550f);
            float spawnY = 250f + (float) (Math.random() * 750f);
            makeNewEntity(type, false, spawnX, spawnY);
            EnemySpawn = 7f + (float) (Math.random() * 3f);
            EnemySpawnTimer  = 0f;
        }
    }

    private Entity makeNewEntity(EntityType type, boolean b, float spawnX, float spawnY) {
        Entity e = arenaController.spawnEntity(type, false, spawnX, spawnY);
        return e;
    }

    public String getMatchResult() {
        boolean pDead = arena.isPlayerTowerDestroyed();
        boolean eDead = arena.isEnemyTowerDestroyed();
        if (eDead && !pDead) {
            return "You Win!";
        } else if (pDead && !eDead) {
            return "You Lose...";
        } else {
            float pHP = arena.getPlayerTowerHP();
            float eHP = arena.getEnemyTowerHP();
            if (eHP == pHP) {
                return "Draw!";
            } else {
                return (eHP > pHP) ? "You Lose..." : "You Win!";
            }
        }
    }

    private void updateTimer(float delta) {
        float timeLeft = arena.getTimeLeft();
        arena.setTimeLeft(timeLeft - delta);
    }

    public boolean isPaused() {
        return paused;
    }
    public boolean isEnded() {
        return ended;
    }

    public void onPauseClicked() {
        paused = true;
    }
    public void onResumeClicked() {
        paused = false;
    }

    public void onMenuClicked() {
        game.setScreen(new MenuScreen(game));
    }

    public float getPlayerElixir() {
        return arena.getPlayerElixir();
    }

    public float getMaxElixir() {
        return arena.getMaxElixir();
    }

    public String getFormattedTimeLeft() {
        return arena.getFormattedTimeLeft();
    }

    public float getFormattedPlayerElixir() {
        return (float) Math.floor(arena.getPlayerElixir() * 10) / 10;
    }

    public Entity spawnEntity(EntityType entityType, boolean b, int i, int i1) {
        return arenaController.spawnEntity(entityType, b, i, i1);
    }

    public void spendElixir(int elixirCost) {
        arenaController.spendElixir(elixirCost);
    }
}
