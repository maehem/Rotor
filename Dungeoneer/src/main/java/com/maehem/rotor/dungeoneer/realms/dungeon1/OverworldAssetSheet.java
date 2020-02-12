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

import com.maehem.rotor.renderer.AssetSheet;

/**
 *
 * @author maehem
 */
public class OverworldAssetSheet extends AssetSheet {

    private static final double GRID_SIZE = 16;
    
    public OverworldAssetSheet( double outputSize ) {
        super(OverworldAssetSheet.class.getResourceAsStream("/sheets/overworld.png"), GRID_SIZE, outputSize);

        putAsset("floor1", 0,   0);
        putAsset("water1", 48, 112);
        // Pond
        putAsset("pondTL", 32,  96);
        putAsset("pondTC", 48,  96);
        putAsset("pondTR", 64,  96);
        putAsset("pondCL", 32, 112);
        putAsset("pondCC", 48, 112);
        putAsset("pondCR", 64, 112);
        putAsset("pondBL", 32, 128);
        putAsset("pondBC", 48, 128);
        putAsset("pondBR", 64, 128);
        
        putAsset("clifBaseL", 64, 224);
        putAsset("clifBaseC", 80, 224);
        putAsset("clifBaseR", 96, 224);
        putAsset("clifFace1", 80, 192);
        putAsset("clifFace2", 80, 208);
        putAsset("clifBaseShadeL", 64, 240);
        putAsset("clifBaseShadeC", 80, 240);
        putAsset("clifBaseShadeR", 96, 240);
        
        putAsset("clifBaseCave", 112, 192);
        
        putAsset("forestEdgeTL", 80, 256);
        putAsset("forestEdgeTC", 80, 288);
        putAsset("forestEdgeTR", 96, 256);

        putAsset("forestEdgeCR", 112, 288);
        putAsset("forestEdgeCC",  32, 256);
        putAsset("forestEdgeCL",  96, 288);

        putAsset("forestEdgeBL", 80, 272);
        putAsset("forestEdgeBC", 80, 304);
        putAsset("forestEdgeBR", 96, 272);
        
        putAsset("forestEdgeITL", 48, 256);
        putAsset("forestEdgeITR", 64, 256);
        putAsset("forestEdgeIBL", 48, 272);
        putAsset("forestEdgeIBR", 64, 272);
        
        
        putAsset("bigRockTL", 64, 16);
        putAsset("bigRockTR", 80, 16);
        putAsset("bigRockBL", 64, 32);
        putAsset("bigRockBR", 80, 32);
    }
    
}
