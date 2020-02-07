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
package com.maehem.rotor.dungeoneer;

import com.maehem.rotor.dungeoneer.realms.dungeon1.Dungeon1Realm;
import com.maehem.rotor.engine.data.PlayerState;
import com.maehem.rotor.engine.data.World;
import com.maehem.rotor.engine.game.Game;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import com.maehem.rotor.engine.logging.LoggingFormatter;
import com.maehem.rotor.engine.logging.LoggingHandler;
import com.maehem.rotor.engine.logging.LoggingMessageList;
import com.maehem.rotor.renderer.GameScene;
import com.maehem.rotor.renderer.Graphics;
import com.maehem.rotor.renderer.PlayerNode;
import com.maehem.rotor.renderer.RoomNode;
import com.maehem.rotor.ui.UserInterfaceLayer;
import com.maehem.rotor.ui.DebugWindow;
import com.maehem.rotor.ui.MainMenu;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author maehem
 */
public class GameWindow extends Application implements GameListener {

    private static final Logger LOGGER = Logger.getLogger(GameWindow.class.getName());

    // Note: The Window title is derived from the MessageBundle gameTitle
    // GAME_NAME is used for setting the folder name in the file system.
    public static final String GAME_NAME = "Dungeoneer";
    public static final String VERSION = "0.0.0";
    public static final int SCREEN_WIDTH = 640;
    public static final int SCREEN_HEIGHT = 360;

    // Developers:   Use this format for exceptions printing in the log.
    // LOGGER.log( Level.SEVERE, ex.toString(), ex );
    private Graphics gfx;
    //private Scene scene;
    GameScene gameScene;
    
    private final Game game = new Game(GAME_NAME);
    private DebugWindow debugWindow;

    private final LoggingMessageList messageLog = new LoggingMessageList();
    private final LoggingHandler loggingHandler = new LoggingHandler(messageLog);

    private final ResourceBundle messages;
    private MainMenu mainMenu;
    private Group roomLayer = new Group();

    public GameWindow() {
        super();

        // For locale testing.
        //Locale.setDefault(Locale.GERMANY);
        loggingHandler.setFormatter(new LoggingFormatter());
        messages = ResourceBundle.getBundle("MessageBundle");

        // TODO:  Maybe move this initialization into the Logging module?
        // Get the top most logger and add our handler.
        Logger.getLogger("com.maehem.rotor").setUseParentHandlers(false);  // Prevent INFO and HIGHER from going to stderr.
        Logger.getLogger("com.maehem.rotor").addHandler(loggingHandler);
        Logger.getLogger("com.maehem.rotor").setLevel(Level.FINEST);

        LOGGER.log(Level.INFO, "{0} version: {1}", new Object[]{messages.getString("gameTitle"), VERSION});
        LOGGER.log(Level.CONFIG, "OS Name: {0} {1}",
                new Object[]{
                    System.getProperty("os.name"),
                    System.getProperty("os.version")
                });
        LOGGER.log(Level.CONFIG, "   Java: {0}", System.getProperty("java.version"));
        LOGGER.log(Level.CONFIG, " JavaFX: {0}", System.getProperty("javafx.version"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        LOGGER.info(messages.getString("gameWindowStartMessage"));

        stage.setTitle(messages.getString("gameTitle") + " " + VERSION);
        
        // TODO:  Scene and Root need to move into the renderer.
        //Group root = new Group();
        //scene = new Scene(root);
        
        gameScene = new GameScene();
        
        //stage.setScene(scene);
        stage.setScene(gameScene);

        gfx = new Graphics(game);

        // Get the Debug window displayed as soon as possible.
        debugWindow = new DebugWindow(messageLog, gfx.debug, loggingHandler);
        stage.setOnCloseRequest((t) -> {
            debugWindow.close();
        });

        // TODO: Canvas goes away.  Use scene graph instead
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        //root.getChildren().add(canvas);
        gameScene.add(canvas);
        
        gfx.setCanvas(canvas);
        
        
        gfx.init();

        //initLayers(root);
        initLayers(gameScene);
//        initGameLoop();

        // Listen to the game loop.
        game.addListener(this);
        gfx.ui.addListener(gameScene);

        stage.show();

        debugWindow.reloadLog();

        //initWorld(game);
        stage.setHeight(canvas.getHeight() + stage.getHeight() - stage.getScene().getHeight());
        stage.setWidth(canvas.getWidth());
        LOGGER.log(Level.FINEST, "Window Size: {0}x{1}", new Object[]{stage.getWidth(), stage.getHeight()});
        mainMenu.show();

    }

    //private void initLayers(Group root) {
    private void initLayers(GameScene root) {
        LOGGER.config("Layers Initialization.");
        // GUI Controls and Debug Tab
        UserInterfaceLayer uiLayer = new UserInterfaceLayer(game);

        // Add debug toggles for the UI overlay layer.
        uiLayer.populateDebugToggles(debugWindow.getTogglesPane());

        mainMenu = new MainMenu(gfx);
        
        root.addAll(roomLayer, uiLayer, mainMenu);
    }

    private void initGameLoop() {
        LOGGER.config("Game Loop Init.");
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(gfx.getTickRate()), (ActionEvent ae) -> {
            gameScene.tick(game);
            gfx.tick();
        });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

        game.setRunning(true);
    }

    @Override
    public void gameEvent(GameEvent e) {
        switch (e.type) {
            case GAME_INIT:
                // Someone selected the "New Game" option.
                initWorld(game);
                break;
            case DATA_LOADED:
                initGameLoop();
                try {
                    PlayerNode playerNode = new PlayerNode(e.getSource().getPlayer());
                    roomLayer.getChildren().add(new RoomNode(game, game.getWorld().getRealms().get(0).getRooms().get(0), playerNode));
                } catch (IOException ex) {
                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;

        }
    }

    /**
     * TODO: Move this to it's own class in the game content sub-dir.
     *
     * @param game
     */
    private void initWorld(Game game) {
        World w = game.getWorld();

        w.setDisplayName("Realms of Helios");

        // Add the realms
        w.getRealms().add(new Dungeon1Realm(w));

        w.setLoaded(true);
    }

    public static void main(String[] args) {
        launch();
    }
}
