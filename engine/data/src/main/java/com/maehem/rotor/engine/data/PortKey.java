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
 * A token to indicate a destination @Room and destination @Point for the player
 * to teleport to.
 *
 * @author maehem
 */
public class PortKey {

    public final long destRoomUID;
    public final Point destPos;
    
    public final Point layoutPos;
    public final Point size;

    /**
     * 
     * @param layoutPos relative placement of upper left corner 0.0 - 1.0 per room unit size.
     * @param size
     * @param destRoomUID
     * @param destPos 
     */
    public PortKey(Point layoutPos, Point size, long destRoomUID, Point destPos ) {
        this.layoutPos = layoutPos;
        this.size = size;
        
        this.destRoomUID = destRoomUID;
        this.destPos = destPos;
    }

}
