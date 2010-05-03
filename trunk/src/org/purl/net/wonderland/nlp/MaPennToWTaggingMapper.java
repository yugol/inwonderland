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
import org.purl.net.wonderland.kb.WKBUtil;
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
                String posTypes = CollocationsManager.getTypes(tagging.getLemma());
                if (WKBUtil.ADVERB.equals(posTypes)) {
                    tagging.setPartOfSpeech(WKBUtil.ADVERB);
                    continue;
                }
                if (WKBUtil.ADJECTIVE.equals(posTypes)) {
                    tagging.setPartOfSpeech(WKBUtil.ADJECTIVE);
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
            } else if (tag.equals("MD")) { // MD
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
            } else {
                mapPunct(tagging, tag);
            }
        }
    }

    public void mapDT(WTagging tagging, String tag) {
        String maTag = tagging.getPartsOfSpeech();
        if ("av-dx".equals(maTag)) {
            tagging.setPartOfSpeech(WKBUtil.ADVERB);
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
                tagging.setPartOfSpeech(WKBUtil.DEMONSTRATIVEDETERMINER);
            } else {
                tagging.setPartOfSpeech(WKBUtil.DEMONSTRATIVEDETERMINER + WKBUtil.TYPE_SEPARATOR + WKBUtil.DEMONSTRATICEPRONOUN);
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
                    tagging.setPartOfSpeech(WKBUtil.CARDINALNUMBER);
                    return;
                }
            } else if (maTag.indexOf("v") == 0) {
                if (maTag.charAt(2) == 'g') {
                    tagging.setPartOfSpeech(WKBUtil.VERB);
                    tagging.setVerbFormMood(WKBUtil.GERUND);
                    return;
                } else if (maTag.charAt(2) == 'd') {
                    tagging.setPartOfSpeech(WKBUtil.VERB);
                    tagging.setVerbFormMood(WKBUtil.INDICATIVE);
                    tagging.setGrammaticalTense(WKBUtil.PAST);
                    return;
                } else if (maTag.charAt(2) == 'z') {
                    tagging.setPartOfSpeech(WKBUtil.VERB);
                    tagging.setVerbFormMood(WKBUtil.INDICATIVE);
                    tagging.setGrammaticalTense(WKBUtil.PRESENT);
                    tagging.setGrammaticalNumber(WKBUtil.SINGULAR);
                    tagging.setPerson(WKBUtil.THIRDPERSON);
                    return;
                }
            } else if (maTag.indexOf("vvg") > 0) {
                tagging.setVerbFormMood(WKBUtil.GERUND);
            }
        }

        if (tag.indexOf("NNP") == 0) {
            tagging.setPartOfSpeech(WKBUtil.PROPERNOUN);
        } else {
            tagging.setPartOfSpeech(WKBUtil.COMMONNOUN);
        }

        if (tag.charAt(tag.length() - 1) == 'S') {
            tagging.setGrammaticalNumber(WKBUtil.PLURAL);
        } else {
            tagging.setGrammaticalNumber(WKBUtil.SINGULAR);
        }

    }

    public void mapVB(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();

        WTagging vb = MorphologicalDatabase.verb.get(word);

        if (vb != null) {
            tagging.copyWTagsAndLemma(vb);
        } else {
            tagging.setPartOfSpeech(WKBUtil.VERB);
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
                tagging.setVerbFormMood(WKBUtil.INDICATIVE);
                tagging.setGrammaticalNumber(WKBUtil.SINGULAR);
                tagging.setPerson(WKBUtil.THIRDPERSON);
                tagging.setGrammaticalTense(WKBUtil.PRESENT);
            } else if (tag.equals("VBP")) {
                if (maTag != null) {
                    if (maTag.indexOf("j") == 0) {
                        tagging.setPartOfSpeech(WKBUtil.ADJECTIVE);
                        return;
                    } else if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(1) == 'm') {
                            tagging.setPartOfSpeech(WKBUtil.MODAL);
                            if (maTag.charAt(2) == 'd') {
                                tagging.setGrammaticalTense(WKBUtil.PAST);
                            } else {
                                tagging.setGrammaticalTense(WKBUtil.PRESENT);
                            }
                            return;
                        }
                        if (maTag.charAt(2) == 'd') {
                            tagging.setVerbFormMood(WKBUtil.INDICATIVE);
                            tagging.setGrammaticalTense(WKBUtil.PAST);
                            return;
                        }
                    }
                }
                tagging.setVerbFormMood(WKBUtil.INDICATIVE);
                tagging.setGrammaticalTense(WKBUtil.PRESENT);
            } else if (tag.equals("VB")) {
                if (maTag != null) {
                    if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(1) == 'm') {
                            tagging.setPartOfSpeech(WKBUtil.MODAL);
                            if (maTag.charAt(2) == 'd') {
                                tagging.setGrammaticalTense(WKBUtil.PAST);
                            } else {
                                tagging.setGrammaticalTense(WKBUtil.PRESENT);
                            }
                            return;
                        }
                        if (maTag.charAt(2) == 'i') {
                            tagging.setVerbFormMood(WKBUtil.INFINITIVE);
                        } else if (maTag.charAt(2) == 'b') {
                            if (maTag.charAt(1) == 'b') {
                                tagging.setVerbFormMood(WKBUtil.INFINITIVE);
                            } else {
                                tagging.setVerbFormMood(WKBUtil.INDICATIVE);
                                tagging.setGrammaticalTense(WKBUtil.PRESENT);
                            }
                        } else if (maTag.charAt(2) == 'd') {
                            tagging.setVerbFormMood(WKBUtil.INDICATIVE);
                            tagging.setGrammaticalTense(WKBUtil.PAST);
                        } else if (maTag.charAt(2) == 'n') {
                            tagging.setVerbFormMood(WKBUtil.PARTICIPLE);
                            tagging.setGrammaticalTense(WKBUtil.PAST);
                        }
                    } else if (maTag.indexOf("j") == 0) {
                        IndexWord wnWord = WordNetWrapper.lookup(word, POS.ADJECTIVE);
                        if (wnWord == null) {
                            wnWord = WordNetWrapper.lookup(word, POS.NOUN);
                            if (wnWord != null) {
                                tagging.setPartOfSpeech(WKBUtil.COMMONNOUN);
                                tagging.setGrammaticalNumber(WKBUtil.SINGULAR);
                            }
                        } else {
                            tagging.setPartOfSpeech(WKBUtil.ADJECTIVE);
                        }
                    }
                }
            } else if (tag.equals("VBD")) {
                tagging.setVerbFormMood(WKBUtil.INDICATIVE);
                tagging.setGrammaticalTense(WKBUtil.PAST);
            } else if (tag.equals("VBG")) {
                if (maTag != null) {
                    if (maTag.equals("j-vvg")) {
                        tagging.setPartOfSpeech(WKBUtil.ADJECTIVE);
                        tagging.setVerbFormMood(WKBUtil.GERUND);
                        return;
                    }
                }
                tagging.setVerbFormMood(WKBUtil.GERUND);
                if (word.equals("being")) {
                    tagging.setLemma("be");
                }
            } else if (tag.equals("VBN")) {
                if (maTag != null) {
                    if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(2) == 'g') {
                            tagging.setVerbFormMood(WKBUtil.GERUND);
                            return;
                        }
                    }
                }
                tagging.setVerbFormMood(WKBUtil.PARTICIPLE);
                tagging.setGrammaticalTense(WKBUtil.PAST);
            }
        }
    }

    void mapIN(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();

        if (tagging.isCollocation()) {
            String pos = CollocationsManager.getTypes(word);
            if (pos.indexOf(WKBUtil.ADVERB) >= 0) {
                tagging.setPartOfSpeech(WKBUtil.ADVERB);
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
                    tagging.setPartOfSpeech(WKBUtil.SUBORDONATINGCONJUNCTION);
                    return;
                } else if (maTag.indexOf("p") == 0) {
                    tagging.setPartOfSpeech(WKBUtil.ADPOSITION);
                    return;
                } else if (maTag.equals("vvg")) {
                    tagging.setPartOfSpeech(WKBUtil.VERB);
                    tagging.setVerbFormMood(WKBUtil.GERUND);
                    return;
                } else if (maTag.equals("a-acp")) {
                    tagging.setPartOfSpeech(WKBUtil.ADVERB);
                    return;
                }
            }
            tagging.setPartOfSpeech(WKBUtil.SUBORDONATINGCONJUNCTION + WKBUtil.TYPE_SEPARATOR + WKBUtil.ADPOSITION);
        }
    }

    void mapCC(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging cjcrd = MorphologicalDatabase.coordinatingConjunction.get(word);

        if (cjcrd != null) {
            tagging.copyWTags(cjcrd);
        } else {
            tagging.setPartOfSpeech(WKBUtil.COORDINATINGCONJUNCTION);
        }
    }

    void mapPRP(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();

        WTagging pnprs = MorphologicalDatabase.personalPronoun.get(word);
        WTagging pnref = MorphologicalDatabase.reflexivePersonalPronoun.get(word);
        WTagging pnidf = MorphologicalDatabase.indefinitePronoun.get(word);

        if (pnprs != null) {
            tagging.copyWTags(pnprs);
        } else if (pnref != null) {
            tagging.copyWTags(pnref);
        } else if (pnidf != null) {
            tagging.copyWTags(pnidf);
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
            tagging.setPartOfSpeech(WKBUtil.ADVERB);
            return;
        } else {
            tagging.setPartOfSpeech(WKBUtil.ADVERB);
        }

        if (tag.equals("RBR")) {
            tagging.setDegree(WKBUtil.COMPARATIVE);
        } else if (tag.equals("RBS")) {
            tagging.setDegree(WKBUtil.SUPERLATIVE);
        }

        String maTag = tagging.getPartsOfSpeech();
        if (maTag != null) {
            if (maTag.indexOf("av") == 0) {
                // if (maTag.indexOf("-j") == 2) {
                if ((word.lastIndexOf("ly") == (word.length() - 2)) && (!word.equals(tagging.getLemma()))) {
                    tagging.setPartOfSpeech(WKBUtil.MANNERADVERB);
                    tagging.setLemma(word);
                }
                // }
                if (maTag.indexOf('c') > 0) {
                    tagging.setDegree(WKBUtil.COMPARATIVE);
                }
            } else if (maTag.indexOf('n') == 0) {
                tagging.setPartOfSpeech(WKBUtil.COMMONNOUN);
                if (maTag.indexOf("1") > 0) {
                    tagging.setGrammaticalNumber(WKBUtil.SINGULAR);
                } else if (maTag.indexOf('2') > 0) {
                    tagging.setGrammaticalNumber(WKBUtil.PLURAL);
                }
            } else if (maTag.indexOf("jc") == 0) {
                tagging.setDegree(WKBUtil.COMPARATIVE);
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
            tagging.setPartOfSpeech(WKBUtil.ORDINALADJECTIVE);
            return;
        }

        tagging.setPartOfSpeech(WKBUtil.ADJECTIVE);
        if (tag.equals("JJR")) {
            tagging.setDegree(WKBUtil.COMPARATIVE);
        } else if (tag.equals("JJS")) {
            tagging.setDegree(WKBUtil.SUPERLATIVE);
        }

        if (maTag != null) {
            if ("j-vvn".equals(maTag)) {
                tagging.setVerbFormMood(WKBUtil.PARTICIPLE);
                tagging.setGrammaticalTense(WKBUtil.PAST);
            } else if (maTag.indexOf("vvg") >= 0) {
                tagging.setVerbFormMood(WKBUtil.GERUND);
                if (maTag.equals("vvg")) {
                    tagging.setPartOfSpeech(WKBUtil.VERB);
                    tagging.setVerbFormMood(WKBUtil.GERUND);
                }
            } else if (maTag.indexOf("av") == 0) {
                tagging.setPartOfSpeech(WKBUtil.ADVERB);
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
                    tagging.setPartOfSpeech(WKBUtil.SUBORDONATINGCONJUNCTION);
                } else if (maTag.indexOf("r-crq") == 0) {
                    tagging.setPartOfSpeech(WKBUtil.RELATIVEPRONOUN);
                }
            } else {
                tagging.setPartOfSpeech(WKBUtil.SUBORDONATINGCONJUNCTION + WKBUtil.TYPE_SEPARATOR + WKBUtil.RELATIVEPRONOUN);
            }
        }
    }

    void mapCD(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        tagging.setPartOfSpeech(WKBUtil.CARDINALNUMBER);
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
                    tagging.setPartOfSpeech(WKBUtil.POSSESIVEPRONOUN);
                } else if (maTag.indexOf("po") == 0) {
                    tagging.setPartOfSpeech(WKBUtil.POSSESIVEADJECIVE);
                }
            } else {
                tagging.setLemma(pnpos.getLemma());
                tagging.setPartOfSpeech(WKBUtil.POSSESIVEADJECIVE + WKBUtil.TYPE_SEPARATOR + WKBUtil.POSSESIVEPRONOUN);
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
                tagging.setPartOfSpeech(WKBUtil.INTERROGATIVEADVERB);
            } else if ("c-crq".equals(maTag)) {
                tagging.setPartOfSpeech(WKBUtil.RELATIVEADVERB);
            } else if ("vhdx".equals(maTag)) {
                tagging.setPartOfSpeech(WKBUtil.VERB);
                tagging.setVerbFormMood(WKBUtil.INDICATIVE);
                tagging.setGrammaticalTense(WKBUtil.PAST);
            } else if ("a-acp".equals(maTag)) {
                tagging.setPartOfSpeech(WKBUtil.RELATIVEADVERB);
            } else if ("cs".equals(maTag)) {
                tagging.setPartOfSpeech(WKBUtil.RELATIVEADVERB);
            }
        }
    }

    void mapEX(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        String maTag = tagging.getPartsOfSpeech();

        if (word.equals("there")) {
            if ("a-acp".equals(maTag)) {
                tagging.setPartOfSpeech(WKBUtil.ADVERB);
            } else {
                tagging.setPartOfSpeech(WKBUtil.EXISTENTIALPRONOUN);
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
                tagging.setPartOfSpeech(WKBUtil.VERB);
                tagging.setVerbFormMood(WKBUtil.GERUND);
            }
        }
    }

    void mapRP(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging rb = MorphologicalDatabase.adverb.get(word);
        WTagging pr = MorphologicalDatabase.adposition.get(word);

        if (rb != null && pr == null) {
            tagging.copyWTags(rb);
            tagging.setPartOfSpeech(WKBUtil.GENERALADVERB);
        } else if (rb == null && pr != null) {
            tagging.copyWTags(pr);
        } else {
            tagging.setPartOfSpeech(WKBUtil.ADPOSITION + WKBUtil.TYPE_SEPARATOR + WKBUtil.GENERALADVERB);
            if (rb != null) {
                tagging.setLemma(rb.getLemma());
            } else {
                tagging.setLemma(noLemma);
            }
        }
    }

    void mapPOS(WTagging tagging, String tag) {
        tagging.setLemma("$");
        tagging.setPartOfSpeech(WKBUtil.POSSESIVEPARTICLE);
    }

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
        tagging.setPartOfSpeech(WKBUtil.INTERJECTION);
    }

    private void mapPunct(WTagging wt, String tag) {
        String word = wt.getWrittenForm();
        if (".".equals(word)) {
            wt.setPartOfSpeech(WKBUtil.POINT);
        } else if ("``".equals(wt.getPennTag())) {
            wt.setPartOfSpeech(WKBUtil.QUOTE);
        } else if ("''".equals(wt.getPennTag())) {
            wt.setPartOfSpeech(WKBUtil.QUOTE);
        } else if ("?".equals(word)) {
            wt.setPartOfSpeech(WKBUtil.QUESTIONMARK);
        } else if ("!".equals(word)) {
            wt.setPartOfSpeech(WKBUtil.EXCLAMATIVEPOINT);
        } else if (",".equals(word)) {
            wt.setPartOfSpeech(WKBUtil.COMMA);
        }
    }
}
