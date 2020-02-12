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
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * A high level map area containing @Realms
 * 
 * @author maehem
 */
public class World {
    private static final Logger LOGGER = Logger.getLogger(World.class.getName());
    
    private static World instance;

    private boolean loaded = false;
    
    private WorldState state;
    
    private final ArrayList<Realm> realms = new ArrayList<>();

    private Player player = null;
    private String displayName = "Some World";
    private long startRealm = 0L;
    private long startRoom = 0L;
    private int screenWidth = 640;
    private int screenHeight = 360;
    private int tileSize = 32;

    private World() {
        LOGGER.config("World Instance Created.");
        this.player = new Player(this);
    }
    
    public static final World getInstance() {
        if ( instance == null ) {
            instance = new World();
        }
        
        return instance;
    }
    
    public ArrayList<Realm> getRealms() {
        return realms;
    }
    
    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean state) {
        loaded = state;
    }

    Realm getRealm(long uid) {
        for ( Realm realm : getRealms() ) {
            if ( realm.getUid() == uid ) {
                return realm;
            }
        }
        
        return null;
    }

    public void load(DataInputStream dis) throws IOException {
        LOGGER.finer("World Load starting...");

        if ( !dis.readUTF().equals("<WORLD>") ) {
            throw new IOException("Data Chunk is not a <WORLD> type.");
        }
        // Read header block
        loadHeader(dis);
        

        int nRealms = dis.readChar();
        
        for ( int i=0; i< nRealms; i++ ) {
            realms.add(new Realm(this, dis));
        }
                
        if ( !dis.readUTF().equals("<WORLD_>") ) {
            throw new IOException("Data Chunk has more items in the <WORLD> chunk than we expected. File version mis-match?");
        }
        LOGGER.finer("World Load completed OK.");
        setLoaded(true);        
    }

    public void save(DataOutputStream dos) throws IOException {
         LOGGER.finer("World Save starting...");
       
        dos.writeUTF("<WORLD>");
        saveHeader(dos); // Write Header
        
        dos.writeChar(realms.size()); // How many rooms
        
        // Save each room
        for ( int i=0; i<realms.size(); i++ ) {
            realms.get(i).save(dos);
        }
        dos.writeUTF("<WORLD_>");
        
        LOGGER.finer("World Save completed OK.");        
    }

    private void loadHeader(DataInputStream dis) throws IOException {
        LOGGER.finer("World header load starting...");
        if ( !dis.readUTF().equals("<WLD_HDR>") ) {
            throw new IOException("Data Chunk is not a <WORLD> type.");
        }
        // Display Name
        displayName = dis.readUTF();
        setStartRealm(dis.readLong());
        setStartRoom(dis.readLong());
        
        if ( !dis.readUTF().equals("<WLD_HDR_>") ) {
            throw new IOException("Chunk has more items in the <WLD_HDR> chunk than we expected. File version mis-match?");
        }
        LOGGER.finer("World header load done.");
    }

    private void saveHeader(DataOutputStream dos) throws IOException {
        LOGGER.finer("World header save starting...");
        dos.writeUTF("<WLD_HDR>");
        // Display Name
        dos.writeUTF(displayName);
        dos.writeLong(getStartRealm());
        dos.writeLong(getStartRoom());
        
        dos.writeUTF("<WLD_HDR_>");
        LOGGER.finer("World header save done.");
    }

    public WorldState initState() {
        state = new WorldState(this);
        // Set current Realm
        state.setCurrentRealm(getStartRealm());
        // Set Current Room
        state.setCurrentRoom(getStartRoom());
        
        return getState();
    }
    
    /**
     * @return the state
     */
    public WorldState getState() {
        return state;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String name) {
        displayName = name;
    }

    /**
     * @return the startRealm
     */
    public long getStartRealm() {
        return startRealm;
    }

    /**
     * @param startRealm the startRealm to set
     */
    public void setStartRealm(long startRealm) {
        this.startRealm = startRealm;
    }

    /**
     * @return the startRoom
     */
    public long getStartRoom() {
        return startRoom;
    }

    /**
     * @param startRoom the startRoom to set
     */
    public void setStartRoom(long startRoom) {
        this.startRoom = startRoom;
    }

    public void setScreenWidth(int width) {
        screenWidth = width;
    }

    public void setScreenHeight(int height) {
        screenHeight = height;
    }

    /**
     * @return the screenWidth
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * @return the screenHeight
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    public int getTileSize() {
        return tileSize;
    }
    
    public void setTileSize(int size) {
        this.tileSize = size;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    public Realm getCurrentRealm() {
        return getRealm(getState().getCurrentRealm());
    }
    
    public Room getCurrentRoom() {
        return getCurrentRealm().getRoom(getState().getCurrentRoom());
    }
}
