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
import com.maehem.rotor.engine.data.World;
import com.maehem.rotor.engine.game.Game;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import com.maehem.rotor.renderer.ui.UIEvent;
import com.maehem.rotor.renderer.ui.UIListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

/**
 *
 * @author mark
 */
public class GameScene extends Scene implements GameListener, UIListener, DataListener {

    private static final Logger LOGGER = Logger.getLogger(GameScene.class.getName());

    private final Group roomLayer = new Group();
    private final Group scrimLayer = new Group();

    //private Node mainMenu;
    private RoomNode currentRoomNode;
    private WorldNode worldNode;

    private SceneFader scrim;

    // TODO Move this into Player
    public boolean running, goNorth, goSouth, goEast, goWest;
    private final Game game;

    public GameScene(Game game) {
        super(new Group());
        this.game = game;
        game.addListener(this);

        initKeyEvents();
    }

    public boolean add(Node n) {
        return ((Group) getRoot()).getChildren().add(n);
    }

    public boolean addAll(Node... n) {
        return ((Group) getRoot()).getChildren().addAll(n);
    }

    /**
     * UI of the game can request cursor changes here.
     *
     * @param e Event to be handled.
     */
    @Override
    public void uiEvent(UIEvent e) {
        if (e.type == UIEvent.Type.CURSOR_CHANGE) {
            setCursor(new ImageCursor((Image) e.objects[0]));
        }
    }

    public void initLayers(Node menuLayer, Node hudLayer) {
        LOGGER.config("Layers Initialization.");
        addAll(roomLayer, hudLayer, scrimLayer, menuLayer);
    }

    private void initKeyEvents() {
        setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP:
                    goNorth = true;
                    break;
                case DOWN:
                    goSouth = true;
                    break;
                case LEFT:
                    goWest = true;
                    break;
                case RIGHT:
                    goEast = true;
                    break;
                case SHIFT:
                    running = true;
                    break;
            }
        });
        setOnKeyReleased((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP:
                    goNorth = false;
                    break;
                case DOWN:
                    goSouth = false;
                    break;
                case LEFT:
                    goWest = false;
                    break;
                case RIGHT:
                    goEast = false;
                    break;
                case SHIFT:
                    running = false;
                    break;
            }
        });
    }

    public void tick(Game game) {
        if (game.isRunning()) {

            game.tick();  // Game logic state update.

            // TODO: Move this into Player
            double dx = 0, dy = 0;

            if (goNorth) {
                dy -= Player.WALK_SPEED;
            }
            if (goSouth) {
                dy += Player.WALK_SPEED;
            }
            if (goEast) {
                dx += Player.WALK_SPEED;
            }
            if (goWest) {
                dx -= Player.WALK_SPEED;
            }
            if (running) {
                dx *= Player.RUN_MULT;
                dy *= Player.RUN_MULT;
            }

            game.getWorld().getPlayer().moveBy(dx, dy);
        } 
        else {
            if (scrim.isFading()) {
                scrim.update();
            }
//            else {
//                game.setRunning(true);
//            }
        }
    }

    public double getTickRate() {
        return 0.066; // 66mS == 15FPS
    }

    public void initGameLoop(Game game) {
        LOGGER.config("Game Loop Init.");
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(getTickRate()), (ActionEvent ae) -> {
            tick(game);
        });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

        game.setRunning(true);
    }

    @Override
    public void dataChange(String key, Object oldValue, Object newValue) {
        switch (key) {
            case World.PROP_ROOM:
                LOGGER.log(Level.CONFIG, "Game Scene: Change Room to: {0}", newValue);

                if (currentRoomNode != null) {
                    roomLayer.getChildren().remove(currentRoomNode);
                }
                currentRoomNode = worldNode.getCurrentRealmNode().getCurrentRoomNode();
                currentRoomNode.enter(game, worldNode.getPlayerNode());
                roomLayer.getChildren().add(currentRoomNode);
                scrim.initClosing(worldNode.getPlayerNode().getLayoutX(), worldNode.getPlayerNode().getLayoutY());
                scrim.startFade(); // Game loop handles animation updates.
                scrim.setOnFadeInFinished((data_type) -> {
                    game.setRunning(true);
                });
                break;
            case PlayerState.STATE_LEAVE:
                LOGGER.log(Level.CONFIG, "Game Scene: Player left room. New destination: {0}", newValue);
                // Pause game
                game.setRunning(false);
                // Run transistion
                scrim.initOpening(worldNode.getPlayerNode().getLayoutX(), worldNode.getPlayerNode().getLayoutY());
                scrim.startFade(); // Game loop handles animation updates.
                scrim.setOnFadeOutFinished((data_type) -> {
                    // Unload old room.
                    worldNode.getCurrentRealmNode().getCurrentRoomNode().leave();
                    World.getInstance().setCurrentRoom((long) newValue);

                });

            // Run Transistion
            // Un-pause game.
        }
    }

    @Override
    public void gameEvent(GameEvent e) {
        switch (e.type) {
            case DATA_LOADED:
                this.worldNode = new WorldNode(game.getWorld());
                worldNode.getPlayerNode().player.getState().addDataChangeListener(PlayerState.STATE_LEAVE, this);
                scrim = new SceneFader(getWidth(), getHeight());
                scrimLayer.getChildren().add(scrim);

                LOGGER.log(Level.CONFIG, "Add SceneFader of size: {0}x{1}", new Object[]{getWidth(), getHeight()});

                game.getWorld().addDataChangeListener(World.PROP_ROOM, this);

//                PlayerNode playerNode = new PlayerNode(e.getSource().getWorld().getPlayer());
//                
//                // TODO Move this off somewhere so we can call it when switching rooms.
//                //worldNode = new WorldNode(e.getSource().getWorld());
//                
//                currentRoomNode = worldNode.getCurrentRealmNode().getCurrentRoomNode();
//                currentRoomNode.enter(e.getSource(), playerNode);
//                roomLayer.getChildren().add(currentRoomNode);
                break;
        }
    }

}
