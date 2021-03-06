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

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author maehem
 */
public class HealthBarStrip extends HBox {

    private static final String GLYPH_PATH = "/_glyphs/hud/heart.png";
    public static final int HEART_SIZE = 16;
    public static final int N_HEARTS = 10;
    private final Pane hearts[] = new Pane[N_HEARTS];
    
    // TODO:  Partial hearts
    // TODO:  Heart image from game content. Needs ClassLoader from World.
    
    public HealthBarStrip() {
        setGlyph(HUD.class, GLYPH_PATH);
//        for ( int i=0; i<hearts.length; i++ ) {
//            setGlyph(i, getClass().getClassLoader(), GLYPH_PATH );
//            getChildren().add(hearts[i]);
//        }
    }
    
    protected void setValue(int value) {
        for ( int i=0; i< hearts.length; i++ ) {
            hearts[i].setVisible(value/5 > i);
        }
    }
    
    public final void setGlyph( Class cl, String path ) {
        for ( int i=0; i<hearts.length; i++ ) {
            hearts[i]= HUD.createGlyph(cl, path, HEART_SIZE);
            getChildren().add(hearts[i]);
        }
    }
    
}
