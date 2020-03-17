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

import com.maehem.rotor.engine.data.DataListener;
import com.maehem.rotor.engine.game.PortKey;
import com.maehem.rotor.engine.data.PlayerState;
import com.maehem.rotor.engine.data.Point;
import com.maehem.rotor.engine.game.Entity;
import com.maehem.rotor.engine.game.Room;
import com.maehem.rotor.engine.game.Game;
import com.maehem.rotor.engine.game.Item;
import com.maehem.rotor.engine.game.Player;
import com.maehem.rotor.engine.game.World;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author maehem
 */
public class RoomNode extends Group implements GameListener, DataListener {

    private static final Logger LOGGER = Logger.getLogger(RoomNode.class.getName());

    private final Room room;
    private PlayerNode playerNode;
    private final ArrayList<DoorNode> doorNodes = new ArrayList<>();
    private final ArrayList<EntityNode> entityNodes = new ArrayList<>();
    private final CopyOnWriteArrayList<ItemNode> itemNodes = new CopyOnWriteArrayList<>();

    public RoomNode(Room room) {
        this.room = room;
        setViewOrder(1000.0); // Ensures that it renders first.
    }

    public void enter(Game game, PlayerNode playerNode) {

        this.playerNode = playerNode;

        constructRoom(game);

        getChildren().add(playerNode);
        if (playerNode.player.hasPortKey()) {
            playerNode.player.gotoPortKey();
            playerNode.updateLayout(playerNode.player.getState().getPosition());
        }

        //blackness.toFront();
        playerNode.player.getState().addDataChangeListener(PlayerState.PROP_POSITION, this);

        // Clip to the room's actual size so that camera panning works correctly.
        setClip(new Rectangle(
                room.getWidth() * room.parent.getParent().getScreenWidth(),
                room.getHeight() * room.parent.getParent().getScreenHeight()
        ));

        game.addListener(this);
    }

    public void leave(Game game) {

        for (EntityNode e : entityNodes) {
            game.removeListener(e);
        }
        getChildren().removeAll();
        doorNodes.clear();
        entityNodes.clear();
        itemNodes.clear();

        this.playerNode = null;
    }

