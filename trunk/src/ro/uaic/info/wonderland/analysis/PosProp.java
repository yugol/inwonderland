/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.analysis;

import java.util.ArrayList;
import net.didion.jwnl.data.Synset;

/**
 *
 * @author Iulian
 */
public class PosProp {

    public String lemma = null; // dictionary form
    public Synset[] senses = null; // from WordNet
    public String type = null; // depends on the part of speech
    public String number = null; // singular, plural
    public String theCase = null; // nominative, genitive, dative, accusative
    public String gender = null; // masculine, feminine, neuter, common
    public String person = null; // first, second, third

    public String[] getTypes() {
        ArrayList<String> types = new ArrayList<String>();
        if (type != null) {
            types.add(type);
        }
        if (person != null) {
            types.add(person);
        }
        if (theCase != null) {
            types.add(theCase);
        }
        if (number != null) {
            types.add(number);
        }
        if (gender != null) {
            types.add(gender);
        }
        return types.toArray(new String[]{});
    }
}
