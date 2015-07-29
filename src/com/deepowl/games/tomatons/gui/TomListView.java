package com.deepowl.games.tomatons.gui;

import java.util.List;

import com.deepowl.games.tomatons.game.Game;
import com.deepowl.games.tomatons.game.Tomaton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class TomListView<Tomaton> extends ListView<Tomaton>{

	
	public void update(Game game){
		ObservableList<Tomaton> items =FXCollections.observableArrayList ();
		List<Tomaton> toms = (List<Tomaton>) game.getTomatons();
		for(Tomaton unit : toms){
			items.add(unit);
		}
		this.setItems(items);
	}
	
	
}
