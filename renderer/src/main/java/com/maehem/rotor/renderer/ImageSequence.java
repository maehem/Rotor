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

import java.io.InputStream;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Source file is a PNG with image frames arranged in a single row.
 * For example, a four frame animation sequence that is 128px x 128px
 * would be a PNG file that is 512px wide by 128px high containing the four
 * images next to each other.
 *
 * @author maehem
 */
public class ImageSequence extends ImageView {

    private static final Logger LOGGER = Logger.getLogger(ImageSequence.class.getName());
    
    public static enum Type { ONE_SHOT, REPEAT }
    
    private final Type type;
    
    private final double srcSize;
    //private final double destSize;
    private final int nFrames;
    private boolean hideWhenDone = false;
    
    private int currentFrame = 0;

    public ImageSequence(InputStream in, double size, Type type) {
        super(new Image(in));
        //this.destSize = size;
        this.type = type;

        setFitHeight(size);
        setPreserveRatio(true);
        
        this.srcSize = getImage().getHeight();
        this.nFrames = (int) (getImage().getWidth()/srcSize);
        if ( type == Type.ONE_SHOT ) currentFrame = nFrames;
        
        updateView();
    }
    
    /**
     * Configure the ViewPort to show this sub-image.
     *
     * @param idx walk index (0-nFrames)
     */
    public void setView(int idx) {
        currentFrame = idx;
        
        updateView();
    }
    
    public void step() {
        this.currentFrame++;
        if ( currentFrame >= getFrameCount()) {
            switch( type ) {
                case ONE_SHOT:
                    setVisible(!hideWhenDone);
                    currentFrame = getFrameCount();
                    break;
                case REPEAT:
                    currentFrame = 0;
                    break;
            }
        }

        updateView();
    }

    public void reset() {
        this.currentFrame = 0;
        
        if ( type == Type.ONE_SHOT ) {
            setVisible(true);
        }
        updateView();
    }
    
    private void updateView() {
        Rectangle2D viewportRect = new Rectangle2D(currentFrame * srcSize, 0, srcSize, srcSize);
        setViewport(viewportRect);
    }    
    
    /**
     * @return the nFrames
     */
    public int getFrameCount() {
        return nFrames;
    }

    boolean isDone() {
        return type == Type.ONE_SHOT && currentFrame >= nFrames;
    }
    
    public int getCurrentFrame() {
        return currentFrame;
    }
    
    /**
     * @return the hideWhenDone
     */
    public boolean isHideWhenDone() {
        return hideWhenDone;
    }

    /**
     * @param hideWhenDone the hideWhenDone to set
     */
    public void setHideWhenDone(boolean hideWhenDone) {
        this.hideWhenDone = hideWhenDone;
    }
    
    public void setAngle( double angle ) {
        setRotate(angle);
    }
}
