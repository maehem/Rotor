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

import com.maehem.rotor.engine.data.DataListener;
import com.maehem.rotor.engine.data.PlayerState;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import com.maehem.rotor.ui.UserInterfaceLayer;
import com.maehem.rotor.ui.debug.DebugChangeSupport;
import com.maehem.rotor.ui.debug.DebugListener;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author maehem
 */
public class ManaIndicator extends VBox implements GameListener, DataListener, DebugListener {
    private final String key = PlayerState.PROP_MANA;
    
    private static final int NBARS = 10;
    public static final int MAX_MANA = 100;
    
    Rectangle[] bars = new Rectangle[NBARS];

    public ManaIndicator(DebugChangeSupport changes) {
        
        changes.addDebugChangeListener(UserInterfaceLayer.DebugProp.SHOW_UI_PANE_BORDERS, this);

        VBox box = new VBox();
                BorderStroke strokes[] = new BorderStroke[2];
        strokes[0] = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2));
        strokes[1] = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(3), new Insets(2));

        //box.setBorder(new Border(strokes));
        box.setBorder(new Border(strokes[1]));
        box.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(6), Insets.EMPTY)));
        
        for ( int i=0; i< NBARS; i++ ) {
            bars[i] = new Rectangle(24, 6);
            bars[i].setFill(Color.GREEN);
            //bars[i].setFill(Color.hsb(180-(180*i/NBARS),1.0,1.0));
            bars[i].setStroke(Color.gray(0.15));
            bars[i].setStrokeWidth(0.5);
            
            box.getChildren().add(0, bars[i]); // Index in reverse so they display from bottom upward.
        }
        
        getChildren().add(box);
    }

    void setValue(int mana) {
        for ( int i=0; i< NBARS; i++ ) {
            if(mana/NBARS > i) {
                bars[i].setFill(Color.hsb((double)mana/MAX_MANA*120.0,1.0,1.0));
            } else if(mana/NBARS == i) {
                bars[i].setFill(Color.hsb((double)mana/MAX_MANA*120.0,1.0,1.0, ((double)mana%NBARS)/10.0));
            } else {
                bars[i].setFill(Color.TRANSPARENT);
            }
        }
    }
    
    @Override
    public void gameEvent(GameEvent e) {
        switch (e.type) {
            case DATA_LOADED:
                PlayerState state = e.getSource().getPlayer().getState();

                state.addDataChangeListener(this.key, this);
                break;

        }
    }

    @Override
    public void dataChange(String dataKey, Object oldValue, Object newValue) {
        if ( dataKey.equals(this.key) ) {
            setValue((int) newValue);
        }
    }

    @Override
    public void debugPropertyChange(UserInterfaceLayer.DebugProp property, Object oldValue, Object newValue) {
        switch(property) {
            case SHOW_UI_PANE_BORDERS:
                showDebugBorders((boolean)newValue);
                break;
        }
    }

    private void showDebugBorders(boolean show) {
        if ( show ) {
            setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        } else {
            setBorder(Border.EMPTY);
        }
    }
    
}
