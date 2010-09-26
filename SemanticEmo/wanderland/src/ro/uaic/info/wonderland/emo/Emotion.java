/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.emo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iulian
 */
public class Emotion implements Comparable<Emotion> {

    public static final int maxControl = 100;
    public static final int minControl = -maxControl;
    public static final int maxValence = 100;
    public static final int minValence = -maxValence;
    public static final int minIntensity = 0;
    public static final int maxIntensity = 100;
    //
    byte control = 0; // the degree of control perceived in the situation of having that emotion [-100, 100]
    byte valence = 0; // the degree of pleasantness of that emotion [-100, 100]
    byte intensity = 0; // the intensity of emotion [0, 200]
    byte nominalIntensity = 0; // {1, 2, 3, 4}
    byte emoCategory = 0; // {1, 2, ..., 16}
    String label = null; // emotion generic name
    List<String> markers = new ArrayList<String>(); // string patterns to identify the emotion "[*]label[*]"
    short index = -1;

    public byte getControl() {
        return control;
    }

    public byte getEmoCategory() {
        return emoCategory;
    }

    public byte getIntensity() {
        return intensity;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getMarkers() {
        return markers;
    }

    public byte getNominalIntensity() {
        return nominalIntensity;
    }

    public byte getValence() {
        return valence;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(short index) {
        this.index = index;
    }

    public Emotion(byte valence, byte control, byte intensity, String label, byte emoCategory) {
        if (control < minControl || maxControl < control) {
            throw new IllegalArgumentException("control out of bounds " + label);
        }
        this.control = control;
        if (valence < minValence || maxValence < valence) {
            throw new IllegalArgumentException("valence out of bounds " + label);
        }
        this.valence = valence;
        if (intensity < minIntensity || maxIntensity < intensity) {
            throw new IllegalArgumentException("intensity out of bounds: " + label + " " + intensity);
        }
        this.intensity = intensity;
        this.label = label;
        this.emoCategory = emoCategory;
        if (intensity < maxIntensity / 4) {
            nominalIntensity = 1;
        } else if (intensity < maxIntensity / 2) {
            nominalIntensity = 2;
        } else if (intensity < 3 * maxIntensity / 4) {
            nominalIntensity = 3;
        } else {
            nominalIntensity = 4;
        }
    }

    public int compareTo(Emotion o) {
        if (emoCategory < o.emoCategory) {
            return -1;
        }
        if (emoCategory > o.emoCategory) {
            return 1;
        }
        if (nominalIntensity < o.nominalIntensity) {
            return 1;
        }
        if (nominalIntensity > o.nominalIntensity) {
            return -1;
        }
        return 0;
    }

    public String getCode() {
        if (emoCategory < 10) {
            return ("0" + emoCategory) + nominalIntensity;
        } else {
            return ("" + emoCategory) + nominalIntensity;
        }
    }
}
