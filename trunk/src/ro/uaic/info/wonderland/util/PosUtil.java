/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import ro.uaic.info.wonderland.analysis.PosProp;

/**
 *
 * @author Iulian
 */
public class PosUtil {

    private static String indent = "        ";

    public static String areConsistent(PosProp gold, PosProp auto) {
        StringBuilder sb = new StringBuilder();
        if (gold.comparison != null) {
            if (!gold.comparison.equals(auto.comparison)) {
                sb.append(indent + Corpus.compTag + ": " + gold.comparison + " / " + auto.comparison + "\n");
            }
        }
        if (gold.gender != null) {
            if (!gold.gender.equals(auto.gender)) {
                sb.append(indent + Corpus.genTag + ": " + gold.gender + " / " + auto.gender + "\n");
            }
        }
        if (gold.mood != null) {
            if (!gold.mood.equals(auto.mood)) {
                sb.append(indent + Corpus.moodTag + ": " + gold.mood + " / " + auto.mood + "\n");
            }
        }
        if (gold.number != null) {
            if (!gold.number.equals(auto.number)) {
                sb.append(indent + Corpus.numTag + ": " + gold.number + " / " + auto.number + "\n");
            }
        }
        if (gold.person != null) {
            if (!gold.person.equals(auto.person)) {
                sb.append(indent + Corpus.persTag + ": " + gold.person + " / " + auto.person + "\n");
            }
        }
        if (gold.posType != null) {
            if (!gold.posType.equals(auto.posType)) {
                sb.append(indent + Corpus.posTag + ": " + gold.posType + " / " + auto.posType + "\n");
            }
        }
        if (gold.tense != null) {
            if (!gold.tense.equals(auto.tense)) {
                sb.append(indent + Corpus.tenseTag + ": " + gold.tense + " / " + auto.tense + "\n");
            }
        }
        if (gold.theCase != null) {
            if (!gold.theCase.equals(auto.theCase)) {
                sb.append(indent + Corpus.caseTag + ": " + gold.theCase + " / " + auto.theCase + "\n");
            }
        }

        return ((sb.length() > 0) ? (sb.toString()) : (null));
    }
}
