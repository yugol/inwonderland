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
public class WTagMapper {

    private static final String noLemma = "_~_";

    void mapWTags(List<WTagging> sentence) {
        for (WTagging tagging : sentence) {
            String tag = tagging.getPennTag();

            if (tagging.isCollocation()) {
                // System.out.println(tagging.getForm());
                String posTypes = CollocationManager.getTypes(tagging.getLemma());
                if ("Rb".equals(posTypes)) {
                    tagging.setPos("Rb");
                    continue;
                }
                if ("Jj".equals(posTypes)) {
                    tagging.setPos("Jj");
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
            }
        }
    }

    public void mapDT(WTagging tagging, String tag) {
        String maTag = tagging.getPartsOfSpeech();
        if ("av-dx".equals(maTag)) {
            tagging.setPos("Rb");
            return;
        }

        String word = tagging.getForm().toLowerCase();
        WTagging ar = MorphologicalDatabase.ar.get(word);
        WTagging jjind = MorphologicalDatabase.jjind.get(word);
        WTagging jjdem = MorphologicalDatabase.jjdem.get(word);
        WTagging pndem = MorphologicalDatabase.pndem.get(word);

        if (ar != null && jjind == null && jjdem == null && pndem == null) {
            tagging.copyWTags(ar);
        } else if (ar == null && jjind != null && jjdem == null && pndem == null) {
            tagging.copyWTags(jjind);
        } else if (ar == null && jjind == null && jjdem != null && pndem == null) {
            tagging.copyWTags(jjdem);
        } else if (ar == null && jjind == null && jjdem != null && pndem != null) {
            tagging.copyWTags(jjdem);
            if (maTag != null && tagging.getPartsOfSpeech().charAt(0) == 'd') {
                tagging.setPos("JjDEM");
            } else {
                tagging.setPos("JjPnDEM");
            }
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
                String word = tagging.getForm().toLowerCase();
                String number = TextToNumber.getValue(word);
                if (number != null) {
                    tagging.setLemma(number);
                    tagging.setPos("NmCRD");
                    return;
                }
            } else if (maTag.indexOf("v") == 0) {
                if (maTag.charAt(2) == 'g') {
                    tagging.setPos("Vb");
                    tagging.setMood("ger");
                    return;
                } else if (maTag.charAt(2) == 'd') {
                    tagging.setPos("Vb");
                    tagging.setMood("ind");
                    tagging.setTense("pt");
                    return;
                } else if (maTag.charAt(2) == 'z') {
                    tagging.setPos("Vb");
                    tagging.setMood("ind");
                    tagging.setTense("ps");
                    tagging.setNumber("sng");
                    tagging.setPerson("rd");
                    return;
                }
            } else if (maTag.indexOf("vvg") > 0) {
                tagging.setMood("ger");
            }
        }

        if (tag.indexOf("NNP") == 0) {
            tagging.setPos("NnPRP");
        } else {
            tagging.setPos("NnCOM");
        }

        if (tag.charAt(tag.length() - 1) == 'S') {
            tagging.setNumber("plu");
        } else {
            tagging.setNumber("sng");
        }

    }

