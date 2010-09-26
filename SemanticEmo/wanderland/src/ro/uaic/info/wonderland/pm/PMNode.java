/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.pm;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iulian
 */
public class PMNode implements Comparable<PMNode> {

    public static List<PMNode> newList() {
        return new ArrayList<PMNode>();
    }
    char ch = '\0';
    Object match = null;
    List<PMNode> fwd = null;

    public PMNode(char charAt) {
        this.ch = charAt;
    }

    public char getCh() {
        return ch;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public List<PMNode> getFwd() {
        return fwd;
    }

    public void setFwd(List<PMNode> fwd) {
        this.fwd = fwd;
    }

    public Object getMatch() {
        return match;
    }

    public void setMatch(Object match) {
        this.match = match;
    }

    public int compareTo(PMNode o) {
        return ch - o.ch;
    }
}
