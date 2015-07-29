package com.deepowl.games.tomatons.game;

import java.util.List;

import com.deepowl.games.tomatons.game.Tomaton.Slot;

public class Equipment {
	String name;
	Tomaton.Slot slot;
	List<StatEffect> effects;
	public Equipment(String name, Tomaton.Slot slot, List<StatEffect> effects){
		this.name=name;
		this.slot=slot;
		this.effects=effects;
	}
	public Slot getSlot(){
		return slot;
	}
	public String getName() {
		return name;
	}
}