    public void mapVB(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging vb = MorphologicalDatabase.vb.get(word);

        if (vb != null) {
            tagging.copyWTagsAndLemma(vb);
        } else {
            tagging.setPos("Vb");
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
                tagging.setMood("ind");
                tagging.setNumber("sng");
                tagging.setPerson("rd");
                tagging.setTense("ps");
            } else if (tag.equals("VBP")) {
                if (maTag != null) {
                    if (maTag.indexOf("j") == 0) {
                        tagging.setPos("Jj");
                        return;
                    } else if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(1) == 'm') {
                            tagging.setPos("Md");
                            if (maTag.charAt(2) == 'd') {
                                tagging.setTense("pt");
                            } else {
                                tagging.setTense("ps");
                            }
                            return;
                        }
                        if (maTag.charAt(2) == 'd') {
                            tagging.setMood("ind");
                            tagging.setTense("pt");
                            return;
                        }
                    }
                }
                tagging.setMood("ind");
                tagging.setTense("ps");
            } else if (tag.equals("VB")) {
                if (maTag != null) {
                    if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(1) == 'm') {
                            tagging.setPos("Md");
                            if (maTag.charAt(2) == 'd') {
                                tagging.setTense("pt");
                            } else {
                                tagging.setTense("ps");
                            }
                            return;
                        }
                        if (maTag.charAt(2) == 'i') {
                            tagging.setMood("sinf");
                        } else if (maTag.charAt(2) == 'b') {
                            if (maTag.charAt(1) == 'b') {
                                tagging.setMood("sinf");
                            } else {
                                tagging.setMood("ind");
                                tagging.setTense("ps");
                            }
                        } else if (maTag.charAt(2) == 'd') {
                            tagging.setMood("ind");
                            tagging.setTense("pt");
                        } else if (maTag.charAt(2) == 'n') {
                            tagging.setMood("par");
                            tagging.setTense("pt");
                        }
                    } else if (maTag.indexOf("j") == 0) {
                        IndexWord wnWord = WordNetWrapper.lookup(word, POS.ADJECTIVE);
                        if (wnWord == null) {
                            wnWord = WordNetWrapper.lookup(word, POS.NOUN);
                            if (wnWord != null) {
                                tagging.setPos("NnCOM");
                                tagging.setNumber("sng");
                            }
                        } else {
                            tagging.setPos("Jj");
                        }
                    }
                }
            } else if (tag.equals("VBD")) {
                tagging.setMood("ind");
                tagging.setTense("pt");
            } else if (tag.equals("VBG")) {
                if (maTag != null) {
                    if (maTag.equals("j-vvg")) {
                        tagging.setPos("Jj");
                        tagging.setMood("ger");
                        return;
                    }
                }
                tagging.setMood("ger");
                if (word.equals("being")) {
                    tagging.setLemma("be");
                }
            } else if (tag.equals("VBN")) {
                if (maTag != null) {
                    if (maTag.indexOf("v") == 0) {
                        if (maTag.charAt(2) == 'g') {
                            tagging.setMood("ger");
                            return;
                        }
                    }
                }
                tagging.setMood("par");
                tagging.setTense("pt");
            }
        }
    }

    void mapIN(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging pr = MorphologicalDatabase.pr.get(word);
        WTagging cjsub = MorphologicalDatabase.cjsub.get(word);

        if (pr != null && cjsub == null) {
            tagging.copyWTags(pr);
        } else if (pr == null && cjsub != null) {
            tagging.copyWTags(cjsub);
        } else {
            String maTag = tagging.getPartsOfSpeech();
            if (maTag != null) {
                if (maTag.indexOf("c") == 0) {
                    tagging.setPos("CjSUB");
                    return;
                } else if (maTag.indexOf("p") == 0) {
                    tagging.setPos("Pp");
                    return;
                } else if (maTag.equals("vvg")) {
                    tagging.setPos("Vb");
                    tagging.setMood("ger");
                    return;
                } else if (maTag.equals("a-acp")) {
                    tagging.setPos("Rb");
                    return;
                }
            }
            tagging.setPos("PpCjSUB");
        }
    }

    void mapCC(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging cjcrd = MorphologicalDatabase.cjcrd.get(word);

        if (cjcrd != null) {
            tagging.copyWTags(cjcrd);
        } else {
            tagging.setPos("CjCRD");
        }
    }

    void mapPRP(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging pnprs = MorphologicalDatabase.pnprs.get(word);
        WTagging pnref = MorphologicalDatabase.pnref.get(word);

        if (pnprs != null && pnref == null) {
            tagging.copyWTags(pnprs);
        } else if (pnprs == null && pnref != null) {
            tagging.copyWTags(pnref);
        }
    }

    void mapTO(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging pr = MorphologicalDatabase.pr.get(word);

        if (pr != null && pr.getLemma().equals("to")) {
            tagging.copyWTags(pr);
        }
    }

    void mapRB(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging rb = MorphologicalDatabase.rb.get(word);

        if (rb != null) {
            tagging.copyWTagsAndLemma(rb);
            return;
        } else {
            tagging.setPos("Rb");
        }

        if (tag.equals("RBR")) {
            tagging.setComp("cmp");
        } else if (tag.equals("RBS")) {
            tagging.setComp("sup");
        }

        String maTag = tagging.getPartsOfSpeech();
        if (maTag != null) {
            if (maTag.indexOf("av") == 0) {
                if (maTag.indexOf("-j") == 2) {
                    if ((word.lastIndexOf("ly") == (word.length() - 2)) && (!word.equals(tagging.getLemma()))) {
                        tagging.setPos("RbMNN");
                        tagging.setLemma(word);
                    }
                }
                if (maTag.indexOf('c') > 0) {
                    tagging.setComp("cmp");
                }
            } else if (maTag.indexOf("n") == 0) {
                tagging.setPos("NnCOM");
                if (maTag.indexOf("1") > 0) {
                    tagging.setNumber("sng");
                } else if (maTag.indexOf("2") > 0) {
                    tagging.setNumber("plu");
                }
            }
        }
    }

    void mapJJ(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();

        WTagging jj = MorphologicalDatabase.jj.get(word);
        if (jj != null) {
            tagging.copyWTags(jj);
            return;
        }

        WTagging jjidf = MorphologicalDatabase.jjind.get(word);
        if (jjidf != null) {
            tagging.copyWTagsAndLemma(jjidf);
            return;
        }

        // see if is not an ordinal numeral
        String nmord = TextToNumber.getValue(word);
        if (nmord != null) {
            tagging.setLemma(nmord);
            tagging.setPos("NmORD");
            return;
        }

        tagging.setPos("Jj");
        if (tag.equals("JJR")) {
            tagging.setComp("cmp");
        } else if (tag.equals("JJS")) {
            tagging.setComp("sup");
        }

        String maTag = tagging.getPartsOfSpeech();
        if (maTag != null) {
            if ("j-vvn".equals(maTag)) {
                tagging.setMood("par");
                tagging.setTense("pt");
            } else if (maTag.indexOf("vvg") >= 0) {
                tagging.setMood("ger");
                if (maTag.equals("vvg")) {
                    tagging.setPos("Vb");
                    tagging.setMood("ger");
                }
            } else if (maTag.indexOf("av") == 0) {
                tagging.setPos("Rb");
                return;
            }
        }
    }

    void mapMD(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging md = MorphologicalDatabase.md.get(word);

        if (md != null) {
            tagging.copyWTagsAndLemma(md);
        }
    }

    void mapWDT(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging pnrel = MorphologicalDatabase.pnrel.get(word);
        WTagging cjsub = MorphologicalDatabase.cjsub.get(word);

        if (pnrel != null && cjsub == null) {
            tagging.copyWTags(pnrel);
        } else if (pnrel == null && cjsub != null) {
            tagging.copyWTags(cjsub);
        } else if (pnrel != null && cjsub != null) {
            tagging.copyWTags(cjsub);
            String maTag = tagging.getPartsOfSpeech();
            if (maTag != null) {
                if (maTag.indexOf("cs") == 0) {
                    tagging.setPos("CjSUB");
                } else if (maTag.indexOf("r-crq") == 0) {
                    tagging.setPos("PnREL");
                }
            } else {
                tagging.setPos("CjSUBPnREL");
            }
        }
    }

    void mapCD(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        tagging.setPos("NmCRD");
        tagging.setLemma(TextToNumber.getValue(word));

        if (tagging.getLemma() == null) {
            tagging.setLemma(noLemma);
        }
    }

    void mapPRPS(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging pnpos = MorphologicalDatabase.pnpos.get(word);
        WTagging jjpos = MorphologicalDatabase.jjpos.get(word);

        if (pnpos != null && jjpos == null) {
            tagging.copyWTags(pnpos);
        } else if (pnpos == null && jjpos != null) {
            tagging.copyWTags(jjpos);
        } else if (pnpos != null && jjpos != null) {
            String maTag = tagging.getPartsOfSpeech();
            if (maTag != null) {
                if (maTag.indexOf("png") == 0) {
                    tagging.setPos("PnPOS");
                } else if (maTag.indexOf("po") == 0) {
                    tagging.setPos("JjPOS");
                }
            } else {
                tagging.setLemma(pnpos.getLemma());
                tagging.setPos("JjPnPOS");
            }
        }
    }

    void mapWRB(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging rbint = MorphologicalDatabase.rbint.get(word);

        if (rbint != null) {
            tagging.copyWTags(rbint);
        }

        String maTag = tagging.getPartsOfSpeech();
        if (maTag != null) {
            if ("q-crq".equals(maTag)) {
                tagging.setPos("RbINT");
            } else if ("c-crq".equals(maTag)) {
                tagging.setPos("RbREL");
            } else if ("vhdx".equals(maTag)) {
                tagging.setPos("Vb");
                tagging.setMood("ind");
                tagging.setTense("pt");
            }
        }
    }

    void mapEX(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();

        if (word.equals("there")) {
            tagging.setPos("RbEX");
        }
    }

    void mapWP(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging pnint = MorphologicalDatabase.pnint.get(word);

        if (pnint != null) {
            tagging.copyWTags(pnint);
        }
    }

    void mapFW(WTagging tagging, String tag) {
        // String word = tagging.getForm().toLowerCase();
        String maTag = tagging.getPartsOfSpeech();

        if (maTag != null) {
            if (maTag.indexOf("vvg") >= 0) {
                tagging.setPos("Vb");
                tagging.setMood("ger");
            }
        }
    }

    void mapRP(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging rb = MorphologicalDatabase.rb.get(word);
        WTagging pr = MorphologicalDatabase.pr.get(word);

        if (rb != null && pr == null) {
            tagging.copyWTags(rb);
        } else if (rb == null && pr != null) {
            tagging.copyWTags(pr);
        } else {
            tagging.setPos("PpRbPLC_Dir");
            if (rb != null) {
                tagging.setLemma(rb.getLemma());
            } else {
                tagging.setLemma(noLemma);
            }
        }
    }

    void mapPOS(WTagging tagging, String tag) {
        tagging.setLemma("$");
        tagging.setPos("MkPOS");
    }

    /*
    void mapCollocation(WTagging tagging) {
    String word = tagging.getForm().toLowerCase();
    WTagging collocation = MorphologicalDatabase.collocations.get(word);

    if (collocation != null) {
    tagging.copyWTags(collocation);

    }
    }
     * 
     */
    private boolean tryIndefinitePronoun(WTagging tagging) {
        String word = tagging.getForm().toLowerCase();
        WTagging pnidf = MorphologicalDatabase.pnidf.get(word);
        if (pnidf != null) {
            tagging.copyWTagsAndLemma(pnidf);
            return true;
        }
        return false;
    }

    void mapPDT(WTagging tagging, String tag) {
        mapDT(tagging, tag);
    }
}
