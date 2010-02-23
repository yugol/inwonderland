/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.explore;

import edu.stanford.nlp.trees.WordNetConnection;

/**
 *
 * @author Iulian
 */
public class WordNetInstance implements WordNetConnection {

    public boolean wordNetContains(String s) {
        System.out.println(s);
        if ("one_by_one".equals(s)) {
            return true;
        }
        return false;
    }
}
