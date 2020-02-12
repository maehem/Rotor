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
 * A map area containing rooms.
 * 
 * @author maehem
 */
public class Realm {
    private static final Logger LOGGER = Logger.getLogger(Realm.class.getName());

    private boolean loaded = false;
    private RealmState state = new RealmState(this);
    
    private final ArrayList<Room> rooms = new ArrayList<>();
    
    
    private final World parent;
    private long uid;
    private String displayName = "Some Realm";


    public Realm(World parent, DataInputStream dis) throws IOException {
        this.parent = parent;
        
        load(dis);
    }

    public Realm(World parent) {
        this.parent = parent;
    }
    
    public final ArrayList<Room> getRooms() {
        return rooms;
    }
    
    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean state) {
        loaded = state;
    }

    public final void load(DataInputStream dis) throws IOException {
        LOGGER.finer("Realm Load starting...");

        if ( !dis.readUTF().equals("<REALM>") ) {
            throw new IOException("Data Chunk is not a <REALM> type.");
        }
        // Read GameInfo block
        //gameInfo = new GameInfo(dis);

        int nRooms = dis.readChar();
        
        for ( int i=0; i< nRooms; i++ ) {
            rooms.add(new Room(this, dis));
        }
                
        if ( !dis.readUTF().equals("<REALM_>") ) {
            throw new IOException("Data Chunk has more items in the <REALM> chunk than we expected. File version mis-match?");
        }
        
        LOGGER.finer("Realm Load completed OK.");
        setLoaded(true);       
    }

    public final void save(DataOutputStream dos) throws IOException {
        LOGGER.finer("Realm Save starting...");
       
        dos.writeUTF("<REALM>");
        //gameInfo.save(dos); // Write GameInfo
        // Realm Display Name
        
        dos.writeChar(rooms.size()); // How many rooms
        
        // Save each room
        for ( int i=0; i<rooms.size(); i++ ) {
            rooms.get(i).save(dos);
        }
        
        dos.writeUTF("<REALM_>");

        LOGGER.finer("Realm Save completed OK.");
        
    }

    public long getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    protected final void setUid(long uid) {
        this.uid = uid;
    }
 
    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public final void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

   /**
     * @return the state
     */
    public RealmState getState() {
        return state;
    }

    void setState(RealmState state) {
        this.state = state;
    }
    
    Room getRoom(long uid) {
        for ( Room room : getRooms() ) {
            if ( room.getUid() == uid ) {
                return room;
            }
        }
        
        return null;
    }

    /**
     * @return the parent
     */
    public World getParent() {
        return parent;
    }
}
