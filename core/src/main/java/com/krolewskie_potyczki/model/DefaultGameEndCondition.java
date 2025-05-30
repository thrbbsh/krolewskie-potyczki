package com.krolewskie_potyczki.model;

public class DefaultGameEndCondition implements GameEndCondition {
    private static final int MAX_CROWNS = 3;

    public DefaultGameEndCondition() {}

    @Override
    public boolean isGameOver(Arena arena) {
        return arena.crownsCount(true) >= MAX_CROWNS
            || arena.crownsCount(false) >= MAX_CROWNS
            || arena.mainTowerDestroyed(true)
            || arena.mainTowerDestroyed(false)
            || arena.getTimeLeft() <= 0f;
    }

    @Override
    public MatchResult calculateResult(Arena arena) {
        int pc = arena.crownsCount(true);
        int ec = arena.crownsCount(false);
        float php = arena.getMinTowerHP(true);
        float ehp = arena.getMinTowerHP(false);

        if (arena.mainTowerDestroyed(true)) {
            return new MatchResult(Winner.ENEMY, pc, ec, php, ehp);
        }

        if (arena.mainTowerDestroyed(false)) {
            return new MatchResult(Winner.PLAYER, pc, ec, php, ehp);
        }

        Winner winner;
        if (pc > ec) {
            winner = Winner.PLAYER;
        } else if (pc < ec) {
            winner = Winner.ENEMY;
        } else {
            if (php > ehp) winner = Winner.PLAYER;
            else if (php < ehp) winner = Winner.ENEMY;
            else winner = Winner.DRAW;
        }

        return new MatchResult(winner, pc, ec, php, ehp);
    }
}
