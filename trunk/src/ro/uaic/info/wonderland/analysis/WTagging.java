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
public class WTagging {

    public String form = null; // word form
    public String lemma = null; // dictionary form
    public String pos = null; // depends on the part of speech
    public String gender = null; // masculine, feminine, neuter, common
    public String number = null; // singular, plural
    public String theCase = null; // nominative, genitive, dative, accusative
    public String person = null; // first, second, third
    public String comparison = null; // comparative, superlative
    public String mood = null; // indicative, subjunctive, conditional, ...
    public String tense = null; // present, past, future, ...
    public Synset[] senses = null; // from WordNet

    public String[] asStringArray() {
        ArrayList<String> types = new ArrayList<String>();
        if (pos != null) {
            types.add(pos);
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
        if (comparison != null) {
            types.add(comparison);
        }
        if (mood != null) {
            types.add(mood);
        }
        if (tense != null) {
            types.add(tense);
        }
        return types.toArray(new String[]{});
    }
}
