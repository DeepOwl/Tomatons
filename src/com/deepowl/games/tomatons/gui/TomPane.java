package com.deepowl.games.tomatons.gui;

import java.util.HashMap;
import java.util.Map;

import com.deepowl.games.tomatons.game.Equipment;
import com.deepowl.games.tomatons.game.Equipment.EquipmentType;
import com.deepowl.games.tomatons.game.Game;
import com.deepowl.games.tomatons.game.Tomaton;
import com.deepowl.games.tomatons.game.Tomaton.Slot;
import com.deepowl.games.tomatons.game.Unit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TomPane extends VBox{
	TextArea statArea;
	ComboBox headSlot;
	ComboBox rightSlot;
	ComboBox leftSlot;
	ComboBox legSlot;
	ComboBox bodySlot;
	///ComboBox comboBox;
	Map<ComboBox, Slot> types = new HashMap<ComboBox, Slot>();
	Game game;
	Tomaton focus;
	boolean dontChange;
	public TomPane(Game game){
		super();
		
		this.game=game;
		focus = null;
		headSlot=new ComboBox();
		bodySlot=new ComboBox();
		rightSlot=new ComboBox();
		leftSlot=new ComboBox();
		legSlot=new ComboBox();
		types.put(headSlot, Slot.HEAD);
		types.put(leftSlot, Slot.LEFT);
		types.put(rightSlot, Slot.RIGHT);
		types.put(bodySlot, Slot.CORE);
		types.put(legSlot, Slot.LEGS);
		dontChange= false;
		//combos = new HashMap<Tomaton.Slot, ComboBox>();
		ObservableList<Equipment> options = FXCollections.observableArrayList();
			
		//for(Tomaton.Slot slot : Tomaton.Slot.values()){
		//	System.out.println(slot.name());
		//	combos.put(slot, new ComboBox(options));
		//	getChildren().add(combos.get(slot));
		//	combos.get(slot).setCellFactory();
	    Callback<ListView<Equipment>, ListCell<Equipment>> cellFactory = new Callback<ListView<Equipment>, ListCell<Equipment>>() {
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
	        };
	        
			for(ComboBox combo : types.keySet()){
				HBox hbox = new HBox();
				hbox.getChildren().add(new Label(types.get(combo).name()));
				hbox.getChildren().add(combo);
				getChildren().add(hbox);
				combo.setCellFactory(cellFactory);
				combo.getSelectionModel().selectedItemProperty().addListener(
		                new ChangeListener<Equipment>() {
		                    public void changed(ObservableValue<? extends Equipment> ov, 
		                        Equipment old_val, Equipment new_val) {
		                    	if(focus!=null && !dontChange){
		                    		//System.out.println(old_val + " " + new_val);
		                    		game.equip(focus, new_val,  types.get(combo));
		                    	}
		                }
		            });
			}		
	}
	
	public void updateEquipment(){
		dontChange = true;
		rightSlot.getItems().clear();
		leftSlot.getItems().clear();
		bodySlot.getItems().clear();
		legSlot.getItems().clear();
		headSlot.getItems().clear();
		dontChange = false;
		if(focus== null) return;
		for(ComboBox combo : types.keySet()){
			for(Equipment eq : game.getAvailableEquipment(focus, types.get(combo))){
				combo.getItems().add(eq);
			}
			
			dontChange = true;
			combo.getSelectionModel().select(focus.getEquipment(types.get(combo)));			
			dontChange = false;
		}
		
	}
	private void addIfNotPresent(ComboBox combo, Equipment eq){
		if(!combo.getItems().contains(eq)){
			combo.getItems().add(eq);
		}
	}

	public void updateFocus(Tomaton new_val) {
		focus = new_val;		
	}
	
}
