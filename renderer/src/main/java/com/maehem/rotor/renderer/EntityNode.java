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
import com.maehem.rotor.engine.data.EntityState;
import com.maehem.rotor.engine.data.Point;
import com.maehem.rotor.engine.game.Entity;
import com.maehem.rotor.engine.game.Item;
import com.maehem.rotor.engine.game.events.GameEvent;
import com.maehem.rotor.engine.game.events.GameListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

/**
 *
 * @author maehem
 */
public class EntityNode extends Group implements DataListener, GameListener {

    private final static Logger LOGGER = Logger.getLogger(EntityNode.class.getName());

    private final WalkSheet graphic;
    private final ImageSequence swordSwish;
    private final Entity entity;
    private final Point screenSize;
    private final ImageView ghost;
    private final BoxBlur ghostBlur = new BoxBlur();
    private final Rectangle healthBarValue = new Rectangle(30, 2);
    private final FlowPane healthBar;
    private final Text nameTag;
    private final ImageView ashPile;
    private final ImageSequence lootSparkle;

    private boolean walking = false;
    private int damageTicks = 0;
    private ImageSequence lootItemImage;
    private final Rectangle collisionBox;

    public EntityNode(Entity entity, ClassLoader cl, double size, Point screenSize) throws IOException {
        this.entity = entity;

        LOGGER.log(Level.CONFIG, "Try to load walk sheet graphic: {0} from class {1}", new Object[]{entity.getWalkSheetFilename(), cl.getName()});
        this.graphic = new WalkSheet(cl.getResourceAsStream(entity.getWalkSheetFilename()), size);
        this.screenSize = screenSize;

        getChildren().add(graphic);

        swordSwish = new ImageSequence(EntityNode.class.getResourceAsStream("/renderer/glyphs/sword-swish.png"), 32, ImageSequence.Type.ONE_SHOT);
        swordSwish.setHideWhenDone(true);
        swordSwish.setVisible(false);
        swordSwish.getTransforms().add(new Translate(0, -32.0 / 3.0));
        //swordSwish.setTranslateX(-32/2.0);
        //swordSwish.setTranslateY(32.0/2.0);
        getChildren().add(swordSwish);

        // Name tag
        nameTag = new Text(entity.getName());
        nameTag.setLayoutY(-8.0);
        nameTag.setFont(new Font(10.0));
        //nameTag.setLayoutY(-16);
        getChildren().add(nameTag);

        healthBar = new FlowPane();
        healthBar.setMinWidth(32.0);
        healthBar.setMaxWidth(32.0);
        healthBar.setLayoutY(-6.0);
        healthBar.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(2.0), new BorderWidths(1.0))));
        healthBarValue.setFill(Color.RED);
        updateHealthBar(entity.getState().getHealth());
        healthBar.getChildren().add(healthBarValue);

        getChildren().add(healthBar);

        // Ghost
        ghost = new ImageView(new Image(cl.getResourceAsStream("characters/ghost.png")));
        ghost.setFitHeight(size);
        ghost.setPreserveRatio(true);
        ghost.setVisible(false);
        ghost.setLayoutY(-64.0);
        ghost.setEffect(ghostBlur);
        //ghost.setTranslateY(16.0);
        getChildren().add(ghost);

        // Ash Pile
        ashPile = new ImageView(new Image(cl.getResourceAsStream("characters/ash-pile.png")));
        ashPile.setFitHeight(size);
        ashPile.setPreserveRatio(true);
        ashPile.setVisible(false);
        ashPile.setOpacity(0.5);
        getChildren().add(ashPile);

        // Loot Sparkle
        lootSparkle = new ImageSequence(cl.getResourceAsStream("characters/loot-sparkle.png"), size, ImageSequence.Type.REPEAT);
        lootSparkle.setVisible(false);
        getChildren().add(lootSparkle);

        if (hasLoot()) {
            Item lootItem = entity.getLootItem();
            lootItemImage = new ImageSequence(cl.getResourceAsStream(lootItem.getImagePath()), size/1.2, ImageSequence.Type.REPEAT);
            lootItemImage.setLayoutX(size/8.0);
            lootItemImage.setLayoutY(size/8.0);
            lootItemImage.setVisible(false);
            this.getChildren().add(lootItemImage);
        }
        
        collisionBox = new Rectangle(graphic.getBoundsInLocal().getWidth(), graphic.getBoundsInLocal().getHeight());
        collisionBox.setFill(Color.TRANSPARENT);
        collisionBox.setStroke(Color.RED);
        collisionBox.setStrokeWidth(1.0);
        getChildren().add(collisionBox);
        
        entity.getState().addDataChangeListener(EntityState.PROP_POSITION, this);
        entity.getState().addDataChangeListener(EntityState.PROP_HEALTH, this);

        LOGGER.finer("Create Entity Node.");
        updateLayout(entity.getState().getPosition());
    }

    public WalkSheet getWalkSheet() {
        return graphic;
    }

    @Override
    public void dataChange(String key, Object source, Object oldValue, Object newValue) {
        switch (key) {
            case EntityState.PROP_POSITION:
                Point oPos = (Point) oldValue;
                Point pos = (Point) newValue;

                if (oPos.x == pos.x && oPos.y == pos.y) {
                    getWalkSheet().stand();
                    walking = false;
                    return;
                }

                if (oPos.x < pos.x) {
                    getWalkSheet().setDir(WalkSheet.DIR.RIGHT);
                    swordSwish.setAngle(90.0);

                } else if (oPos.x > pos.x) {
                    getWalkSheet().setDir(WalkSheet.DIR.LEFT);
                    swordSwish.setAngle(270.0);
                }
                if (oPos.y < pos.y) {
                    getWalkSheet().setDir(WalkSheet.DIR.TOWARD);
                    swordSwish.setAngle(180.0);
                } else if (oPos.y > pos.y) {
                    getWalkSheet().setDir(WalkSheet.DIR.AWAY);
                    swordSwish.setAngle(0.0);
                }
                walking = true;
                updateLayout((Point) newValue);
                break;
            case EntityState.PROP_HEALTH:
                updateHealthBar((double) newValue);
                if ((double) oldValue > (double) newValue) {
                    // Flash the entity red.
                    getWalkSheet().showDamage(true);
                    damageTicks = 2;
                }
                if ((double) newValue <= 0.0) {
                    collisionBox.setWidth(getCollisionBox().getWidth());
                    collisionBox.setHeight(getCollisionBox().getHeight());
                    collisionBox.setTranslateX(getCollisionBox().getCenterX()-getCollisionBox().getWidth()/2);
                    collisionBox.setTranslateY(getCollisionBox().getCenterY()-getCollisionBox().getHeight()/2);
                    // Death
                    graphic.setVisible(false);
                    healthBar.setVisible(false);
                    nameTag.setVisible(false);
                    ghost.setVisible(true);
                    ashPile.setVisible(true);
                    lootSparkle.setVisible(true);
                    ghost.setTranslateY(-ghost.getLayoutY());
                    if ( lootItemImage != null) {
                        lootItemImage.setVisible(true);
                    }
                }
                break;
        }
    }

    @Override
    public void gameEvent(GameEvent e) {
        switch (e.type) {
            case TICK:
                if (walking && e.getSource().getSubTicks() % 2 == 0) {
                    getWalkSheet().step();
                }
                if (getEntity().isMeleeAttack()) {
                    swordSwish.step();
                    if (swordSwish.isDone()) {
                        getEntity().meleeAttack(false); // Consume request
                    }
                }
                if (damageTicks <= 0) {
                    getWalkSheet().showDamage(false);
                }
                if (damageTicks > 0) {
                    damageTicks--;
                }
                if (entity.getState().getHealth() <= 0.0) {
                    if (ghost.getTranslateY() > 0.0) {
                        ghost.setTranslateY(ghost.getTranslateY() - 3.0);
                        double amount = ghost.getTranslateY() / -ghost.getLayoutY(); // Start at 1.0, diminish to 0.0
                        ghost.setOpacity(amount);
                        ghostBlur.setWidth((1.0 - amount) * 16.0);
                        ghostBlur.setHeight((1.0 - amount) * 16.0);
                        ghost.setScaleY(1.0 + (2.0 * (1.0 - amount)));
                        ghost.setScaleX(2.0 * (1.0 - amount));
                        if ( lootItemImage != null ) {
                            lootItemImage.setOpacity(1.0-amount);
                        }
                    }

                    if (e.getSource().getSubTicks() % 2 == 0) {
                        lootSparkle.step();
                        if (lootItemImage != null) {
                            lootItemImage.step();
                        }
                    }
                }
        }
    }

    private void updateHealthBar(double value) {
        healthBarValue.setWidth((double) value / EntityState.HEALTH_MAX * 28);
    }

    void attackWithSword() {
        if (getEntity().isMeleeAttack()) {
            return; // Ignore new attacks while swinging.
        }
        getEntity().meleeAttack(true);
        swordSwish.reset();
    }

    public boolean meleeBegun() {
        return !swordSwish.isDone() && swordSwish.getCurrentFrame() == 1; // Frame 0 might have been already processed before we get here.
    }

    public Bounds getCollisionBox() {
        if ( isAlive() ) {
        return getWalkSheet().getBoundsInParent();
        } else {
            Bounds bb = getBoundsInLocal();
            return new BoundingBox(bb.getCenterX(), bb.getCenterY(), bb.getWidth()/4, bb.getHeight()/4);
        }
    }

    public final void updateLayout(Point pos) {
        setLayoutX(pos.x * screenSize.x);
        setLayoutY(pos.y * screenSize.y);
    }

    /**
     * @return the entity
     */
    public Entity getEntity() {
        return entity;
    }

    public boolean isAlive() {
        return entity.getState().getHealth() > 0.0 || ghost.getTranslateY() > 0.0;
    }
    
    public final boolean hasLoot() {
        return entity.getLootItem() != null;
    }

    void clearLootItem() {
        entity.setLootItem(null);
        this.getChildren().remove(lootItemImage);
    }
}
