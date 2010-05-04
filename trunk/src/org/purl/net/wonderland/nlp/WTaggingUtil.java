/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, mergeWtags, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.purl.net.wonderland.nlp;

import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.util.Corpus;
import org.purl.net.wonderland.util.MafCorpus;

/**
 *
 * @author Iulian
 */
public final class WTaggingUtil {

    private static String INDENT = "        ";

    public static String areConsistent(WTagging gold, WTagging auto) {
        StringBuilder errStr = new StringBuilder();

        String goldStr = gold.getPartOfSpeech();
        String autoStr = auto.getPartOfSpeech();
        if (goldStr != null) {
            if (!goldStr.equals(autoStr)) {
                if (autoStr == null) {
                    autoStr = auto.getPennTag();
                }
                printErr(goldStr, autoStr, WkbConstants.PARTOFSPEECH, errStr);
            }
        }

        goldStr = gold.getLemma();
        autoStr = auto.getLemma();
        checkTag(goldStr, autoStr, MafCorpus.WORDFORM_ATTR_LEMMA, errStr);

        goldStr = gold.getVerbFormMood();
        autoStr = auto.getVerbFormMood();
        checkTag(goldStr, autoStr, WkbConstants.VERBFORMMOOD, errStr);

        goldStr = gold.getGrammaticalTense();
        autoStr = auto.getGrammaticalTense();
        checkTag(goldStr, autoStr, WkbConstants.GRAMMATICALTENSE, errStr);

        goldStr = gold.getGrammaticalGender();
        autoStr = auto.getGrammaticalGender();
        checkTag(goldStr, autoStr, WkbConstants.GRAMMATICALGENDER, errStr);

        goldStr = gold.getGrammaticalNumber();
        autoStr = auto.getGrammaticalNumber();
        checkTag(goldStr, autoStr, WkbConstants.GRAMMATICALNUMBER, errStr);

        goldStr = gold.getGrammaticalCase();
        autoStr = auto.getGrammaticalCase();
        checkTag(goldStr, autoStr, WkbConstants.GRAMMATICALCASE, errStr);

        goldStr = gold.getPerson();
        autoStr = auto.getPerson();
        checkTag(goldStr, autoStr, WkbConstants.PERSON, errStr);

        goldStr = gold.getDegree();
        autoStr = auto.getDegree();
        checkTag(goldStr, autoStr, WkbConstants.DEGREE, errStr);

        goldStr = gold.getDefiniteness();
        autoStr = auto.getDefiniteness();
        checkTag(goldStr, autoStr, WkbConstants.DEFINITNESS, errStr);

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
        errStr.append(INDENT + tag + ": " + gold + " / " + auto + "\n");
    }

    public static void mergeWtags(WTagging from, WTagging to) {
        if ((from.getPartOfSpeech() != null) && (!from.getPartOfSpeech().equals("Pos"))) {
            to.setPartOfSpeech(from.getPartOfSpeech());
        }
        if (from.getGrammaticalGender() != null) {
            to.setGrammaticalGender(from.getGrammaticalGender());
        }
        if (from.getGrammaticalNumber() != null) {
            to.setGrammaticalNumber(from.getGrammaticalNumber());
        }
        if (from.getGrammaticalCase() != null) {
            to.setGrammaticalCase(from.getGrammaticalCase());
        }
        if (from.getPerson() != null) {
            to.setPerson(from.getPerson());
        }
        if (from.getDegree() != null) {
            to.setDegree(from.getDegree());
        }
        if (from.getVerbFormMood() != null) {
            to.setVerbFormMood(from.getVerbFormMood());
        }
        if (from.getGrammaticalTense() != null) {
            to.setGrammaticalTense(from.getGrammaticalTense());
        }
    }
}
