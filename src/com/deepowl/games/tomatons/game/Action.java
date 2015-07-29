package com.deepowl.games.tomatons.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danny on 7/26/2015.
 */
public class Action {
    List<Unit> targets;
    Unit source;
    List<Effect> effects;
    public Action(Unit source, List<Unit> targets, List<Effect> effects){
        this.targets=targets;
        this.source=source;
        this.effects=effects;
    }

    public Action(Unit source, Unit target, List<Effect> effects){
        List<Unit> targets = new ArrayList<Unit>();
        targets.add(target);
        this.source=source;
        this.effects=effects;
        this.targets=targets;
    }

    public Action(Unit source, Unit target, Effect effect){
        List<Unit> targets = new ArrayList<Unit>();
        List<Effect> effects = new ArrayList<Effect>();
        targets.add(target);
        effects.add(effect);
        this.source=source;
        this.effects=effects;
        this.targets=targets;

    }


}
