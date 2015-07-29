package com.deepowl.games.tomatons.gui;

import java.util.HashMap;
import java.util.Map;

import com.deepowl.games.tomatons.game.Equipment;
import com.deepowl.games.tomatons.game.Game;
import com.deepowl.games.tomatons.game.Tomaton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TomPane extends VBox{
	TextArea statArea;
	Map<Tomaton.Slot, ComboBox> combos;
	///ComboBox comboBox;
	Game game;
	
	public TomPane(Game game){
		super();
		this.game=game;
		combos = new HashMap<Tomaton.Slot, ComboBox>();
		ObservableList<Equipment> options = FXCollections.observableArrayList();
			
		for(Tomaton.Slot slot : Tomaton.Slot.values()){
			System.out.println(slot.name());
			combos.put(slot, new ComboBox(options));
			getChildren().add(combos.get(slot));
			combos.get(slot).setCellFactory(
		            new Callback<ListView<Equipment>, ListCell<Equipment>>() {
		                @Override public ListCell<Equipment> call(ListView<Equipment> param) {
		                    final ListCell<Equipment> cell = new ListCell<Equipment>() {
		                        {
		                            super.setPrefWidth(100);
		                        }    
		                        @Override public void updateItem(Equipment item, 
		                            boolean empty) {
		                                super.updateItem(item, empty);
		                                if (item != null) {
		                                    setText(item.getName());    
		                                }
		                                else {
		                                    setText(null);
		                                }
		                            }
		                };
		                return cell;
		            }
		        });
		}
			
		//getChildren().add(statArea);
			
	}
	
	public void updateEquipment(){
		Map<Tomaton.Slot, ObservableList> datas = new HashMap<Tomaton.Slot, ObservableList>();
		for(Tomaton.Slot slot : Tomaton.Slot.values()){
			ObservableList<Equipment> options = FXCollections.observableArrayList();
			datas.put(slot, options);
		}
		for(Equipment eq : game.getEquipment()){
			datas.get(eq.getSlot()).add(eq);
		}
		for(Tomaton.Slot slot :  Tomaton.Slot.values()){
			combos.get(slot).getItems().clear();
			combos.get(slot).getItems().addAll(datas.get(slot));
			System.out.println(datas.get(slot));
		}
	}
	
}
