/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp.resources;

import java.io.FileInputStream;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.IndexWordSet;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;
import ro.uaic.info.wonderland.Globals;

/**
 *
 * @author Iulian
 */
public abstract class WordNetWrapper {

    static Dictionary dict;

    static {
        try {
            JWNL.initialize(new FileInputStream(Globals.getJwnlPropertiesFile()));
            dict = Dictionary.getInstance();
        } catch (Exception ex) {
            System.out.println("Could not initialize WordNet");
            System.out.println(ex);
            Globals.exit();
        }
    }

    public static IndexWord lookup(String word, POS posType) {
        try {
            return dict.lookupIndexWord(posType, word);
        } catch (JWNLException ex) {
            System.err.println(ex);
            Globals.exit();
            return null;
        }
    }

    public static Synset[] senses(IndexWord word) {
        try {
            return word.getSenses();
        } catch (JWNLException ex) {
            System.err.println(ex);
            Globals.exit();
            return null;
        }
    }

    public static boolean contains(String str) {
        try {
            str = str.replace('_', ' ');
            IndexWordSet findings = dict.lookupAllIndexWords(str);
            for (Object o : findings.getIndexWordCollection()) {
                String lemma = ((IndexWord) o).getLemma();
                if (lemma.equalsIgnoreCase(str)) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
}
