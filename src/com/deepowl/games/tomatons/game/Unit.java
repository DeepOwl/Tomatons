package com.deepowl.games.tomatons.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Danny on 7/25/2015.
 */
public class Unit {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;
    Map<Stat, Integer> stats = new HashMap<Stat, Integer>();
    public Unit(){
        name = "unnamed unit";
        stats.put(Stat.AP, 0);
        stats.put(Stat.HP, 0);
        stats.put(Stat.STR, 0);
        stats.put(Stat.MAG, 0);
        stats.put(Stat.SPD, 0);
        stats.put(Stat.MAX_HP, 0);
    stats.put(Stat.DEF, 0);
}
    public Unit(String name, int hp, int str, int mag, int spd, int def){
        this.name = name;
        stats.put(Stat.AP, 0);
        stats.put(Stat.HP, hp);
        stats.put(Stat.STR, str);
        stats.put(Stat.MAG, mag);
        stats.put(Stat.SPD, spd);
        stats.put(Stat.MAX_HP, hp);
        stats.put(Stat.DEF, def);
    }
    public int getStat(Stat stat){
        return stats.get(stat);
    }
    public void adjustStat(Stat stat, int i) {
        stats.put(stat , stats.get(stat) + i);
    }

    public boolean isReady(){
        return stats.get(Stat.AP) <=0;
    }
    public void adjustHP(int dmg){
       adjustStat(Stat.HP, dmg);
    }
    public boolean isAlive(){
        return stats.get(Stat.HP) > 0;
    }
    public enum Stat{
        STR,
        SPD,
        HP,
        MAG,
        AP,
        MAX_HP,
        DEF
    }

    public Action getAction(Battle battle){
        return getBasicAction(battle);
    }
    public Action getBasicAction(Battle battle){
        //randomly select target from opponents
        UnitGroup enemies = battle.getEnemiesOf(this);
        Random random = new Random();
        Unit target = enemies.getUnits().get(random.nextInt(enemies.getUnits().size()));
        StatEffect eff = new StatEffect(Stat.HP, -1*getStat(Stat.STR));

        return new Action(this, target, eff);
    }
    
    public String toString(){
    	return ""+name+"HP:"+this.getStat(Stat.HP);
    }


}
