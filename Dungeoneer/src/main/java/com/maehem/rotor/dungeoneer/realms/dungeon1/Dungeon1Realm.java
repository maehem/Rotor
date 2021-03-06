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

import com.maehem.rotor.engine.game.Realm;
import com.maehem.rotor.engine.game.World;

/**
 *
 * @author maehem
 */
public class Dungeon1Realm extends Realm {

    public static final long UID = 10000;
    
    public Dungeon1Realm( World parent) {
        super(parent);
        
        setUid(UID);
        setDisplayName("Realm of Light");
        
        getRooms().add(new Room001(this));
        getRooms().add(new Room002(this));
    }
    
    
}
