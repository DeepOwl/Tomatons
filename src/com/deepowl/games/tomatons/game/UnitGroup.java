package com.deepowl.games.tomatons.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danny on 7/25/2015.
 */
public class UnitGroup {
    List<Unit> units = new ArrayList<Unit>();
    String name = "NO NAME GROUP";
    public UnitGroup() {
    }

    public UnitGroup(String text) {
		name = text;
	}

	public List<Unit> getUnits() {
        return units;
    }

    public void adjustAP(int dAP) {
        for(Unit unit : units){
            unit.adjustStat(Unit.Stat.AP, -1);
        }
    }

    public List<Action> getActions(Battle battle){
        List<Action> ret = new ArrayList<Action>();
        for(Unit unit : units){
            if(unit.isAlive() && unit.isReady()){
                ret.add(unit.getAction(battle));
            }
        }
        return ret;
    }

    public boolean isAnyoneAlive(){
        for(Unit unit : units){
            if(unit.isAlive()){
                return true;
            }
        }
        return false;
    }

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
