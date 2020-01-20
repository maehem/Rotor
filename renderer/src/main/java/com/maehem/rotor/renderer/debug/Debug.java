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
package com.maehem.rotor.renderer.debug;

import com.maehem.rotor.engine.data.Cell;
import com.maehem.rotor.engine.game.Game;
import com.maehem.rotor.renderer.Graphics;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Mark J Koch <rocketcolony-maintainer@maehem.com>
 */
public class Debug {

    public boolean enabled = false;

    public boolean showOverlayInfo = true;
    public boolean showStatsPanel = false;  // Another pop tab

    //public int clipOffset = 0;   // ???   delete me
    //public int tileCount = 0;    // ??? delete me

    public long beginTime = System.nanoTime();
    public long previousTime = System.nanoTime();
    public int frameTime = 0;
    public int frames = 0;
    public int frameCount = 0;
    public int fps = 0;
    private int msPerFrame = 0;

    private final Graphics gfx;

    public Debug(Graphics gfx) {
        this.gfx = gfx;
    }

    public void main(GraphicsContext g) {
        if (!enabled) {
            return;
        }

        debugOverlay(g);
        showTileInfo(g);
        showFrameStats(g);
    }

    public void begin() {
        beginTime = System.nanoTime();
        //tileCount = 0;
    }

    public long end() {
        long time = System.nanoTime();
        frames++;

        if (time > previousTime + 1000000) {
            msPerFrame = Math.round(time - beginTime)/1000;

            fps = Math.round((frames * 1000000000) / (time - previousTime));
            previousTime = time;

            frameCount += frames;
            frames = 0;
        }

        return time;
    }

    public void showFrameStats(GraphicsContext g) {
        int width = 260;
        int height = 30;

        int x = (int) (g.getCanvas().getWidth() - width);
        int y = (int) (g.getCanvas().getHeight() - height);

        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Verdana", (int)(height*0.5)));
        g.setFill(new Color(255, 255, 255, 126));


        g.fillText(msPerFrame / 1000 + " m/s per frame (" + fps + " FPS)",
                x + 20, y + height/3);

    }

    public void drawDebugLayer(GraphicsContext g, Cell cell) {
//        if (!showTileCount) {
//            return;
//        }

    }

    public void debugOverlay(GraphicsContext g) {
        if (!showOverlayInfo) {
            return;
        }

        float fontSize = 20.0f;
        int width = 320;
        int height = (int) (fontSize*6);
        int lineInc = (int) fontSize;

        int x = 20;
        int y = (int) gfx.canvas.getHeight();

        g.setFill(new Color( 0,0,0,60));
        g.fillRect(x-5, y-height, width, y-5);

        g.setLineWidth(1.0);
        g.setStroke(Color.DARKGREY);
        g.strokeRect(x-5, y-height, width, y-5);

        y -= lineInc;
        Font origFont = g.getFont();
        g.setFont(new Font(g.getFont().getFamily(), fontSize));
        g.setFill(new Color(255, 255, 255, 180));


        g.fillText("cursor x: " + gfx.ui.cursorX + ", y: " + gfx.ui.cursorY, x, y);
        y -= lineInc;
        g.fillText("map rotation: " + gfx.game.data.mapInfo.getRotation(), x, y);
        y -= lineInc;
        g.fillText("zoom: " + Math.round(gfx.ui.zoom*10)/10.0f, x, y);
        y -= lineInc;
        if (gfx.ui.selectedCell != null) {
            g.fillText("selected tile"
                    +  " x: " + gfx.ui.selectedCell.getMapLocation().x
                    + ", y: " + gfx.ui.selectedCell.getMapLocation().y,
                    x, y);
            y -= lineInc;
        }

        g.setFont(origFont); // Put the font settings back.
    }

    public void showTileInfo(GraphicsContext g) {
//        if (!showSelectedTileInfo) {
//            return;
//        }

        //Cell cell = gfx.game.data.getMapCell(gfx.ui.cursorTileX, gfx.ui.cursorTileY);

        // TODO   No cells are ever null!
//        if (cell == null) {
//            return;
//        }

        StringBuilder textData = new StringBuilder();

//        // todo: this should be moved to a function for drawing text?
//        textData.append("Cell Position:");
//        textData.append("Current X: ").append(cell.getMapLocation().x)
//                .append(", Y: ").append(cell.getMapLocation().y)
//                .append(", Z: ").append(cell.altm.altitude);

        int height = 20 + (textData.length() * 15);
        int width = 220;

        g.fillRect(0, 0, width, height);
        int lineX = gfx.ui.cursorX + 20;
        int lineY = gfx.ui.cursorY + 25;

        g.setFont(new Font("Verdana", 10));
        g.setFill(new Color(255, 255, 255, 230));

        g.fillText(textData.toString(), lineX, lineY);
    }


}
