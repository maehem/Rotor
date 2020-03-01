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

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Walk Sheets are a single image that contain all four walking directions and a
 * 8 tile walking sequence per direction. 4 rows x 8 columns
 *
 * Upon load, we determine the individual sprite dimensions by dividing the
 * width by eight and the height by 4. The final sprite is then scaled to fit
 * the requested size specified in the constructor.
 *
 * Character sprite directions are: Row 1: Toward Row 2: Away Row 3: Left Row 4:
 * Right
 *
 * Processed Sprites are always square.
 *
 * @author maehem
 */
public class WalkSheet extends Group {
    private static final Logger LOGGER = Logger.getLogger(WalkSheet.class.getName());

    public static final int NWALKS = 8;
    public enum DIR {
        TOWARD, AWAY, LEFT, RIGHT
    }

    private final ImageView view;
    
    private final double size;
    private DIR currentDir = DIR.TOWARD;
    private int currentWalk = 0;
    private final ImageView damageView = new ImageView();
    
    

    public WalkSheet(InputStream in, double size) throws IOException {
        view = new ImageView(new Image(in));
        //super(new Image(in));

        this.size = view.getImage().getHeight() / 4.0;  // TODO: we don't use local size!
        
        PixelReader pixelReader = view.getImage().getPixelReader();
         
        int width = (int)view.getImage().getWidth();
        int height = (int)view.getImage().getHeight();
        WritableImage writableImage 
                = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
         
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Color color = pixelReader.getColor(x, y);
                if ( color.isOpaque() )
                    pixelWriter.setColor(x, y, Color.RED);
            }
        }
        
        damageView.setImage(writableImage);
        damageView.setOpacity(0.5);
        damageView.setBlendMode(BlendMode.MULTIPLY);
        damageView.setVisible(false);
        
        getChildren().addAll(view, damageView);
        
        updateView();
    }

    /**
     * Configure the ViewPort to show this sub-image.
     *
     * @param d direction
     * @param idx walk index (0-7)
     */
    public void setView(DIR d, int idx) {
        currentDir = d;
        currentWalk = idx;
        
        updateView();
    }
    
    public void step() {
        this.currentWalk++;
        if (currentWalk >= NWALKS) {
            currentWalk = 0;
        }

        updateView();
    }

    public void stand() {
        this.currentWalk = 0;
        
        updateView();
    }

    public void reset() {
        this.currentDir = DIR.TOWARD;
        this.currentWalk = 0;
        
        updateView();
    }

    public void setDir(DIR d) {
        currentDir = d;
        
        updateView();
    }

    private void updateView() {
        Rectangle2D viewportRect = new Rectangle2D(currentWalk * size, currentDir.ordinal() * size, size, size);
        
        view.setViewport(viewportRect);
        damageView.setViewport(viewportRect);
    }


    void showDamage(boolean show) {
        damageView.setVisible(show);
    }

}
