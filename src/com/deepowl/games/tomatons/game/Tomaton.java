package com.deepowl.games.tomatons.game;

import java.util.Map;

/**
 * Created by Danny on 7/25/2015.
 */
public class Tomaton extends Unit{
    Map<Slot, AddOn> addons;

    public Tomaton(String name, int hp, int str, int mag, int spd, int def) {
        super(name, hp, str, mag, spd, def);
    }

    public static enum Slot {
        HEAD,
        CORE,
        RIGHT,
        LEFT,
        LEGS,
        FEET
    }
}
