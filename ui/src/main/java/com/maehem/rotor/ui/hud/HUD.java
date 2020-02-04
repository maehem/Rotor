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
package com.maehem.rotor.ui.hud;

import com.maehem.rotor.engine.data.PlayerState;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import com.maehem.rotor.renderer.Graphics;
import com.maehem.rotor.ui.controls.UserInterfaceLayer;
import com.maehem.rotor.ui.debug.DebugChangeSupport;
import com.maehem.rotor.ui.debug.DebugListener;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author maehem
 */
public class HUD extends HBox implements GameListener, DebugListener {

    private static final Logger LOGGER = Logger.getLogger(HUD.class.getName());
    
    //public static final double CORNER_ARC = 10;
    //public static final double HEIGHT = 100;
    public static final double PADDING = 4;
    //public static final double STUB_WIDTH = 24;

    private final Graphics gfx;
    
    //private final StackPane tabPopGlyph;
    //private final StackPane tabTuckGlyph;
    //private double tuckedX = -100;
    ManaIndicator mana;
    EquipedItemIndicator equiped;
    MoneyIndicator money;
    BombsIndicator bombs;
    ArrowsIndicator arrows;
    LifeIndicator life;
    

    private boolean popped = true;

    public HUD(Graphics gfx, DebugChangeSupport changes) {
        this.gfx = gfx;
        
        changes.addDebugChangeListener(UserInterfaceLayer.DebugProp.SHOW_UI_PANE_BORDERS, this);
        setAlignment(Pos.TOP_CENTER);
        
        //setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        
        setPadding(new Insets(PADDING));
        setSpacing(20);
        
        mana =    new ManaIndicator(changes);  // Magic
        
        equiped = new EquipedItemIndicator(changes); // Equiped Item
        money =   new MoneyIndicator(changes);     // Money
        bombs =   new BombsIndicator(changes);     // Bombs
        arrows =  new ArrowsIndicator(changes);    // Arrows
        life =    new LifeIndicator(changes);  // LifeIndicator

        // Create a gap between arrows and life panels
        Pane spacer = new Pane( new Rectangle(40, 20, Color.TRANSPARENT));
        //spacer.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        getChildren().addAll(mana, equiped, money, bombs, arrows, spacer, life);
        
        gfx.game.addListener(this);
    }
    
    public static StackPane createGlyph(String path, double size) {
        try {
            URL glyphResource = HUD.class.getResource(path);
            ImageView glyphImage = new ImageView(new Image(glyphResource.openStream()));
            glyphImage.setPreserveRatio(true);
            glyphImage.setFitWidth(size);
            return new StackPane(glyphImage);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return new StackPane();
        }
    }

    @Override
    public void gameEvent(GameEvent e) {
        switch ( e.type ) {
            case DATA_LOADED:
                PlayerState state = gfx.game.getPlayer().getState();
                mana.setValue(state.getMana());
                // equiped item
                money.setValue(state.getMoney());
                bombs.setValue(state.getBombs());
                arrows.setValue(state.getArrows());
                life.setValue(state.getHealth());

        }
    }
    
    @Override
    public void debugPropertyChange(UserInterfaceLayer.DebugProp property, Object oldValue, Object newValue) {
        LOGGER.finest("HUD.debugPropertyChange.");
        switch(property) {
            case SHOW_UI_PANE_BORDERS:
                showDebugBorders((boolean)newValue);
                break;
        }
    }

    private void showDebugBorders(boolean show) {
        if ( show ) {
            setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        } else {
            setBorder(Border.EMPTY);
        }
    }
}
