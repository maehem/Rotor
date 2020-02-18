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
package com.maehem.rotor.dungeoneer.realms.dungeon1;

import com.maehem.rotor.engine.data.PortKey;
import com.maehem.rotor.engine.data.Point;
import com.maehem.rotor.engine.data.Realm;
import com.maehem.rotor.engine.data.Room;
import com.maehem.rotor.engine.data.Tile;
import com.maehem.rotor.engine.data.Tile.FLAG;
import static com.maehem.rotor.engine.data.Tile.FLAG.BLK;

/**
 *
 * @author maehem
 */
public class Room002 extends Room {
    
    public static final long UID = 102;
        
    public Room002(Realm parent) {
        super(parent, 2,1);
        
        setUid(UID);
        setDisplayName("The Room of Widening");
        setAssetSheet(new OverworldAssetSheet(parent.getParent().getTileSize()));
        
        addDoor(new PortKey(
                new Point(0.55, 0.85), new Point(0.05, 0.05),
                Room001.UID, new Point(0.55, 0.3)
        ));
        
        int row = 0;
        put(  0, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  1, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  2, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  3, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  4, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  5, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  6, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  7, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  8, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  9, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 10, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 11, row, new Tile("floor1", "clifBaseCave",  new FLAG[]{BLK}));
        put( 12, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 13, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 14, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 15, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 16, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 17, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 18, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 19, row, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));

