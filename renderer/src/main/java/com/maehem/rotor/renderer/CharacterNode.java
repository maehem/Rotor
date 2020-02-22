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

import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import java.util.logging.Logger;
import javafx.scene.Group;

/**
 *
 * @author maehem
 */
public class CharacterNode extends Group implements GameListener {
    private final static Logger LOGGER = Logger.getLogger(CharacterNode.class.getName());

    private final WalkSheet graphic;
    private final ImageSequence swordSwish;
    
    public CharacterNode(WalkSheet walkSheet, double size ) {
        this.graphic = walkSheet;
        getChildren().add(graphic);
        
        swordSwish = new ImageSequence(PlayerNode.class.getResourceAsStream("/renderer/glyphs/sword-swish.png"), 32, ImageSequence.Type.ONE_SHOT);
        swordSwish.setHideWhenDone(true);
        getChildren().add(swordSwish);
    }
    
    public WalkSheet getWalkSheet() {
        return graphic;
    }
        
    @Override
    public void gameEvent(GameEvent e) {
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

    void attackWithSword() {
//        if ( player.isSwordAttack() ) return; // Ignore new attacks while swinging.
//        player.swordAttack(true);
//        swordSwish.reset();
    }
}
