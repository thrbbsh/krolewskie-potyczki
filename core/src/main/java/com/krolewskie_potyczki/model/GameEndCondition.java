package com.krolewskie_potyczki.model;

public interface GameEndCondition {
    boolean isGameOver(Arena arena);
    MatchResult calculateResult(Arena arena);
}
