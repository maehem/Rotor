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
package com.maehem.rotor.renderer.ui;

import com.maehem.rotor.engine.game.Game;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import com.maehem.rotor.renderer.Graphics;
import java.util.ArrayList;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Cell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author maehem
 */
public class UI implements GameListener {

    private static final Logger LOGGER = Logger.getLogger(UI.class.getName());

    private boolean selectionBoxActive = false;
    private Cell selectionBoxStart = null;
    private Cell selectionBoxEnd = null;
    private int pricePerCell = 0;
    private Image currentCursorImage;

    final static double ZOOM_DEFAULT = 1.0;
    final static double ZOOM_IN_MAX = 2.0;
    final static double ZOOM_OUT_MAX = 0.15;

    public double zoom = ZOOM_DEFAULT;

    private final Game game;
    private final Graphics graphics;

    private final ArrayList<UIListener> listeners = new ArrayList<>();

    public Cell centerCell = null;

    public Cell selectedCell;
    private TOOL currentTool = TOOL.NONE;

    public int cursorX = -1;
    public int cursorY = -1;
    public int cursorTileX = -1;
    public int cursorTileY = -1;

    public enum TOOL {
        NONE, ZONE_DRAW
    }

    public enum MAP_ROTATE {
        CCW, CW
    }

    public UI(Game game, Graphics graphics) {
        this.game = game;
        this.graphics = graphics;
        game.addListener(this);
    }

    public void addListener(UIListener l) {
        listeners.add(l);
    }

    public void removeListener(UIListener l) {
        listeners.remove(l);
    }

    private void notifyCursorChange(Image image) {
        listeners.forEach((l) -> {
            l.uiEvent(new UIEvent(UIEvent.Type.CURSOR_CHANGE, /*null,*/ new Object[]{image}));
        });
    }

//    private void notifyTileSelected() {
//        for (UIListener l : listeners) {
//            l.uiEvent(new UIEvent(UIEvent.CELL_SELECTED, selectedCell));
//        }
//    }
    @Override
    public void gameEvent(GameEvent e) {
        switch (e.type) {
            case DATA_LOADED:
//                centerCell = game.data.map[game.data.mapInfo.mapSize / 2][game.data.mapInfo.mapSize / 2]; // Center the map view.
                break;
        }
    }

    /**
     * Zoom in or out by amount. Each int is an increment as set by this class.
     * Each increment is generally one mouse wheel notch.
     *
     * @param amount a positive or negative amount to increment the zoom.
     */
    public void zoom(double amount) {
        if (amount == 0) {
            return;
        }

        if (amount < 0) {
            for (int i = 0; i < -amount; i++) {
                zoom *= 0.96;
            }
            if (zoom < ZOOM_OUT_MAX) {
                zoom = ZOOM_OUT_MAX;
                //doNotify(UIEvent.TYPE.ZOOM_MAX);
            } else {
                //doNotify(UIEvent.TYPE.ZOOM_MID);
            }
        } else {
            for (int i = 0; i < amount; i++) {
                zoom *= 1.04;
            }
            if (zoom > ZOOM_IN_MAX) {
                zoom = ZOOM_IN_MAX;
                //doNotify(UIEvent.TYPE.ZOOM_MAX);
            } else {
                //doNotify(UIEvent.TYPE.ZOOM_MID);
            }
        }
    }

    public void mouseClicked(MouseEvent t) {
        cursorX = (int) t.getX();
        cursorY = (int) t.getY();
        t.consume();

        LOGGER.finest("Mouse clicked.");
        
    }

    public void mousePressed(MouseEvent t) {
        cursorX = (int) t.getX();
        cursorY = (int) t.getY();
        t.consume();
        
        LOGGER.finest("Mouse pressed.");
    }

    public void mouseMoved(MouseEvent t) {
        cursorX = (int) t.getX();
        cursorY = (int) t.getY();

        //LOGGER.info("Mouse moved.");
        t.consume();
    }

    public void mouseReleased(MouseEvent t) {
        cursorX = (int) t.getX();
        cursorY = (int) t.getY();

        LOGGER.finest("Mouse released.");

        t.consume();
    }

    public void mouseDragged(MouseEvent t) {
        int oldCursorX = cursorX;
        int oldCursorY = cursorY;

        cursorX = (int) t.getX();
        cursorY = (int) t.getY();

        //Cell c = getCellUnderCursor();

        t.consume();
    }


    public void draw(GraphicsContext gc) {
        // Debug
        //highlightSelectedCell(gc);
    }


}
