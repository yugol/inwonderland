/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import edu.stanford.nlp.util.StringUtils;
import ro.uaic.info.wonderland.nlp.resources.WordNetWrapper;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;

/**
 *
 * @author Iulian
 */
public class MorphologicalAnalyser {

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

    WTagging analyzeNoun(String word, String tag) {
        WTagging tagging = new WTagging();
        IndexWord wnWord = WordNetWrapper.lookup(word, POS.NOUN);
        WTagging pnind = MorphologicalDatabase.pnind.get(word);

        if (wnWord != null && pnind == null) {
            tagging.setLemma(wnWord.getLemma());
        } else if (wnWord == null && pnind != null) {
            tagging.copyNoFormNoPennNoSenses(pnind);
            return tagging;
        } else {
            tagging.setLemma(noLemma);
        }
        if (tag.indexOf("NNP") == 0) {
            tagging.setLemma(word);
            tagging.setLemma(StringUtils.capitalize(word));
        }

        if (Character.isUpperCase(tagging.getLemma().charAt(0))) {
            tagging.setPos("NnPRP");
        } else {
            tagging.setPos("NnCOM");
        }

        if (tag.charAt(tag.length() - 1) == 'S') {
            tagging.setNumber("plu");
        } else {
            tagging.setNumber("sng");
        }

        return tagging;
    }

    WTagging analyzeAdjective(String word, String tag) {
        WTagging tagging = new WTagging();

        // see if is not an ordinal numeral
        String nmord = TextToNumber.getValue(word);
        if (nmord != null) {
            tagging.setLemma(nmord);
            tagging.setPos("NmORD");
            return tagging;
        }

        // lookup word in WordNet
        IndexWord wnWord = WordNetWrapper.lookup(word, POS.ADJECTIVE);
        if (wnWord != null) {
            tagging.setLemma(wnWord.getLemma());
        } else {
            tagging.setLemma(noLemma);
        }

        tagging.setPos("Jj");

        if (tag.equals("JJR")) {
            tagging.setComp("cmp");
        } else if (tag.equals("JJS")) {
            tagging.setComp("sup");
        }

        return tagging;
    }

    WTagging analyzeVerb(String word, String tag) {
        WTagging tagging = new WTagging();
        WTagging vb = MorphologicalDatabase.vb.get(word);

        if (vb != null) {
            tagging.copyNoFormNoPennNoSenses(vb);
        } else {
            IndexWord wnWord = wnWord = WordNetWrapper.lookup(word, POS.VERB);

            if (wnWord != null) {
                tagging.setLemma(wnWord.getLemma());
            } else {
                tagging.setLemma(noLemma);
            }

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
            } else if (tag.equals("VBD")) {
                fillIndicative(tagging);
                tagging.setTense("pt");
            } else if (tag.equals("VBG")) {
                if (isGerundForm(word)) {
                    fillGerund(tagging);
                } else {
                    return null;
                }
            } else if (tag.equals("VBN")) {
                fillParticiple(tagging);
                tagging.setTense("pt");
            } else {
                return null;
            }
        }

