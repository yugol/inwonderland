/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import ro.uaic.info.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian
 */
public class WTaggingUtil {

    private static String indent = "        ";

    public static String areConsistent(WTagging gold, WTagging auto) {
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
        if (gold.wTag != null) {
            if (!gold.wTag.equals(auto.wTag)) {
                sb.append(indent + Corpus.wTag + ": " + gold.wTag + " / ");
                if (auto.wTag == null) {
                    sb.append(auto.pennTag);
                } else {
                    sb.append(auto.wTag);
                }
                sb.append("\n");
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
