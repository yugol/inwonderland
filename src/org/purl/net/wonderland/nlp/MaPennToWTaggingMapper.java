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
import org.purl.net.wonderland.kb.WkbConstants;
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
                if (WkbConstants.ADVERB.equals(posTypes)) {
                    tagging.setPartOfSpeech(WkbConstants.ADVERB);
                    continue;
                }
                if (WkbConstants.ADJECTIVE.equals(posTypes)) {
                    tagging.setPartOfSpeech(WkbConstants.ADJECTIVE);
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
            tagging.setPartOfSpeech(WkbConstants.ADVERB);
            return;
        }

        String word = tagging.getWrittenForm().toLowerCase();
        WTagging ar = Gazetteers.article.get(word);
        WTagging jjind = Gazetteers.indefiniteDeterminer.get(word);
        WTagging jjdem = Gazetteers.demonstrativeDeterminer.get(word);
        WTagging pndem = Gazetteers.demonstrativePronoun.get(word);
        WTagging pnpos = Gazetteers.possessivePronoun.get(word);

        if (ar != null && jjind == null && jjdem == null && pndem == null && pnpos == null) {
            tagging.copyWTags(ar);
        } else if (ar == null && jjind != null && jjdem == null && pndem == null && pnpos == null) {
            tagging.copyWTags(jjind);
        } else if (ar == null && jjind == null && jjdem != null && pndem == null && pnpos == null) {
            tagging.copyWTags(jjdem);
        } else if (ar == null && jjind == null && jjdem != null && pndem != null && pnpos == null) {
            tagging.copyWTags(jjdem);
            if (maTag != null && tagging.getPartsOfSpeech().charAt(0) == 'd') {
                tagging.setPartOfSpeech(WkbConstants.DEMONSTRATIVEDETERMINER);
            } else {
                tagging.setPartOfSpeech(WkbConstants.DEMONSTRATIVEDETERMINER + WkbConstants.TYPE_SEPARATOR + WkbConstants.DEMONSTRATICEPRONOUN);
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
                    tagging.setPartOfSpeech(WkbConstants.CARDINALNUMBER);
                    return;
                }
            } else if (maTag.indexOf("v") == 0) {
                if (maTag.charAt(2) == 'g') {
                    tagging.setPartOfSpeech(WkbConstants.VERB);
                    tagging.setVerbFormMood(WkbConstants.GERUND);
                    return;
                } else if (maTag.charAt(2) == 'd') {
                    tagging.setPartOfSpeech(WkbConstants.VERB);
                    tagging.setVerbFormMood(WkbConstants.INDICATIVE);
                    tagging.setGrammaticalTense(WkbConstants.PAST);
                    return;
                } else if (maTag.charAt(2) == 'z') {
                    tagging.setPartOfSpeech(WkbConstants.VERB);
                    tagging.setVerbFormMood(WkbConstants.INDICATIVE);
                    tagging.setGrammaticalTense(WkbConstants.PRESENT);
                    tagging.setGrammaticalNumber(WkbConstants.SINGULAR);
                    tagging.setPerson(WkbConstants.THIRDPERSON);
                    return;
                }
            } else if (maTag.indexOf("vvg") > 0) {
                tagging.setVerbFormMood(WkbConstants.GERUND);
            }
        }

        if (tag.indexOf("NNP") == 0) {
            tagging.setPartOfSpeech(WkbConstants.PROPERNOUN);
        } else {
            tagging.setPartOfSpeech(WkbConstants.COMMONNOUN);
        }

        if (tag.charAt(tag.length() - 1) == 'S') {
            tagging.setGrammaticalNumber(WkbConstants.PLURAL);
        } else {
            tagging.setGrammaticalNumber(WkbConstants.SINGULAR);
        }

    }

    public void mapVB(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();

        WTagging vb = Gazetteers.verb.get(word);

        if (vb != null) {
            tagging.copyWTagsAndLemma(vb);
        } else {
            tagging.setPartOfSpeech(WkbConstants.VERB);
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
                tagging.setVerbFormMood(WkbConstants.INDICATIVE);
                tagging.setGrammaticalNumber(WkbConstants.SINGULAR);
                tagging.setPerson(WkbConstants.THIRDPERSON);
                tagging.setGrammaticalTense(WkbConstants.PRESENT);
            } else if (tag.equals("VBP")) {
                if (maTag != null) {
                    if (maTag.indexOf("j") == 0) {
                        tagging.setPartOfSpeech(WkbConstants.ADJECTIVE);
                        return;
                    } else if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(1) == 'm') {
                            tagging.setPartOfSpeech(WkbConstants.MODAL);
                            if (maTag.charAt(2) == 'd') {
                                tagging.setGrammaticalTense(WkbConstants.PAST);
                            } else {
                                tagging.setGrammaticalTense(WkbConstants.PRESENT);
                            }
                            return;
                        }
                        if (maTag.charAt(2) == 'd') {
                            tagging.setVerbFormMood(WkbConstants.INDICATIVE);
                            tagging.setGrammaticalTense(WkbConstants.PAST);
                            return;
                        }
                    }
                }
                tagging.setVerbFormMood(WkbConstants.INDICATIVE);
                tagging.setGrammaticalTense(WkbConstants.PRESENT);
            } else if (tag.equals("VB")) {
                if (maTag != null) {
                    if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(1) == 'm') {
                            tagging.setPartOfSpeech(WkbConstants.MODAL);
                            if (maTag.charAt(2) == 'd') {
                                tagging.setGrammaticalTense(WkbConstants.PAST);
                            } else {
                                tagging.setGrammaticalTense(WkbConstants.PRESENT);
                            }
                            return;
                        }
                        if (maTag.charAt(2) == 'i') {
                            tagging.setVerbFormMood(WkbConstants.INFINITIVE);
                        } else if (maTag.charAt(2) == 'b') {
                            if (maTag.charAt(1) == 'b') {
                                tagging.setVerbFormMood(WkbConstants.INFINITIVE);
                            } else {
                                tagging.setVerbFormMood(WkbConstants.INDICATIVE);
                                tagging.setGrammaticalTense(WkbConstants.PRESENT);
                            }
                        } else if (maTag.charAt(2) == 'd') {
                            tagging.setVerbFormMood(WkbConstants.INDICATIVE);
                            tagging.setGrammaticalTense(WkbConstants.PAST);
                        } else if (maTag.charAt(2) == 'n') {
                            tagging.setVerbFormMood(WkbConstants.PARTICIPLE);
                            tagging.setGrammaticalTense(WkbConstants.PAST);
                        }
                    } else if (maTag.indexOf("j") == 0) {
                        IndexWord wnWord = WordNetWrapper.lookup(word, POS.ADJECTIVE);
                        if (wnWord == null) {
                            wnWord = WordNetWrapper.lookup(word, POS.NOUN);
                            if (wnWord != null) {
                                tagging.setPartOfSpeech(WkbConstants.COMMONNOUN);
                                tagging.setGrammaticalNumber(WkbConstants.SINGULAR);
                            }
                        } else {
                            tagging.setPartOfSpeech(WkbConstants.ADJECTIVE);
                        }
                    }
                }
            } else if (tag.equals("VBD")) {
                tagging.setVerbFormMood(WkbConstants.INDICATIVE);
                tagging.setGrammaticalTense(WkbConstants.PAST);
            } else if (tag.equals("VBG")) {
                if (maTag != null) {
                    if (maTag.equals("j-vvg")) {
                        tagging.setPartOfSpeech(WkbConstants.ADJECTIVE);
                        tagging.setVerbFormMood(WkbConstants.GERUND);
                        return;
                    }
                }
                tagging.setVerbFormMood(WkbConstants.GERUND);
                if (word.equals("being")) {
                    tagging.setLemma("be");
                }
            } else if (tag.equals("VBN")) {
                if (maTag != null) {
                    if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(2) == 'g') {
                            tagging.setVerbFormMood(WkbConstants.GERUND);
                            return;
                        }
                    }
                }
                tagging.setVerbFormMood(WkbConstants.PARTICIPLE);
                tagging.setGrammaticalTense(WkbConstants.PAST);
            }
        }
    }

    void mapIN(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();

        if (tagging.isCollocation()) {
            String pos = CollocationsManager.getTypes(word);
            if (pos.indexOf(WkbConstants.ADVERB) >= 0) {
                tagging.setPartOfSpeech(WkbConstants.ADVERB);
                return;
            }
        }

        WTagging pr = Gazetteers.adposition.get(word);
        WTagging cjsub = Gazetteers.subordinatingConjunction.get(word);

        if (pr != null && cjsub == null) {
            tagging.copyWTags(pr);
        } else if (pr == null && cjsub != null) {
            tagging.copyWTags(cjsub);
        } else {
            String maTag = tagging.getPartsOfSpeech();
            if (maTag != null) {
                if (maTag.indexOf("c") == 0) {
                    tagging.setPartOfSpeech(WkbConstants.SUBORDONATINGCONJUNCTION);
                    return;
                } else if (maTag.indexOf("p") == 0) {
                    tagging.setPartOfSpeech(WkbConstants.ADPOSITION);
                    return;
                } else if (maTag.equals("vvg")) {
                    tagging.setPartOfSpeech(WkbConstants.VERB);
                    tagging.setVerbFormMood(WkbConstants.GERUND);
                    return;
                } else if (maTag.equals("a-acp")) {
                    tagging.setPartOfSpeech(WkbConstants.ADVERB);
                    return;
                }
            }
            tagging.setPartOfSpeech(WkbConstants.SUBORDONATINGCONJUNCTION + WkbConstants.TYPE_SEPARATOR + WkbConstants.ADPOSITION);
        }
    }

    void mapCC(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging cjcrd = Gazetteers.coordinatingConjunction.get(word);

        if (cjcrd != null) {
            tagging.copyWTags(cjcrd);
        } else {
            tagging.setPartOfSpeech(WkbConstants.COORDINATINGCONJUNCTION);
        }
    }

    void mapPRP(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();

        WTagging pnprs = Gazetteers.personalPronoun.get(word);
        WTagging pnref = Gazetteers.reflexivePersonalPronoun.get(word);
        WTagging pnidf = Gazetteers.indefinitePronoun.get(word);

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
        WTagging pr = Gazetteers.adposition.get(word);

        if (pr != null && pr.getLemma().equals("to")) {
            tagging.copyWTags(pr);
        }
    }

    void mapRB(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging rb = Gazetteers.adverb.get(word);

        if (rb != null) {
            tagging.copyWTagsAndLemma(rb);
            tagging.setPartOfSpeech(WkbConstants.ADVERB);
            return;
        } else {
            tagging.setPartOfSpeech(WkbConstants.ADVERB);
        }

        if (tag.equals("RBR")) {
            tagging.setDegree(WkbConstants.COMPARATIVE);
        } else if (tag.equals("RBS")) {
            tagging.setDegree(WkbConstants.SUPERLATIVE);
        }

        String maTag = tagging.getPartsOfSpeech();
        if (maTag != null) {
            if (maTag.indexOf("av") == 0) {
                // if (maTag.indexOf("-j") == 2) {
                if ((word.lastIndexOf("ly") == (word.length() - 2)) && (!word.equals(tagging.getLemma()))) {
                    tagging.setPartOfSpeech(WkbConstants.MANNERADVERB);
                    tagging.setLemma(word);
                }
                // }
                if (maTag.indexOf('c') > 0) {
                    tagging.setDegree(WkbConstants.COMPARATIVE);
                }
            } else if (maTag.indexOf('n') == 0) {
                tagging.setPartOfSpeech(WkbConstants.COMMONNOUN);
                if (maTag.indexOf("1") > 0) {
                    tagging.setGrammaticalNumber(WkbConstants.SINGULAR);
                } else if (maTag.indexOf('2') > 0) {
                    tagging.setGrammaticalNumber(WkbConstants.PLURAL);
                }
            } else if (maTag.indexOf("jc") == 0) {
                tagging.setDegree(WkbConstants.COMPARATIVE);
            }
        }
    }

    void mapJJ(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        String maTag = tagging.getPartsOfSpeech();

        WTagging jj = Gazetteers.adjective.get(word);
        if (jj != null) {
            tagging.copyWTags(jj);
            return;
        }

        WTagging jjidf = Gazetteers.indefiniteDeterminer.get(word);
        WTagging jjdem = Gazetteers.demonstrativeDeterminer.get(word);

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
            tagging.setPartOfSpeech(WkbConstants.ORDINALADJECTIVE);
            return;
        }

        tagging.setPartOfSpeech(WkbConstants.ADJECTIVE);
        if (tag.equals("JJR")) {
            tagging.setDegree(WkbConstants.COMPARATIVE);
        } else if (tag.equals("JJS")) {
            tagging.setDegree(WkbConstants.SUPERLATIVE);
        }

        if (maTag != null) {
            if ("j-vvn".equals(maTag)) {
                tagging.setVerbFormMood(WkbConstants.PARTICIPLE);
                tagging.setGrammaticalTense(WkbConstants.PAST);
            } else if (maTag.indexOf("vvg") >= 0) {
                tagging.setVerbFormMood(WkbConstants.GERUND);
                if (maTag.equals("vvg")) {
                    tagging.setPartOfSpeech(WkbConstants.VERB);
                    tagging.setVerbFormMood(WkbConstants.GERUND);
                }
            } else if (maTag.indexOf("av") == 0) {
                tagging.setPartOfSpeech(WkbConstants.ADVERB);
                return;
            }
        }
    }

    void mapMD(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging md = Gazetteers.modal.get(word);

        if (md != null) {
            tagging.copyWTagsAndLemma(md);
        }
    }

    void mapWDT(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging pnrel = Gazetteers.relativePronoun.get(word);
        WTagging cjsub = Gazetteers.subordinatingConjunction.get(word);

        if (pnrel != null && cjsub == null) {
            tagging.copyWTags(pnrel);
        } else if (pnrel == null && cjsub != null) {
            tagging.copyWTags(cjsub);
        } else if (pnrel != null && cjsub != null) {
            tagging.copyWTags(cjsub);
            String maTag = tagging.getPartsOfSpeech();
            if (maTag != null) {
                if (maTag.indexOf("cs") == 0) {
                    tagging.setPartOfSpeech(WkbConstants.SUBORDONATINGCONJUNCTION);
                } else if (maTag.indexOf("r-crq") == 0) {
                    tagging.setPartOfSpeech(WkbConstants.RELATIVEPRONOUN);
                }
            } else {
                tagging.setPartOfSpeech(WkbConstants.SUBORDONATINGCONJUNCTION + WkbConstants.TYPE_SEPARATOR + WkbConstants.RELATIVEPRONOUN);
            }
        }
    }

    void mapCD(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        tagging.setPartOfSpeech(WkbConstants.CARDINALNUMBER);
        tagging.setLemma(TextToNumber.getValue(word));

        if (tagging.getLemma() == null) {
            tagging.setLemma(noLemma);
        }
    }

    void mapPRPS(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging pnpos = Gazetteers.possessivePronoun.get(word);
        WTagging jjpos = Gazetteers.possessiveAdjective.get(word);

        if (pnpos != null && jjpos == null) {
            tagging.copyWTags(pnpos);
        } else if (pnpos == null && jjpos != null) {
            tagging.copyWTags(jjpos);
        } else if (pnpos != null && jjpos != null) {
            String maTag = tagging.getPartsOfSpeech();
            if (maTag != null) {
                if (maTag.indexOf("png") == 0) {
                    tagging.setPartOfSpeech(WkbConstants.POSSESIVEPRONOUN);
                } else if (maTag.indexOf("po") == 0) {
                    tagging.setPartOfSpeech(WkbConstants.POSSESIVEADJECIVE);
                }
            } else {
                tagging.setLemma(pnpos.getLemma());
                tagging.setPartOfSpeech(WkbConstants.POSSESIVEADJECIVE + WkbConstants.TYPE_SEPARATOR + WkbConstants.POSSESIVEPRONOUN);
            }
        }
    }

    void mapWRB(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging rbint = Gazetteers.interrogativeAdverb.get(word);

        if (rbint != null) {
            tagging.copyWTags(rbint);
        }

        String maTag = tagging.getPartsOfSpeech();
        if (maTag != null) {
            if ("q-crq".equals(maTag)) {
                tagging.setPartOfSpeech(WkbConstants.INTERROGATIVEADVERB);
            } else if ("c-crq".equals(maTag)) {
                tagging.setPartOfSpeech(WkbConstants.RELATIVEADVERB);
            } else if ("vhdx".equals(maTag)) {
                tagging.setPartOfSpeech(WkbConstants.VERB);
                tagging.setVerbFormMood(WkbConstants.INDICATIVE);
                tagging.setGrammaticalTense(WkbConstants.PAST);
            } else if ("a-acp".equals(maTag)) {
                tagging.setPartOfSpeech(WkbConstants.RELATIVEADVERB);
            } else if ("cs".equals(maTag)) {
                tagging.setPartOfSpeech(WkbConstants.RELATIVEADVERB);
            }
        }
    }

    void mapEX(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        String maTag = tagging.getPartsOfSpeech();

        if (word.equals("there")) {
            if ("a-acp".equals(maTag)) {
                tagging.setPartOfSpeech(WkbConstants.ADVERB);
            } else {
                tagging.setPartOfSpeech(WkbConstants.EXISTENTIALPRONOUN);
            }
        }
    }

    void mapWP(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging pnint = Gazetteers.interrogativePronoun.get(word);

        if (pnint != null) {
            tagging.copyWTags(pnint);
        }
    }

    void mapFW(WTagging tagging, String tag) {
        // String word = tagging.getWrittenForm().toLowerCase();
        String maTag = tagging.getPartsOfSpeech();

        if (maTag != null) {
            if (maTag.indexOf("vvg") >= 0) {
                tagging.setPartOfSpeech(WkbConstants.VERB);
                tagging.setVerbFormMood(WkbConstants.GERUND);
            }
        }
    }

    void mapRP(WTagging tagging, String tag) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging rb = Gazetteers.adverb.get(word);
        WTagging pr = Gazetteers.adposition.get(word);

        if (rb != null && pr == null) {
            tagging.copyWTags(rb);
            tagging.setPartOfSpeech(WkbConstants.GENERALADVERB);
        } else if (rb == null && pr != null) {
            tagging.copyWTags(pr);
        } else {
            tagging.setPartOfSpeech(WkbConstants.ADPOSITION + WkbConstants.TYPE_SEPARATOR + WkbConstants.GENERALADVERB);
            if (rb != null) {
                tagging.setLemma(rb.getLemma());
            } else {
                tagging.setLemma(noLemma);
            }
        }
    }

    void mapPOS(WTagging tagging, String tag) {
        tagging.setLemma("$");
        tagging.setPartOfSpeech(WkbConstants.POSSESIVEPARTICLE);
    }

    private boolean tryIndefinitePronoun(WTagging tagging) {
        String word = tagging.getWrittenForm().toLowerCase();
        WTagging pnidf = Gazetteers.indefinitePronoun.get(word);
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
        tagging.setPartOfSpeech(WkbConstants.INTERJECTION);
    }

    private void mapPunct(WTagging wt, String tag) {
        String word = wt.getWrittenForm();
        if (".".equals(word)) {
            wt.setPartOfSpeech(WkbConstants.POINT);
        } else if ("``".equals(wt.getPennTag())) {
            wt.setPartOfSpeech(WkbConstants.QUOTE);
        } else if ("''".equals(wt.getPennTag())) {
            wt.setPartOfSpeech(WkbConstants.QUOTE);
        } else if ("?".equals(word)) {
            wt.setPartOfSpeech(WkbConstants.QUESTIONMARK);
        } else if ("!".equals(word)) {
            wt.setPartOfSpeech(WkbConstants.EXCLAMATIVEPOINT);
        } else if (",".equals(word)) {
            wt.setPartOfSpeech(WkbConstants.COMMA);
        }
    }
}
