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

import com.maehem.rotor.engine.data.RoomState;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    private final ArrayList<PortKey> doors = new ArrayList<>();
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();

    public static final int MAX_WIDTH = 8;
    public static final int MAX_HEIGHT = 8;

    public int nTilesX;
    public int nTilesY;
    private Tile[][] tiles;
    
    private Object assetSheetObject;

    public long uid = Math.round(Math.random());

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

        World w = parent.getParent();
        this.nTilesX = w.getScreenWidth() / w.getTileSize() * width;
        this.nTilesY = w.getScreenHeight() / w.getTileSize() * height;

        tiles = new Tile[nTilesX][nTilesY];

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
    public final void setUid(long uid) {
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

    public final void setAssetSheet(Object sheet) {
        this.assetSheetObject = sheet;
    }

    public Object getAssetSheet() {
        return assetSheetObject;
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

    public boolean isBlocked(double x, double y) {
        int blockX = (int) (x / width * nTilesX + 0.5);
        int blockY = (int) (y / height * nTilesY + 1);

        Tile tile = get(blockX, blockY);
        if (tile == null) {
            LOGGER.log(Level.WARNING, "Player walked off the map!  Fix the map at {0},{1}", new Object[]{blockX, blockY});
            return true;
        }
        return tile.isBlock();
    }

    /**
     *
     * @return room state object. @null if not visited.
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

//    public final void setDoor(Edge e, int index, long destRoomUID) throws RoomDimensionException {
//
//        try {
//            switch (e) {
//                case TOP:
//                    topExit[index] = destRoomUID;
//                    break;
//                case RIGHT:
//                    rightExit[index] = destRoomUID;
//                    break;
//                case BOTTOM:
//                    bottomExit[index] = destRoomUID;
//                    break;
//                case LEFT:
//                    leftExit[index] = destRoomUID;
//                    break;
//            }
//        } catch (ArrayIndexOutOfBoundsException ex) {
//            throw new RoomDimensionException(this, e, index, destRoomUID);
//        }
//    }

//    public long getDoor(Edge edge, int index) {
//        switch (edge) {
//            case TOP:
//                return topExit[index];
//            case RIGHT:
//                return rightExit[index];
//            case BOTTOM:
//                return bottomExit[index];
//            case LEFT:
//                return leftExit[index];
//        }
//        return 0;
//    }
//    
    /**
     * @return the doors
     */
    public ArrayList<PortKey> getDoors() {
        return doors;
    }


    public final void addDoor( PortKey d ) {
        doors.add(d);
    }

    public final Tile get(int x, int y) {
        try {
            return tiles[x][y];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    public final void put(int x, int y, Tile tile) {
        // TODO need some range checking?
        tiles[x][y] = tile;
    }

    public final void addEntity( Entity ent ) {
        entities.add(ent);
    }
    
    /**
     * @return the entities
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * 
     * @param item to add
     */
    public final void addItem( Item item ) {
        items.add(item);
    }
    
    /**
     * @return the items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    
//    public Point getEntryPos(Edge e, int index) {
//        switch (e) {
//            case TOP:
//                return new Point(index + 0.5, 0.1);
//            case RIGHT:
//                return new Point(getWidth() - 0.1, index + 0.5);
//            case BOTTOM:
//                return new Point(index + 0.5, getHeight() - 0.1);
//            case LEFT:
//                return new Point(0.1, index + 0.5);
//            default:
//                return null;
//        }
//    }
//
    public final void load(DataInputStream dis) throws IOException {
        // <ROOM>
        if (!dis.readUTF().equals("<ROOM>")) {
            throw new IOException("Expected <ROOM> mnemonic in file!");
        }
        uid = dis.readLong();   // UID
        //Check that UID is not already used.
        if (uid == 0) {
            throw new IOException("UID cannot be zero!");
        }
        // UID must be unique.
        for (Room r : parent.getRooms()) {
            if (r.uid == uid) {
                throw new IOException("UID " + uid + " is not unique!");
            }
        }

        displayName = dis.readUTF(); // Display Name

        LOGGER.log(Level.FINER, "Loading Room: {0}", displayName);

        width = dis.readInt(); // Width
        height = dis.readInt(); // Height
        // Top Exit
        topExit = new long[dis.readInt()];
        for (int i = 0; i < topExit.length; i++) {
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
        for (int i = 0; i < topExit.length; i++) {
            dos.writeLong(topExit[i]);
        }

        dos.writeUTF("<ROOM_>");
    }

}
