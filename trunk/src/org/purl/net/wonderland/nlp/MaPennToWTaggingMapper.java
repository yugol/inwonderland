/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
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

import java.util.List;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;

/**
 *
 * @author Iulian
 */
public class MaPennToWTaggingMapper {

    private static final String noLemma = "_~_";

    void mapWTags(List<WTagging> sentence) {
        for (WTagging tagging : sentence) {
            String tag = tagging.getPennTag();

            if (tagging.isCollocation()) {
                // System.out.println(tagging.getWrittenForm());
                String posTypes = CollocationManager.getTypes(tagging.getLemma());
                if ("adverb".equals(posTypes)) {
                    tagging.setPartOfSpeech("adverb");
                    continue;
                }
                if ("adjective".equals(posTypes)) {
                    tagging.setPartOfSpeech("adjective");
                    continue;
                }
            }

            if (tag.indexOf("NN") == 0) { // NN, NNP, NNS, NNPS
                mapNN(tagging, tag);
            } else if (tag.indexOf("VB") == 0) { // VBZ, VBP, VB, VBG
                mapVB(tagging, tag);
            } else if (tag.equals("IN")) { // IN
                mapIN(tagging, tag);
            } else if (tag.equals("DT")) { // DT
                mapDT(tagging, tag);
            } else if (tag.equals("CC")) { // CC
                mapCC(tagging, tag);
            } else if (tag.equals("PRP")) { // PRP
                mapPRP(tagging, tag);
            } else if (tag.equals("TO")) { // TO
                mapTO(tagging, tag);
            } else if (tag.indexOf("RB") == 0) { // RB
                mapRB(tagging, tag);
            } else if (tag.indexOf("JJ") == 0) { // JJ
                mapJJ(tagging, tag);
            } else if (tag.equals("modal")) { // MD
                mapMD(tagging, tag);
            } else if (tag.equals("WDT")) { // WDT
                mapWDT(tagging, tag);
            } else if (tag.equals("CD")) { // CD
                mapCD(tagging, tag);
            } else if (tag.equals("PRP$")) { // PRP$
                mapPRPS(tagging, tag);
            } else if (tag.equals("WRB")) { // WRB
                mapWRB(tagging, tag);
            } else if (tag.equals("EX")) { // EX
                mapEX(tagging, tag);
            } else if (tag.equals("WP")) { // WP
                mapWP(tagging, tag);
            } else if (tag.equals("FW")) { // FW
                mapFW(tagging, tag);
            } else if (tag.equals("RP")) { // RP
                mapRP(tagging, tag);
            } else if (tag.equals("POS")) { // POS
                mapPOS(tagging, tag);
            } else if (tag.equals("PDT")) { // PDT
                mapPDT(tagging, tag);
            } else if (tag.equals("UH")) { // UH
                mapUH(tagging, tag);
            }
        }
    }

    public void mapDT(WTagging tagging, String tag) {
        String maTag = tagging.getPartsOfSpeech();
        if ("av-dx".equals(maTag)) {
            tagging.setPartOfSpeech("adverb");
            return;
        }

        String word = tagging.getWrittenForm().toLowerCase();
        WTagging ar = MorphologicalDatabase.article.get(word);
        WTagging jjind = MorphologicalDatabase.indefiniteDeterminer.get(word);
        WTagging jjdem = MorphologicalDatabase.demonstrativeDeterminer.get(word);
        WTagging pndem = MorphologicalDatabase.demonstrativePronoun.get(word);
        WTagging pnpos = MorphologicalDatabase.possessivePronoun.get(word);

        if (ar != null && jjind == null && jjdem == null && pndem == null && pnpos == null) {
            tagging.copyWTags(ar);
        } else if (ar == null && jjind != null && jjdem == null && pndem == null && pnpos == null) {
            tagging.copyWTags(jjind);
        } else if (ar == null && jjind == null && jjdem != null && pndem == null && pnpos == null) {
            tagging.copyWTags(jjdem);
        } else if (ar == null && jjind == null && jjdem != null && pndem != null && pnpos == null) {
            tagging.copyWTags(jjdem);
            if (maTag != null && tagging.getPartsOfSpeech().charAt(0) == 'd') {
                tagging.setPartOfSpeech("demonstrativeDeterminer");
            } else {
                tagging.setPartOfSpeech("demonstrativeDeterminer;demonstrativePronoun");
            }
        } else if (ar == null && jjind == null && jjdem == null && pndem == null && pnpos != null) {
            tagging.copyWTags(pnpos);
        }
    }