        return tagging;
    }

    WTagging analyzeAdverb(String word, String tag) {
        WTagging tagging = new WTagging();
        WTagging rb = MorphologicalDatabase.rb.get(word);

        if (rb != null) {
            tagging.copyNoFormNoPennNoSenses(rb);
        } else {
            IndexWord wnWord = WordNetWrapper.lookup(word, POS.ADVERB);
            if (wnWord != null) {
                tagging.setLemma(wnWord.getLemma());
            } else {
                tagging.setLemma(noLemma);
            }
            tagging.setPos("Rb");
        }

        if (tag.equals("RBR")) {
            tagging.setComp("cmp");
        } else if (tag.equals("RBS")) {
            tagging.setComp("sup");
        }

        return tagging;
    }

    WTagging analyzePrepOrSubConj(String word, String tag) {
        WTagging tagging = new WTagging();
        WTagging pr = MorphologicalDatabase.pr.get(word);
        WTagging cjsub = MorphologicalDatabase.cjsub.get(word);

        if (pr == null && cjsub == null) {
            tagging.setLemma(noLemma);
            tagging.setPos("PrCjSUB");
        } else if (pr != null && cjsub != null) {
            tagging.setLemma(pr.getLemma());
            tagging.setPos("PrCjSUB");
        } else if (pr != null) {
            tagging.copyNoFormNoPennNoSenses(pr);
        } else {
            tagging.copyNoFormNoPennNoSenses(cjsub);
        }

        return tagging;
    }

    WTagging analyzeDeterminer(String word, String tag) {
        WTagging tagging = new WTagging();
        WTagging ar = MorphologicalDatabase.ar.get(word);
        WTagging jjind = MorphologicalDatabase.jjind.get(word);
        WTagging jjdem = MorphologicalDatabase.jjdem.get(word);
        WTagging pndem = MorphologicalDatabase.pndem.get(word);

        if (ar != null && jjind == null && jjdem == null && pndem == null) {
            tagging.copyNoFormNoPennNoSenses(ar);
        } else if (ar == null && jjind != null && jjdem == null && pndem == null) {
            tagging.copyNoFormNoPennNoSenses(jjind);
        } else if (ar == null && jjind == null && jjdem != null && pndem == null) {
            tagging.copyNoFormNoPennNoSenses(jjdem);
        } else if (ar == null && jjind == null && jjdem != null && pndem != null) {
            tagging.copyNoFormNoPennNoSenses(jjdem);
            tagging.setPos("JjPnDEM");
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeCoordConj(String word, String tag) {
        WTagging tagging = new WTagging();
        WTagging cjcrd = MorphologicalDatabase.cjcrd.get(word);

        if (cjcrd != null) {
            tagging.copyNoFormNoPennNoSenses(cjcrd);
        } else {
            tagging.setLemma(noLemma);
            tagging.setPos("CjCRD");
        }

        return tagging;
    }

    WTagging analyzePersPron(String word, String tag) {
        if (word.equals("i")) {
            word = "I";
        }
        WTagging tagging = new WTagging();
        WTagging pnprs = MorphologicalDatabase.pnprs.get(word);
        WTagging pnref = MorphologicalDatabase.pnref.get(word);

        if (pnprs != null && pnref == null) {
            tagging.copyNoFormNoPennNoSenses(pnprs);
        } else if (pnprs == null && pnref != null) {
            tagging.copyNoFormNoPennNoSenses(pnref);
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeTo(String word, String tag) {
        WTagging tagging = new WTagging();
        WTagging pr = MorphologicalDatabase.pr.get(word);

        if (pr != null && pr.getLemma().equals("to")) {
            tagging.copyNoFormNoPennNoSenses(pr);
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeModal(String word, String tag) {
        WTagging tagging = new WTagging();
        WTagging md = MorphologicalDatabase.md.get(word);

        if (md != null) {
            tagging.copyNoFormNoPennNoSenses(md);
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeWhDeterminer(String word, String tag) {
        WTagging tagging = new WTagging();
        WTagging pnrel = MorphologicalDatabase.pnrel.get(word);
        WTagging cjsub = MorphologicalDatabase.cjsub.get(word);

        if (pnrel != null && cjsub == null) {
            tagging.copyNoFormNoPennNoSenses(pnrel);
        } else if (pnrel == null && cjsub != null) {
            tagging.copyNoFormNoPennNoSenses(cjsub);
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeCardinalNumber(String word, String tag) {
        WTagging tagging = new WTagging();
        tagging.setPos("NmCRD");
        tagging.setLemma(TextToNumber.getValue(word));
        if (tagging.getLemma() == null) {
            tagging.setLemma(noLemma);
        }
        return tagging;
    }

    WTagging analyzePossPron(String word, String tag) {
        WTagging tagging = new WTagging();
        WTagging pnpos = MorphologicalDatabase.pnpos.get(word);
        WTagging jjpos = MorphologicalDatabase.jjpos.get(word);

        if (pnpos != null && jjpos == null) {
            tagging.copyNoFormNoPennNoSenses(pnpos);
        } else if (pnpos == null && jjpos != null) {
            tagging.copyNoFormNoPennNoSenses(jjpos);
        } else if (pnpos != null && jjpos != null) {
            tagging.setLemma(pnpos.getLemma());
            tagging.setPos("PnJjPOS");
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeWhAdverb(String word, String tag) {
        WTagging tagging = new WTagging();
        WTagging rbint = MorphologicalDatabase.rbint.get(word);

        if (rbint != null) {
            tagging.copyNoFormNoPennNoSenses(rbint);
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeExThere(String word, String tag) {
        if (!word.equals("there")) {
            return null;
        }
        WTagging tagging = new WTagging();
        tagging.setPos("RbEX");
        tagging.setLemma("there");
        return tagging;
    }

    WTagging analyzeWhPron(String word, String tag) {
        WTagging tagging = new WTagging();

        WTagging pnint = MorphologicalDatabase.pnint.get(word);

        if (pnint != null) {
            tagging.copyNoFormNoPennNoSenses(pnint);
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeForeignWord(String word, String tag) {
        WTagging tagging = null;

        IndexWord wnWord = WordNetWrapper.lookup(word, POS.VERB);
        if (wnWord != null) {
            tagging = new WTagging();
            tagging.setLemma(wnWord.getLemma());
            if (isGerundForm(word)) {
                fillGerund(tagging);
            } else {
                tagging.setPos("Pos");
            }
        }

        return tagging;
    }

    WTagging analyzeParticle(String lcWord, String tag) {
        WTagging tagging = new WTagging();
        WTagging rb = MorphologicalDatabase.rb.get(lcWord);
        WTagging pr = MorphologicalDatabase.pr.get(lcWord);

        if (rb != null && pr == null) {
            tagging.copyNoFormNoPennNoSenses(rb);
        } else if (rb == null && pr != null) {
            tagging.copyNoFormNoPennNoSenses(pr);
        } else {
            tagging.setPos("PrRbPLC_Dir");
            if (rb != null) {
                tagging.setLemma(rb.getLemma());

            } else {
                tagging.setLemma(noLemma);

            }
        }

        return tagging;
    }

    WTagging analyzePossEnding(String lcWord, String tag) {
        WTagging tagging = new WTagging();
        tagging.setLemma("$");
        tagging.setPos("MkPOS");
        return tagging;
    }
}
