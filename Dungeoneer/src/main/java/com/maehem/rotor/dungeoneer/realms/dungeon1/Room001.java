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

import com.maehem.rotor.engine.data.Point;
import com.maehem.rotor.engine.game.PortKey;
import com.maehem.rotor.engine.game.Realm;
import com.maehem.rotor.engine.game.Room;
import com.maehem.rotor.engine.game.Tile;
import com.maehem.rotor.engine.game.Tile.FLAG;
import static com.maehem.rotor.engine.game.Tile.FLAG.BLK;

/**
 *
 * @author maehem
 */
public class Room001 extends Room {
    
    public static final long UID = 100;
        
    public Room001(Realm parent) {
        super(parent, 1,1);
        
        setUid(UID);
        setDisplayName("The Room of Starting");
        setAssetSheet(new OverworldAssetSheet(parent.getParent().getTileSize()));
        
        addDoor(new PortKey(
                new Point(0.55, 0.1), new Point(0.05, 0.05),
                Room002.UID, new Point(0.5, 0.7)
        ));
        
        put(  0, 0, new Tile("clifFace1", new FLAG[]{BLK}));        
        put(  1, 0, new Tile("clifFace2", new FLAG[]{BLK}));
        put(  2, 0, new Tile("clifFace1", new FLAG[]{BLK}));
        put(  3, 0, new Tile("clifFace2", new FLAG[]{BLK}));
        put(  4, 0, new Tile("clifFace1", new FLAG[]{BLK}));
        put(  5, 0, new Tile("clifFace2", new FLAG[]{BLK}));
        put(  6, 0, new Tile("clifFace1", new FLAG[]{BLK}));
        put(  7, 0, new Tile("clifFace2", new FLAG[]{BLK}));
        put(  8, 0, new Tile("clifFace1", new FLAG[]{BLK}));
        put(  9, 0, new Tile("clifFace2", new FLAG[]{BLK}));
        put( 10, 0, new Tile("clifFace1", new FLAG[]{BLK}));
        put( 11, 0, new Tile("clifFace2", new FLAG[]{BLK}));
        put( 12, 0, new Tile("clifFace1", new FLAG[]{BLK}));
        put( 13, 0, new Tile("clifFace2", new FLAG[]{BLK}));
        put( 14, 0, new Tile("clifFace1", new FLAG[]{BLK}));
        put( 15, 0, new Tile("clifFace2", new FLAG[]{BLK}));
        put( 16, 0, new Tile("clifFace1", new FLAG[]{BLK}));
        put( 17, 0, new Tile("clifFace2", new FLAG[]{BLK}));
        put( 18, 0, new Tile("clifFace1", new FLAG[]{BLK}));
        put( 19, 0, new Tile("clifFace2", new FLAG[]{BLK}));
        
        put(  0, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  1, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  2, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  3, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  4, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  5, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  6, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  7, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  8, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put(  9, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 10, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 11, 1, new Tile("floor1", "clifBaseCave"));
        put( 12, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 13, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 14, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 15, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 16, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 17, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 18, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));
        put( 19, 1, new Tile("floor1", "clifBaseC",  new FLAG[]{BLK}));

        put(  0, 2, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put(  1, 2, new Tile("floor1", "forestEdgeTR", new FLAG[]{BLK}));
        put(  2, 2, new Tile("floor1", "clifBaseShadeC"));
        put(  3, 2, new Tile("floor1", "clifBaseShadeC"));
        put(  4, 2, new Tile("floor1", "clifBaseShadeC"));
        put(  5, 2, new Tile("floor1", "clifBaseShadeC"));
        put(  6, 2, new Tile("floor1", "clifBaseShadeC"));
        put(  7, 2, new Tile("floor1", "clifBaseShadeC"));
        put(  8, 2, new Tile("floor1", "clifBaseShadeC"));
        put(  9, 2, new Tile("floor1", "clifBaseShadeC"));
        put( 10, 2, new Tile("floor1", "clifBaseShadeC"));
        put( 11, 2, new Tile("floor1", "clifBaseShadeC"));
        put( 12, 2, new Tile("floor1", "clifBaseShadeC"));
        put( 13, 2, new Tile("floor1", "clifBaseShadeC"));
        put( 14, 2, new Tile("floor1", "clifBaseShadeC"));
        put( 15, 2, new Tile("floor1", "clifBaseShadeC"));
        put( 16, 2, new Tile("floor1", "clifBaseShadeC"));
        put( 17, 2, new Tile("floor1", "clifBaseShadeC"));
        put( 18, 2, new Tile("floor1", "clifBaseShadeC"));
        put( 19, 2, new Tile("floor1", "clifBaseShadeC"));

        put(  0, 3, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, 3, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, 3, new Tile("floor1"));
        put(  3, 3, new Tile("pondTL"));
        put(  4, 3, new Tile("pondTC"));
        put(  5, 3, new Tile("pondTR"));
        put(  6, 3, new Tile("floor1"));
        put(  7, 3, new Tile("floor1"));        
        put(  8, 3, new Tile("floor1"));
        put(  9, 3, new Tile("floor1"));
        put( 10, 3, new Tile("floor1"));
        put( 11, 3, new Tile("floor1"));
        put( 12, 3, new Tile("floor1"));
        put( 13, 3, new Tile("floor1"));
        put( 14, 3, new Tile("floor1"));
        put( 15, 3, new Tile("floor1"));
        put( 16, 3, new Tile("floor1"));
        put( 17, 3, new Tile("floor1"));
        put( 18, 3, new Tile("floor1"));
        put( 19, 3, new Tile("floor1"));
        
        put( 0, 4, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put( 1, 4, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put( 2, 4, new Tile("floor1"));
        put( 3, 4, new Tile("pondCL"));
        put( 4, 4, new Tile("pondCC", new FLAG[]{BLK}));
        put( 5, 4, new Tile("pondCR"));
        put( 6, 4, new Tile("floor1"));
        put( 7, 4, new Tile("floor1"));
        put( 8, 4, new Tile("floor1"));
        put( 9, 4, new Tile("floor1"));
        put( 10, 4, new Tile("floor1"));
        put( 11, 4, new Tile("floor1"));
        put( 12, 4, new Tile("floor1"));
        put( 13, 4, new Tile("floor1"));
        put( 14, 4, new Tile("floor1"));
        put( 15, 4, new Tile("floor1"));
        put( 16, 4, new Tile("floor1"));
        put( 17, 4, new Tile("floor1"));
        put( 18, 4, new Tile("floor1"));
        put( 19, 4, new Tile("floor1"));
        
        put(  0, 5, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, 5, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, 5, new Tile("floor1"));
        put(  3, 5, new Tile("pondBL"));
        put(  4, 5, new Tile("pondBC"));
        put(  5, 5, new Tile("pondBR"));
        put(  6, 5, new Tile("floor1"));
        put(  7, 5, new Tile("floor1"));
        put(  8, 5, new Tile("floor1"));
        put(  9, 5, new Tile("floor1"));
        put( 10, 5, new Tile("floor1"));
        put( 11, 5, new Tile("floor1"));
        put( 12, 5, new Tile("floor1"));
        put( 13, 5, new Tile("floor1"));
        put( 14, 5, new Tile("floor1"));
        put( 15, 5, new Tile("floor1"));
        put( 16, 5, new Tile("floor1"));
        put( 17, 5, new Tile("floor1"));
        put( 18, 5, new Tile("floor1"));
        put( 19, 5, new Tile("floor1"));

        put(  0, 6, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, 6, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, 6, new Tile("floor1"));
        put(  3, 6, new Tile("floor1"));
        put(  4, 6, new Tile("floor1"));
        put(  5, 6, new Tile("floor1"));
        put(  6, 6, new Tile("floor1"));
        put(  7, 6, new Tile("floor1"));
        put(  8, 6, new Tile("floor1"));
        put(  9, 6, new Tile("floor1"));
        put( 10, 6, new Tile("floor1", "bigRockTL"));
        put( 11, 6, new Tile("floor1", "bigRockTR"));
        put( 12, 6, new Tile("floor1"));
        put( 13, 6, new Tile("floor1"));
        put( 14, 6, new Tile("floor1"));
        put( 15, 6, new Tile("floor1"));
        put( 16, 6, new Tile("floor1"));
        put( 17, 6, new Tile("floor1"));
        put( 18, 6, new Tile("floor1"));
        put( 19, 6, new Tile("floor1"));
        
        put(  0, 7, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, 7, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, 7, new Tile("floor1"));
        put(  3, 7, new Tile("floor1"));
        put(  4, 7, new Tile("floor1"));
        put(  5, 7, new Tile("floor1"));
        put(  6, 7, new Tile("floor1"));
        put(  7, 7, new Tile("floor1"));
        put(  8, 7, new Tile("floor1"));
        put(  9, 7, new Tile("floor1"));
        put( 10, 7, new Tile("floor1", "bigRockBL", new FLAG[]{BLK}));
        put( 11, 7, new Tile("floor1", "bigRockBR", new FLAG[]{BLK}));
        put( 12, 7, new Tile("floor1"));
        put( 13, 7, new Tile("floor1"));
        put( 14, 7, new Tile("floor1", "forestEdgeTL", new FLAG[]{BLK}));
        put( 15, 7, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put( 16, 7, new Tile("floor1", "forestEdgeTR", new FLAG[]{BLK}));
        put( 17, 7, new Tile("floor1"));
        put( 18, 7, new Tile("floor1"));
        put( 19, 7, new Tile("floor1"));
        
        put(  0, 8, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, 8, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, 8, new Tile("floor1"));
        put(  3, 8, new Tile("floor1"));
        put(  4, 8, new Tile("floor1"));
        put(  5, 8, new Tile("floor1"));
        put(  6, 8, new Tile("floor1"));
        put(  7, 8, new Tile("floor1"));
        put(  8, 8, new Tile("floor1"));
        put(  9, 8, new Tile("floor1"));
        put( 10, 8, new Tile("floor1"));
        put( 11, 8, new Tile("floor1"));
        put( 12, 8, new Tile("floor1"));
        put( 13, 8, new Tile("floor1"));
        put( 14, 8, new Tile("floor1", "forestEdgeCL", new FLAG[]{BLK}));
        put( 15, 8, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put( 16, 8, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put( 17, 8, new Tile("floor1"));
        put( 18, 8, new Tile("floor1"));
        put( 19, 8, new Tile("floor1"));
        
        put(  0, 9, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, 9, new Tile("floor1", "forestEdgeCR", new FLAG[]{BLK}));
        put(  2, 9, new Tile("floor1"));
        put(  3, 9, new Tile("floor1"));
        put(  4, 9, new Tile("floor1"));
        put(  5, 9, new Tile("floor1"));
        put(  6, 9, new Tile("floor1"));
        put(  7, 9, new Tile("floor1"));
        put(  8, 9, new Tile("floor1"));
        put(  9, 9, new Tile("floor1"));
        put( 10, 9, new Tile("floor1"));
        put( 11, 9, new Tile("floor1"));
        put( 12, 9, new Tile("floor1"));
        put( 13, 9, new Tile("floor1"));
        put( 14, 9, new Tile("floor1", "forestEdgeBL", new FLAG[]{BLK}));
        put( 15, 9, new Tile("floor1", "forestEdgeBC", new FLAG[]{BLK}));
        put( 16, 9, new Tile("floor1", "forestEdgeBR", new FLAG[]{BLK}));
        put( 17, 9, new Tile("floor1"));
        put( 18, 9, new Tile("floor1"));
        put( 19, 9, new Tile("floor1"));
        
        put(  0, 10, new Tile("floor1", "forestEdgeCC", new FLAG[]{BLK}));
        put(  1, 10, new Tile("floor1", "forestEdgeITR", new FLAG[]{BLK}));
        put(  2, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put(  3, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put(  4, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put(  5, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put(  6, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put(  7, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put(  8, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put(  9, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put( 10, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put( 11, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put( 12, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put( 13, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put( 14, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put( 15, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put( 16, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put( 17, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put( 18, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        put( 19, 10, new Tile("floor1", "forestEdgeTC", new FLAG[]{BLK}));
        
    }
    
}
