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

import com.maehem.rotor.engine.game.Realm;
import com.maehem.rotor.engine.game.Room;
import java.util.ArrayList;

/**
 *
 * @author maehem
 */
public class RealmNode {

    private final ArrayList<RoomNode> roomNodes = new ArrayList<>();
    private final Realm realm;

    public RealmNode(Realm realm) {
        this.realm = realm;

        realm.getRooms().forEach((room) -> {
            roomNodes.add(new RoomNode(room));
        });
    }

    /**
     * @return the roomNodes
     */
    public ArrayList<RoomNode> getRoomNodes() {
        return roomNodes;
    }

    /**
     * @return the realm
     */
    public Realm getRealm() {
        return realm;
    }

    public RoomNode getCurrentRoomNode() {
        Room currentRoom = realm.getParent().getCurrentRoom();
        if (currentRoom != null) {
            long currentRoomUID = currentRoom.getUid();

            for (var r : realm.getRooms()) {
                if (r.getUid() == currentRoomUID) {
                    for (RoomNode rn : getRoomNodes()) {
                        if (rn.getRoom() == r) {
                            return rn;
                        }
                    }
                }
            }
        }

        return null;
    }

}
