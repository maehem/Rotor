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
package com.maehem.rotor.engine.game;

import com.maehem.rotor.engine.data.PlayerState;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class Player extends Entity {

    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    private final PlayerState state = new PlayerState();
    
    private PortKey portKey;
    
    public Player() {
        LOGGER.config("Create Player.");
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
    }

    public boolean hasPortKey() {
        return portKey != null;
    }
    
    public void gotoPortKey() {
        warpPosition(portKey.destPos.x, portKey.destPos.y);
        LOGGER.log(Level.CONFIG, "Set player destination position to: {0},{1}", new Object[]{portKey.destPos.x, portKey.destPos.y});
        portKey = null; // Consume the Port Key
    }
    
    /**
     * @return the state
     */
    @Override
    public PlayerState getState() {
        return state;
    }

    /**
     * Add this item to inventory.
     * 
     * @param item
     * @return true if item was added to player inventory.
     */
    public boolean takeItem(Item item) {
        // If item is arrow, bomb, money or health, then add use it. 
        // Otherwise, it is an inventory item.
        if ( item instanceof ArrowsLootItem ) {
            LOGGER.log(Level.CONFIG, "Player takes {0} Arrows.", ((ArrowsLootItem) item).getAmount());
            return getState().addArrows(((ArrowsLootItem) item).getAmount());
        }
        if ( item instanceof BombsLootItem ) {
            LOGGER.log(Level.CONFIG, "Player takes {0} Bombs.", ((BombsLootItem) item).getAmount());
            return getState().addBombs(((BombsLootItem) item).getAmount());
        }
        if ( item instanceof MoneyLootItem ) {
            LOGGER.log(Level.CONFIG, "Player takes {0} Coins.", ((MoneyLootItem) item).getAmount());
            return getState().addMoney(((MoneyLootItem) item).getAmount());
        }        
        
        return false;        
    }

}
