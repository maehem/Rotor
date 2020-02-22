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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class Player {

    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    public static final double WALK_SPEED = 0.005;
    public static final double RUN_MULT = 3.0;
    
    private final PlayerState state = new PlayerState();
    private final World parent;
    
    private PortKey portKey;
    
    private Object walkSheet;
    private boolean swordAttack = false;
    
    public Player( World parent ) {
        this.parent = parent;
        LOGGER.config("Create Player.");
    }
    
    /**
     * @return the state
     */
    public PlayerState getState() {
        return state;
    }

    public void warpPosition( double x, double y ) {
        getState().warpPosition(x,y);
    }
    
    public void moveBy( double dx, double dy ) {
        Point p = getState().getPosition();
        
        // TODO:  Use double to track Player position as 0.0 to 1.0
        // Check if we are able to move into the next location.
        boolean blocked = getParent().getCurrentRoom().isBlocked( p.x + dx, p.y + dy );
        if ( blocked ) {
            LOGGER.finer("Path is blocked.");
        } else  {
            getState().move(dx,dy);
        }
    }
    /**
     * @return the parent
     */
    public World getParent() {
        return parent;
    }

    /**
     * @return the walkSheet
     */
    public Object getWalkSheet() {
        return walkSheet;
    }

    /**
     * @param walkSheet the walkSheet to set
     */
    public void setWalkSheet(Object walkSheet) {
        this.walkSheet = walkSheet;
    }
    
    /**
     * @return the portKey
     */
    public PortKey getPortKey() {
        return portKey;
    }

    /**
     * @param portKey the portKey to set
     */
    public void setPortKey(PortKey portKey) {
        this.portKey = portKey;
        getState().changeRoom(portKey.destRoomUID);
    }

    public boolean hasPortKey() {
        return portKey != null;
    }
    
    public void gotoPortKey() {
        warpPosition(portKey.destPos.x, portKey.destPos.y);
        LOGGER.log(Level.CONFIG, "Set player destination position to: {0},{1}", new Object[]{portKey.destPos.x, portKey.destPos.y});
        portKey = null; // Consume the Port Key
    }
    
    public void swordAttack(boolean attack) {
        this.swordAttack = attack;
    }

    public boolean isSwordAttack() {
        return swordAttack;
    }
}
