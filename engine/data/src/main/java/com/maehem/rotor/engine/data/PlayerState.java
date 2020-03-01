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
public class PlayerState extends EntityState {
    private static final Logger LOGGER = Logger.getLogger(PlayerState.class.getName());

    private static final int MANA_MAX = 99;
    private static final int MONEY_MAX = 999;
    private static final int BOMBS_MAX = 99;
    private static final int ARROWS_MAX = 99;

    public static final String PROP_MANA = "mana";
    public static final String PROP_MONEY = "money";
    public static final String PROP_BOMBS = "bombs";
    public static final String PROP_ARROWS = "arrows";
    public static final String PROP_NIGHT_VISION = "nightVision";
    
    private int mana = 0;
    private int money = 0;
    private int bombs = 0;
    private int arrows = 0;
    private int difficulty = 1;
    private double nightVision = 0.0;

    public PlayerState() {
        LOGGER.config("Player State created.");
    }

    /**
     * @return the mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * @param val the mana to set
     */
    public void setMana(int val) {
        int oldMoney = this.mana;
        this.mana = checkIntNumber(val, 0, MANA_MAX);

        getChanges().firePropertyChange(PROP_MANA, this, oldMoney, val);
    }

    /**
     * @return the money
     */
    public int getMoney() {
        return money;
    }

    /**
     *
     * @param val
     */
    public void setMoney(int val) {
        int oldMoney = this.money;
        this.money = checkIntNumber(val, 0, MONEY_MAX);

        getChanges().firePropertyChange(PROP_MONEY, this, oldMoney, getMoney());
    }

    /**
     * @return the bombs
     */
    public int getBombs() {
        return bombs;
    }

    /**
     * Set bombs.
     *
     * @param val
     */
    public void setBombs(int val) {
        int oldVal = this.bombs;
        this.bombs = checkIntNumber(val, 0, BOMBS_MAX);

        LOGGER.log(Level.CONFIG, "Bombs amount changing from {0} to {1}", new Object[]{oldVal, getBombs()});
        getChanges().firePropertyChange(PROP_BOMBS, this, oldVal, getBombs());
    }

    /**
     * @return the arrows
     */
    public int getArrows() {
        return arrows;
    }

    /**
     * Set arrows.
     *
     * @param val
     */
    public void setArrows(int val) {
        int oldVal = this.arrows;
        this.arrows = checkIntNumber(val, 0, ARROWS_MAX);
        getChanges().firePropertyChange(PROP_ARROWS, this, oldVal, getArrows());
    }

    /**
     * @return the difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     *
     * @param difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return the nightVision
     */
    public double getNightVision() {
        return nightVision;
    }

    /**
     * @param val the nightVision to set 0.0 - 1.0(max)
     */
    public void setNightVision(double val) {
        double oldVal = this.nightVision;
        this.nightVision = val;
        
        getChanges().firePropertyChange(PROP_NIGHT_VISION, this, oldVal, val);
    }

    protected int checkIntNumber(int val, int min, int max) {
        if (val < 0) {
            return 0;
        } else if (val > max) {
            return max;
        } else {
            return val;
        }
    }

    public boolean addArrows(int amount) {
        // Check if we can take at least one.
        if ( ARROWS_MAX - arrows < 1 ) return false;
        
        setArrows(getArrows() + amount);
        
        return true;
    }

    public boolean addBombs(int amount) {
        // Check if we can take at least one.
        if ( BOMBS_MAX - getBombs() < 1 ) return false;
        
        setBombs(getBombs() + amount);
        
        return true;
    }

    public boolean addMoney(int amount) {
        // Check if we can take at least one.
        if ( MONEY_MAX - getMoney() < 1 ) return false;
        
        setMoney(getMoney() + amount);
        
        return true;
    }

}
