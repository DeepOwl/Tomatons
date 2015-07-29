package com.deepowl.games.tomatons.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.deepowl.games.tomatons.game.Tomaton.Slot;

/**
 * Created by Danny on 7/26/2015.
 */
public class Battle {
    private UnitGroup player;
    private UnitGroup enemy;


    public Battle(UnitGroup player, UnitGroup enemy){
        this.player = player;
        this.enemy  = enemy;
    }

    public void advanceTime(int seconds){
        for(int i=0;i< seconds; i++){
            player.adjustAP(-1);
            List<Action> playerActions = player.getActions(this);
            applyActions(playerActions);

            enemy.adjustAP(-1);
            List<Action> enemyActions = enemy.getActions(this);
            applyActions(enemyActions);


        }
    }

    private void applyActions(List<Action> playerActions) {
        for(Action action : playerActions){
            for(Unit target : action.targets){
                for(Effect effect : action.effects){
                    applyEffect(action.source, target, effect);
                }
            }
        }
    }

    private void applyEffect(Unit source, Unit target, Effect effect) {
        //calculate hit probability
        int hitChance = 90;
        Random random = new Random();
        if (random.nextInt(100) <= 90){ // success
            if(effect instanceof StatEffect){
                StatEffect sEffect = (StatEffect) effect;
                target.adjustStat(sEffect.targetStat, sEffect.modifier);
                //((TextView) view).append(source.getName() + "->" + target.getName() + sEffect.targetStat.name() + sEffect.modifier+ "\n");
            }
        } else {
            //miss!

        }

    }

    public UnitGroup getEnemiesOf(Unit unit) {
        if(player.getUnits().contains(unit)) return enemy;
        else if(enemy.getUnits().contains(unit)) return player;
        else return null;
    }

    public boolean isOver() {
        if(player.isAnyoneAlive() && enemy.isAnyoneAlive()){
            return false;
        }
        return true;
    }

    public boolean isPlayerWin(){
        if(isOver() && player.isAnyoneAlive()){
            return true;
        } else return false;
    }
    
    public List<Equipment> getLoot(){
    	List<Equipment> ret = new ArrayList<Equipment>();
    	if(isOver() && isPlayerWin()){
    		List<StatEffect> effects = new ArrayList<StatEffect>();
    		effects.add(new StatEffect(Unit.Stat.STR, 2));
    		Equipment leftArm = new Equipment("BlasterArm", Slot.LEFT, effects);
    		ret.add(leftArm);
    	}
    	return ret;
    }
}
