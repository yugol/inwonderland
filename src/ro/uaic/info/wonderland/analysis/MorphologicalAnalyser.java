/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.analysis;

import java.util.Arrays;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import ro.uaic.info.wonderland.engine.EngineKnowledgeBase;

/**
 *
 * @author Iulian
 */
public class MorphologicalAnalyser {

    public PosProp analyzeNoun(String wordForm, String spTag) {
        IndexWord word = null;
        try {
            word = WordNetWrapper.lookup(wordForm, POS.NOUN);
        } catch (JWNLException ex) {
            System.out.println(ex);
        }
        PosProp prop = new PosProp();

        // lemma

        if (word == null) {
            prop.lemma = wordForm.toLowerCase();
        } else {
            prop.lemma = word.getLemma();

            // senses
            try {
                prop.senses = word.getSenses();
            } catch (JWNLException ex) {
                System.out.println(ex);
            }
        }
        if (spTag.indexOf("NNP") == 0) {
            prop.lemma = prop.lemma.substring(0, 1).toUpperCase() + prop.lemma.substring(1);
        }

        // posType
        if (Character.isUpperCase(prop.lemma.charAt(0))) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("NnPRP");
        } else {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("NnCOM");
        }

        // case

        // number
        if (spTag.charAt(spTag.length() - 1) == 'S') {
            prop.number = EngineKnowledgeBase.ctLabel2ctId("plu");
        } else {
            prop.number = EngineKnowledgeBase.ctLabel2ctId("sng");
        }

        // gender (look synsets for person or person name database)

        return prop;
    }

    public PosProp analyzeDeterminer(String wordForm, String spTag) {
        PosProp prop = new PosProp();
        prop.lemma = wordForm.toLowerCase();
        if (prop.lemma.equals("the")) {
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("ArDEF");
        } else if (prop.lemma.equals("a") || prop.lemma.equals("an")) {
            prop.lemma = "a";
            prop.posType = EngineKnowledgeBase.ctLabel2ctId("ArIDF");
        } else if (Arrays.binarySearch(MorphologicalDatabase.demonstrativePronouns, prop.lemma) >= 0) {
            return MorphologicalDatabase.getPronoun(prop);
        }
        return prop;
    }

    public PosProp analyzePersonalPronoun(String wordForm, String spTag) {
        PosProp prop = new PosProp();
        prop.lemma = wordForm.toLowerCase();
        if (prop.lemma.equals("i")) {
            prop.lemma = "I";
        }
        return MorphologicalDatabase.getPronoun(prop);
    }

    public PosProp analyzeAdjective(String wordForm, String wordTag) {
        PosProp prop = new PosProp();
        prop.lemma = wordForm.toLowerCase();
        if (Arrays.binarySearch(MorphologicalDatabase.possesivePronouns, prop.lemma) >= 0) {
            return MorphologicalDatabase.getPronoun(prop);
        }
        if (prop.lemma.equals("former") || prop.lemma.equals("former")) {
            return MorphologicalDatabase.getPronoun(prop);
        }
        return prop;
    }
}
