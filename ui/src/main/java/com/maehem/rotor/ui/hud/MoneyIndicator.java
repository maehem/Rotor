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
package com.maehem.rotor.ui.hud;

import com.maehem.rotor.engine.data.PlayerState;
import com.maehem.rotor.ui.debug.DebugChangeSupport;

/**
 *
 * @author maehem
 */
public class MoneyIndicator extends ItemAmountIndicator {

    
    public MoneyIndicator(DebugChangeSupport changes) {
        super(PlayerState.PROP_MONEY, "/glyphs/hud/money.png", 999, changes);
    }

//    @Override
//    public void gameEvent(GameEvent e) {
//        switch (e.type) {
//            case DATA_LOADED:
//                PlayerState state = e.getSource().getPlayer().getState();
//
//                state.addDataChangeListener(KEY, this);
//                break;
//
//        }
//    }
//
//    @Override
//    public void dataChange(String key, Object oldValue, Object newValue) {
//        if ( key.equals(KEY) ) {
//            setValue((int) newValue);
//        }
//    }
//
}
