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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author maehem
 */
public abstract class ItemAmountIndicator extends VBox implements GameListener, DataListener, DebugListener {
    private static final Logger LOGGER = Logger.getLogger(ItemAmountIndicator.class.getName());

    public static final double GLYPH_SIZE = 32;
    private static final double TEXT_SIZE = 16;
    
    //private Text quanityLabel;
    private final HudText quantityLabel;
    private final String key;
    
    public ItemAmountIndicator( String key, String glyphName, int quantity, DebugChangeSupport changes) {
        this.key = key;
        
        changes.addDebugChangeListener(UserInterfaceLayer.DebugProp.SHOW_UI_PANE_BORDERS, this);

        setSpacing(2);
        setPadding(new Insets(4));
        setAlignment(Pos.TOP_CENTER);
        
        quantityLabel = new HudText(String.valueOf(quantity), TEXT_SIZE);
        
        getChildren().addAll(HUD.createGlyph(glyphName, GLYPH_SIZE), quantityLabel);
    }
    
    public void setValue(int value) {
        quantityLabel.setText(String.valueOf(value));
    }
    
    @Override
    public void gameEvent(GameEvent e) {
        switch (e.type) {
            case DATA_LOADED:
                PlayerState state = e.getSource().getWorld().getPlayer().getState();
                state.addDataChangeListener(this.key, this);
                break;

        }
    }

    @Override
    public void dataChange(String dataKey, Object source, Object oldValue, Object newValue) {
        LOGGER.log(Level.CONFIG, "Data change for ItemAmountIndicator [{0}] old: {1}    new:{2}", new Object[]{key, oldValue, newValue});
        if ( dataKey.equals(this.key) ) {
            setValue((int) newValue);
        }
    }

    @Override
    public void debugPropertyChange(UserInterfaceLayer.DebugProp property, Object oldValue, Object newValue) {
        //LOGGER.finest(key + " debugPropertyChange");
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
