/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.pm;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Iulian
 */
public class TreeTextPatternMatcher {

    List<PMNode> tree = PMNode.newList();

    public boolean add(String patt, Object obj) {
        PMNode node = null;
        List<PMNode> domain = tree;

        for (int i = 0; i < patt.length(); ++i) {

            if (domain.size() > 0) {
                node = domain.get(0);
                if (node.getCh() == '*') {
                    return false;
                }
            }

            node = new PMNode(patt.charAt(i));
            int where = Collections.binarySearch(domain, node);

            if (where >= 0) {
                PMNode found = domain.get(where);
                domain = found.getFwd();
                if (domain == null) {
                    found.setFwd(PMNode.newList());
                    domain = found.getFwd();
                } else if (i == (patt.length() - 1)) {
                    if (domain.get(0).getCh() == '*') {
                        return false;
                    }
                    if (found.getMatch() == null) {
                        found.setMatch(obj);
                        return true;
                    }
                    return false;
                }

            } else {
                if ((node.getCh() == '*') && (domain.size() > 0)) {
                    return false;
                }
                domain.add(node);
                Collections.sort(domain);
                if (i == (patt.length() - 1)) {
                    node.setMatch(obj);
                    return true;
                } else {
                    node.setFwd(PMNode.newList());
                    domain = node.getFwd();
                }
            }

        }
        return false;
    }

    public Object match(String patt) {
        List<PMNode> domain = tree;
        if (domain.size() <= 0) {
            return null;
        }
        PMNode node = domain.get(0);
        if (node.getCh() == '*') {
            return node.getMatch();
        }

        for (int i = 0; i < patt.length(); ++i) {
            node = new PMNode(patt.charAt(i));
            int where = Collections.binarySearch(domain, node);
            if (where >= 0) {
                node = domain.get(where);
                domain = node.getFwd();
                if (domain == null) {
                    if (i == (patt.length() - 1)) {
                        return node.getMatch();
                    }
                    break;
                } else {
                    Object match = node.getMatch();
                    node = domain.get(0);
                    if (node.getCh() == '*') {
                        return node.getMatch();
                    }
                    if (i == (patt.length() - 1)) {
                        return match;
                    }
                }
            } else {
                break;
            }
        }

        return null;
    }
}
