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
public class EntityState {
    private static final Logger LOGGER = Logger.getLogger(PlayerState.class.getName());

    public static final int HEALTH_MAX = 99;

    public static final String PROP_POSITION = "position";
    public static final String PROP_HEALTH = "health";
    
    /**
     * @return the changes
     */
    public DataChangeSupport getChanges() {
        return changes;
    }
    
    private String name;
    private final Point position = new Point(0.25, 0.5);
    private final DataChangeSupport changes = new DataChangeSupport();
    private double health = 99;

    public final void addDataChangeListener(String key, DataListener l) {
        getChanges().addDataChangeListener(key, l);
    }

    public final void removeDataChangeListener(String key, DataListener l) {
        getChanges().removeDataChangeListener(key, l);
    }

    protected double checkNumber(double val, double min, double max) {
        if (val < 0.0) {
            return 0.0;
        } else if (val > max) {
            return max;
        } else {
            return val;
        }
    }

    public void move(double dx, double dy) {
        Point oldPos = new Point(getPosition().x, getPosition().y);
        position.x += dx;
        position.y += dy;

        getChanges().firePropertyChange(PROP_POSITION, this, oldPos, getPosition());
    }

    /**
     * @return the position
     */
    public Point getPosition() {
        return position;
    }

    public void warpPosition(double x, double y) {
        Point oldPos = new Point(position.x, position.y);
        position.x = x;
        position.y = y;
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
     * @return the health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Set health.
     *
     * @param val
     */
    public void setHealth(double val) {
        double oldVal = this.health;
        this.health = checkNumber(val, 0, HEALTH_MAX);
        getChanges().firePropertyChange(PROP_HEALTH, this, oldVal, val);
    }

}
