package com.deepowl.games.tomatons.gui;

import com.deepowl.games.tomatons.game.Equipment;
import com.deepowl.games.tomatons.game.Game;
import com.deepowl.games.tomatons.game.Unit;
import com.deepowl.games.tomatons.game.UnitGroup;
import com.deepowl.games.tomatons.gui.Launcher.UnitCell;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class PartyPane extends VBox{
	Game game = null;
	TextField newParty;
	ListView<UnitGroup> partyList;
	ListView<Unit> memberList;
	TomSelector selector;
	ListView<Unit> freeList;
	
	
	public PartyPane(Game game, TomSelector sel){
		super();
		this.game = game;
		selector = sel;
		HBox newBox = new HBox();
		newParty = new TextField("Party Name");
		Button newButton = new Button("Create");
		newButton.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                UnitGroup newGroup = game.createParty(newParty.getText());
                updatePartyList();
                if(newGroup != null) {
                	partyList.getSelectionModel().select(newGroup);
                }
            }
        });
		newBox.getChildren().add(newParty);
		newBox.getChildren().add(newButton);
		this.getChildren().add(newBox);
		
        partyList = new ListView<UnitGroup>();
        partyList.setPrefHeight(Game.MAX_PARTY_NUM * 24 + 2);
        partyList.setCellFactory(new Callback<ListView<UnitGroup>, 
                ListCell<UnitGroup>>() {
                    @Override 
                    public ListCell<UnitGroup> call(ListView<UnitGroup> list) {
                        return new UnitGroupCell();
                    }
                }
            );
        this.getChildren().add(partyList);
        partyList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<UnitGroup>() {
                    public void changed(ObservableValue<? extends UnitGroup> ov, 
                        UnitGroup old_val, UnitGroup new_val) {
                    		updateMemberList(new_val);
                    	}
                });
        
        memberList = new ListView<Unit>();
        memberList.setCellFactory(new Callback<ListView<Unit>, 
                ListCell<Unit>>() {
                    @Override 
                    public ListCell<Unit> call(ListView<Unit> list) {
                        return new UnitCell();
                    }
                }
            );
        memberList.setPrefHeight(Game.MAX_PARTY_SIZE * 24 + 2);
        this.getChildren().add(memberList);
        
        freeList = new ListView<Unit>();
        freeList.setCellFactory(new Callback<ListView<Unit>, 
                ListCell<Unit>>() {
                    @Override 
                    public ListCell<Unit> call(ListView<Unit> list) {
                        return new UnitCell();
                    }
                }
            );
        ScrollPane scroll = new ScrollPane(freeList);
        scroll.setPrefHeight(Game.MAX_PARTY_SIZE * 24 + 2);
        this.getChildren().add(scroll);
		Button addMember = new Button("Add selected Tom to Party");
		addMember.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	UnitGroup selectedParty = partyList.getSelectionModel().getSelectedItem();
            	Unit selectedUnit = freeList.getSelectionModel().getSelectedItem();
            	if( selectedUnit != null && selectedParty != null){
	                game.addToParty(selectedParty, selectedUnit);
	                updateMemberList(selectedParty);
            	}
            }
        });
		Button removeMember = new Button("Remove from party");
		removeMember.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	UnitGroup selectedParty = partyList.getSelectionModel().getSelectedItem();
            	Unit selectedUnit = memberList.getSelectionModel().getSelectedItem();
            	if(selectedUnit!= null && selectedParty != null){
	                game.removeFromParty(selectedParty, selectedUnit);
	                updateMemberList(selectedParty);
            	}
            }
        });
		HBox addRemoveBtns = new HBox();
		addRemoveBtns.getChildren().add(addMember);
		addRemoveBtns.getChildren().add(removeMember);
		this.getChildren().add(addRemoveBtns);
	}
	
    protected void updatePartyList() {
    	UnitGroup selected = partyList.getSelectionModel().getSelectedItem();
		partyList.getItems().clear();
		for(UnitGroup party : game.getPartyList()){
			partyList.getItems().add(party);
		}
		//updateMemberList(selected);
		
	}
    protected void updateMemberList(UnitGroup party){
    	ObservableList<Unit> data = FXCollections.observableArrayList();
    	memberList.getItems().clear();  	
    	if(party != null){
	    	for(Unit tom : party.getUnits()){
	    		memberList.getItems().add(tom);
	    	}
    	}
    	updateFreeList();
    }

    protected void updateFreeList(){
    	freeList.getItems().clear();
    	for(Unit unit : game.getFreeUnits()){
    		freeList.getItems().add(unit);
    	}
    }
    
	static class UnitGroupCell extends ListCell<UnitGroup> {
        @Override
        public void updateItem(UnitGroup item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
            	setText(item.getName());
            }
        }
    }
	
	
}

