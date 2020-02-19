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

import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author maehem
 */
public class AtmosphereLayer extends Group implements GameListener {
    Random rand = new Random();
    private final Group mist1;
    private final Group mist2;
    
    private final double width;
    private final double height;

    public AtmosphereLayer(double width, double height) {
        this.width = width;
        this.height = height;
        
        setClip(new Rectangle(width, height));

        mist1 = createMist();
        mist2 = createMist();
        
        getChildren().addAll(mist1, mist2);
    }

    private double dr( double lower, double upper ) {
        return lower + rand.nextDouble()*(upper-lower);
    }
    
    private Group createMist() {
        Group g = new Group();
        for (int i = 0; i < 80; i++) {
            Ellipse e = new Ellipse(
                    dr(0,width * 4), 
                    dr(0,height), 
                    dr( 40, 180), 
                    dr( 10, 50)
            );
            e.setEffect(new BoxBlur(dr(60,130), dr(10,60), 1));
            e.setFill(Color.WHITE);
            e.setOpacity(0.2 + rand.nextDouble()/6);
            g.getChildren().add(e);
        }
        
        g.setLayoutX(-width*3);
        g.setOpacity(0);
        return g;        
    }

    @Override
    public void gameEvent(GameEvent e) {
        switch( e.type ) {
            case TICK:
                // Move the mist.
                mist1.setTranslateX(mist1.getTranslateX()+0.708352);
                // Start fading in the mist at 0% of its motion.
                if ( mist1.getTranslateX() < width && mist1.getOpacity() < 1.0) {
                    mist1.setOpacity(mist1.getOpacity()+0.02);
                    if ( mist1.getOpacity() > 1.0 ) mist1.setOpacity(1.0);
                }
                // Start fading the mist at 75% of its motion.
                if ( mist1.getTranslateX() > width*2 ) {
                    mist1.setOpacity(mist1.getOpacity()-0.02);
                }
                // Rest the motion when the mist has traversed all the way accross.
                if ( mist1.getTranslateX() > width*3 ) {
                    mist1.setTranslateX(0);
                    mist1.setOpacity(0.0);
                }
                
                
                
                mist2.setTranslateX(mist2.getTranslateX()+3.42353);
                // Start fading in the mist at 0% of its motion.
                if ( mist2.getTranslateX() < width  && mist2.getOpacity() < 1.0) {
                    mist2.setOpacity(mist2.getOpacity()+0.01);
                    if ( mist2.getOpacity() > 1.0 ) mist2.setOpacity(1.0);
                }
                if ( mist2.getTranslateX() > width*2 ) {
                    mist2.setOpacity(mist2.getOpacity()-0.01);
                    if ( mist2.getOpacity() < 0.0 ) mist2.setOpacity(0.0);
                }
                if ( mist2.getTranslateX() > width*3 ) {
                    mist2.setTranslateX(0);
                    mist2.setOpacity(0.0);
                }
        }
    }
}
