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

/**
 *
 * @author maehem
 */
public class Tile {

    public static enum FLAG { BLK }
    
    private final String assetName[] = new String[2];
    
    private boolean block = false;
    
//    public final int x;
//    public final int y;
    
//    private Tile(int x, int y) {
//        this.x = x;
//        this.y = y;
//        
//    }
//    
//    private Tile(int x, int y, FLAG[] flags) {
//        this.x = x;
//        this.y = y;
//        
//        for ( FLAG f : flags ) {
//            switch( f ) {
//                case BLK:
//                    block = true;
//                    break;
//            }
//        }
//    }
//    
//    public Tile(String assetName, int x, int y) {
//        this(x, y);
//        this.assetName = new String[1];
//        this.assetName[0] = assetName;
//    }
//    
//    public Tile(String assetName, int x, int y, FLAG[] flags) {
//        this(x, y, flags);
//        this.assetName = new String[1];
//        this.assetName[0] = assetName;
//    }
//    
//    public Tile(String assetName1, String assetName2, int x, int y) {
//        this(x, y);
//        this.assetName = new String[2];
//        this.assetName[0] = assetName1;
//        this.assetName[1] = assetName2;
//    }
//    
//    public Tile(String assetName1, String assetName2, int x, int y, FLAG[] flags) {
//        this(x, y, flags);
//        this.assetName = new String[2];
//        this.assetName[0] = assetName1;
//        this.assetName[1] = assetName2;
//    }
//    
    public Tile(String assetName, FLAG[] flags) {
        this.assetName[0] = assetName;
        for ( FLAG f : flags ) {
            switch( f ) {
                case BLK:
                    block = true;
                    break;
            }
        }
    }
    
    public Tile(String assetName) {
        this(assetName, new FLAG[]{});
    }
    
    public Tile(String assetName0, String assetName1) {
        this(assetName0, new FLAG[]{});
        this.assetName[1] = assetName1;
    }
    
    public Tile(String assetName0, String assetName1, FLAG[] flags) {
        this(assetName0, flags);
        this.assetName[1] = assetName1;
    }
    
    public String getAssetName(int index) {
        return assetName[index];
    }

    public int getAssetCount() {
        return assetName.length;
    }

    /**
     * @return is blocking
     */
    public boolean isBlock() {
        return block;
    }

    /**
     * @param block set if blocking
     */
    public void setBlock(boolean block) {
        this.block = block;
    }
}
