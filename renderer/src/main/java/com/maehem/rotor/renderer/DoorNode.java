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

import com.maehem.rotor.engine.data.PortKey;
import com.maehem.rotor.engine.data.World;
import com.maehem.rotor.renderer.debug.Debug;
import com.maehem.rotor.renderer.debug.DebugListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author maehem
 */
public class DoorNode extends Rectangle implements DebugListener {

    private final static Logger LOGGER = Logger.getLogger(DoorNode.class.getName());

    public final PortKey door;

    public DoorNode(PortKey door, World w) {
        super(
                door.layoutPos.x * w.getScreenWidth(),
                door.layoutPos.y * w.getScreenHeight(),
                door.size.x * w.getScreenWidth(), door.size.y * w.getScreenHeight());
        this.door = door;

        LOGGER.log(Level.FINER, "Create new Door node at: {0},{1}  size: {2}x{3}", new Object[]{getX(), getY(), getWidth(), getHeight()});

        setFill(Color.TRANSPARENT);
        setStrokeWidth(1.0);
        setOpacity(0.6);
        
        showDebugBorders(false);
        Debug.getInstance().addDebugChangeListener(Debug.Prop.SHOW_PORTKEYS, this);

    }

    @Override
    public void debugPropertyChange(Debug.Prop propertyName, Object oldValue, Object newValue) {
        switch (propertyName) {
            case SHOW_PORTKEYS:
                showDebugBorders((boolean) newValue);
                break;
        }
    }

    private void showDebugBorders(boolean show) {
        if (show) {
            setStroke(Color.RED);
        } else {
            setStroke(Color.TRANSPARENT);
        }
    }
}
