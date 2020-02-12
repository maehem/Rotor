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

import com.maehem.rotor.engine.data.World;
import java.util.ArrayList;

/**
 *
 * @author maehem
 */
public class WorldNode {

    private final ArrayList<RealmNode> realmNodes = new ArrayList<>();
    private final World world;
    
    public WorldNode( World world ) {
        this.world = world;
        world.getRealms().forEach((realm) -> {
            realmNodes.add(new RealmNode(realm));
        });
        
    }
    
    /**
     * @return the realmNodes
     */
    public ArrayList<RealmNode> getRealmNodes() {
        return realmNodes;
    }

    public RealmNode getCurrentRealmNode() {
        long currentRealmUID = world.getState().getCurrentRealm();
        for ( var r : world.getRealms() ) {
            if ( r.getUid() == currentRealmUID ) {
                for (RealmNode rn : getRealmNodes()) {
                    if ( rn.getRealm() == r ) {
                        return rn;
                    }
                }
            }
        }
        
        return null;
    }

}