    public void mapNN(WTagging tagging, String tag) {

        String maTag = tagging.getPartsOfSpeech();
        if (maTag != null) {
            if ((maTag.indexOf("pi") == 0) || (maTag.indexOf("n2-jn") == 0)) {
                if (tryIndefinitePronoun(tagging)) {
                    return;
                }
            } else if (maTag.indexOf("av") == 0) {
                mapRB(tagging, tag);
                return;
            } else if (maTag.indexOf("crd") == 0) {
                String word = tagging.getWrittenForm().toLowerCase();
                String number = TextToNumber.getValue(word);
                if (number != null) {
                    tagging.setLemma(number);
                    tagging.setPartOfSpeech("cardinalNumeral");
                    return;
                }
            } else if (maTag.indexOf("v") == 0) {
                if (maTag.charAt(2) == 'g') {
                    tagging.setPartOfSpeech("verb");
                    tagging.setVerbFormMood("gerund");
                    return;
                } else if (maTag.charAt(2) == 'd') {
                    tagging.setPartOfSpeech("verb");
                    tagging.setVerbFormMood("indicative");
                    tagging.setGrammaticalTense("past");
                    return;
                } else if (maTag.charAt(2) == 'z') {
                    tagging.setPartOfSpeech("verb");
                    tagging.setVerbFormMood("indicative");
                    tagging.setGrammaticalTense("present");
                    tagging.setGrammaticalNumber("singular");
                    tagging.setPerson("thirdPerson");
                    return;
                }
            } else if (maTag.indexOf("vvg") > 0) {
                tagging.setVerbFormMood("gerund");
            }
        }

        if (tag.indexOf("NNP") == 0) {
            tagging.setPartOfSpeech("properNoun");
        } else {
            tagging.setPartOfSpeech("commonNoun");
        }

        if (tag.charAt(tag.length() - 1) == 'S') {
            tagging.setGrammaticalNumber("plural");
        } else {
            tagging.setGrammaticalNumber("singular");
        }

    }

