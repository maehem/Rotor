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
package com.maehem.rotor.renderer;

import javafx.scene.Group;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;

/**
 *
 * @author maehem
 */
public class FlashLightLayer extends Group/* implements DataListener */{

    private final Polygon lightCone = new Polygon();
    private final Rectangle darkness;
    
    private final double W = 300;
    private final double H = 150;
    private final double BLUR = H/2.0;

    public FlashLightLayer(PlayerNode node) {
        lightCone.getPoints().addAll(new Double[]{
            W*-0.0,  H*0.5,
            W*0.75, H*0.0,
            W*1.0,  H*0.25,
            W*1.0,  H*0.75,
            W*0.75, H*1.0
        });
        lightCone.setFill(Color.BLUE);
        lightCone.getTransforms().add(new Translate(W/2.6, 0));
        lightCone.setTranslateX(-W/2.0 + 16);
        lightCone.setTranslateY(-32);

        darkness = new Rectangle();
        darkness.setFill(Color.BLACK);
        setDarkness(0.9);
        
    }

    public void setAngle( double angle ) {
        lightCone.setRotate(angle);
        
        getChildren().clear();
        Shape s = Shape.subtract(darkness, lightCone);
        s.setEffect(new BoxBlur(BLUR, BLUR, 2));
        getChildren().add(s);
    }
    
    public void setMask(double w, double h) {
        darkness.setWidth(w*2);
        darkness.setHeight(h*2);
        darkness.setLayoutX(-w);
        darkness.setLayoutY(-h);
    }
    
    public final void setDarkness( double amount ) {
        this.setOpacity(amount);
    }
}
