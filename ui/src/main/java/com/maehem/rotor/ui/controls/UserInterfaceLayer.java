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
package com.maehem.rotor.ui.controls;

import com.maehem.rotor.renderer.Graphics;
import com.maehem.rotor.ui.hud.HUD;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 *
 * @author maehem
 */
public class UserInterfaceLayer extends Group implements DialogLayer {
    private final static Logger LOGGER = Logger.getLogger(UserInterfaceLayer.class.getName());
    private Node currentDialog;

    public UserInterfaceLayer(Graphics gfx) {
        //Toolbar toolbar = new Toolbar(gfx.game, gfx.ui, this);
        
        HUD hud = new HUD(gfx.game, this);
        hud.setTranslateX(20);
        
        getChildren().addAll( hud );

    }

    @Override
    public void presentDialog(Node dialog) {
        if ( currentDialog != null ) {
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
    
}