package com.krolewskie_potyczki;

import com.badlogic.gdx.Game;
import com.krolewskie_potyczki.screens.MenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}

