package com.krolewskie_potyczki.controller;

import com.badlogic.gdx.math.Vector2;
import com.krolewskie_potyczki.model.config.GameConfig;
import com.krolewskie_potyczki.model.team.TeamType;
import com.krolewskie_potyczki.model.config.EntityType;
import com.krolewskie_potyczki.model.entity.Arena;
import com.krolewskie_potyczki.view.ArenaView;
import com.krolewskie_potyczki.model.entity.Deck;

import java.util.List;

public abstract class DeckController {
    public static final float UP_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().upBorder;
    public static final float DOWN_BORDER = GameConfig.getInstance().getZonePointsConstantsConfig().downBorder;

    public static final List<EntityType> SPAWN_LIST = GameConfig.getInstance().getDeckConstants().spawnList;

    protected final Arena arena;
    protected final Deck deck;
    protected final ArenaView arenaView;
    protected final ArenaController arenaController;
    protected TeamType teamType;

    public DeckController(Arena arena, ArenaView arenaView, ArenaController arenaController) {
        this.arena = arena;
        this.arenaView = arenaView;
        this.arenaController = arenaController;
        deck = new Deck(SPAWN_LIST);
    }

    public abstract void onCardClicked(int cardIdx);

    public void onMapTouched(Vector2 pos) {
        arenaController.spawnEntity(deck.getSelectedCardEntityType(), teamType, pos);
        deck.selectedCardWasChosen();
        deck.setSelectedCardIdx(-1);
    }

    public abstract void update(float delta);
}
