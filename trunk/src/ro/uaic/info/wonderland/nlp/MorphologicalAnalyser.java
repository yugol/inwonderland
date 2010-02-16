/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import ro.uaic.info.wonderland.nlp.resources.WordNetWrapper;
import java.util.Arrays;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;

/**
 *
 * @author Iulian
 */
public class MorphologicalAnalyser {

    public void analyzeNoun(WTagging tagging) {
        if (Arrays.binarySearch(MorphologicalDatabase.demonstrativePronouns, tagging.lemma) >= 0) {
            MorphologicalDatabase.tagPronoun(tagging);
            return;
        }

        IndexWord word = null;
        try {
            word = WordNetWrapper.lookup(tagging.form, POS.NOUN);
        } catch (JWNLException ex) {
            System.out.println(ex);
        }

        // lemma

        if (word != null) {
            tagging.lemma = word.getLemma();

            // senses
            try {
                tagging.senses = word.getSenses();
            } catch (JWNLException ex) {
                System.out.println(ex);
            }
        }
        if (tagging.pennTag.indexOf("NNP") == 0) {
            tagging.lemma = tagging.lemma.substring(0, 1).toUpperCase() + tagging.lemma.substring(1);
        }

        // wTag
        if (Character.isUpperCase(tagging.lemma.charAt(0))) {
            tagging.wTag = "NnPRP";
        } else {
            tagging.wTag = "NnCOM";
        }

        // case

        // number
        if (tagging.pennTag.charAt(tagging.pennTag.length() - 1) == 'S') {
            tagging.number = "plu";
        } else {
            tagging.number = "sng";
        }

        // gender (look synsets for person or person name database)

    }

    public void analyzeDeterminer(WTagging tagging) {
        if (tagging.lemma.equals("the")) {
            tagging.wTag = "ArDEF";
        } else if (tagging.lemma.equals("a") || tagging.lemma.equals("an")) {
            tagging.lemma = "a";
            tagging.wTag = "ArIDF";
        } else if (Arrays.binarySearch(MorphologicalDatabase.demonstrativePronouns, tagging.lemma) >= 0) {
            MorphologicalDatabase.tagPronoun(tagging);
        } else if (Arrays.binarySearch(MorphologicalDatabase.indefinitePronouns, tagging.lemma) >= 0) {
            MorphologicalDatabase.tagIndefinitePronoun(tagging);
        }
    }

    public void analyzePersonalPronoun(WTagging tagging) {
        if (tagging.lemma.equals("i")) {
            tagging.lemma = "I";
        }
        MorphologicalDatabase.tagPronoun(tagging);
    }

    public void analyzeAdjective(WTagging tagging) {
        if (Arrays.binarySearch(MorphologicalDatabase.possesivePronouns, tagging.lemma) >= 0) {
            MorphologicalDatabase.tagPronoun(tagging);
        }
        if (tagging.lemma.equals("former") || tagging.lemma.equals("former")) {
            MorphologicalDatabase.tagPronoun(tagging);
        }
    }
}
