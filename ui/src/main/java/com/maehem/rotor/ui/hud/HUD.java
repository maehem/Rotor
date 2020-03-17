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
import com.maehem.rotor.engine.game.Game;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import com.maehem.rotor.ui.UserInterfaceLayer;
import com.maehem.rotor.ui.debug.DebugChangeSupport;
import com.maehem.rotor.ui.debug.DebugListener;
import java.io.IOException;
import java.io.InputStream;
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

    public static final double PADDING = 4;

    //private double tuckedX = -100;
    private final ManaIndicator mana;
    private final EquipedItemIndicator equiped;
    private final MoneyIndicator money;
    private final BombsIndicator bombs;
    private final ArrowsIndicator arrows;
    private final LifeIndicator life;
    
    private boolean popped = true;

    public HUD(Game game, DebugChangeSupport debugChanges) {
        
        debugChanges.addDebugChangeListener(UserInterfaceLayer.DebugProp.SHOW_UI_PANE_BORDERS, this);
        setAlignment(Pos.TOP_CENTER);
        
        //setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        
        setPadding(new Insets(PADDING));
        setSpacing(20);
        
        mana =    new ManaIndicator(debugChanges);  // Magic
        
        equiped = new EquipedItemIndicator(debugChanges); // Equiped Item
        money =   new MoneyIndicator(debugChanges);     // Money
        bombs =   new BombsIndicator(debugChanges);     // Bombs
        arrows =  new ArrowsIndicator(debugChanges);    // Arrows
        life =    new LifeIndicator(debugChanges);  // LifeIndicator

        
        game.addListener(mana);
        //game.addListener(equiped);  // Not working yet.
        game.addListener(money);
        game.addListener(bombs);
        game.addListener(arrows);
        game.addListener(life);
        
        // Attempt to override the default glyph image with one from
        // another source.  Just supply a ClassLoader and a Path.
        setArrowsGlyph(game.getContentClassLoader(), ArrowsIndicator.ALT_GLYPH );
        setBombsGlyph( game.getContentClassLoader(), BombsIndicator.ALT_GLYPH );
        setMoneyGlyph( game.getContentClassLoader(), MoneyIndicator.ALT_GLYPH );

        // Create a gap between arrows and life panels
        Pane spacer = new Pane( new Rectangle(40, 20, Color.TRANSPARENT));
        //spacer.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));

        getChildren().addAll(mana, equiped, money, bombs, arrows, spacer, life);
        
        game.addListener(this);
    }
    
    public static StackPane createGlyph(Class cl, String path, double size) {
        try {
            //InputStream is = cl.getClassLoader().getResourceAsStream(path);
            //URL glyphResource = cl.getResource(path);
            URL glyphResource = cl.getResource(path);
            
            if ( glyphResource == null ) {
            //if ( is == null) {
                LOGGER.log(Level.WARNING, "{0}:{1} could not be found.", new Object[]{cl.getName(),path});
                return null;
            }
            ImageView glyphImage = new ImageView(new Image(glyphResource.openStream()));
            //ImageView glyphImage = new ImageView(new Image(is));
            glyphImage.setPreserveRatio(true);
            glyphImage.setFitWidth(size);
            return new StackPane(glyphImage);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            //return new StackPane();
            return null;
        }
    }

    public static StackPane createGlyph2(ClassLoader cl, String path, double size) {
//        try {
            InputStream is = cl.getResourceAsStream(path);
            //URL glyphResource = cl.getResource(path);
//            if ( glyphResource == null ) {
//            //if ( is == null) {
//                LOGGER.log(Level.WARNING, "{0}:{1} could not be found.", new Object[]{cl.getName(),path});
//                return null;
//            }
            if ( is == null ) return null;
            
            ImageView glyphImage = new ImageView(new Image(is));
            //ImageView glyphImage = new ImageView(new Image(is));
            glyphImage.setPreserveRatio(true);
            glyphImage.setFitWidth(size);
            return new StackPane(glyphImage);
//        } catch (IOException ex) {
//            LOGGER.log(Level.SEVERE, null, ex);
//            //return new StackPane();
//            return null;
//        }
    }
    
    @Override
    public void gameEvent(GameEvent e) {
        switch ( e.type ) {
            case DATA_LOADED:
                PlayerState state = e.getSource().getWorld().getPlayer().getState();
                mana.setValue(state.getMana());
                // equiped item
                money.setValue(state.getMoney());
                bombs.setValue(state.getBombs());
                arrows.setValue(state.getArrows());
                life.setValue((int) state.getHealth());

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
    
    public final void setLifeGlyph( Class cl, String path ) {
        life.setGlyph( cl, path );
    }
    
    public final void setBombsGlyph( ClassLoader cl, String path ) {
        bombs.setGlyph( cl, path );
    }
    
    public final void setArrowsGlyph( ClassLoader cl, String path ) {
        arrows.setGlyph( cl, path );
    }
    
    public final void setMoneyGlyph( ClassLoader cl, String path ) {
        money.setGlyph( cl, path );
    }
    
}
