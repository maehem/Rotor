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
package com.maehem.rotor.engine.data;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author maehem
 */
public class DataChangeSupport {
    private final CopyOnWriteArrayList<DataListenerKeyValue> listeners = new CopyOnWriteArrayList<>();

    public void addDataChangeListener(String propertyName, DataListener dl) {
        listeners.add(new DataListenerKeyValue(propertyName, dl));
    }

    public void removeDataChangeListener(String propertyName, DataListener dl) {
        for (DataListenerKeyValue l : listeners) {
            if ( l.getKey().equals(propertyName) && l.getListener().equals(dl) ) {
                listeners.remove(l);
                return;
            }
        }
    }
    
    public DataListenerKeyValue[] getDataChangeListeners() {
        return listeners.toArray(new DataListenerKeyValue[listeners.size()]);
    }
    
    public void firePropertyChange​(String propertyName, Object source, Object oldValue, Object newValue) {
        listeners.stream().filter((l) -> (propertyName.equals(l.getKey()))).forEachOrdered((l) -> {
            l.getListener().dataChange(propertyName, source, oldValue, newValue);
        });
    }               
}
