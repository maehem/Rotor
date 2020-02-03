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

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author maehem
 */
public class EquipedItemIndicator extends Group {

    private static final double SIZE = 48;
    
    public EquipedItemIndicator() {
        StackPane sp = new StackPane();
        
        BorderStroke strokes[] = new BorderStroke[2];
        strokes[0] = new BorderStroke(Color.GOLD, BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(4));
        strokes[1] = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(6), new BorderWidths(2), new Insets(4));
        
        
        
        StackPane glyph = HUD.createGlyph("/glyphs/equipable/bow-1.png", SIZE);
        
        glyph.setBorder(new Border(strokes));
        sp.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        
        Rectangle r = new Rectangle(SIZE, SIZE, Color.BLACK);
        
        sp.getChildren().addAll(r, glyph);
        
        getChildren().add(sp);
    }
    
}
