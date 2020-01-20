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
package com.maehem.rotor.engine.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class Data {

    private static final Logger LOGGER = Logger.getLogger(Data.class.getName());

    private boolean loaded = false;

    public GameInfo mapInfo;
    public Cell[][] map;

    /**
     * Create a new Data object and initialize it to a default state. Use for a
     * new game.
     */
    public Data() {
        mapInfo = new GameInfo();
        //initMap();
        
        
        //loaded = false;
    }

    public Data(int i) {
        mapInfo = new GameInfo();
        //MapData mapData = new Map_0003();
//        mapInfo.mapSize = mapData.size;
//        this.map = new Cell[mapInfo.mapSize][mapInfo.mapSize];
//        for (int y = 0; y < mapInfo.mapSize; y++) {
//            for (int x = 0; x < mapInfo.mapSize; x++) {
//                map[x][y] = new Cell(this);
//                map[x][y].altm.altitude = mapData.altitude[y][x];
//                map[x][y].structure.id = mapData.structure[y][x];
//                map[x][y].terrain.surfaceWater = mapData.water[y][x] > 0;
//            }
//        }

        resolveStuff();


        loaded = true;
    }

    public void initMap() {
        this.map = new Cell[mapInfo.mapSize][mapInfo.mapSize];
        for (int y = 0; y < mapInfo.mapSize; y++) {
            for (int x = 0; x < mapInfo.mapSize; x++) {
                map[x][y] = new Cell(this);
                //map[x][y].altm.altitude = mapInfo.mapSize / 2;
            }
        }
        
        resolveStuff();       
    }

    private void resolveStuff() {
        for (int y = 0; y < mapInfo.mapSize; y++) {
            for (int x = 0; x < mapInfo.mapSize; x++) {
                //Cell c = map[x][y];

            }
        }
    }

    public int getMapSize() {
        return mapInfo.mapSize;
    }

    public Point getMapLocation(Cell cell) {
        return cell.getMapLocation();
    }

    public Cell getMapCell(int xT, int yT) {
        try {
            return map[xT][yT];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean state) {
        loaded = state;
    }

    public void load(DataInputStream dis) throws IOException {

        // Read GameInfo block
        mapInfo = new GameInfo(dis);

        // Read labels
        // Read each cell
        this.map = new Cell[mapInfo.mapSize][mapInfo.mapSize];
        for (int y = 0; y < mapInfo.mapSize; y++) {
            for (int x = 0; x < mapInfo.mapSize; x++) {
                map[x][y] = new Cell(this);
                map[x][y].load(dis);
            }
        }
        LOGGER.finer("Data Stream Load completed OK.");
        setLoaded(true);
    }

    public void save(DataOutputStream dos) throws IOException {
        // Write GameInfo
        mapInfo.save(dos);
        // Write labels

        // Write Cells           
        for (int y = 0; y < mapInfo.mapSize; y++) {
            for (int x = 0; x < mapInfo.mapSize; x++) {
                map[x][y].save(dos);
            }
        }
    }

}
