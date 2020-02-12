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

import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class PlayerState {

    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());
    
    private final DataChangeSupport changes = new DataChangeSupport();

    private static final int MANA_MAX = 99;
    private static final int MONEY_MAX = 999;
    private static final int BOMBS_MAX = 99;
    private static final int ARROWS_MAX = 99;
    private static final int HEALTH_MAX = 99;
    
    public static final String PROP_MANA = "mana";
    public static final String PROP_MONEY = "money";
    public static final String PROP_BOMBS = "bombs";
    public static final String PROP_ARROWS = "arrows";
    public static final String PROP_HEALTH = "health";
    public static final String PROP_POSITION = "position";
    
    private String name;
    private int mana = 99;
    private int money = 999;
    private int bombs = 99;
    private int arrows = 99;
    private int health = 99;
    private int difficulty = 1;
    
    private final Point position = new Point(0.25,0.5);
        
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
        this.mana = checkNumber(val, 0, MANA_MAX);
        
        changes.firePropertyChange(PROP_MANA, oldMoney, val);
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
        this.money = checkNumber(val, 0, MONEY_MAX);
        
        changes.firePropertyChange(PROP_MONEY, oldMoney, money);
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
        this.bombs = checkNumber(val, 0, BOMBS_MAX);
        
        changes.firePropertyChange(PROP_BOMBS, oldVal, val);
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
        this.arrows = checkNumber(val, 0, ARROWS_MAX);
        changes.firePropertyChange(PROP_ARROWS, oldVal, val);
    }

    /**
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Set health.
     * 
     * @param val 
     */
    public void setHealth(int val) {
        int oldVal = this.health;
        this.health = checkNumber(val, 0, HEALTH_MAX);
        changes.firePropertyChange(PROP_HEALTH, oldVal, val);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param text 
     */
    public void setName(String text) {
        this.name = text;
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

    public final void addDataChangeListener(String key, DataListener l) {
        changes.addDataChangeListener(key, l);
    }

    public final void removeDataChangeListener(String key, DataListener l) {
        changes.removeDataChangeListener(key, l);
    }

    private int checkNumber( int val, int min, int max ) {
        if ( val < 0 ) {
            return 0;
        } else if ( val > max ) {
            return max;
        } else {
            return val;
        }        
    }

    public void move(double dx, double dy) {
//        if (dx == 0 && dy == 0) {
//            return;
//        }

        
//        final double cx = hero.getBoundsInLocal().getWidth() / 2;
//        final double cy = hero.getBoundsInLocal().getHeight() / 2;
//
//        double x = cx + hero.getLayoutX() + dx;
//        double y = cy + hero.getLayoutY() + dy;

        //moveHeroTo(x, y);
        Point oldPos = new Point(getPosition().x, getPosition().y);
        
        position.x += dx;
        position.y += dy;
        
        changes.firePropertyChange(PROP_POSITION, oldPos, getPosition());
        
    
    }

    /**
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

}
