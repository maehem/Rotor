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

import com.maehem.rotor.ui.UserInterfaceLayer;
import com.maehem.rotor.ui.debug.DebugChangeSupport;
import com.maehem.rotor.ui.debug.DebugListener;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 *
 * @author maehem
 */
public class EquipedItemIndicator extends Group implements DebugListener {
    private static final Logger LOGGER = Logger.getLogger(EquipedItemIndicator.class.getName());

    private static final double SIZE = 48;
    private StackPane sp = new StackPane();;
    
    public EquipedItemIndicator(DebugChangeSupport changes) {
        
        changes.addDebugChangeListener(UserInterfaceLayer.DebugProp.SHOW_UI_PANE_BORDERS, this);

        BorderStroke strokes[] = new BorderStroke[2];
        strokes[0] = new BorderStroke(Color.GOLD, BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(3));
        strokes[1] = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(6), new BorderWidths(2), new Insets(3));
        
        //StackPane glyph = HUD.createGlyph(HUD.class, "/_glyphs/equipable/bow-1.png", SIZE);
    //    StackPane glyph = HUD.createGlyph(getClass().getClassLoader(), "_glyphs/equipable/bow-1.png", SIZE);
        
        //sp.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        
        //Rectangle r = new Rectangle(SIZE-4, SIZE-4, Color.BLACK);
        sp.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(8), new Insets(2))));
        //glyph.setBorder(new Border(strokes));
        
        //sp.getChildren().addAll(glyph);
        
        getChildren().add(sp);
    }
    
    @Override
    public void debugPropertyChange(UserInterfaceLayer.DebugProp property, Object oldValue, Object newValue) {
        LOGGER.finest("Equiped Item Indicator debugPropertyChange.");
        switch(property) {
            case SHOW_UI_PANE_BORDERS:
                showDebugBorders((boolean)newValue);
                break;
        }
    }

    private void showDebugBorders(boolean show) {
        if ( show ) {
            sp.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        } else {
            sp.setBorder(Border.EMPTY);
        }
    }
}
