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
package com.maehem.rotor.ui;

import com.maehem.rotor.engine.game.Game;
import com.maehem.rotor.ui.controls.DialogLayer;
import com.maehem.rotor.ui.debug.DebugChangeSupport;
import com.maehem.rotor.ui.hud.HUD;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author maehem
 */
public class UserInterfaceLayer extends Group implements DialogLayer {

    private final static Logger LOGGER = Logger.getLogger(UserInterfaceLayer.class.getName());

    public enum DebugProp {
        SHOW_UI_PANE_BORDERS
    };

    public static final int GLYPH_HEIGHT = 24;
    
    private Node currentDialog;
    private final DebugChangeSupport changes = new DebugChangeSupport();

    private boolean showUIPaneBorders = false; // Draw debugging border around UI pane elements.

    public UserInterfaceLayer(Game game) {
        LOGGER.config("User Interface Layer Initialization.");
        HUD hud = new HUD(game, changes);
        hud.setTranslateX(30);

        getChildren().addAll(hud);
    }

    @Override
    public void presentDialog(Node dialog) {
        if (currentDialog != null) {
            getChildren().remove(currentDialog);
            currentDialog = null;
        }
        currentDialog = dialog;
        getChildren().add(dialog);
    }

    @Override
    public void destroyDialog(Node dialog) {
        getChildren().remove(dialog);
    }

    /**
     * @return true if debug pane borders are showing
     */
    public boolean showUIPaneBorders() {
        return showUIPaneBorders;
    }

    /**
     * @param show the debug pane borders
     */
    public void setShowUIPaneBorders(boolean show) {
        boolean oldValue = this.showUIPaneBorders;
        this.showUIPaneBorders = show;
        changes.fireDebugChange(DebugProp.SHOW_UI_PANE_BORDERS, oldValue, show);
    }

    public void populateDebugToggles(FlowPane togglesPane) {
        LOGGER.fine("UI Layer Populate Debug Toggles.");
        // Create and place debug toggles here.
        ToggleButton paneBorders = new ToggleButton("", createGlyph("/glyphs/debug/xy-visible.png"));
        paneBorders.setTooltip(new Tooltip("Show Pane Borders"));
        paneBorders.setSelected(showUIPaneBorders());
        paneBorders.selectedProperty().addListener((ov, prev, current) -> {
            setShowUIPaneBorders(current);
        });
        togglesPane.getChildren().add(paneBorders);

    }

    public static StackPane createGlyph(String path) {
        try {
            URL glyphResource = UserInterfaceLayer.class.getResource(path);
            ImageView glyphImage = new ImageView(new Image(glyphResource.openStream()));
            glyphImage.setPreserveRatio(true);
            glyphImage.setFitHeight(GLYPH_HEIGHT);
            return new StackPane(glyphImage);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return new StackPane();
        }
    }
}
