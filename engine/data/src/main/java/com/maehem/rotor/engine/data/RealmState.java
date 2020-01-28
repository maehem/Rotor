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
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class RealmState {
    private static final Logger LOGGER = Logger.getLogger(RealmState.class.getName());
    
    private Realm realm;

    public RealmState(Realm realm) {
        this.realm = realm;
    }

    private RealmState(World world, DataInputStream dis) throws IOException {
        load(world, dis);
    }

    protected static void generate(World world, DataInputStream dis) throws IOException {
        RealmState realmState = new RealmState(null);
        realmState.load(world, dis);
    }
    
    /**
     * @return the realm
     */
    public Realm getRealm() {
        return realm;
    }

    public final void load(World world, DataInputStream dis) throws IOException {
        LOGGER.finer("Realm State Load starting...");
        if ( !dis.readUTF().equals("<REALM_STATE>") ) {
            throw new IOException("Data Chunk is not a <REALM_STATE> type.");
        }
        // Read local settings block
        
        long uid = dis.readLong();
        realm = world.getRealm(uid);
        if ( realm == null ) {
            throw new IOException("Realm State for UID " + uid + " is not for any room in this Realm!");
        }
        realm.setState(this);
        
        // Number of Rooms
        int nRooms = dis.readInt();
        for ( int i=0; i<nRooms; i++) {
            RoomState rmState = new RoomState(getRealm(), dis);
            
        }
        
        if ( !dis.readUTF().equals("<REALM_STATE_>") ) {
            throw new IOException("Chunk has more items in the <REALM_STATE> chunk than we expected. File version mis-match?");
        }

        // Assign roomStates to their rooms.
                
        LOGGER.finer("Realm State Load completed OK.");        
    }

    public void save(DataOutputStream dos) throws IOException {
         LOGGER.finer("Realm State Save starting...");
       
        // Write @Realm local state
        
        
        // Count the visited rooms to save
        int nRooms = 0;
        for( Room room: realm.getRooms() ) {
            if ( room.getState() != null ) {
                nRooms++;
            }
        }
        dos.writeUTF("<REALM_STATE>");
        dos.writeInt(nRooms);
        
        
        // Write @Room states
        for( Room room: realm.getRooms() ) {
            room.getState().save(dos);
        }
        
        dos.writeUTF("<REALM_STATE_>");
                
        LOGGER.finer("Realm State Save completed OK.");        
    }
    
}
