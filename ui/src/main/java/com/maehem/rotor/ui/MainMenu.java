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
package com.maehem.rotor.ui;

import com.maehem.rotor.engine.game.Game;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import com.maehem.rotor.ui.controls.DialogLayer;
import com.maehem.rotor.ui.controls.menu.loadsavesettings.GameLoadDialog;
import com.maehem.rotor.ui.controls.menu.loadsavesettings.GameNewDialog;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author maehem
 */
public class MainMenu extends StackPane  implements DialogLayer, GameListener {
    private static final Logger LOGGER = Logger.getLogger(MainMenu.class.getName());

    Rectangle r = new Rectangle();
    //private final Graphics gfx;
    private Node currentDialog;
    private final Group dialogArea = new Group();
    
    public MainMenu(/*Graphics gfx*/ Game game, int w, int h) {
        LOGGER.config("Main Menu Initialization.");
        //this.gfx = gfx;
        this.setVisible(false);
        //double buttonScale = gfx.canvas.getHeight()/300;
        double buttonScale = h/333.3;
        
        //setPadding(new Insets(30));
        //setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
       
        //Rectangle r = new Rectangle(gfx.canvas.getWidth(), gfx.canvas.getHeight());
        Rectangle r = new Rectangle(w, h);
        r.setFill(new Color(0.5, 0.2, 0.0, 1.0));
        
//        Button b = new Button("OK");
//        b.setTranslateX(-c.getWidth()/20);
//        b.setTranslateY(-c.getHeight()/20);
//        StackPane.setAlignment(b, Pos.BOTTOM_RIGHT);
//        b.setOnMouseClicked((t) -> {
//            hide();
//        });
        
        Button newMap = new Button("New Game...");
        newMap.setScaleX(buttonScale);
        newMap.setScaleY(buttonScale);
        //newMap.setTranslateX(-gfx.canvas.getWidth()/5);
        newMap.setTranslateX(-w/5);
        newMap.setOnMouseClicked((t) -> {
            GameNewDialog d = new GameNewDialog(game, this, 0, -900);
        });
        
        Button loadMap = new Button("Load Game...");
        loadMap.setScaleX(buttonScale);
        loadMap.setScaleY(buttonScale);
        //loadMap.setTranslateX(gfx.canvas.getWidth()/5);
        loadMap.setTranslateX(w/5);
        loadMap.setOnMouseClicked((t) -> {
            GameLoadDialog d = new GameLoadDialog(game, this, 0, -900);
        });
        
//        gfx.canvas.widthProperty().addListener((o, old, newV) -> {
//            r.setWidth(newV.doubleValue());
//            //b.setTranslateX(-c.getWidth()/20);
//        });
//        gfx.canvas.heightProperty().addListener((o,old, newV) -> {
//            r.setHeight(newV.doubleValue());
//            //b.setTranslateY(-c.getHeight()/20);
//        });

        getChildren().addAll(r,/*b,*/ newMap, loadMap, dialogArea);
        
        game.addListener(this);
    }

    public void show() {
        LOGGER.finest("Main menu presented.");
        this.setVisible(true);
    }
    
    
    public void hide() {
        LOGGER.finest("Main menu hidden.");
        this.setVisible(false);
    }

    @Override
    public void presentDialog(Node dialog) {
        if ( currentDialog != null ) {
            dialogArea.getChildren().remove(currentDialog);
            currentDialog = null;
        }
        currentDialog = dialog;
        dialogArea.getChildren().add(dialog);
    }

    @Override
    public void destroyDialog(Node dialog) {
        dialogArea.getChildren().remove(dialog);
    }

    @Override
    public void gameEvent(GameEvent e) {
        switch (e.type) {
            case GAME_INIT:
                hide();
        }
    }
}
