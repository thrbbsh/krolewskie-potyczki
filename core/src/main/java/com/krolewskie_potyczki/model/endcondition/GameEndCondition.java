package com.krolewskie_potyczki.model.endcondition;

import com.krolewskie_potyczki.model.result.MatchResult;
import com.krolewskie_potyczki.model.entity.Arena;

public interface GameEndCondition {
    boolean isGameOver(Arena arena);
    MatchResult calculateResult(Arena arena);
}
