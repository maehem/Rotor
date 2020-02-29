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

import com.maehem.rotor.engine.game.World;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class WorldNode {
    private static final Logger LOGGER = Logger.getLogger(WorldNode.class.getName());

    private final ArrayList<RealmNode> realmNodes = new ArrayList<>();
    private final World world;
    private final PlayerNode playerNode;
    
    public WorldNode( World world ) throws IOException {
        this.world = world;
        playerNode = new PlayerNode(world.getPlayer(), world);
        
        world.getRealms().forEach((realm) -> {
            realmNodes.add(new RealmNode(realm));
        });        
    }
    
    /**
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return the realmNodes
     */
    public ArrayList<RealmNode> getRealmNodes() {
        return realmNodes;
    }

    public RealmNode getCurrentRealmNode() {
        long currentRealmUID = getWorld().getState().getCurrentRealm();
        for ( var r : getWorld().getRealms() ) {
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

    /**
     * @return the playerNode
     */
    public PlayerNode getPlayerNode() {
        return playerNode;
    }

}
