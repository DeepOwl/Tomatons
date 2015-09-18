package com.deepowl.games.tomatons.game;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.deepowl.games.tomatons.game.Equipment.EquipmentType;
import com.deepowl.games.tomatons.game.Tomaton.Slot;
import com.deepowl.games.tomatons.gui.StatusLogger;

/**
 * Created by Danny on 7/26/2015.
 */
public class Game {
	public final static int MAX_PARTY_NUM = 3;
	public final static int MAX_PARTY_SIZE = 3;
    private List<Tomaton> tomatons = new ArrayList<Tomaton>();
    private List<UnitGroup> groups = new ArrayList<UnitGroup>();
    private List<Equipment> inventory = new ArrayList<Equipment>();
    public List<Battle> battles;
    public StatusLogger log;
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
        initiateBattle(new Battle(player, enemies));


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
			}
		}
		for(Battle battle : completedBattles){
			cleanupBattle(battle);
		}
	}

	public List<Equipment> getEquipment() {
		// TODO Auto-generated method stub
		return inventory;
	}

	public Collection<Equipment> getAvailableEquipment(Tomaton focus) {
		Set<Equipment> eq = new HashSet<Equipment>();
		if(focus != null){
			eq.addAll(focus.getAllEquipment());
		}
		eq.addAll(getEquipment());
		return eq;
	}
	public boolean equip(Tomaton tom, Equipment eq, Slot slot){
		if(inventory.contains(eq)){
			Equipment old = tom.getEquipment(slot);
			if(old != null){
				inventory.add(old);
			}
        	log.logStatus(tom.getName() + " equips " + eq);
        	inventory.remove(eq);
			tom.setEquipment(slot, eq);
			return true;
		} else {
			System.out.println("ERROR: tried to equip something not in inventory" + tom + eq + slot.name());
			return false;
		}
	}

	public Collection<Equipment> getAvailableEquipment(Tomaton tom, Slot slot) {
		Set<Equipment> ret = new HashSet<Equipment>();
		for(Equipment eq : getEquipment()){
			if(canEquip(slot, eq.getType())){
				ret.add(eq);
			}
		}
		Equipment current = tom.getEquipment(slot);
		if(current!=null)
			ret.add(tom.getEquipment(slot));
		return ret;
	}
	public boolean canEquip(Slot slot, EquipmentType type){
		if((slot==Slot.CORE && type == EquipmentType.BODY) || 
				(slot==Slot.LEGS && type == EquipmentType.LEG) || 
				(slot==Slot.HEAD && type == EquipmentType.HEAD) || 
				(slot==Slot.RIGHT && type == EquipmentType.ARM) || 
				(slot==Slot.LEFT && type == EquipmentType.ARM)){
			return true;
		}
		return false;
				
	}
	
	public void initiateBattle(Battle battle){
		log.logStatus("Initiating battle");
		battles.add(battle);
	}
	public void cleanupBattle(Battle battle){
		List<Equipment> loot = battle.getLoot();
		log.logStatus("Battle complete!");
		for(Equipment eq : loot){
			log.logStatus("Got "+eq);
		}
		inventory.addAll(loot);	
		battles.remove(battle);
	}

	public void setStatusLogger(StatusLogger logger) {
		log = logger;
		
	}

	public UnitGroup createParty(String text) {
		if(groups.size() < MAX_PARTY_NUM){
			UnitGroup party = new UnitGroup(text);
			log.logStatus("New party created: " + party.getName());
			groups.add(party);
			return party;
		} else {
			log.logStatus("Could not create party: Too many parties already");
			return null;
		}
	}
	public List<UnitGroup> getPartyList(){
		return groups;
	}

	public void addToParty(UnitGroup group, Unit tomaton) {
		if(group.getUnits().size() < MAX_PARTY_SIZE){
			log.logStatus("Adding " + tomaton.getName() + "to party " + group);
			group.getUnits().add(tomaton);
		} else {
			log.logStatus("Could not add to party; too many members.");
		}
	}

	public void removeFromParty(UnitGroup group, Unit unit) {
		if(group.getUnits().contains(unit)){
			log.logStatus("Removed " + unit.getName() + "from party " + group);
			group.getUnits().remove(unit);	
		}
	}
	
	public List<Unit> getFreeUnits(){
		List<Unit> free = new ArrayList<Unit>();
		free.addAll(tomatons);
		for(UnitGroup party: groups){
			free.removeAll(party.getUnits());
		}
		return free;
		
	}
		
	
}
