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

import com.maehem.rotor.engine.data.Point;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author maehem
 */
public class AssetSheet extends Image {
    private static final Logger LOGGER = Logger.getLogger(AssetSheet.class.getName());

    private final HashMap<String,Point> hm2 = new HashMap<>();
    
    private final double FUDGE = 0.4;
    
    private final double outputSize;
    private final double gridSize;

    public AssetSheet(InputStream inputStream, double gridSize, double outputSize) {
        super(inputStream);
        this.outputSize = outputSize;
        this.gridSize = gridSize;
    }
    
    public final ImageView createView( double x, double y) {
        //LOGGER.log(Level.CONFIG, "Create view for: {0},{1}", new Object[]{x, y});
        ImageView v = new ImageView(this);
        double size = gridSize-2*FUDGE;
        v.setViewport(new Rectangle2D(x+FUDGE, y+FUDGE, size, size));
        v.setPreserveRatio(true);
        v.setFitHeight(outputSize);
        //v.setSmooth(true);
        
        return v;
    }
    
    public final void putAsset( String key, double x, double y ) {
        hm2.put(key, new Point(x, y));
        Point p = (Point)hm2.get(key);
    }
    
    public ImageView getAsset2(String key) {
        Point p = (Point)hm2.get(key);
        
        if ( p == null ) {
            LOGGER.log(Level.SEVERE, "Could not find asset for: {0}", key);
            return null;
        }
        //LOGGER.log(Level.CONFIG, "Get asset for : {0} {1},{2}", new Object[]{key, p.x, p.y});
        return createView(p.x, p.y);
    }
}
