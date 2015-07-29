package com.deepowl.games.tomatons.game;

/**
 * Created by Danny on 7/26/2015.
 */
public class StatEffect extends Effect {
    Unit.Stat targetStat;
    int modifier;
    public StatEffect(Unit.Stat stat, int modifier){
        targetStat = stat;
        this.modifier =modifier;
    }

}
