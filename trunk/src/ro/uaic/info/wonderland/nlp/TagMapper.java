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
            tagging.copyWTagsOnly(ar);
        } else if (ar == null && jjind != null && jjdem == null && pndem == null) {
            tagging.copyWTagsOnly(jjind);
        } else if (ar == null && jjind == null && jjdem != null && pndem == null) {
            tagging.copyWTagsOnly(jjdem);
        } else if (ar == null && jjind == null && jjdem != null && pndem != null) {
            tagging.copyWTagsOnly(jjdem);
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
            String word = tagging.getForm().toLowerCase();
            if (maTag.charAt(0) == 'p' && maTag.charAt(1) == 'i') {
                WTagging pnidf = MorphologicalDatabase.pnidf.get(word);
                if (pnidf != null) {
                    tagging.copyWTagsOnly(pnidf);
                    return;
                }
            } else if (maTag.indexOf("vvg") == 0) {
                fillGerund(tagging);
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
            tagging.copyWTagsOnly(vb);
        } else {
            if (tag.equals("VBZ")) {
                fillIndicative(tagging);
                tagging.setNumber("sng");
                tagging.setPerson("rd");
                tagging.setTense("ps");
            } else if (tag.equals("VBP")) {
                fillIndicative(tagging);
                tagging.setTense("ps");
            } else if (tag.equals("VB")) {
                tagging.setPos("Vb");
                String maTag = tagging.getPartsOfSpeech();
                if (maTag != null) {
                    if (maTag.charAt(0) == 'v') {
                        if (maTag.charAt(1) == 'v') {
                            if (maTag.charAt(2) == 'i') {
                                tagging.setMood("sinf");
                            } else if (maTag.charAt(2) == 'b') {
                                tagging.setMood("ind");
                                tagging.setTense("ps");
                            }
                        }
                    } else if (maTag.charAt(0) == 'j') {
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
                fillGerund(tagging);
            } else if (tag.equals("VBN")) {
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
            tagging.copyWTagsOnly(pr);
        } else if (pr == null && cjsub != null) {
            tagging.copyWTagsOnly(cjsub);
        } else {
            String maTag = tagging.getPartsOfSpeech();
            if (maTag != null && maTag.charAt(0) == 'c') {
                tagging.setPos("CjSUB");
            } else {
                tagging.setPos("PrCjSUB");
            }
        }
    }

    void mapCC(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging cjcrd = MorphologicalDatabase.cjcrd.get(word);

        if (cjcrd != null) {
            tagging.copyWTagsOnly(cjcrd);
        } else {
            tagging.setPos("CjCRD");
        }
    }

    void mapPRP(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging pnprs = MorphologicalDatabase.pnprs.get(word);
        WTagging pnref = MorphologicalDatabase.pnref.get(word);

        if (pnprs != null && pnref == null) {
            tagging.copyWTagsOnly(pnprs);
        } else if (pnprs == null && pnref != null) {
            tagging.copyWTagsOnly(pnref);
        }
    }

    void mapTO(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging pr = MorphologicalDatabase.pr.get(word);

        if (pr != null && pr.getLemma().equals("to")) {
            tagging.copyWTagsOnly(pr);
        }
    }

    void mapRB(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging rb = MorphologicalDatabase.rb.get(word);

        if (rb != null) {
            tagging.copyWTagsOnly(rb);
        } else {
            tagging.setPos("Rb");
        }

        if (tag.equals("RBR")) {
            tagging.setComp("cmp");
        } else if (tag.equals("RBS")) {
            tagging.setComp("sup");
        }

        if ("av-j".equals(tagging.getPartsOfSpeech())) {
            tagging.setPos("RbMNN");
        }
    }

    void mapJJ(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging jj = MorphologicalDatabase.jj.get(word);

        if (jj != null) {
            tagging.copyWTagsOnly(jj);
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
            }
        }
    }

    void mapMD(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging md = MorphologicalDatabase.md.get(word);

        if (md != null) {
            tagging.copyWTagsOnly(md);
        }
    }

    void mapWDT(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging pnrel = MorphologicalDatabase.pnrel.get(word);
        WTagging cjsub = MorphologicalDatabase.cjsub.get(word);

        if (pnrel != null && cjsub == null) {
            tagging.copyWTagsOnly(pnrel);
        } else if (pnrel == null && cjsub != null) {
            tagging.copyWTagsOnly(cjsub);
        } else if (pnrel != null && cjsub != null) {
            tagging.copyWTagsOnly(cjsub);
            String maTag = tagging.getPartsOfSpeech();
            if (maTag != null && maTag.charAt(0) == 'c' && maTag.charAt(1) == 's') {
                tagging.setPos("CjSUB");
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
            tagging.copyWTagsOnly(pnpos);
        } else if (pnpos == null && jjpos != null) {
            tagging.copyWTagsOnly(jjpos);
        } else if (pnpos != null && jjpos != null) {
            tagging.setLemma(pnpos.getLemma());
            tagging.setPos("PnJjPOS");
        }
    }

    void mapWRB(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging rbint = MorphologicalDatabase.rbint.get(word);

        if (rbint != null) {
            tagging.copyWTagsOnly(rbint);
        }

        if ("q-crq".equals(tagging.getPartsOfSpeech())) {
            tagging.setPos("RbINT");
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
            tagging.copyWTagsOnly(pnint);
        }
    }

    void mapFW(WTagging tagging, String tag) {
        // String word = tagging.getForm().toLowerCase();
    }

    void mapRP(WTagging tagging, String tag) {
        String word = tagging.getForm().toLowerCase();
        WTagging rb = MorphologicalDatabase.rb.get(word);
        WTagging pr = MorphologicalDatabase.pr.get(word);

        if (rb != null && pr == null) {
            tagging.copyWTagsOnly(rb);
        } else if (rb == null && pr != null) {
            tagging.copyWTagsOnly(pr);
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
            tagging.copyWTagsOnly(collocation);
        }
    }
}
