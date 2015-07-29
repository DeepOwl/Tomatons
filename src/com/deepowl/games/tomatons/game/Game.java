package com.deepowl.games.tomatons.game;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danny on 7/26/2015.
 */
public class Game {
    private List<Tomaton> tomatons = new ArrayList<Tomaton>();
    private List<UnitGroup> groups = new ArrayList<UnitGroup>();
    private List<Equipment> inventory = new ArrayList<Equipment>();
    public List<Battle> battles;
    public Game(){
    	battles = new ArrayList<Battle>();
        tomatons.add(new Tomaton("Tom1", 10, 3, 5, 5, 5));
        tomatons.add(new Tomaton("Tom2", 10, 3, 5, 5, 5));
        tomatons.add(new Tomaton("Tom3", 10, 3, 5, 5, 5));
    }

    public void startGame(){
        
        UnitGroup player = new UnitGroup();
        player.getUnits().add(tomatons.get(0));
        Unit enemy = new Unit("Enemy", 10, 2, 5, 5, 5);
        UnitGroup enemies = new UnitGroup();
        enemies.getUnits().add(enemy);
        battles.add(new Battle(player, enemies));


        /*
        while(!battle.isOver()){
            ((TextView)view).append("advancing time 1\n");
            battle.advanceTime(1);

        }*/
    }
    
    public List<Tomaton> getTomatons(){
    	return tomatons;
    }

	public void advanceTime(int i) {
		// TODO Auto-generated method stub
		List<Battle> completedBattles = new ArrayList<Battle>();
		for(Battle battle : battles){
			battle.advanceTime(i);
			if(battle.isOver()){
				completedBattles.add(battle);
				inventory.addAll(battle.getLoot());			
			}
		}
		for(Battle battle : completedBattles){
			battles.remove(battle);
		}
	}

	public List<Equipment> getEquipment() {
		// TODO Auto-generated method stub
		return inventory;
	}
}
