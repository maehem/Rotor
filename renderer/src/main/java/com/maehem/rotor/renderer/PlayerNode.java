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
import com.maehem.rotor.engine.data.Player;
import com.maehem.rotor.engine.data.PlayerState;
import com.maehem.rotor.engine.data.Point;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class PlayerNode extends CharacterNode implements DataListener {

    private final static Logger LOGGER = Logger.getLogger(PlayerNode.class.getName());

    Player player;

    public PlayerNode(Player player) throws IOException {
        super("/characters/person-1.png", player.getParent().getTileSize());

        this.player = player;
        updateLayout(player.getState().getPosition());
        
        player.getState().addDataChangeListener(PlayerState.PROP_POSITION, this);

        LOGGER.finer("Create Player Node.");
    }

    @Override
    public void dataChange(String key, Object oldValue, Object newValue) {
        switch (key) {
            case PlayerState.PROP_POSITION:
                Point oPos = (Point) oldValue;
                Point pos = (Point) newValue;

                if (oPos.x == pos.x && oPos.y == pos.y) {
                    getWalkSheet().stand();
                    return;
                }

                if (oPos.x < pos.x) {
                    getWalkSheet().setDir(WalkSheet.DIR.RIGHT);
                } else if (oPos.x > pos.x) {
                    getWalkSheet().setDir(WalkSheet.DIR.LEFT);
                }
                if (oPos.y < pos.y) {
                    getWalkSheet().setDir(WalkSheet.DIR.TOWARD);
                } else if (oPos.y > pos.y) {
                    getWalkSheet().setDir(WalkSheet.DIR.AWAY);
                }

                getWalkSheet().step();
                updateLayout(pos);
        }
    }

    private void updateLayout(Point pos) {
        setLayoutX(pos.x * player.getParent().getScreenWidth());
        setLayoutY(pos.y * player.getParent().getScreenHeight());

    }

}
