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
import java.util.logging.Logger;
import javafx.scene.Group;

/**
 *
 * @author maehem
 */
public class RoomNode extends Group implements GameListener {
    private static final Logger LOGGER = Logger.getLogger(RoomNode.class.getName());

    private final Room room;
    private final PlayerNode playerNode;

    public RoomNode( Game game, Room room, PlayerNode playerNode ) {
        this.room = room;
        this.playerNode = playerNode;
        
        constructRoom();
        
        // TODO:  Insert playerNode when this becomes the active room.
        getChildren().add(playerNode);
        
        // TODO: Listen to the game when room becomes active node.
        game.addListener(this);
    }

    private void constructRoom() {
        // Place all tiles in the room.
        
    }

    @Override
    public void gameEvent(GameEvent e) {
        switch( e.type ) {
            case TICK:
                LOGGER.finest("tick");
                //playerNode.getWalkSheet().step();
                break;
        }
    }
    
}
