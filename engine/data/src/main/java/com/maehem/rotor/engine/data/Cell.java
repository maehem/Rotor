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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author maehem
 */
public class Cell {

    public Data parent;


    public Cell(Data parent) {
        this.parent = parent;
    }

    public Point getMapLocation() {
        for (int y = 0; y < parent.mapInfo.mapSize; y++) {
            for (int x = 0; x < parent.mapInfo.mapSize; x++) {
                if (this.equals(parent.map[x][y])) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }


    public void load(DataInputStream dis) throws IOException {
        //structure.load(dis);
    }

    public void save(DataOutputStream dos) throws IOException {
        //structure.save(dos);
    }

}
