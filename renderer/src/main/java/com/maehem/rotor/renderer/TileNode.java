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

import com.maehem.rotor.engine.data.Tile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.image.ImageView;

/**
 *
 * @author maehem
 */
public class TileNode extends Group {

    private static final Logger LOGGER = Logger.getLogger(TileNode.class.getName());

    public final Tile tile;

    public TileNode(Tile tile) {
        this.tile = tile;

    }

    public boolean create(AssetSheet sheet) {
        if (tile == null) {
            return false;
        }
        for (int i = 0; i < tile.getAssetCount(); i++) {
            if (tile.getAssetName(i) == null) {
                continue;
            }
            ImageView tileImage = sheet.getAsset2(tile.getAssetName(i));
            if (tileImage != null) {
                getChildren().add(tileImage);
                //LOGGER.log(Level.CONFIG, "Add asset: {0}", tile.getAssetName(i));
            } else {
                LOGGER.log(Level.SEVERE, "Could not find image asset for {0}", tile.getAssetName(i));
            }
        }

        return true;
    }

}
