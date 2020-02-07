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
package com.maehem.rotor.ui.debug;

import com.maehem.rotor.ui.UserInterfaceLayer;
import java.util.ArrayList;

/**
 *
 * @author maehem
 */
public class DebugChangeSupport {
    private final ArrayList<DebugListenerKeyValue> listeners = new ArrayList<>();

    public void addDebugChangeListener(UserInterfaceLayer.DebugProp propertyName, DebugListener dl) {
        listeners.add(new DebugListenerKeyValue(propertyName, dl));
    }

    public void removeDebugChangeListener(UserInterfaceLayer.DebugProp propertyName, DebugListener dl) {
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
    
    public void fireDebugChange​(UserInterfaceLayer.DebugProp property, Object oldValue, Object newValue) {
        listeners.stream().filter((l) -> (property.equals(l.getKey()))).forEachOrdered((l) -> {
            l.getListener().debugPropertyChange(property, oldValue, newValue);
        });
    }               
}
