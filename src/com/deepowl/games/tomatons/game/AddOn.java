package com.deepowl.games.tomatons.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danny on 7/25/2015.
 */
public class AddOn {
    Tomaton.Slot slot;
    String name;
    List<StatModifier> stats = new ArrayList<StatModifier>();
    
    public AddOn(String name, Tomaton.Slot slot){
        this.name = name;
        this.slot= slot;
    }
    public void addStatModifier(StatModifier mod){
        stats.add(mod);
    }

}
