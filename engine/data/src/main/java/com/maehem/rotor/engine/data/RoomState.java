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
public class RoomState {

    private static final Logger LOGGER = Logger.getLogger(RoomState.class.getName());

    private Room room;

    //private boolean visited = false;
    public RoomState(Room room) {
        this.room = room;
    }

    public RoomState( Realm realm, DataInputStream dis ) throws IOException {
        load(realm, dis);
    }

    
//    public boolean isVisted() {
//        return visited;
//    }
    public final void load(Realm realm, DataInputStream dis) throws IOException {
        LOGGER.finer("Room State Load starting...");
        if ( !dis.readUTF().equals("<ROOM_STATE>") ) {
            throw new IOException("Data Chunk is not a <ROOM_STATE> type.");
        }

        // Read @Room local state
        long uid = dis.readLong();
        room = realm.getRoom(uid);
        if ( room == null ) {
            throw new IOException("Room State for UID " + uid + " is not for any room in this Realm!");
        }
        room.setState(this);
        
        if ( !dis.readUTF().equals("<ROOM_STATE_>") ) {
            throw new IOException("Chunk has more items in the <ROOLM_STATE> chunk than we expected. File version mis-match?");
        }

        LOGGER.finer("Room State Load completed OK.");
    }

    public void save(DataOutputStream dos) throws IOException {
        // We only save state of visited rooms.
        LOGGER.finer("Room State Save starting...");

        // Write @Room local state
        dos.writeLong(room.getUid());
        
        LOGGER.finer("Room State Save completed OK.");
    }
}
