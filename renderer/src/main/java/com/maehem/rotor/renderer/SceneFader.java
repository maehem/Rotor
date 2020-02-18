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

import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author maehem
 */
public class SceneFader extends Group {

    private static final Logger LOGGER = Logger.getLogger(SceneFader.class.getName());

    Rectangle blackness;
    double blurAmount = 0.3; // 1.0 = 100%
    double irisSize;
    private final double DELTA = 40.0;
    private double irisDelta = DELTA;
    private final double IRIS_MAX;
    private final Circle iris = new Circle(100);
    private final double screenHeight;
    private final double screenWidth;
    private final double blurSpread;
    private boolean fading = false;

    public SceneFader(double width, double height) {
        this.screenHeight = height;
        this.screenWidth = width;

        blurSpread = blurAmount * height;
        IRIS_MAX = width * 0.7;

        double margin = 2 * blurAmount * height;
        blackness = new Rectangle(width + margin, height + margin);
        blackness.setX(-margin / 2);
        blackness.setY(-margin / 2);
        blackness.setFill(Color.BLACK);

        initOpening(width / 2, height / 2);
    }

    public void initOpening( double x, double y) {
        irisSize = IRIS_MAX;
        iris.setRadius(irisSize);
        iris.setCenterX(x);
        iris.setCenterY(y);
        irisDelta = -DELTA;
    }

    public void initClosing(double x, double y) {
        irisSize = 0;
        iris.setRadius(irisSize);
        iris.setCenterX(x);
        iris.setCenterY(y);
        irisDelta = DELTA;
    }

    public void startFade() {
        fading = true;
    }

    public boolean isFading() {
        return fading;
    }

    public void update() {
        if (!fading) {
            return;
        }
        getChildren().clear();
        irisSize += irisDelta;

        if (irisSize < 0) {
            irisSize = 0.0;
            fading = false;
            m_lFadeOutFinished.onFadeOutFinishedListener(1);
        }
        if (irisSize > IRIS_MAX) {
            irisSize = IRIS_MAX;
            fading = false;
            m_lFadeInFinished.onFadeInFinishedListener(1);
        }

        iris.setRadius(irisSize);
        Shape scrim = Shape.subtract(blackness, iris);
        scrim.setEffect(new BoxBlur(blurSpread, blurSpread, 1));
        getChildren().add(scrim);
    }

    // Fade out event handler
    public interface FadeOutFinishedListener {
        public void onFadeOutFinishedListener(int data_type);
    }

    private FadeOutFinishedListener m_lFadeOutFinished;

    public void setOnFadeOutFinished(FadeOutFinishedListener dlf) {
        this.m_lFadeOutFinished = dlf;
    }

    // Fade In event handler
    public interface FadeInFinishedListener {
        public void onFadeInFinishedListener(int data_type);
    }

    private FadeInFinishedListener m_lFadeInFinished;

    public void setOnFadeInFinished(FadeInFinishedListener dlf) {
        this.m_lFadeInFinished = dlf;
    }

}
