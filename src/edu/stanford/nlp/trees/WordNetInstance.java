/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stanford.nlp.trees;

import ro.uaic.info.wonderland.nlp.WordNetWrapper;

/**
 *
 * @author Iulian
 */
public class WordNetInstance implements WordNetConnection {

    public boolean wordNetContains(String s) {
        if (WordNetWrapper.contains(s)) {
            System.out.println(s);
            return true;
        }
        return false;
    }
}
