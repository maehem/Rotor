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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class WorldState {

    private static final Logger LOGGER = Logger.getLogger(WorldState.class.getName());

    private World world;    
    private boolean loaded = false;
    
    // Saveable Local Values
    private long ticksElapsed = 0;
    
    // Savable Player State
    //private String playerName = "Charlie";
    //private int playerDifficulty = 3;
    //private int  playerHealth = 0;
    private long currentRealm = 0;  // UID of current realm
    private long currentRoom = 0;   // UID of current room
    // Current Weapon UID
    
    /**
     * Create state for the world.
     * 
     * @param world
     */
    public WorldState( World world) {
        this.world = world;
        LOGGER.config("World State initialization...");
    }

//    /**
//     * @return the playerName
//     */
//    public String getPlayerName() {
//        return playerName;
//    }
//
//    /**
//     * @param playerName the playerName to set
//     */
//    public void setPlayerName(String playerName) {
//        this.playerName = playerName;
//    }
//
    /**
     * After loading, resolve anything here.
     */
    private void resolveStuff() {
    }
    
    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean state) {
        loaded = state;
    }

    public void load(DataInputStream dis) throws IOException {
        LOGGER.finer("World State Load starting...");

        if ( !dis.readUTF().equals("<WORLD_STATE>") ) {
            throw new IOException("Data Chunk is not a <WORLD_STATE> type.");
        }
        // Read local settings block
        ticksElapsed = dis.readLong();
        //setPlayerHealth(dis.readInt());
        setCurrentRealm(dis.readLong());
        setCurrentRoom(dis.readLong());

        // Load Realm states
        int nRealms = dis.readChar();
        
        for ( int i=0; i< nRealms; i++ ) {
            RealmState.generate(world, dis);
        }

        if ( !dis.readUTF().equals("<WORLD_STATE_>") ) {
            throw new IOException("Chunk has more items in the <WORLD_STATE> chunk than we expected. File version mis-match?");
        }
        
        LOGGER.finer("World State Load completed OK.");
        setLoaded(true);
        
        resolveStuff();
    }

    public void save(DataOutputStream dos) throws IOException {
         LOGGER.finer("World State Save starting...");
       
        dos.writeUTF("<WORLD_STATE>");

        // Write World local state
        dos.writeLong(ticksElapsed);
        //dos.writeInt(getPlayerHealth());
        dos.writeLong(getCurrentRealm());
        dos.writeLong(getCurrentRoom());
        
        // Write state for each realm
        for ( Realm realm : world.getRealms() ) {
            realm.getState().save(dos);
        }
                
        dos.writeUTF("<WORLD_STATE_>");

        LOGGER.finer("World State Save completed OK.");        
    }

    /**
     * @return the ticksElapsed
     */
    public long getTicksElapsed() {
        return ticksElapsed;
    }
    
    public void tick() {
        ticksElapsed++;
    }

//    /**
//     * @return the playerDifficulty
//     */
//    public int getPlayerDifficulty() {
//        return playerDifficulty;
//    }
//
//    public void setPlayerDifficulty(int amount) {
//        playerDifficulty = amount;
//    }
//
//    /**
//     * @return the playerHealth
//     */
//    public int getPlayerHealth() {
//        return playerHealth;
//    }
//
//    /**
//     * @param playerHealth the playerHealth to set
//     */
//    public void setPlayerHealth(int playerHealth) {
//        this.playerHealth = playerHealth;
//    }
//
    /**
     * @return the currentRealm
     */
    public long getCurrentRealm() {
        return currentRealm;
    }

    /**
     * @param currentRealm the currentRealm to set
     */
    public void setCurrentRealm(long currentRealm) {
        this.currentRealm = currentRealm;
        LOGGER.log(Level.INFO, "Current Realm set to: {0}", this.currentRealm);
    }

    /**
     * @return the currentRoom
     */
    public long getCurrentRoom() {
        return currentRoom;
    }

    /**
     * @param currentRoom the currentRoom to set
     */
    public void setCurrentRoom(long currentRoom) {
        this.currentRoom = currentRoom;
        LOGGER.log(Level.INFO, "Current Room set to: {0}", this.currentRoom);
    }

    
}
