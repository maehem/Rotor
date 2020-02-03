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

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author maehem
 */
public class HudText extends Group {

    Text titleWhite;
    Text titleBlack;

    public HudText(String text, double size) {
        titleBlack = new Text(text);
        Font f = Font.font(System.getProperty("Font"), FontWeight.BOLD, FontPosture.ITALIC, size);

        titleWhite = new Text(text);
        titleWhite.setFont(f);
        titleWhite.setFill(Color.WHITE);

        titleBlack.setFont(f);
        titleBlack.setFill(Color.BLACK);
        titleBlack.setX(2.0);
        titleBlack.setY(2.0);

        getChildren().addAll(titleBlack, titleWhite);
    }
    
    public void setText( String text ) {
        titleBlack.setText(text);
        titleWhite.setText(text);
    }

}
