package com.deepowl.games.tomatons.game;

import java.util.ArrayList;
import java.util.List;

import com.deepowl.games.tomatons.game.Tomaton.Slot;

public class Equipment {
	String name;
	EquipmentType type;
	List<StatEffect> effects;
	List<StatModifier> stats = new ArrayList<StatModifier>();
	public Equipment(String name, EquipmentType type, List<StatEffect> effects){
		this.name=name;
		this.type=type;
		this.effects=effects;
	}
	public EquipmentType getType(){
		return type;
	}
	public String getName() {
		return name;
	}
	public enum EquipmentType {
		HEAD,
		ARM,
		BODY,
		LEG
	}
	public String toString(){
		return name;
	}
}
