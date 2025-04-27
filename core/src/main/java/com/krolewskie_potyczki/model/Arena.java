package com.krolewskie_potyczki.model;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    List<Entity> activeEntities;
    public Arena() {
        activeEntities = new ArrayList<>();
        activeEntities.add(new Tower(true, 100.0, 100.0));
        activeEntities.add(new Tower(false, 500.0, 100.0));
    }
}
