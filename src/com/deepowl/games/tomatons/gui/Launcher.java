package com.deepowl.games.tomatons.gui;

import com.deepowl.games.tomatons.game.Game;
import com.deepowl.games.tomatons.game.Tomaton;
import com.deepowl.games.tomatons.game.Unit;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class Launcher extends Application implements StatusLogger, TomSelector{
	TextArea tom;
	Game game;
	TextArea status;
	TomPane tomPane;
	PartyPane partyPane;
	ListView<Unit> list;
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
        stage.setTitle("Hello, World!");
        Launcher pthis = this;
        BorderPane border = new BorderPane();
        Scene scene = new Scene(border);
        Button testBtn = new Button("test");
        border.setTop(testBtn);
        status = new TextArea();
        tom = new TextArea();
        TabPane tabs = new TabPane();
        
        border.setBottom(status);
        game = new Game();
        game.setStatusLogger(this);
        game.startGame();
        border.setLeft(tom);
        tomPane = new TomPane(game);
        Tab equipTab = new Tab();
        equipTab.setContent(tomPane);
        equipTab.setText("Equip");
        tabs.getTabs().add(equipTab);
        
        partyPane = new PartyPane(game, this);
        Tab partyTab = new Tab();
        partyTab.setContent(partyPane);
        partyTab.setText("Party");
        tabs.getTabs().add(partyTab);
        
        //questPane = new QuestPane(game);
        //Tab questTab = new Tab();
        //questTab.setContent(questPane);
        
        border.setCenter(tabs);
        list = new ListView<Unit>();
        list.setCellFactory(new Callback<ListView<Unit>, 
                ListCell<Unit>>() {
                    @Override 
                    public ListCell<Unit> call(ListView<Unit> list) {
                        return new UnitCell();
                    }
                }
            );
        
        
        list.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Unit>() {
                    public void changed(ObservableValue<? extends Unit> ov, 
                        Unit old_val, Unit new_val) {
                    	updateCenterPanel(new_val);
                    	tomPane.updateFocus((Tomaton)new_val);
                           	// label.setText(new_val);
                            //label.setTextFill(Color.web(new_val));
                    	
                }
            });
        ObservableList<Unit> data = FXCollections.observableArrayList();
        for(Unit unit : game.getTomatons()) data.add(unit);
        list.setItems(data);
        border.setRight(list);
		Timeline oneSecondWonder = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event)
            {
				
            	game.advanceTime(1);
                ObservableList<Unit> data = FXCollections.observableArrayList();
                for(Unit unit : game.getTomatons()) data.add(unit);
                int selected = list.getSelectionModel().getSelectedIndex();
                list.setItems(null);
                list.setItems(data);
                list.getSelectionModel().select(selected);
                //tomList
                //tom.setText(""+game.getTomatons().get(0));
                //testBtn.setText("time"+currentNanoTime);
                //status.appendText("time"+currentNanoTime+"\n");
                tomPane.updateEquipment();
               
            }
        }));
		oneSecondWonder.setCycleCount(Timeline.INDEFINITE);
		oneSecondWonder.play();
        stage.setScene(scene);
        stage.show();
	}
	private void updateCenterPanel(Unit unit){
		if(unit != null){
			tom.setText(unit.getName() + "\n"
					+"HP: " + unit.getStat(Unit.Stat.HP));
		}
	}

    static class UnitCell extends ListCell<Unit> {
        @Override
        public void updateItem(Unit item, boolean empty) {
            super.updateItem(item, empty);
            //Rectangle rect = new Rectangle(100, 20);
            if (item != null) {
            	setText(item.getName() + " HP: " + item.getStat(Unit.Stat.HP));
            } else setText(null);
        }
    }
    
    public void logStatus(String message){
    	status.appendText(message + "\n");
    }

  public static void main(String[] args) 
    {
        launch(args);
    }
	@Override
	public Tomaton getTomaton() {
		return (Tomaton) list.getSelectionModel().getSelectedItem();
	}
}