    public void mapVB(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();

        WTagging vb = MorphologicalDatabase.verb.get(word);

        if (vb != null) {
            tagging.copyWTagsAndLemma(vb);
        } else {
            tagging.setPartOfSpeech("verb");
            String maTag = tagging.getPartsOfSpeech();

            if (maTag != null) {
                if (maTag.indexOf("vd") == 0) {
                    tagging.setLemma("do");
                }
                if (maTag.indexOf("vh") == 0) {
                    tagging.setLemma("have");
                }
            }

            if (tag.equals("VBZ")) {
                tagging.setVerbFormMood("indicative");
                tagging.setGrammaticalNumber("singular");
                tagging.setPerson("thirdPerson");
                tagging.setGrammaticalTense("present");
            } else if (tag.equals("VBP")) {
                if (maTag != null) {
                    if (maTag.indexOf("j") == 0) {
                        tagging.setPartOfSpeech("adjective");
                        return;
                    } else if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(1) == 'm') {
                            tagging.setPartOfSpeech("modal");
                            if (maTag.charAt(2) == 'd') {
                                tagging.setGrammaticalTense("past");
                            } else {
                                tagging.setGrammaticalTense("present");
                            }
                            return;
                        }
                        if (maTag.charAt(2) == 'd') {
                            tagging.setVerbFormMood("indicative");
                            tagging.setGrammaticalTense("past");
                            return;
                        }
                    }
                }
                tagging.setVerbFormMood("indicative");
                tagging.setGrammaticalTense("present");
            } else if (tag.equals("VB")) {
                if (maTag != null) {
                    if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(1) == 'm') {
                            tagging.setPartOfSpeech("modal");
                            if (maTag.charAt(2) == 'd') {
                                tagging.setGrammaticalTense("past");
                            } else {
                                tagging.setGrammaticalTense("present");
                            }
                            return;
                        }
                        if (maTag.charAt(2) == 'i') {
                            tagging.setVerbFormMood("infinitive");
                        } else if (maTag.charAt(2) == 'b') {
                            if (maTag.charAt(1) == 'b') {
                                tagging.setVerbFormMood("infinitive");
                            } else {
                                tagging.setVerbFormMood("indicative");
                                tagging.setGrammaticalTense("present");
                            }
                        } else if (maTag.charAt(2) == 'd') {
                            tagging.setVerbFormMood("indicative");
                            tagging.setGrammaticalTense("past");
                        } else if (maTag.charAt(2) == 'n') {
                            tagging.setVerbFormMood("participle");
                            tagging.setGrammaticalTense("past");
                        }
                    } else if (maTag.indexOf("j") == 0) {
                        IndexWord wnWord = WordNetWrapper.lookup(word, POS.ADJECTIVE);
                        if (wnWord == null) {
                            wnWord = WordNetWrapper.lookup(word, POS.NOUN);
                            if (wnWord != null) {
                                tagging.setPartOfSpeech("commonNoun");
                                tagging.setGrammaticalNumber("singular");
                            }
                        } else {
                            tagging.setPartOfSpeech("adjective");
                        }
                    }
                }
            } else if (tag.equals("VBD")) {
                tagging.setVerbFormMood("indicative");
                tagging.setGrammaticalTense("past");
            } else if (tag.equals("VBG")) {
                if (maTag != null) {
                    if (maTag.equals("j-vvg")) {
                        tagging.setPartOfSpeech("adjective");
                        tagging.setVerbFormMood("gerund");
                        return;
                    }
                }
                tagging.setVerbFormMood("gerund");
                if (word.equals("being")) {
                    tagging.setLemma("be");
                }
            } else if (tag.equals("VBN")) {
                if (maTag != null) {
                    if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(2) == 'g') {
                            tagging.setVerbFormMood("gerund");
                            return;
                        }
                    }
                }
                tagging.setVerbFormMood("participle");
                tagging.setGrammaticalTense("past");
            }
        }
    }

    void mapIN(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();

        if (tagging.isCollocation()) {
            String pos = CollocationManager.getTypes(word);
            if (pos.indexOf("adverb") >= 0) {
                tagging.setPartOfSpeech("adverb");
                return;
            }
        }

        WTagging pr = MorphologicalDatabase.adposition.get(word);
        WTagging cjsub = MorphologicalDatabase.subordinatingConjunction.get(word);

        if (pr != null && cjsub == null) {
            tagging.copyWTags(pr);
        } else if (pr == null && cjsub != null) {
            tagging.copyWTags(cjsub);
        } else {
            String maTag = tagging.getPartsOfSpeech();
            if (maTag != null) {
                if (maTag.indexOf("c") == 0) {
                    tagging.setPartOfSpeech("subordinatingConjunction");
                    return;
                } else if (maTag.indexOf("p") == 0) {
                    tagging.setPartOfSpeech("adposition");
                    return;
                } else if (maTag.equals("vvg")) {
                    tagging.setPartOfSpeech("verb");
                    tagging.setVerbFormMood("gerund");
                    return;
                } else if (maTag.equals("a-acp")) {
                    tagging.setPartOfSpeech("adverb");
                    return;
                }
            }
            tagging.setPartOfSpeech("subordinatingConjunction;adposition");
        }
    }

    void mapCC(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging cjcrd = MorphologicalDatabase.coordinatingConjunction.get(word);

        if (cjcrd != null) {
            tagging.copyWTags(cjcrd);
        } else {
            tagging.setPartOfSpeech("coordinatingConjunction");
        }
    }

    void mapPRP(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging pnprs = MorphologicalDatabase.personalPronoun.get(word);
        WTagging pnref = MorphologicalDatabase.reflexivePersonalPronoun.get(word);

        if (pnprs != null && pnref == null) {
            tagging.copyWTags(pnprs);
        } else if (pnprs == null && pnref != null) {
            tagging.copyWTags(pnref);
        }
    }

    void mapTO(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging pr = MorphologicalDatabase.adposition.get(word);

        if (pr != null && pr.getLemma().equals("to")) {
            tagging.copyWTags(pr);
        }
    }

    void mapRB(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging rb = MorphologicalDatabase.adverb.get(word);

        if (rb != null) {
            tagging.copyWTagsAndLemma(rb);
            return;
        } else {
            tagging.setPartOfSpeech("adverb");
        }

        if (tag.equals("RBR")) {
            tagging.setDegree("comparative");
        } else if (tag.equals("RBS")) {
            tagging.setDegree("superlative");
        }

        String maTag = tagging.getPartsOfSpeech();
        if (maTag != null) {
            if (maTag.indexOf("av") == 0) {
                // if (maTag.indexOf("-j") == 2) {
                if ((word.lastIndexOf("ly") == (word.length() - 2)) && (!word.equals(tagging.getLemma()))) {
                    tagging.setPartOfSpeech("mannerAdverb");
                    tagging.setLemma(word);
                }
                // }
                if (maTag.indexOf('c') > 0) {
                    tagging.setDegree("comparative");
                }
            } else if (maTag.indexOf('n') == 0) {
                tagging.setPartOfSpeech("commonNoun");
                if (maTag.indexOf("1") > 0) {
                    tagging.setGrammaticalNumber("singular");
                } else if (maTag.indexOf('2') > 0) {
                    tagging.setGrammaticalNumber("plural");
                }
            } else if (maTag.indexOf("jc") == 0) {
                tagging.setDegree("comparative");
            }
        }
    }

    void mapJJ(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        String maTag = tagging.getPartsOfSpeech();

        WTagging jj = MorphologicalDatabase.adjective.get(word);
        if (jj != null) {
            tagging.copyWTags(jj);
            return;
        }

        WTagging jjidf = MorphologicalDatabase.indefiniteDeterminer.get(word);
        WTagging jjdem = MorphologicalDatabase.demonstrativeDeterminer.get(word);

        if (jjidf != null && jjdem == null) {
            tagging.copyWTagsAndLemma(jjidf);
            return;
        }
        if (jjidf == null && jjdem != null) {
            tagging.copyWTagsAndLemma(jjdem);
            return;
        }
        if (jjidf != null && jjdem != null) {
            if (maTag != null) {
                if (maTag.indexOf('d') == 0) {
                    tagging.copyWTagsAndLemma(jjdem);
                } else {
                    tagging.copyWTagsAndLemma(jjidf);
                }
                return;
            }
        }

        // see if is not an ordinal numeral
        String nmord = TextToNumber.getValue(word);
        if (nmord != null) {
            tagging.setLemma(nmord);
            tagging.setPartOfSpeech("ordinalAdjective");
            return;
        }

        tagging.setPartOfSpeech("adjective");
        if (tag.equals("JJR")) {
            tagging.setDegree("comparative");
        } else if (tag.equals("JJS")) {
            tagging.setDegree("superlative");
        }

        if (maTag != null) {
            if ("j-vvn".equals(maTag)) {
                tagging.setVerbFormMood("participle");
                tagging.setGrammaticalTense("past");
            } else if (maTag.indexOf("vvg") >= 0) {
                tagging.setVerbFormMood("gerund");
                if (maTag.equals("vvg")) {
                    tagging.setPartOfSpeech("verb");
                    tagging.setVerbFormMood("gerund");
                }
            } else if (maTag.indexOf("av") == 0) {
                tagging.setPartOfSpeech("adverb");
                return;
            }
        }
    }

    void mapMD(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging md = MorphologicalDatabase.modal.get(word);

        if (md != null) {
            tagging.copyWTagsAndLemma(md);
        }
    }

    void mapWDT(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging pnrel = MorphologicalDatabase.relativePronoun.get(word);
        WTagging cjsub = MorphologicalDatabase.subordinatingConjunction.get(word);

        if (pnrel != null && cjsub == null) {
            tagging.copyWTags(pnrel);
        } else if (pnrel == null && cjsub != null) {
            tagging.copyWTags(cjsub);
        } else if (pnrel != null && cjsub != null) {
            tagging.copyWTags(cjsub);
            String maTag = tagging.getPartsOfSpeech();
            if (maTag != null) {
                if (maTag.indexOf("cs") == 0) {
                    tagging.setPartOfSpeech("subordinatingConjunction");
                } else if (maTag.indexOf("r-crq") == 0) {
                    tagging.setPartOfSpeech("relativePronoun");
                }
            } else {
                tagging.setPartOfSpeech("subordinatingConjunction;relativePronoun");
            }
        }
    }

    void mapCD(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        tagging.setPartOfSpeech("cardinalNumeral");
        tagging.setLemma(TextToNumber.getValue(word));

        if (tagging.getLemma() == null) {
            tagging.setLemma(noLemma);
        }
    }

    void mapPRPS(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging pnpos = MorphologicalDatabase.possessivePronoun.get(word);
        WTagging jjpos = MorphologicalDatabase.possessiveAdjective.get(word);

        if (pnpos != null && jjpos == null) {
            tagging.copyWTags(pnpos);
        } else if (pnpos == null && jjpos != null) {
            tagging.copyWTags(jjpos);
        } else if (pnpos != null && jjpos != null) {
            String maTag = tagging.getPartsOfSpeech();
            if (maTag != null) {
                if (maTag.indexOf("png") == 0) {
                    tagging.setPartOfSpeech("possessivePronoun");
                } else if (maTag.indexOf("po") == 0) {
                    tagging.setPartOfSpeech("possessiveAdjective");
                }
            } else {
                tagging.setLemma(pnpos.getLemma());
                tagging.setPartOfSpeech("possessiveAdjective;possessivePronoun");
            }
        }
    }

    void mapWRB(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging rbint = MorphologicalDatabase.interrogativeAdverb.get(word);

        if (rbint != null) {
            tagging.copyWTags(rbint);
        }

        String maTag = tagging.getPartsOfSpeech();
        if (maTag != null) {
            if ("q-crq".equals(maTag)) {
                tagging.setPartOfSpeech("interrogativeAdverb");
            } else if ("c-crq".equals(maTag)) {
                tagging.setPartOfSpeech("relativeAdverb");
            } else if ("vhdx".equals(maTag)) {
                tagging.setPartOfSpeech("verb");
                tagging.setVerbFormMood("indicative");
                tagging.setGrammaticalTense("past");
            } else if ("a-acp".equals(maTag)) {
                tagging.setPartOfSpeech("relativeAdverb");
            } else if ("cs".equals(maTag)) {
                tagging.setPartOfSpeech("relativeAdverb");
            }
        }
    }

    void mapEX(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        String maTag = tagging.getPartsOfSpeech();

        if (word.equals("there")) {
            if ("a-acp".equals(maTag)) {
                tagging.setPartOfSpeech("adverb");
            } else {
                tagging.setPartOfSpeech("existentialPronoun");
            }
        }
    }

    void mapWP(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging pnint = MorphologicalDatabase.interrogativePronoun.get(word);

        if (pnint != null) {
            tagging.copyWTags(pnint);
        }
    }

    void mapFW(WTagging tagging, String tag) {
        // String word = tagging.getWrittenForm().toLowerCase();
        String maTag = tagging.getPartsOfSpeech();

        if (maTag != null) {
            if (maTag.indexOf("vvg") >= 0) {
                tagging.setPartOfSpeech("verb");
                tagging.setVerbFormMood("gerund");
            }
        }
    }

    void mapRP(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging rb = MorphologicalDatabase.adverb.get(word);
        WTagging pr = MorphologicalDatabase.adposition.get(word);

        if (rb != null && pr == null) {
            tagging.copyWTags(rb);
            tagging.setPartOfSpeech("generalAdverb");
        } else if (rb == null && pr != null) {
            tagging.copyWTags(pr);
        } else {
            tagging.setPartOfSpeech("adposition;generalAdverb");
            if (rb != null) {
                tagging.setLemma(rb.getLemma());
            } else {
                tagging.setLemma(noLemma);
            }
        }
    }

    void mapPOS(WTagging tagging, String tag) {
        tagging.setLemma("$");
        tagging.setPartOfSpeech("possessiveParticle");
    }

    /*
    void mapCollocation(WTagging tagging) {
    String word = tagging.getWrittenForm().toLowerCase();
    WTagging collocation = MorphologicalDatabase.collocations.get(word);

    if (collocation != null) {
    tagging.copyWTags(collocation);

    }
    }
     * 
     */
    private boolean tryIndefinitePronoun(WTagging tagging) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging pnidf = MorphologicalDatabase.indefinitePronoun.get(word);
        if (pnidf != null) {
            tagging.copyWTagsAndLemma(pnidf);
            return true;
        }
        return false;
    }

    private void mapPDT(WTagging tagging, String tag) {
        mapDT(tagging, tag);
    }

    private void mapUH(WTagging tagging, String tag) {
        tagging.setPartOfSpeech("interjection");
    }
}
