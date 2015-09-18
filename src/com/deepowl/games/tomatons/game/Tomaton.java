package com.deepowl.games.tomatons.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deepowl.games.tomatons.game.Tomaton.Slot;

/**
 * Created by Danny on 7/25/2015.
 */
public class Tomaton extends Unit{
    Map<Slot, Equipment> equipment;

    public Tomaton(String name, int hp, int str, int mag, int spd, int def) {
        super(name, hp, str, mag, spd, def);
        equipment = new  HashMap<Slot, Equipment>();
    }

    public static enum Slot {
        HEAD,
        CORE,
        RIGHT,
        LEFT,
        LEGS,
        FEET
    }

	public Equipment getEquipment(Slot slot) {
		return equipment.get(slot);
	}

	public void setEquipment(Slot slot, Equipment new_val) {
		equipment.put(slot, new_val);
		
	}

	public Collection<Equipment> getAllEquipment() {
		Collection<Equipment> ret = equipment.values();
		ret.remove(null);
		return ret;
		
	}
}
