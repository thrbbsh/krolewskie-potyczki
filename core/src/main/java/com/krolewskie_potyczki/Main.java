package com.krolewskie_potyczki;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.krolewskie_potyczki.screens.MenuScreen;

public class Main extends Game {
    @Override
    public void setScreen(Screen screen) {
        Screen old = getScreen();
        super.setScreen(screen);
        if (old != null) old.dispose();
    }
    @Override
    public void create() { setScreen(new MenuScreen(this)); }

    public void exitGame() {
        Gdx.app.exit();
    }
}

