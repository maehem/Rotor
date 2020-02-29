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
import com.maehem.rotor.engine.game.Player;
import com.maehem.rotor.engine.data.PlayerState;
import com.maehem.rotor.engine.data.Point;
import com.maehem.rotor.engine.game.World;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class PlayerNode extends EntityNode implements DataListener, GameListener {

    private final static Logger LOGGER = Logger.getLogger(PlayerNode.class.getName());

    protected final Player player;
    private final FlashLightLayer flashlight;
    private final World world;

    public PlayerNode(Player player, World world) throws IOException {
        //super(player, (WalkSheet) player.getWalkSheet(), World.getInstance().getTileSize() /*player.getParent().getTileSize()*/);
        super(player, world.getClassLoader(), world.getTileSize(), new Point(world.getScreenWidth(), world.getScreenHeight()));

        this.player = player;
        this.world = world;
        
        flashlight = new FlashLightLayer(this);
        player.getState().addDataChangeListener(PlayerState.PROP_NIGHT_VISION, this);
        
        getChildren().add(flashlight);

        // done at super() level.
        //updateLayout(player.getState().getPosition());

        player.getState().addDataChangeListener(PlayerState.PROP_POSITION, this);

        LOGGER.finer("Entity is Player");
    }

    @Override
    public void dataChange(String key, Object source, Object oldValue, Object newValue) {
        super.dataChange(key, source, oldValue, newValue);

        switch (key) {
            case PlayerState.PROP_POSITION:
                Point oPos = (Point) oldValue;
                Point pos = (Point) newValue;

                if (oPos.x < pos.x) {
                    getFlashlight().setAngle(0.0);
                } else if (oPos.x > pos.x) {
                    getFlashlight().setAngle(180.0);
                }
                if (oPos.y < pos.y) {
                    getFlashlight().setAngle(90.0);
                } else if (oPos.y > pos.y) {
                    getFlashlight().setAngle(270.0);
                }
                updateLayout((Point) newValue);
                break;
            case PlayerState.PROP_NIGHT_VISION:
                flashlight.setDarkness(1.0 - (double)newValue);
                break;
        }
    }

    /**
     * @return the flashlight
     */
    public FlashLightLayer getFlashlight() {
        return flashlight;
    }

    @Override
    public void gameEvent(GameEvent e) {
        super.gameEvent(e);

//        switch ( e.type ) {
//            case TICK:
//                if ( player.isSwordAttack()) {
//                    swordSwish.step();
//                    if ( swordSwish.isDone() ) {
//                        player.swordAttack(false); // Consume request
//                    }
//                }
//        }
    }

    World getWorld() {
        return world;
    }

}
