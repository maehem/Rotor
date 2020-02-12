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

import com.maehem.rotor.engine.logging.LoggingHandler;
import com.maehem.rotor.engine.logging.LoggingMessageList;
import com.maehem.rotor.ui.controls.DebugPanelGroup;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author mark
 */
public class DebugWindow extends Stage {
    private static final Logger LOGGER = Logger.getLogger(DebugWindow.class.getName());

    DebugPanelGroup debugPanel;
    
    public DebugWindow(LoggingMessageList messageLog, /*Debug gfxDebug, */ LoggingHandler loggingHandler) {
        this.debugPanel = new DebugPanelGroup(messageLog/*, gfxDebug*/);
        debugPanel.setFormatter(loggingHandler.getFormatter());

        setTitle("Debug Window");
        setScene(new Scene(debugPanel));
        setOnCloseRequest((t) -> {
            t.consume();
            LOGGER.config("User tried to close debug window.");
        });
        setResizable(false);
        show();

        setWidth(debugPanel.getBoundsInParent().getWidth());
        setHeight(debugPanel.getBoundsInParent().getHeight() + 6 - (getHeight() - getScene().getHeight()));

        // Place the debug window at the bottom of the screen
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        setY(screen.getHeight() - getHeight());
    }

    public void reloadLog() {
        debugPanel.reloadDebugLog();
    }

    public FlowPane getTogglesPane() {
        return debugPanel.getTogglesPane();
    }
    
}
