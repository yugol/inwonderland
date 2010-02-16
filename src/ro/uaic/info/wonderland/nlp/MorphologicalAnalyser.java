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
        if (Arrays.binarySearch(MorphologicalDatabase.demonstrativePronouns, tagging.getLemma()) >= 0) {
            MorphologicalDatabase.tagPronoun(tagging);
            return;
        }

        IndexWord word = null;
        try {
            word = WordNetWrapper.lookup(tagging.getForm(), POS.NOUN);
        } catch (JWNLException ex) {
            System.out.println(ex);
        }

        // lemma

        if (word != null) {
            tagging.setLemma(word.getLemma());

            // senses
            try {
                tagging.setSenses(word.getSenses());
            } catch (JWNLException ex) {
                System.out.println(ex);
            }
        }
        if (tagging.getPennTag().indexOf("NNP") == 0) {
            tagging.setLemma(tagging.getLemma().substring(0, 1).toUpperCase() + tagging.getLemma().substring(1));
        }

        // pos
        if (Character.isUpperCase(tagging.getLemma().charAt(0))) {
            tagging.setPos("NnPRP");
        } else {
            tagging.setPos("NnCOM");
        }

        // case

        // number
        if (tagging.getPennTag().charAt(tagging.getPennTag().length() - 1) == 'S') {
            tagging.setNumber("plu");
        } else {
            tagging.setNumber("sng");
        }

        // gender (look synsets for person or person name database)

    }

    public void analyzeDeterminer(WTagging tagging) {
        if (tagging.getLemma().equals("the")) {
            tagging.setPos("ArDEF");
        } else if (tagging.getLemma().equals("a") || tagging.getLemma().equals("an")) {
            tagging.setLemma("a");
            tagging.setPos("ArIDF");
        } else if (Arrays.binarySearch(MorphologicalDatabase.demonstrativePronouns, tagging.getLemma()) >= 0) {
            MorphologicalDatabase.tagPronoun(tagging);
        } else if (Arrays.binarySearch(MorphologicalDatabase.indefinitePronouns, tagging.getLemma()) >= 0) {
            MorphologicalDatabase.tagIndefinitePronoun(tagging);
        }
    }

    public void analyzePersonalPronoun(WTagging tagging) {
        if (tagging.getLemma().equals("i")) {
            tagging.setLemma("I");
        }
        MorphologicalDatabase.tagPronoun(tagging);
    }

    public void analyzeAdjective(WTagging tagging) {
        if (Arrays.binarySearch(MorphologicalDatabase.possesivePronouns, tagging.getLemma()) >= 0) {
            MorphologicalDatabase.tagPronoun(tagging);
        }
        if (tagging.getLemma().equals("former") || tagging.getLemma().equals("former")) {
            MorphologicalDatabase.tagPronoun(tagging);
        }
    }
}
