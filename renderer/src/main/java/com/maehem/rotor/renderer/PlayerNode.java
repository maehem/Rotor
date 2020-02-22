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
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import java.util.logging.Logger;
import javafx.geometry.Bounds;

/**
 *
 * @author maehem
 */
public class PlayerNode extends CharacterNode implements DataListener, GameListener  {

    private final static Logger LOGGER = Logger.getLogger(PlayerNode.class.getName());

    protected final Player player;
    private final FlashLightLayer flashlight;
    private final ImageSequence swordSwish;

    public PlayerNode(Player player) {
        super((WalkSheet) player.getWalkSheet(), player.getParent().getTileSize());

        this.player = player;
        flashlight = new FlashLightLayer(this);
        
        getChildren().add(flashlight);
        
        swordSwish = new ImageSequence(PlayerNode.class.getResourceAsStream("/renderer/glyphs/sword-swish.png"), 32, ImageSequence.Type.ONE_SHOT);
        swordSwish.setHideWhenDone(true);
        getChildren().add(swordSwish);
        
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
                    getFlashlight().setAngle(0.0);
                } else if (oPos.x > pos.x) {
                    getWalkSheet().setDir(WalkSheet.DIR.LEFT);
                    getFlashlight().setAngle(180.0);
                }
                if (oPos.y < pos.y) {
                    getWalkSheet().setDir(WalkSheet.DIR.TOWARD);
                    getFlashlight().setAngle(90.0);
                } else if (oPos.y > pos.y) {
                    getWalkSheet().setDir(WalkSheet.DIR.AWAY);
                    getFlashlight().setAngle(270.0);
                }
                getWalkSheet().step();                
                updateLayout((Point) newValue);
                break;
        }
    }

    public Bounds getCollisionBox() {
        return getWalkSheet().getBoundsInParent();
    }
    
    public final void updateLayout(Point pos) {
        setLayoutX(pos.x * player.getParent().getScreenWidth());
        setLayoutY(pos.y * player.getParent().getScreenHeight());

    }

    /**
     * @return the flashlight
     */
    public FlashLightLayer getFlashlight() {
        return flashlight;
    }

    @Override
    public void gameEvent(GameEvent e) {
        switch ( e.type ) {
            case TICK:
                if ( player.isSwordAttack()) {
                    swordSwish.step();
                    if ( swordSwish.isDone() ) {
                        player.swordAttack(false); // Consume request
                    }
                }
        }
    }

    void attackWithSword() {
        if ( player.isSwordAttack() ) return; // Ignore new attacks while swinging.
        player.swordAttack(true);
        swordSwish.reset();
    }

}
