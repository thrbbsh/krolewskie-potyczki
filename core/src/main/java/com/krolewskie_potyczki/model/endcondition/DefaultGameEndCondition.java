package com.krolewskie_potyczki.model.endcondition;

import com.krolewskie_potyczki.model.TeamType;
import com.krolewskie_potyczki.model.result.MatchResult;
import com.krolewskie_potyczki.model.result.Winner;
import com.krolewskie_potyczki.model.entity.Arena;

public class DefaultGameEndCondition implements GameEndCondition {
    private static final int MAX_CROWNS = 3;

    public DefaultGameEndCondition() {}

    @Override
    public boolean isGameOver(Arena arena) {
        return arena.crownsCount(TeamType.PLAYER) >= MAX_CROWNS
            || arena.crownsCount(TeamType.BOT) >= MAX_CROWNS
            || arena.mainTowerDestroyed(TeamType.PLAYER)
            || arena.mainTowerDestroyed(TeamType.BOT)
            || arena.getTimeLeft() <= 0f;
    }

    @Override
    public MatchResult calculateResult(Arena arena) {
        int pc = arena.crownsCount(TeamType.PLAYER);
        int ec = arena.crownsCount(TeamType.BOT);
        float php = arena.getMinTowerHP(TeamType.PLAYER);
        float ehp = arena.getMinTowerHP(TeamType.BOT);

        if (arena.mainTowerDestroyed(TeamType.PLAYER)) {
            return new MatchResult(Winner.ENEMY, pc, ec, php, ehp);
        }

        if (arena.mainTowerDestroyed(TeamType.BOT)) {
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
