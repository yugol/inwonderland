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
        if (gold.getPos() != null) {
            if (!gold.getPos().equals(auto.getPos())) {
                sb.append(indent + Corpus.wTag + ": " + gold.getPos() + " / ");
                if (auto.getPos() == null) {
                    sb.append(auto.getPennTag());
                } else {
                    sb.append(auto.getPos());
                }
                sb.append("\n");
            }
        }
        if (!gold.getLemma().equals(auto.getLemma())) {
            sb.append(indent + Corpus.lemmaTag + ": " + gold.getLemma() + " / " + auto.getLemma() + "\n");
        }
        if (gold.getComp() != null) {
            if (!gold.getComp().equals(auto.getComp())) {
                sb.append(indent + Corpus.compTag + ": " + gold.getComp() + " / " + auto.getComp() + "\n");
            }
        }
        if (gold.getGender() != null) {
            if (!gold.getGender().equals(auto.getGender())) {
                sb.append(indent + Corpus.genTag + ": " + gold.getGender() + " / " + auto.getGender() + "\n");
            }
        }
        if (gold.getMood() != null) {
            if (!gold.getMood().equals(auto.getMood())) {
                sb.append(indent + Corpus.moodTag + ": " + gold.getMood() + " / " + auto.getMood() + "\n");
            }
        }
        if (gold.getNumber() != null) {
            if (!gold.getNumber().equals(auto.getNumber())) {
                sb.append(indent + Corpus.numTag + ": " + gold.getNumber() + " / " + auto.getNumber() + "\n");
            }
        }
        if (gold.getPerson() != null) {
            if (!gold.getPerson().equals(auto.getPerson())) {
                sb.append(indent + Corpus.persTag + ": " + gold.getPerson() + " / " + auto.getPerson() + "\n");
            }
        }
        if (gold.getTense() != null) {
            if (!gold.getTense().equals(auto.getTense())) {
                sb.append(indent + Corpus.tenseTag + ": " + gold.getTense() + " / " + auto.getTense() + "\n");
            }
        }
        if (gold.getWcase() != null) {
            if (!gold.getWcase().equals(auto.getWcase())) {
                sb.append(indent + Corpus.caseTag + ": " + gold.getWcase() + " / " + auto.getWcase() + "\n");
            }
        }

        return ((sb.length() > 0) ? (sb.toString()) : (null));
    }
}
