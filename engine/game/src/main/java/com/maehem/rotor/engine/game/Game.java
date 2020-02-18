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

import com.maehem.rotor.engine.data.World;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 *
 * @author maehem
 */
public class Game {
    private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

    private final String gameName;
    private World world = null;
    //private Player player = null;
    private boolean initialized = false;
    private boolean running = false;  // Running==true or Paused==false

    private int subTicks = 0;
    
    public CopyOnWriteArrayList<GameListener> listeners = new CopyOnWriteArrayList<>();

    public Game(String gameName) {
        this.gameName = gameName;
    }

    private void init() {
        LOGGER.config("Game Initialization...");
        FileSystem.init(gameName);
        world = World.getInstance();
        //this.player = new Player();
        
        initialized = true;
        doNotify(GameEvent.TYPE.GAME_INIT);
     }

    /**
     * Called from GameWindow after graphics have been initiated.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public final void initFromFile() throws FileNotFoundException, IOException {
        init();
        // TODO: Have FileSystem return a DataInputStream for the world map.
        File worldMap = new File(FileSystem.getInstance().getGameDir(), "WorldMap.map");
        DataInputStream dis = new DataInputStream(new FileInputStream(worldMap));

        getWorld().load(dis);
    }

    public void initNewGame() {
        init();
        getWorld().initState();
        
        getWorld().getPlayer().getState().setHealth(66);
        getWorld().getPlayer().getState().setMana(74);
    }

    /**
     * Game state is computed here. Called by Graphics at each frame update.
     */
    public void tick() {
        if (!initialized || !running) {
            return;
        }

        //doTick();

        subTicks++;
        if (subTicks > 511) {
            subTicks = 0;
        }
        
        
        if (subTicks % 8 == 0) {
            doTick();
        }

//        switch (data.mapInfo.speed) {
//            case SLOW:
//                if (subTicks % 36 == 0) {
//                    doTick();
//                }
//                break;
//            case MEDIUM:
//                if (subTicks % 22 == 0) {
//                    doTick();
//                }
//                break;
//            case FAST:
//                if (subTicks % 9 == 0) {
//                    doTick();
//                }
//                break;
//            default:
//            case PLAID:
//                doTick();
//                break;
//
//        }
    }

    private void doTick() {
        //LOGGER.finer("Game.doTick()");
        getWorld().getState().tick();
        doNotify(GameEvent.TYPE.TICK);
    }

    public void addListener(GameListener l) {
        listeners.add(l);
    }

    public void removeListener(GameListener l) {
        listeners.remove(l);
    }

    public void doNotify(GameEvent.TYPE type) {
        listeners.forEach((l) -> {
            l.gameEvent(new GameEvent(this, type));
        });
    }

    public void setRunning(boolean running) {
        this.running = running;
        if (running) {
            LOGGER.info("Game Running");
            doNotify(GameEvent.TYPE.RUNNING);
        } else {
            LOGGER.info("Game Paused");
            doNotify(GameEvent.TYPE.PAUSED);
        }
    }

    public boolean isRunning() {
        return running;
    }

    /**
     * @return the world data object
     */
    public World getWorld() {
        return world;
    }

}
