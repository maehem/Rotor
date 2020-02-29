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

import com.maehem.rotor.engine.data.EntityState;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class Entity {

    private static final Logger LOGGER = Logger.getLogger(Entity.class.getName());

    private final EntityState state = new EntityState();
    
    private boolean meleeAttack = false;
    private Object walkSheet;
    private String walkSheetFilename;
    private Item lootItem;

    public Entity() {
    }

    public Entity(String name, String walkSheetFilename, Item lootItem) {
        this(name, walkSheetFilename);
        this.lootItem = lootItem;
    }
    
    public Entity(String name, String walkSheetFilename) {
        setName(name);
        this.walkSheetFilename = walkSheetFilename;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return state.getName();
    }

    /**
     * @param name the name to set
     */
    public final void setName(String name) {
        state.setName(name);
    }

    /**
     * @return the walkSheetFilename
     */
    public String getWalkSheetFilename() {
        return walkSheetFilename;
    }

    /**
     * @param walkSheetFilename the walkSheetFilename to set
     */
    public void setWalkSheetFilename(String walkSheetFilename) {
        this.walkSheetFilename = walkSheetFilename;
    }

    /**
     * @return the state
     */
    public EntityState getState() {
        return state;
    }

    public void warpPosition( double x, double y ) {
        getState().warpPosition(x,y);
    }
    
    public void moveBy( double dx, double dy ) {
        getState().move(dx,dy);
    }
    
    public void meleeAttack(boolean attack) {
        this.meleeAttack = attack;
    }

    public boolean isMeleeAttack() {
        return meleeAttack;
    }
    
    /**
     * Return amount of damage.
     * 
     * @return 
     */
    public double getMeleeDamage() {
        return 5.0 + Math.random()*5.0;
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

    public void damage(double meleeDamage) {
        double health = getState().getHealth();
        if ( meleeDamage > health ) {
            // Die.
            LOGGER.log(Level.WARNING, "{0} has died!", getName());
            getState().setHealth(0.0);
        } else {
            getState().setHealth(health-meleeDamage);
        }
    }
    
    /**
     * @return the lootItem
     */
    public Item getLootItem() {
        return lootItem;
    }

    /**
     * @param lootItem the lootItem to set
     */
    public void setLootItem(Item lootItem) {
        this.lootItem = lootItem;
    }

}
