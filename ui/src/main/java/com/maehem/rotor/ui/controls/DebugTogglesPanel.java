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
package com.maehem.rotor.ui.controls;

import com.maehem.rotor.renderer.debug.Debug;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

/**
 *
 * @author maehem
 */
public class DebugTogglesPanel extends FlowPane /*implements GameListener*/ {

    //private final Debug debug;
    
    //private final ToggleButton showCoords;

    public DebugTogglesPanel(/*Game game,*/ Debug debug) {
        // TODO: Probably don't need to remember this var.
        //this.debug = debug;
        
        //game.addListener(this);
        setHgap(4);
        setVgap(4);
        //setGridLinesVisible(true);
        setPrefWrapLength(200);
        
        setBorder(new Border(new BorderStroke(Color.GREY.brighter(), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        setPadding(new Insets(4));
        
        ToggleButton showCoords = new ToggleButton("", Toolbar.createGlyph("/glyphs/xy-visible.png"));
        showCoords.setTooltip(new Tooltip("Show Tile Coordinates"));
        showCoords.setSelected(debug.showTileCoordinates);
        showCoords.selectedProperty().addListener((ov, prev, current) -> {
            debug.showTileCoordinates = current;
        });
        //add(showCoords, 0, 0);
        getChildren().add(showCoords);
        
        
    }
    
}
