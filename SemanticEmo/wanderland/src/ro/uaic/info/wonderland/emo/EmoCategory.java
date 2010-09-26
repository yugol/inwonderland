/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.emo;

import java.awt.Color;

/**
 *
 * @author Iulian
 */
public class EmoCategory implements Comparable<EmoCategory> {

    static float orientationBase = 0.19634954f;
    static float orientationIncrement = orientationBase * 2;
    static float colorIncrement = 22.5f;
    String name = null; // the name of the emotion in natural
    byte index = 0; // from 1 to 16
    float orientation = 0; // angle in color wheel
    float hue = 0; // the color associated to that emotion

    public float getHue() {
        return hue;
    }

    public byte getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public float getOrientation() {
        return orientation;
    }

    public EmoCategory(byte index, String name) {
        this.name = name;
        this.index = index;
        index -= 1;
        this.orientation = orientationBase + orientationIncrement * index;
        hue = colorIncrement * ((20 - index) % 16) / 360;
    }

    public int compareTo(EmoCategory o) {
        return new Byte(index).compareTo(o.index);
    }
}
