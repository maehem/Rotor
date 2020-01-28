/*
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

*/
package com.maehem.rotor.ui.controls.menu.loadsavesettings;

import com.maehem.rotor.engine.data.WorldState;
import com.maehem.rotor.engine.game.Game;
import com.maehem.rotor.ui.controls.DialogLayer;
import com.maehem.rotor.ui.controls.DialogPanel;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author maehem
 */
public class GameNewDialog extends DialogPanel {

    private static final Logger LOGGER = Logger.getLogger(GameNewDialog.class.getName());
    private static final ResourceBundle MSG = ResourceBundle.getBundle("MessageBundle");

    private final Game game;
    private TextField colonyNameField;
    private TextField commanderNameField;
    private ToggleGroup diffGroup;
    private ToggleGroup startGroup;
    private ToggleGroup sizeGroup;

    public GameNewDialog(Game game, DialogLayer dialogLayer, double x, double y) {
        super(MSG.getString("DIALOG_LSS_TITLE_NEWGAME"), dialogLayer, x, y, true);
        this.game = game;

        LOGGER.fine(MSG.getString("MENU_LSS_LOGMSG_NEWGAME_DIALOG"));
        HBox hbox = new HBox();
        //hbox.setPrefSize(1200, 700);
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);

        hbox.getChildren().add(newGameSelectionPane());

        // Add more optons later.
        setContent(hbox);

        
        getDoneButton().addEventHandler(MouseEvent.MOUSE_CLICKED, (t) -> {
            LOGGER.finer("New Game DONE button clicked.");
            // Read all the Dialog settings and apply them here.
            WorldState gameState = game.getWorld().initState();
            
            //d.mapInfo.setName(colonyNameField.getText());
            gameState.setPlayerName(colonyNameField.getText());
            //d.mapInfo.setMoney((int) diffGroup.getSelectedToggle().getUserData());
            gameState.setPlayerDifficulty((int) diffGroup.getSelectedToggle().getUserData());
          
            game.initNewGame(); // ???
        });

        // Start a timer to fade and then destroy this dialog.
    }

    private HBox newGameSelectionPane() {
        Rectangle image = new Rectangle(300, 300); // Will be the eye-catch image on right.

        HBox hb = new HBox();
        hb.setSpacing(30);
        VBox vb = new VBox(
                getColonyNamePanel(),
                getDifficultyLevelPanel()
        );
        vb.setSpacing(10);
        
        hb.getChildren().add(vb);

        hb.getChildren().add(image);

        return hb;
    }

    private Node getColonyNamePanel() {
        VBox box = new VBox();
        box.setPadding(new Insets(6));
        
        Text label = new Text("Name:");
        colonyNameField = new TextField();
        
        box.getChildren().addAll(label, colonyNameField);
        return box;
    }

    private Node getDifficultyLevelPanel() {
        VBox box = new VBox();
        box.setSpacing(6);
        box.setPadding(new Insets(6));        
        
        Text label = new Text("Difficulty:");
        RadioButton easyB = new RadioButton(  "Easy");
        RadioButton mediumB = new RadioButton("Medium");
        RadioButton hardB = new RadioButton(  "Hard");
        
        easyB.setUserData(80000);
        mediumB.setUserData(50000);
        hardB.setUserData(20000);
        
        diffGroup = new ToggleGroup();
        easyB.setToggleGroup(diffGroup);
        mediumB.setToggleGroup(diffGroup);
        hardB.setToggleGroup(diffGroup);
        
        easyB.setSelected(true);
        
        box.getChildren().addAll(label, easyB, mediumB, hardB);
        return box;
    }

}