        row = 1;
        put(  0, row, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put(  1, row, new Tile("floor1", "forestEdgeTR", new FLAG[]{BLK}));
        put(  2, row, new Tile("floor1", "clifBaseShadeC"));
        put(  3, row, new Tile("floor1", "clifBaseShadeC"));
        put(  4, row, new Tile("floor1", "clifBaseShadeC"));
        put(  5, row, new Tile("floor1", "clifBaseShadeC"));
        put(  6, row, new Tile("floor1", "clifBaseShadeC"));
        put(  7, row, new Tile("floor1", "clifBaseShadeC"));
        put(  8, row, new Tile("floor1", "clifBaseShadeC"));
        put(  9, row, new Tile("floor1", "clifBaseShadeC"));
        put( 10, row, new Tile("floor1", "clifBaseShadeC"));
        put( 11, row, new Tile("floor1", "clifBaseShadeC"));
        put( 12, row, new Tile("floor1", "clifBaseShadeC"));
        put( 13, row, new Tile("floor1", "clifBaseShadeC"));
        put( 14, row, new Tile("floor1", "clifBaseShadeC"));
        put( 15, row, new Tile("floor1", "clifBaseShadeC"));
        put( 16, row, new Tile("floor1", "clifBaseShadeC"));
        put( 17, row, new Tile("floor1", "clifBaseShadeC"));
        put( 18, row, new Tile("floor1", "clifBaseShadeC"));
        put( 19, row, new Tile("floor1", "clifBaseShadeC"));
        put( 20, row, new Tile("floor1", "clifBaseShadeC"));
        put( 21, row, new Tile("floor1", "clifBaseShadeC"));
        put( 22, row, new Tile("floor1", "clifBaseShadeC"));
        put( 23, row, new Tile("floor1", "clifBaseShadeC"));
        put( 24, row, new Tile("floor1", "clifBaseShadeC"));
        put( 25, row, new Tile("floor1", "clifBaseShadeC"));
        put( 26, row, new Tile("floor1", "clifBaseShadeC"));
        put( 27, row, new Tile("floor1", "clifBaseShadeC"));
        put( 28, row, new Tile("floor1", "clifBaseShadeC"));
        put( 29, row, new Tile("floor1", "clifBaseShadeC"));
        put( 30, row, new Tile("floor1", "clifBaseShadeC"));
        put( 31, row, new Tile("floor1", "clifBaseShadeC"));
        put( 32, row, new Tile("floor1", "clifBaseShadeC"));
        put( 33, row, new Tile("floor1", "clifBaseShadeC"));
        put( 34, row, new Tile("floor1", "clifBaseShadeC"));
        put( 35, row, new Tile("floor1", "clifBaseShadeC"));
        put( 36, row, new Tile("floor1", "clifBaseShadeC"));
        put( 37, row, new Tile("floor1", "clifBaseShadeC"));
        put( 38, row, new Tile("floor1", "clifBaseShadeC"));
        put( 39, row, new Tile("floor1", "clifBaseShadeC"));

        row = 2;
        put(  0, row, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, row, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, row, new Tile("floor1"));
        put(  3, row, new Tile("floor1"));
        put(  4, row, new Tile("floor1"));
        put(  5, row, new Tile("floor1"));
        put(  6, row, new Tile("floor1"));
        put(  7, row, new Tile("floor1"));        
        put(  8, row, new Tile("floor1"));
        put(  9, row, new Tile("floor1"));
        put( 10, row, new Tile("floor1"));
        put( 11, row, new Tile("floor1"));
        put( 12, row, new Tile("floor1"));
        put( 13, row, new Tile("floor1"));
        put( 14, row, new Tile("floor1"));
        put( 15, row, new Tile("floor1"));
        put( 16, row, new Tile("floor1"));
        put( 17, row, new Tile("floor1"));
        put( 18, row, new Tile("floor1"));
        put( 19, row, new Tile("floor1"));
        put( 20, row, new Tile("floor1"));
        put( 21, row, new Tile("floor1"));
        put( 22, row, new Tile("floor1"));
        put( 23, row, new Tile("floor1"));
        put( 24, row, new Tile("floor1"));
        put( 25, row, new Tile("floor1"));
        put( 26, row, new Tile("floor1"));
        put( 27, row, new Tile("floor1"));
        put( 28, row, new Tile("floor1"));
        put( 29, row, new Tile("floor1"));
        put( 30, row, new Tile("floor1"));
        put( 31, row, new Tile("floor1"));
        put( 32, row, new Tile("floor1"));
        put( 33, row, new Tile("floor1"));
        put( 34, row, new Tile("floor1"));
        put( 35, row, new Tile("floor1"));
        put( 36, row, new Tile("floor1"));
        put( 37, row, new Tile("floor1"));
        put( 38, row, new Tile("floor1"));
        put( 39, row, new Tile("floor1"));
        
        row = 3;
        put(  0, row, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, row, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, row, new Tile("floor1"));
        put(  3, row, new Tile("floor1"));
        put(  4, row, new Tile("floor1"));
        put(  5, row, new Tile("floor1"));
        put(  6, row, new Tile("floor1"));
        put(  7, row, new Tile("floor1"));        
        put(  8, row, new Tile("floor1"));
        put(  9, row, new Tile("floor1"));
        put( 10, row, new Tile("floor1"));
        put( 11, row, new Tile("floor1"));
        put( 12, row, new Tile("floor1"));
        put( 13, row, new Tile("floor1"));
        put( 14, row, new Tile("floor1"));
        put( 15, row, new Tile("floor1"));
        put( 16, row, new Tile("floor1"));
        put( 17, row, new Tile("floor1"));
        put( 18, row, new Tile("floor1"));
        put( 19, row, new Tile("floor1"));
        put( 20, row, new Tile("floor1"));
        put( 21, row, new Tile("floor1"));
        put( 22, row, new Tile("floor1"));
        put( 23, row, new Tile("floor1"));
        put( 24, row, new Tile("floor1"));
        put( 25, row, new Tile("floor1"));
        put( 26, row, new Tile("floor1"));
        put( 27, row, new Tile("floor1"));
        put( 28, row, new Tile("floor1"));
        put( 29, row, new Tile("floor1"));
        put( 30, row, new Tile("floor1"));
        put( 31, row, new Tile("floor1"));
        put( 32, row, new Tile("floor1"));
        put( 33, row, new Tile("floor1"));
        put( 34, row, new Tile("floor1"));
        put( 35, row, new Tile("floor1"));
        put( 36, row, new Tile("floor1"));
        put( 37, row, new Tile("floor1"));
        put( 38, row, new Tile("floor1"));
        put( 39, row, new Tile("floor1"));
        
        row = 4;
        put(  0, row, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, row, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, row, new Tile("floor1"));
        put(  3, row, new Tile("floor1"));
        put(  4, row, new Tile("floor1"));
        put(  5, row, new Tile("floor1"));
        put(  6, row, new Tile("floor1"));
        put(  7, row, new Tile("floor1"));        
        put(  8, row, new Tile("floor1"));
        put(  9, row, new Tile("floor1"));
        put( 10, row, new Tile("floor1"));
        put( 11, row, new Tile("floor1"));
        put( 12, row, new Tile("floor1"));
        put( 13, row, new Tile("floor1"));
        put( 14, row, new Tile("floor1"));
        put( 15, row, new Tile("floor1"));
        put( 16, row, new Tile("floor1"));
        put( 17, row, new Tile("floor1"));
        put( 18, row, new Tile("floor1"));
        put( 19, row, new Tile("floor1"));
        put( 20, row, new Tile("floor1"));
        put( 21, row, new Tile("floor1"));
        put( 22, row, new Tile("floor1"));
        put( 23, row, new Tile("floor1"));
        put( 24, row, new Tile("floor1"));
        put( 25, row, new Tile("floor1"));
        put( 26, row, new Tile("floor1"));
        put( 27, row, new Tile("floor1"));
        put( 28, row, new Tile("floor1"));
        put( 29, row, new Tile("floor1"));
        put( 30, row, new Tile("floor1"));
        put( 31, row, new Tile("floor1"));
        put( 32, row, new Tile("floor1"));
        put( 33, row, new Tile("floor1"));
        put( 34, row, new Tile("floor1"));
        put( 35, row, new Tile("floor1"));
        put( 36, row, new Tile("floor1"));
        put( 37, row, new Tile("floor1"));
        put( 38, row, new Tile("floor1"));
        put( 39, row, new Tile("floor1"));
        
        row = 5;
        put(  0, row, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, row, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, row, new Tile("floor1"));
        put(  3, row, new Tile("floor1"));
        put(  4, row, new Tile("floor1"));
        put(  5, row, new Tile("floor1"));
        put(  6, row, new Tile("floor1"));
        put(  7, row, new Tile("floor1"));        
        put(  8, row, new Tile("floor1"));
        put(  9, row, new Tile("floor1"));
        put( 10, row, new Tile("floor1"));
        put( 11, row, new Tile("floor1"));
        put( 12, row, new Tile("floor1"));
        put( 13, row, new Tile("floor1"));
        put( 14, row, new Tile("floor1"));
        put( 15, row, new Tile("floor1"));
        put( 16, row, new Tile("floor1"));
        put( 17, row, new Tile("floor1"));
        put( 18, row, new Tile("floor1"));
        put( 19, row, new Tile("floor1"));
        put( 20, row, new Tile("floor1"));
        put( 21, row, new Tile("floor1"));
        put( 22, row, new Tile("floor1"));
        put( 23, row, new Tile("floor1"));
        put( 24, row, new Tile("floor1"));
        put( 25, row, new Tile("floor1"));
        put( 26, row, new Tile("floor1"));
        put( 27, row, new Tile("floor1"));
        put( 28, row, new Tile("floor1"));
        put( 29, row, new Tile("floor1"));
        put( 30, row, new Tile("floor1"));
        put( 31, row, new Tile("floor1"));
        put( 32, row, new Tile("floor1"));
        put( 33, row, new Tile("floor1"));
        put( 34, row, new Tile("floor1"));
        put( 35, row, new Tile("floor1"));
        put( 36, row, new Tile("floor1"));
        put( 37, row, new Tile("floor1"));
        put( 38, row, new Tile("floor1"));
        put( 39, row, new Tile("floor1"));
        
        row = 6;
        put(  0, row, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, row, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, row, new Tile("floor1"));
        put(  3, row, new Tile("floor1"));
        put(  4, row, new Tile("floor1"));
        put(  5, row, new Tile("floor1"));
        put(  6, row, new Tile("floor1"));
        put(  7, row, new Tile("floor1"));        
        put(  8, row, new Tile("floor1"));
        put(  9, row, new Tile("floor1"));
        put( 10, row, new Tile("floor1"));
        put( 11, row, new Tile("floor1"));
        put( 12, row, new Tile("floor1"));
        put( 13, row, new Tile("floor1"));
        put( 14, row, new Tile("floor1"));
        put( 15, row, new Tile("floor1"));
        put( 16, row, new Tile("floor1"));
        put( 17, row, new Tile("floor1"));
        put( 18, row, new Tile("floor1"));
        put( 19, row, new Tile("floor1"));
        put( 20, row, new Tile("floor1"));
        put( 21, row, new Tile("floor1"));
        put( 22, row, new Tile("floor1"));
        put( 23, row, new Tile("floor1"));
        put( 24, row, new Tile("floor1"));
        put( 25, row, new Tile("floor1"));
        put( 26, row, new Tile("floor1"));
        put( 27, row, new Tile("floor1"));
        put( 28, row, new Tile("floor1"));
        put( 29, row, new Tile("floor1"));
        put( 30, row, new Tile("floor1"));
        put( 31, row, new Tile("floor1"));
        put( 32, row, new Tile("floor1"));
        put( 33, row, new Tile("floor1"));
        put( 34, row, new Tile("floor1"));
        put( 35, row, new Tile("floor1"));
        put( 36, row, new Tile("floor1"));
        put( 37, row, new Tile("floor1"));
        put( 38, row, new Tile("floor1"));
        put( 39, row, new Tile("floor1"));
        
        row = 7;
        put(  0, row, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, row, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, row, new Tile("floor1"));
        put(  3, row, new Tile("floor1"));
        put(  4, row, new Tile("floor1"));
        put(  5, row, new Tile("floor1"));
        put(  6, row, new Tile("floor1"));
        put(  7, row, new Tile("floor1"));        
        put(  8, row, new Tile("floor1"));
        put(  9, row, new Tile("floor1"));
        put( 10, row, new Tile("floor1"));
        put( 11, row, new Tile("floor1"));
        put( 12, row, new Tile("floor1"));
        put( 13, row, new Tile("floor1"));
        put( 14, row, new Tile("floor1"));
        put( 15, row, new Tile("floor1"));
        put( 16, row, new Tile("floor1"));
        put( 17, row, new Tile("floor1"));
        put( 18, row, new Tile("floor1"));
        put( 19, row, new Tile("floor1"));
        put( 20, row, new Tile("floor1"));
        put( 21, row, new Tile("floor1"));
        put( 22, row, new Tile("floor1"));
        put( 23, row, new Tile("floor1"));
        put( 24, row, new Tile("floor1"));
        put( 25, row, new Tile("floor1"));
        put( 26, row, new Tile("floor1"));
        put( 27, row, new Tile("floor1"));
        put( 28, row, new Tile("floor1"));
        put( 29, row, new Tile("floor1"));
        put( 30, row, new Tile("floor1"));
        put( 31, row, new Tile("floor1"));
        put( 32, row, new Tile("floor1"));
        put( 33, row, new Tile("floor1"));
        put( 34, row, new Tile("floor1"));
        put( 35, row, new Tile("floor1"));
        put( 36, row, new Tile("floor1"));
        put( 37, row, new Tile("floor1"));
        put( 38, row, new Tile("floor1"));
        put( 39, row, new Tile("floor1"));
        
        row = 8;
        put(  0, row, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, row, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, row, new Tile("floor1"));
        put(  3, row, new Tile("floor1"));
        put(  4, row, new Tile("floor1"));
        put(  5, row, new Tile("floor1"));
        put(  6, row, new Tile("floor1"));
        put(  7, row, new Tile("floor1"));        
        put(  8, row, new Tile("floor1"));
        put(  9, row, new Tile("floor1"));
        put( 10, row, new Tile("floor1"));
        put( 11, row, new Tile("floor1"));
        put( 12, row, new Tile("floor1"));
        put( 13, row, new Tile("floor1"));
        put( 14, row, new Tile("floor1"));
        put( 15, row, new Tile("floor1"));
        put( 16, row, new Tile("floor1"));
        put( 17, row, new Tile("floor1"));
        put( 18, row, new Tile("floor1"));
        put( 19, row, new Tile("floor1"));
        put( 20, row, new Tile("floor1"));
        put( 21, row, new Tile("floor1"));
        put( 22, row, new Tile("floor1"));
        put( 23, row, new Tile("floor1"));
        put( 24, row, new Tile("floor1"));
        put( 25, row, new Tile("floor1"));
        put( 26, row, new Tile("floor1"));
        put( 27, row, new Tile("floor1"));
        put( 28, row, new Tile("floor1"));
        put( 29, row, new Tile("floor1"));
        put( 30, row, new Tile("floor1"));
        put( 31, row, new Tile("floor1"));
        put( 32, row, new Tile("floor1"));
        put( 33, row, new Tile("floor1"));
        put( 34, row, new Tile("floor1"));
        put( 35, row, new Tile("floor1"));
        put( 36, row, new Tile("floor1"));
        put( 37, row, new Tile("floor1"));
        put( 38, row, new Tile("floor1"));
        put( 39, row, new Tile("floor1"));
       
        row = 9;
        put(  0, row, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, row, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, row, new Tile("floor1"));
        put(  3, row, new Tile("floor1"));
        put(  4, row, new Tile("floor1"));
        put(  5, row, new Tile("floor1"));
        put(  6, row, new Tile("floor1"));
        put(  7, row, new Tile("floor1"));        
        put(  8, row, new Tile("floor1"));
        put(  9, row, new Tile("floor1"));
        put( 10, row, new Tile("floor1"));
        put( 11, row, new Tile("floor1"));
        put( 12, row, new Tile("floor1"));
        put( 13, row, new Tile("floor1"));
        put( 14, row, new Tile("floor1"));
        put( 15, row, new Tile("floor1"));
        put( 16, row, new Tile("floor1"));
        put( 17, row, new Tile("floor1"));
        put( 18, row, new Tile("floor1"));
        put( 19, row, new Tile("floor1"));
        put( 20, row, new Tile("floor1"));
        put( 21, row, new Tile("floor1"));
        put( 22, row, new Tile("floor1"));
        put( 23, row, new Tile("floor1"));
        put( 24, row, new Tile("floor1"));
        put( 25, row, new Tile("floor1"));
        put( 26, row, new Tile("floor1"));
        put( 27, row, new Tile("floor1"));
        put( 28, row, new Tile("floor1"));
        put( 29, row, new Tile("floor1"));
        put( 30, row, new Tile("floor1"));
        put( 31, row, new Tile("floor1"));
        put( 32, row, new Tile("floor1"));
        put( 33, row, new Tile("floor1"));
        put( 34, row, new Tile("floor1"));
        put( 35, row, new Tile("floor1"));
        put( 36, row, new Tile("floor1"));
        put( 37, row, new Tile("floor1"));
        put( 38, row, new Tile("floor1"));
        put( 39, row, new Tile("floor1"));
        
        row = 10;
        put(  0, row, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, row, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, row, new Tile("floor1"));
        put(  3, row, new Tile("floor1"));
        put(  4, row, new Tile("floor1"));
        put(  5, row, new Tile("floor1"));
        put(  6, row, new Tile("floor1"));
        put(  7, row, new Tile("floor1"));        
        put(  8, row, new Tile("floor1"));
        put(  9, row, new Tile("floor1"));
        put( 10, row, new Tile("floor1"));
        put( 11, row, new Tile("floor1"));
        put( 12, row, new Tile("floor1"));
        put( 13, row, new Tile("floor1"));
        put( 14, row, new Tile("floor1"));
        put( 15, row, new Tile("floor1"));
        put( 16, row, new Tile("floor1"));
        put( 17, row, new Tile("floor1"));
        put( 18, row, new Tile("floor1"));
        put( 19, row, new Tile("floor1"));
        put( 20, row, new Tile("floor1"));
        put( 21, row, new Tile("floor1"));
        put( 22, row, new Tile("floor1"));
        put( 23, row, new Tile("floor1"));
        put( 24, row, new Tile("floor1"));
        put( 25, row, new Tile("floor1"));
        put( 26, row, new Tile("floor1"));
        put( 27, row, new Tile("floor1"));
        put( 28, row, new Tile("floor1"));
        put( 29, row, new Tile("floor1"));
        put( 30, row, new Tile("floor1"));
        put( 31, row, new Tile("floor1"));
        put( 32, row, new Tile("floor1"));
        put( 33, row, new Tile("floor1"));
        put( 34, row, new Tile("floor1"));
        put( 35, row, new Tile("floor1"));
        put( 36, row, new Tile("floor1"));
        put( 37, row, new Tile("floor1"));
        put( 38, row, new Tile("floor1"));
        put( 39, row, new Tile("floor1"));
        
        
    }
    
}
