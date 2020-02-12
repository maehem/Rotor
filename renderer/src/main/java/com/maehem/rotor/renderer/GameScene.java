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

import com.maehem.rotor.engine.data.Player;
import com.maehem.rotor.engine.game.Game;
import com.maehem.rotor.renderer.ui.UIEvent;
import com.maehem.rotor.renderer.ui.UIListener;
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
public class GameScene extends Scene implements UIListener {
    private static final Logger LOGGER = Logger.getLogger(GameScene.class.getName());

    // TODO Move this into Player
    public boolean running, goNorth, goSouth, goEast, goWest;
    
//    public final static double WALK = 0.01;
//    public final static double RUN  = WALK * 3.0;

    //Group root = new Group();
    public GameScene() {
        super(new Group());

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

    public double getTickRate() {
        return 0.066; // 66mS == 15FPS
    }
    
    public void initGameLoop( Game game ) {
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

}
