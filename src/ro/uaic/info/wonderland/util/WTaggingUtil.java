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
public final class WTaggingUtil {

    private static String indent = "        ";

    public static String areConsistent(WTagging gold, WTagging auto) {
        StringBuilder errStr = new StringBuilder();

        String goldStr = gold.getPos();
        String autoStr = auto.getPos();
        if (goldStr != null) {
            if (!goldStr.equals(autoStr)) {
                if (autoStr == null) {
                    autoStr = auto.getPennTag();
                }
                printErr(goldStr, autoStr, Corpus.wTag, errStr);
            }
        }

        goldStr = gold.getLemma();
        autoStr = auto.getLemma();
        checkTag(goldStr, autoStr, Corpus.lemmaTag, errStr);

        goldStr = gold.getMood();
        autoStr = auto.getMood();
        checkTag(goldStr, autoStr, Corpus.moodTag, errStr);

        goldStr = gold.getTense();
        autoStr = auto.getTense();
        checkTag(goldStr, autoStr, Corpus.tenseTag, errStr);

        goldStr = gold.getGender();
        autoStr = auto.getGender();
        checkTag(goldStr, autoStr, Corpus.genTag, errStr);

        goldStr = gold.getNumber();
        autoStr = auto.getNumber();
        checkTag(goldStr, autoStr, Corpus.numTag, errStr);

        goldStr = gold.getWcase();
        autoStr = auto.getWcase();
        checkTag(goldStr, autoStr, Corpus.caseTag, errStr);

        goldStr = gold.getPerson();
        autoStr = auto.getPerson();
        checkTag(goldStr, autoStr, Corpus.persTag, errStr);

        goldStr = gold.getComp();
        autoStr = auto.getComp();
        checkTag(goldStr, autoStr, Corpus.compTag, errStr);

        return ((errStr.length() > 0) ? (errStr.toString()) : (null));
    }

    private static void checkTag(String gold, String auto, String tag, StringBuilder errStr) {
        if (!areSame(gold, auto)) {
            printErr(gold, auto, tag, errStr);
        }
    }

    private static boolean areSame(String gold, String auto) {
        if (gold != null) {
            if (!gold.equals(auto)) {
                return false;
            }
        } else {
            if (auto != null) {
                return false;
            }
        }
        return true;
    }

    private static void printErr(String gold, String auto, String tag, StringBuilder errStr) {
        errStr.append(indent + tag + ": " + gold + " / " + auto + "\n");
    }
}
