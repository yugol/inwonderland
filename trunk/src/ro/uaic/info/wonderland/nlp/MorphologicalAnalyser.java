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
}
