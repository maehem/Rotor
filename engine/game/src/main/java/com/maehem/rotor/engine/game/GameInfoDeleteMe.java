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

import com.maehem.rotor.engine.data.DataChangeSupport;
import com.maehem.rotor.engine.data.DataListener;
import com.maehem.rotor.engine.data.exception.MoneyOverdraftException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class GameInfoDeleteMe {

    private static final Logger LOGGER = Logger.getLogger(GameInfoDeleteMe.class.getName());

    public static final String PROP_NAME = "name";
    public static final String PROP_MONEY = "money";
    public static final String PROP_TICKS_ELEAPSED = "ticksElapsed";
    public static final String PROP_POPULATION = "population";
    public static final String PROP_ROTATION = "rotation";

    public static final int NAME_LENGTH = 24;
    
    public byte[] data;

    private Room currentRoom = null;

    private String fileSaveName;
    private String name;
    private String description;
    private String founder;
    
    // I18N settings
//    private String country = "US";
//    private String language = "en";

    public int mapSize = 8;  // N x N x N. The map is always square. This also the max altitude.
    public int waterLevel = 7;
    public int yearFounded = 2020;
    private int money = 0;
    private int population = 0; // TODO this might be a sum of Cell populations

    // UI Settings changed by user.
    private int rotation = 0;
    public int cityCenterX = 0;  // Center view here.
    public int cityCenterY = 0;
    public int zoomLevel = 0;
    private long ticksElapsed = 0;

    public enum SPEED { SLOW, MEDIUM, FAST, PLAID }
    
    public SPEED speed = SPEED.SLOW;

    private final DataChangeSupport changes = new DataChangeSupport();

    public GameInfoDeleteMe() {
        name = "Unnamed Colony";
        description = "Somewhere on Mars...";
        founder = "";
    }

    public GameInfoDeleteMe(DataInputStream dis) throws IOException {
        load(dis);
    }

    // TODO make load/save and others part of interface implementations

    public final void load(DataInputStream dis) throws IOException {
        LOGGER.finer("Read Game Info block... START");
        dis.readUTF();  // <GINF>

        name = dis.readUTF();
        description = dis.readUTF();
        
        mapSize = dis.readInt();
        waterLevel = dis.readInt();
        yearFounded = dis.readInt();
        ticksElapsed = dis.readLong();
        setMoney(dis.readInt());
        setPopulation(dis.readInt());

        setRotation(dis.readInt());
        cityCenterX = dis.readInt();
        cityCenterY = dis.readInt();
        zoomLevel = dis.readInt();
        speed = SPEED.values()[dis.readByte()];
        
        dis.readUTF();  // <GINF_>
        
        LOGGER.finer("Read Game Info block... OK");

    }

    public void save(DataOutputStream dos) throws IOException {

        LOGGER.finer("Write Game Info block... START");

        dos.writeUTF("<GINF>");
        // TODO: May not need to replace spaces in string.
        //dos.writeChars(String.format("%-" + NAME_LENGTH + "s", getName()).replace(' ', Character.MIN_VALUE));  // Unicode.  48 bytes. 24 chars. Zero padded
        dos.writeUTF(name);
        dos.writeUTF(description);
        
        dos.writeInt(mapSize);
        dos.writeInt(waterLevel);
        dos.writeInt(yearFounded);
        dos.writeLong(ticksElapsed);
        dos.writeInt(getMoney());
        dos.writeInt(getPopulation());

        dos.writeInt(getRotation());
        dos.writeInt(cityCenterX);
        dos.writeInt(cityCenterY);
        dos.writeInt(zoomLevel);
        dos.writeByte(speed.ordinal());
        dos.writeUTF("<GINF_>");
        dos.flush();
        
        LOGGER.finer("Read Game Info block... OK");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Game Info:\n");
        sb.append(printIt("            Name: {0}\n", getName()));
        sb.append(printIt("        Rotation: {0}\n", getRotation()));
        sb.append(printIt("    Year Founded: {0}\n", yearFounded));
        sb.append(printIt("    Days Elapsed: {0}\n", ticksElapsed));
        sb.append(printIt("           Money: {0}\n", getMoney()));
        sb.append(printIt("      Population: {0}\n", getPopulation()));
        sb.append(printIt("      Zoom Level: {0}\n", zoomLevel));
        sb.append(printIt("   City Center X: {0}\n", cityCenterX));
        sb.append(printIt("   City Center Y: {0}\n", cityCenterY));
        sb.append(printIt("     Water Level: {0}\n", waterLevel));

        return sb.toString();
    }

    private String printIt(String message, Object data) {
        MessageFormat form = new MessageFormat(message);

        Object[] testArgs = {data};

        return form.format(testArgs);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        changes.firePropertyChange(PROP_NAME, this, oldName, name);
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
    }

    /**
     * @return the founder
     */
    public String getFounder() {
        return founder;
    }

    /**
     * @param founder the founder to set
     */
    public void setFounder(String founder) {
        String oldFounder = this.founder;
        this.founder = founder;
    }

    /**
     * @return the money
     */
    public int getMoney() {
        return money;
    }

    /**
     * @param money the money to set
     */
    public void setMoney(int money) {
        int oldMoney = this.money;
        this.money = money;
        changes.firePropertyChange(PROP_MONEY, this, oldMoney, money);
    }

    /**
     * @return the population
     */
    public int getPopulation() {
        return population;
    }

    /**
     * @param population the population to set
     */
    public void setPopulation(int population) {
        int oldPopulation = this.population;
        this.population = population;
        changes.firePropertyChange(PROP_POPULATION, this, oldPopulation, population);
    }

    /**
     * @return the rotation
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * @param rotation the rotation to set
     */
    public void setRotation(int rotation) {
        int oldRotation = this.rotation;
        this.rotation = rotation;
        changes.firePropertyChange(PROP_ROTATION, this, oldRotation, rotation);
    }

    /**
     * @return the ticksElapsed
     */
    public long getTicksElapsed() {
        return ticksElapsed;
    }

    /**
     * @param ticksElapsed the ticksElapsed to set
     */
    public void setTicksElapsed(long ticksElapsed) {
        long oldTicks = this.ticksElapsed;
        this.ticksElapsed = ticksElapsed;
        changes.firePropertyChange(PROP_TICKS_ELEAPSED, this, oldTicks, ticksElapsed);
    }

    public void addDataChangeListener(String key, DataListener l) {
        changes.addDataChangeListener(key, l);
    }

    public void removeDataChangeListener(String key, DataListener l) {
        changes.removeDataChangeListener(key, l);
    }

    public void debitMoney(int amount) throws MoneyOverdraftException {
        if ( amount > money ) {
            throw new MoneyOverdraftException();
        }
        setMoney(money-amount);
    }
    
    public String getFileSaveName() {
        return fileSaveName;
    }
    
    public void setFileSaveName( String saveName ) {
        this.fileSaveName = saveName;
    }

    public void setYearFounded(int year) {
        this.yearFounded = year;
    }

    public void setMapSize(int size) {
        this.mapSize = size;
    }
}
