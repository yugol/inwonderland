/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import ro.uaic.info.wonderland.nlp.resources.WordNetWrapper;

/**
 *
 * @author Iulian
 */
public class TagMapper {

    static final String noLemma = "_~_";

    private void fillIndicative(WTagging tagging) {
        tagging.setPos("Vb");
        tagging.setMood("ind");
    }

    private boolean isGerundForm(String word) {
        return word.lastIndexOf("ing") == word.length() - 3;
    }

    private void fillGerund(WTagging tagging) {
        tagging.setPos("Vb");
        tagging.setMood("ger");
    }

    private void fillParticiple(WTagging tagging) {
        tagging.setPos("Vb");
        tagging.setMood("par");
    }

    public void mapDT(WTagging tagging, String tag) {
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
            String maTag = tagging.getPartsOfSpeech();
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
            } else if (maTag.indexOf("vvg") == 0) {
                fillGerund(tagging);
                return;
            } else if (maTag.indexOf("vvd") == 0) {
                fillIndicative(tagging);
                tagging.setTense("pt");
                return;
            } else if (maTag.indexOf("av") == 0) {
                mapRB(tagging, tag);
                return;
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
            String maTag = tagging.getPartsOfSpeech();

            if (maTag.indexOf("vd") == 0) {
                tagging.setLemma("do");
            }
            if (maTag.indexOf("vh") == 0) {
                tagging.setLemma("have");
            }

            //if (word.equals("had")) {
            //    tagging.setLemma("have");
            //}

            if (tag.equals("VBZ")) {
                fillIndicative(tagging);
                tagging.setNumber("sng");
                tagging.setPerson("rd");
                tagging.setTense("ps");
            } else if (tag.equals("VBP")) {
                if (maTag != null) {
                    if (maTag.indexOf("j") == 0) {
                        tagging.setPos("Jj");
                        return;
                    }
                }
                fillIndicative(tagging);
                tagging.setTense("ps");
            } else if (tag.equals("VB")) {
                tagging.setPos("Vb");
                if (maTag != null) {
                    if (maTag.length() >= 3) {
                        if (maTag.charAt(2) == 'i') {
                            tagging.setMood("sinf");
                        } else if (maTag.charAt(2) == 'b') {
                            tagging.setMood("ind");
                            tagging.setTense("ps");
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
                fillIndicative(tagging);
                tagging.setTense("pt");
            } else if (tag.equals("VBG")) {
                if (maTag != null) {
                    if (maTag.equals("j-vvg")) {
                        tagging.setPos("Jj");
                        tagging.setMood("ger");
                        return;
                    }
                }
                fillGerund(tagging);
                if (word.equals("being")) {
                    tagging.setLemma("be");
                }
            } else if (tag.equals("VBN")) {
                /*
                if (maTag != null) {
                if (maTag.equals("vvd")) {
                fillIndicative(tagging);
                tagging.setTense("pt");
                return;
                }
                }
                 */
                fillParticiple(tagging);
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
                    tagging.setPos("Pr");
                    return;
                }
            }
            tagging.setPos("PrCjSUB");

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
            if ("av-j".equals(maTag)) {
                if ((word.lastIndexOf("ly") == (word.length() - 2)) && (!word.equals(tagging.getLemma()))) {
                    tagging.setPos("RbMNN");
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
                    fillGerund(tagging);
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
            tagging.copyWTags(md);
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
                tagging.setPos("PnJjPOS");
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
                fillIndicative(tagging);
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
                fillGerund(tagging);
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
            tagging.setPos("PrRbPLC_Dir");
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

    void mapCollocation(WTagging tagging) {
        String word = tagging.getForm().toLowerCase();
        WTagging collocation = MorphologicalDatabase.collocations.get(word);

        if (collocation != null) {
            tagging.copyWTags(collocation);

        }
    }

    private boolean tryIndefinitePronoun(WTagging tagging) {
        String word = tagging.getForm().toLowerCase();
        WTagging pnidf = MorphologicalDatabase.pnidf.get(word);
        if (pnidf != null) {
            tagging.copyWTagsAndLemma(pnidf);
            return true;
        }
        return false;
    }
}