    private void constructRoom(Game game) {
        LOGGER.log(Level.FINE, "Construct Room {0}:{1}", new Object[]{room.uid, room.getDisplayName()});
        int tileSize = room.parent.getParent().getTileSize();
        LOGGER.log(Level.CONFIG, "Tile Size is: {0}", tileSize);
        for (int y = 0; y < room.nTilesY; y++) {
            for (int x = 0; x < room.nTilesX; x++) {
                TileNode tn = new TileNode(room.get(x, y));
                if (tn.create((AssetSheet) room.getAssetSheet())) {
                    tn.setLayoutX(x * tileSize);
                    tn.setLayoutY(y * tileSize);
                    getChildren().add(tn);
                }
            }
        }

        // Doors
        for (PortKey d : room.getDoors()) {
            //LOGGER.config("Add port key rectangle for: " + d.destRoomUID);
            DoorNode dn = new DoorNode(d, room.parent.getParent());
            getChildren().add(dn);
            doorNodes.add(dn);
        }

        for (Entity e : room.getEntities()) {
            try {
                World w = room.parent.getParent();
                EntityNode en = new EntityNode(e, w.getClassLoader(), 32, new Point(w.getScreenWidth(), w.getScreenHeight()));
                getChildren().add(en);
                entityNodes.add(en);
                game.addListener(en);
            } catch (IOException ex) {
                Logger.getLogger(RoomNode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (Item item : room.getItems()) {
            World w = room.parent.getParent();
            ItemNode itemNode = new ItemNode(item, w.getClassLoader(), w.getTileSize() / 1.4, w.getScreenWidth(), w.getScreenHeight());
            getChildren().add(itemNode);
            itemNodes.add(itemNode);
            //game.addListener(itemNode);
        }

        LOGGER.log(Level.FINE, "Room Size is: {0}x{1}", new Object[]{getBoundsInLocal().getWidth(), getBoundsInLocal().getHeight()});
    }

    @Override
    public void gameEvent(GameEvent e) {
        switch (e.type) {
            case TICK:
                Player player = e.getSource().getWorld().getPlayer();
                player.tryMove(room);

                // Check for collisions.
                for (EntityNode entNode : entityNodes) {

                    if (entNode.isAlive()) {
                        // If entity is within n blocks, move toward player.
                        Point ePos = entNode.getEntity().getState().getPosition();
                        Point pPos = player.getState().getPosition();
                        Entity entity = entNode.getEntity();
                        if (Math.abs(ePos.x - pPos.x) < 0.2 && Math.abs(ePos.y - pPos.y) < 0.2) {
                            WalkSheet ws = entNode.getWalkSheet();
                            boolean goDown = ePos.y - pPos.y < 0.0;
                            boolean goRight = ePos.x - pPos.x < 0.0;
                            boolean closeEnoughX = Math.abs(ePos.x - pPos.x) < 0.05;
                            boolean closeEnoughY = Math.abs(ePos.y - pPos.y) < 0.05;

                            entity.goSouth(goDown && !closeEnoughY);
                            entity.goNorth(!goDown && !closeEnoughY);
                            entity.goEast(goRight && !closeEnoughX);
                            entity.goWest(!goRight && !closeEnoughX);

                            entity.tryMove(room);
                        } else {
                            entity.stop();
                        }
                    }
                    
                    if (entNode.getCollisionBox().intersects(entNode.sceneToLocal(playerNode.localToScene(playerNode.getCollisionBox())))) {
                        //LOGGER.log(Level.WARNING, "Player colided with entity: {0}", entNode.getEntity().getName());
                        if (playerNode.meleeBegun()) {
                            entNode.getEntity().damage(playerNode.player.getMeleeDamage());
                        }
                        if (entNode.meleeBegun()) {
                            playerNode.getEntity().damage(entNode.entity.getMeleeDamage());
                        }

                        if (entNode.isAlive()) {
                            entNode.attackWithSword();
                        }

                        if (!entNode.isAlive() && entNode.hasLoot()) {
                            if (playerNode.player.takeItem(entNode.getEntity().getLootItem())) {
                                LOGGER.log(Level.CONFIG, "Player looted the corpse of {0}", entNode.getEntity().getName());
                                entNode.clearLootItem();
                            }
                        }
                    }
                }

                for (ItemNode node : itemNodes) {
                    if (e.getSource().getSubTicks() % 3 == 0) {
                        node.step();
                    }
                    if (node.intersects(node.sceneToLocal(playerNode.localToScene(playerNode.getCollisionBox())))) {
                        //LOGGER.log(Level.WARNING, "Player colided with entity: {0}", entNode.getEntity().getName());
                        if (playerNode.player.takeItem(node.item)) {
                            LOGGER.config("Player consumed placed item.");
                            getChildren().remove(node);
                            itemNodes.remove(node);
                        }
                    }
                }
                break;
        }
    }

    /**
     * @return the room
     */
    public final Room getRoom() {
        return room;
    }

    @Override
    public void dataChange(String key, Object source, Object oldValue, Object newValue) {
        switch (key) {
            case PlayerState.PROP_POSITION:
                Point oPos = (Point) oldValue;
                Point pos = (Point) newValue;

                // Camera panning
                // If room size > 1 && player.pos > 0.5 of screen width
                // then adjust LayoutX to bring player to 0.5
                if (room.getWidth() > 1) {  // Only need to pan if the room is wider than screen.
                    if (pos.x > 0.5 && pos.x < room.getWidth() - 0.5) {
                        setTranslateX(-(pos.x - 0.5) * getBoundsInLocal().getWidth() / room.getWidth());
                    }
                }
                if (room.getHeight() > 1) {  // Only need to pan if the room is wider than screen.
                    if (pos.y > 0.5 && pos.y < room.getHeight() - 0.5) {
                        setTranslateY(-(pos.y - 0.5) * getBoundsInLocal().getHeight() / room.getHeight());
                    }
                }

                for (DoorNode dn : doorNodes) {
                    if (dn.intersects(dn.sceneToLocal(playerNode.localToScene(playerNode.getCollisionBox())))) {  //  <=== Thank you StackOverflow!
                        LOGGER.log(Level.CONFIG, "Player contacted door to room: {0}", dn.door.destRoomUID);
                        //playerNode.player.getState().removeDataChangeListener(PlayerState.PROP_POSITION, this);
                        ((PlayerState) source).removeDataChangeListener(PlayerState.PROP_POSITION, this);
                        playerNode.player.setPortKey(dn.door);
                        playerNode.getWorld().changeRoom(dn.door.destRoomUID);
                        return;
                    }
                }
                break;
        }
    }

}
