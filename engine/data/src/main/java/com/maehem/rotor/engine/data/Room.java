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

import com.maehem.rotor.engine.data.exception.RoomDimensionException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class Room {
    private static final Logger LOGGER = Logger.getLogger(Room.class.getName());

    public Realm parent;
    
    private RoomState state = null;  // Only set if player has visited.

    public static final int MAX_WIDTH = 8;
    public static final int MAX_HEIGHT = 8;

    public enum Edge {
        TOP, LEFT, BOTTOM, RIGHT
    }

    private long uid = Math.round(Math.random());
    private String displayName = "";
    
    // Width and Height in world units.  A world unit is a standard pixel area 
    // that is the screen's width and height (i.e. 320x240 ). So a width unit 
    // is 320pixels and a height unit is 240 pixels.  The Game object gets inited
    // with the programmer's desired screen height and width.  Each screen is a 
    // unit. A room can be larger than a screen, so we pan the camera to follow 
    // the  player in larger rooms, centering them on the screen.  In smaller 
    // rooms, the player moves around the screen.
    private int width = 1;
    private int height = 1;
    private long[] topExit;
    private long[] rightExit;
    private long[] bottomExit;
    private long[] leftExit;

    public Room(Realm parent, DataInputStream dis) throws IOException {
        LOGGER.config("Room Created.");
        this.parent = parent;
        load(dis);
    }

    public Room(Realm parent, int width, int height) {
        LOGGER.config("Room Created.");
        this.parent = parent;
        this.width = width;
        this.height = height;
        this.topExit = new long[width];
        this.rightExit = new long[height];
        this.bottomExit = new long[width];
        this.leftExit = new long[height];
    }

    /**
     * Get unique ID of this room.
     * 
     * @return
     */
    public long getUid() {
        return uid;
    }
    
    /**
     * Set unique ID for this room.
     * 
     * @param uid
     */
    public final void setUid( long uid ) {
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
        LOGGER.log(Level.CONFIG, "Room {0} named to: {1}", new Object[]{uid, displayName});
        this.displayName = displayName;
    }

    /**
     *
     * @return width in screen-blocks of room.
     */
    public int getWidth() {
        return width;
    }
    
    /**
     *
     * @return height in screen-blocks of room.
     */
    public int getHeight() {
        return height;
    }
    
    /**
     *
     * @return room state object.  @null if not visited.
     */
    public RoomState getState() {
        return state;
    }
    
    /**
     * Set the state object if room has been visited.
     * 
     * @param state
     */
    protected void setState(RoomState state) {
        this.state = state;
    }

    public void setDoor(Edge e, int index, Room destRoom) throws RoomDimensionException {

        try {
            switch (e) {
                case TOP:
                    topExit[index] = destRoom.uid;
                    break;
                case RIGHT:
                    rightExit[index] = destRoom.uid;
                    break;
                case BOTTOM:
                    bottomExit[index] = destRoom.uid;
                    break;
                case LEFT:
                    leftExit[index] = destRoom.uid;
                    break;
            }
        } catch ( ArrayIndexOutOfBoundsException ex ) {
            throw new RoomDimensionException(this, e, index, destRoom);
        }
     }

    public final void load(DataInputStream dis) throws IOException {
        // <ROOM>
        if ( !dis.readUTF().equals("<ROOM>") ) {
            throw new IOException("Expected <ROOM> mnemonic in file!");
        }
        uid = dis.readLong();   // UID
        //Check that UID is not already used.
        if ( uid == 0 ) {
            throw new IOException("UID cannot be zero!");
        }
        // UID must be unique.
        for( Room r : parent.getRooms() ) {
            if ( r.uid == uid ) {
                throw new IOException("UID " + uid + " is not unique!");
            }
        }
        
        
        displayName = dis.readUTF(); // Display Name
        
        LOGGER.log(Level.FINER, "Loading Room: {0}", displayName);
        
        width  = dis.readInt(); // Width
        height = dis.readInt(); // Height
        // Top Exit
        topExit = new long[dis.readInt()];
        for ( int i=0; i<topExit.length; i++) {
            topExit[i] = dis.readLong();
        }
        // Right Exit
        // Bottom Exit
        // Left Exit
        
        dis.readUTF();  // <ROOM_>
        
    }

    public final void save(DataOutputStream dos) throws IOException {
        dos.writeUTF("<ROOM>");
        dos.writeLong(uid);  // UID
        dos.writeUTF(displayName); // Display Name
        
        dos.writeInt(width);
        dos.writeInt(height);
        
        // Top Exit
        dos.writeInt(topExit.length);
        for(int i = 0; i < topExit.length; i++) {
            dos.writeLong(topExit[i]);
        }
        
        dos.writeUTF("<ROOM_>");
    }

}
