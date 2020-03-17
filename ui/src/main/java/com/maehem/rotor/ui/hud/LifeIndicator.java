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
import java.util.logging.Logger;
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
public class LifeIndicator extends VBox  implements GameListener, DataListener, DebugListener {
    private static final Logger LOGGER = Logger.getLogger(LifeIndicator.class.getName());
    
    HealthBarPane hearts = new HealthBarPane();
    private final String KEY = PlayerState.PROP_HEALTH;
    private final String TITLE = "---- LIFE ----";
    private final static double TEXT_SIZE = 12;

    public LifeIndicator(DebugChangeSupport changes) {
        changes.addDebugChangeListener(UserInterfaceLayer.DebugProp.SHOW_UI_PANE_BORDERS, this);

        setAlignment(Pos.TOP_CENTER);
        setSpacing(8);
        
        getChildren().addAll(new HudText(TITLE, TEXT_SIZE), hearts);
    }
    
    @Override
    public void gameEvent(GameEvent e) {
        switch (e.type) {
            case DATA_LOADED:
                PlayerState state = e.getSource().getWorld().getPlayer().getState();

                state.addDataChangeListener(this.KEY, this);
                break;
        }
    }

    @Override
    public void dataChange(String dataKey, Object source, Object oldValue, Object newValue) {
        if ( dataKey.equals(this.KEY) ) {
            setValue((double)newValue);
        }
    }

    void setValue(double val) {
        hearts.setValue((int)val);
    }
    
    @Override
    public void debugPropertyChange(UserInterfaceLayer.DebugProp property, Object oldValue, Object newValue) {
        //LOGGER.finest("Life Indicator debugPropertyChange.");
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

    void setGlyph(Class cl, String path) {
        hearts.setGlyph(cl, path);
    }
}
