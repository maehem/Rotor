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
public abstract class ItemAmountIndicator extends VBox implements GameListener, DataListener {
    public static final double GLYPH_SIZE = 32;
    private static final double TEXT_SIZE = 16;
    
    //private Text quanityLabel;
    private final HudText quantityLabel;
    private String key;
    
    public ItemAmountIndicator( String key, String glyphName, int quantity) {
        this.key = key;
        setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
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

}
