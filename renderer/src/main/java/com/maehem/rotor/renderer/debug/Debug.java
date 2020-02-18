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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Debug {
    private final static Logger LOGGER = Logger.getLogger(Debug.class.getName());
    
    private static Debug instance;
    
    public enum Prop { SHOW_PORTKEYS }
    
    public static final int GLYPH_HEIGHT = 24;
    
    private boolean showPortKey = false; // Draw debugging border around portkeys.
   
    private Debug() {}
    
    public static final Debug getInstance() {
        if ( instance == null ) {
            instance = new Debug();
        }
        
        return instance;
    }
    
    /**
     * @return true if debug pane borders are showing
     */
    public boolean showPortKeys() {
        return showPortKey;
    }

    /**
     * @param show the debug pane borders
     */
    public void setShowPortKeys(boolean show) {
        boolean oldValue = this.showPortKey;
        this.showPortKey = show;
        fireDebugChange(Prop.SHOW_PORTKEYS, oldValue, show);
    }

    public void populateDebugToggles(FlowPane togglesPane) {
        LOGGER.fine("Game Scene - Populate Debug Toggles.");
        
        // Create and place debug toggles here.
        ToggleButton portKeyBorders = new ToggleButton("", createGlyph("/renderer/glyphs/settings.png"));
        portKeyBorders.setTooltip(new Tooltip("Show PortKey Borders"));
        portKeyBorders.setSelected(showPortKeys());
        portKeyBorders.selectedProperty().addListener((ov, prev, current) -> {
            setShowPortKeys(current);
        });
        togglesPane.getChildren().add(portKeyBorders);

    }

    public static StackPane createGlyph(String path) {
        try {
            URL glyphResource = Debug.class.getResource(path);
            ImageView glyphImage = new ImageView(new Image(glyphResource.openStream()));
            glyphImage.setPreserveRatio(true);
            glyphImage.setFitHeight(GLYPH_HEIGHT);
            return new StackPane(glyphImage);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return new StackPane();
        }
    
    }
    
        private final ArrayList<DebugListenerKeyValue> listeners = new ArrayList<>();

    public void addDebugChangeListener(Debug.Prop propertyName, DebugListener dl) {
        listeners.add(new DebugListenerKeyValue(propertyName, dl));
    }

    public void removeDebugChangeListener(Debug.Prop propertyName, DebugListener dl) {
        for (DebugListenerKeyValue l : listeners) {
            if ( l.getKey().equals(propertyName) && l.getListener().equals(dl) ) {
                listeners.remove(l);
                return;
            }
        }
    }
    
    public DebugListenerKeyValue[] getDebugChangeListeners() {
        return listeners.toArray(new DebugListenerKeyValue[listeners.size()]);
    }
    
    public void fireDebugChange​(Debug.Prop property, Object oldValue, Object newValue) {
        listeners.stream().filter((l) -> (property.equals(l.getKey()))).forEachOrdered((l) -> {
            l.getListener().debugPropertyChange(property, oldValue, newValue);
        });
    }               

    
}
