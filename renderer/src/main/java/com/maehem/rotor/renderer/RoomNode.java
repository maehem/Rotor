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
package com.maehem.rotor.renderer;

import com.maehem.rotor.engine.data.DataListener;
import com.maehem.rotor.engine.data.PortKey;
import com.maehem.rotor.engine.data.PlayerState;
import com.maehem.rotor.engine.data.Point;
import com.maehem.rotor.engine.data.Room;
import com.maehem.rotor.engine.game.Game;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Group;

/**
 *
 * @author maehem
 */
public class RoomNode extends Group implements GameListener, DataListener {

    private static final Logger LOGGER = Logger.getLogger(RoomNode.class.getName());

    private final Room room;
    private PlayerNode playerNode;
    private final ArrayList<DoorNode> doorNodes = new ArrayList<>();
    

    public RoomNode(Room room) {
        this.room = room;
        
    }

    public void enter(Game game, PlayerNode playerNode) {

        this.playerNode = playerNode;

        constructRoom(game);

        getChildren().add(playerNode);
        if (playerNode.player.hasPortKey()) {
            playerNode.player.gotoPortKey();
            playerNode.updateLayout(playerNode.player.getState().getPosition());
        }

        //blackness.toFront();
        
        playerNode.player.getState().addDataChangeListener(PlayerState.PROP_POSITION, this);

        game.addListener(this);
    }

    public void leave() {

        getChildren().removeAll();
        doorNodes.clear();

        this.playerNode = null;
    }

    private void constructRoom(Game game) {
        LOGGER.log(Level.FINE, "Construct Room {0}:{1}", new Object[]{room.uid, room.getDisplayName()});
        int tileSize = room.parent.getParent().getTileSize();
        LOGGER.log(Level.CONFIG, "Tile Size is: {0}", tileSize);
        for (int y = 0; y < room.nTilesY; y++) {
            for (int x = 0; x < room.nTilesX; x++) {
                TileNode tn = new TileNode(room.get(x, y));
                if (tn.create((AssetSheet) room.getAssetSheet())) {
                    tn.setLayoutX(x * tileSize);
                    tn.setLayoutY(y * tileSize);
                    getChildren().add(tn);
                }
            }
        }

        // Doors
        for (PortKey d : room.getDoors()) {
            //LOGGER.config("Add port key rectangle for: " + d.destRoomUID);
            DoorNode dn = new DoorNode(d, room.parent.getParent());
            getChildren().add(dn);
            doorNodes.add(dn);
        }

        //getChildren().add(blackness);
        
        LOGGER.log(Level.FINE, "Room Size is: {0}x{1}", new Object[]{getBoundsInLocal().getWidth(), getBoundsInLocal().getHeight()});
    }

    @Override
    public void gameEvent(GameEvent e) {
        switch (e.type) {
            case TICK:
                //LOGGER.finest("tick");
                break;
        }
    }

    /**
     * @return the room
     */
    public final Room getRoom() {
        return room;
    }

    @Override
    public void dataChange(String key, Object oldValue, Object newValue) {
        switch (key) {
            case PlayerState.PROP_POSITION:
                Point oPos = (Point) oldValue;
                Point pos = (Point) newValue;

                // Camera panning
                // If room size > 1 && player.pos > 0.5 of screen width
                // then adjust LayoutX to bring player to 0.5
                if (room.getWidth() > 1) {  // Only need to pan if the room is wider than screen.
                    if (pos.x > 0.5 && pos.x < room.getWidth() - 0.5) {
                        setTranslateX(-(pos.x - 0.5) * getBoundsInLocal().getWidth() / room.getWidth());
                    }
                }
                if (room.getHeight() > 1) {  // Only need to pan if the room is wider than screen.
                    if (pos.y > 0.5 && pos.y < room.getHeight() - 0.5) {
                        setTranslateY(-(pos.y - 0.5) * getBoundsInLocal().getHeight() / room.getHeight());
                    }
                }

                for (DoorNode dn : doorNodes) {
                    if (playerNode.getBoundsInParent().intersects(dn.getBoundsInParent())) {
                        LOGGER.log(Level.CONFIG, "Player contacted door to room: {0}", dn.door.destRoomUID);
                        playerNode.player.getState().removeDataChangeListener(PlayerState.PROP_POSITION, this);
                        playerNode.player.setPortKey(dn.door); // Triggers room transition fade.
                        return;
                    }
                }
                

                break;
        }
    }
    
}
