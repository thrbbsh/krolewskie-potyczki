package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.Main;
import com.krolewskie_potyczki.model.Arena;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.screens.MenuScreen;
import com.krolewskie_potyczki.view.CardView;

public class GameController {
    private final Arena arena;
    private final Main game;
    private final ArenaController arenaController;
    private boolean paused = false;
    private boolean ended = false;
    private float EnemySpawn;
    private float EnemySpawnTimer = 0;
    private CardView selectedCard;

    public GameController(Arena arena, Main game) {
        this.arena = arena;
        this.game = game;
        arenaController = new ArenaController(arena);
        EnemySpawn = (5f + (float) (Math.random() * 1f)) / arena.getElixirSpeed();
    }

    public void update(float delta) {
        if (isEnded() || isPaused()) return;
        arenaController.update(delta);
        updateTimer(delta);
        enemyMove(delta);
        int pCount = arena.CrownsCount(true);
        int eCount = arena.CrownsCount(false);
        if (pCount == 3 || eCount == 3 || arena.MainTowerDestroyed(true) || arena.MainTowerDestroyed(false) || arena.getTimeLeft() <= 0f) ended = true;
    }

    private void enemyMove(float delta) {
        EnemySpawnTimer += delta;
        if (EnemySpawnTimer >= EnemySpawn) {
            EntityType type;
            double spawnChance = Math.random();
            if (spawnChance < 0.25) type = EntityType.SQUARE;
            else if (spawnChance < 0.5) type = EntityType.TRIANGLE;
            else if (spawnChance < 0.75) type = EntityType.SKELETON_ARMY;
            else type = EntityType.TOMBSTONE;
            float spawnX = 1200f + (float) (Math.random() * 550f);
            float spawnY = 250f + (float) (Math.random() * 750f);
            arenaController.spawnEntity(type, false, new Vector2(spawnX, spawnY));
            EnemySpawn = (5f + (float) (Math.random() * 1f)) / arena.getElixirSpeed();
            EnemySpawnTimer  = 0f;
        }
    }

    public String getMatchResult() {
        int pCount = arena.CrownsCount(true);
        int eCount = arena.CrownsCount(false);
        if (pCount > eCount) {
            return "You Win!\n" + pCount + ":" + eCount;
        } else if (pCount < eCount) {
            return "You Lose.\n" + pCount + ":" + eCount;
        } else {
            float pHP = arena.getMinTowerHP(true);
            float eHP = arena.getMinTowerHP(false);
            if (pHP > eHP) {
                return "You Win!\n" + pCount + ":" + eCount + "\nPlayer's tower min HP: " + pHP + ".\nEnemy's tower min HP: " + eHP + ".";
            }
            else if (pHP < eHP) {
                return "You Lose.\n" + pCount + ":" + eCount + "\nPlayer's tower min HP: " + pHP + ".\nEnemy's tower min HP: " + eHP + ".";
            } else {
                return "Draw.\n" + pCount + ":" + eCount + "\nPlayer's tower min HP: " + pHP + ".\nEnemy's tower min HP: " + eHP + ".";
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

    public void onSpawnEntityClicked(CardView cardView) {
        if (getPlayerElixir() < cardView.getCard().getElixirCost())
            return;

        if (cardView == selectedCard) {
            cardView.setSelected(false);
            selectedCard = null;
        } else {
            if (selectedCard != null) {
                selectedCard.setSelected(false);
            }
            selectedCard = cardView;
            cardView.setSelected(true);
        }
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

    public void spendElixir(int elixirCost) {
        arenaController.spendElixir(elixirCost);
    }

    public CardView getSelectedCardView() {
        return selectedCard;
    }

    public boolean onMapTouched(Vector2 pos) {
        if (selectedCard == null || getPlayerElixir() < selectedCard.getCard().getElixirCost() ||
            !(287 <= pos.x && pos.x <= 1027 && 227 <= pos.y && pos.y <= 1062))
            return false;
        arenaController.spawnEntity(selectedCard.getCard().getEntityType(), true, pos);
        selectedCard.setSelected(false);
        spendElixir(selectedCard.getCard().getElixirCost());
        selectedCard = null;

        return true;
    }
}
