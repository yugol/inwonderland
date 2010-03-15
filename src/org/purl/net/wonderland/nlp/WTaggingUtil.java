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

import org.purl.net.wonderland.util.Corpus;

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

        goldStr = gold.getArticle();
        autoStr = auto.getArticle();
        checkTag(goldStr, autoStr, Corpus.articleTag, errStr);

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

    public static void mergeWtags(WTagging from, WTagging to) {
        if ((from.getPos() != null) && (!from.getPos().equals("Pos"))) {
            to.setPos(from.getPos());
        }
        if (from.getGender() != null) {
            to.setGender(from.getGender());
        }
        if (from.getNumber() != null) {
            to.setNumber(from.getNumber());
        }
        if (from.getWcase() != null) {
            to.setWcase(from.getWcase());
        }
        if (from.getPerson() != null) {
            to.setPerson(from.getPerson());
        }
        if (from.getComp() != null) {
            to.setComp(from.getComp());
        }
        if (from.getMood() != null) {
            to.setMood(from.getMood());
        }
        if (from.getTense() != null) {
            to.setTense(from.getTense());
        }
    }
}
