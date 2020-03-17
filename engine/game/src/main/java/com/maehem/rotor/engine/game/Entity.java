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
import com.maehem.rotor.engine.data.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class Entity {

    private static final Logger LOGGER = Logger.getLogger(Entity.class.getName());

    public static final double WALK_SPEED = 0.005;
    public static final double RUN_MULT = 3.0;
    public static final int MELEE_COOLDOWN = 0;

    private final EntityState state = new EntityState();

    private boolean running, goNorth, goSouth, goEast, goWest;
    private boolean meleeAttack = false;       
    private Object walkSheet;
    private String walkSheetFilename;
    private Item lootItem;
    private double walkSpeed = WALK_SPEED;
    private double runMult = RUN_MULT;
    private int meleeCooldownTicks = 0;
    private int meleeCooldown = MELEE_COOLDOWN;

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

    public void warpPosition(double x, double y) {
        getState().warpPosition(x, y);
    }

    public void moveBy(double dx, double dy) {
        getState().move(dx, dy);
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
        return 5.0 + Math.random() * 5.0;
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
        if (meleeDamage > health) {
            // Die.
            LOGGER.log(Level.WARNING, "{0} has died!", getName());
            getState().setHealth(0.0);
        } else {
            getState().setHealth(health - meleeDamage);
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

    public boolean tryMove(Room room) {
        double dx = 0;
        double dy = 0;

        if (isGoNorth()) {
            dy -= walkSpeed;
        } else if (isGoSouth()) {
            dy += walkSpeed;
        }
        if (isGoEast()) {
            dx += walkSpeed;
        } else if (isGoWest()) {
            dx -= walkSpeed;
        }
        if (isRunning()) {
            dx *= runMult;
            dy *= runMult;
        }

        Point pos = getState().getPosition();
        if (!room.isBlocked(pos.x + dx, pos.y + dy)) {
            moveBy(dx, dy);
            return true;
        }

        return false;
    }

    public void goNorth(boolean b) {
        this.goNorth = b;
    }

    public boolean isGoNorth() {
        return this.goNorth;
    }

    public void goSouth(boolean b) {
        this.goSouth = b;
    }

    public boolean isGoSouth() {
        return this.goSouth;
    }

    public void goWest(boolean b) {
        this.goWest = b;
    }

    public boolean isGoWest() {
        return this.goWest;
    }

    public void goEast(boolean b) {
        this.goEast = b;
    }

    public boolean isGoEast() {
        return this.goEast;
    }

    public void stop() {
        goEast(false);
        goWest(false);
        goNorth(false);
        goSouth(false);
        getState().move(0.0, 0.0);
    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * @return the walkSpeed
     */
    public double getWalkSpeed() {
        return walkSpeed;
    }

    /**
     * @param walkSpeed the walkSpeed to set
     */
    public void setWalkSpeed(double walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    /**
     * @return the runMult
     */
    public double getRunMult() {
        return runMult;
    }

    /**
     * @param runMult the runMult to set
     */
    public void setRunMult(double runMult) {
        this.runMult = runMult;
    }

    /**
     * @return the meleeCooldown
     */
    public int getMeleeCooldown() {
        return meleeCooldown;
    }

    /**
     * @param meleeCooldown the meleeCooldown to set
     */
    public void setMeleeCooldown(int meleeCooldown) {
        this.meleeCooldown = meleeCooldown;
    }

    public boolean isMeleeCooldown() {
        return meleeCooldownTicks > 0;
    }
    
    public void meleeCooldownTick() {
        if ( isMeleeCooldown() ) meleeCooldownTicks--;
    }
    
    public void startMeleeCooldown() {
        this.meleeCooldownTicks = this.meleeCooldown;
    }
}
