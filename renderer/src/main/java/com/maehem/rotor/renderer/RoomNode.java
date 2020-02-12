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
public class RoomNode extends Group implements GameListener {

    private static final Logger LOGGER = Logger.getLogger(RoomNode.class.getName());

    private final Room room;
    private PlayerNode playerNode;

    private final ArrayList<TileNode> tileNodes = new ArrayList<>();

    public RoomNode(Room room) {
        this.room = room;
    }

    public void enter(Game game, PlayerNode playerNode) {

        this.playerNode = playerNode;

        constructRoom(game);

        // TODO:  Insert playerNode when this becomes the active room.
        getChildren().add(playerNode);

        // TODO: Listen to the game when room becomes active node.
        game.addListener(this);
    }

    public void leave() {
        getChildren().removeAll();

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
    }

    @Override
    public void gameEvent(GameEvent e) {
        switch (e.type) {
            case TICK:
                //LOGGER.finest("tick");
                //playerNode.getWalkSheet().step();
                break;
        }
    }

    /**
     * @return the room
     */
    public final Room getRoom() {
        return room;
    }

}
