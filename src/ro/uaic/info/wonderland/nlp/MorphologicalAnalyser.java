/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import edu.stanford.nlp.util.StringUtils;
import ro.uaic.info.wonderland.nlp.resources.WordNetWrapper;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import ro.uaic.info.wonderland.Globals;

/**
 *
 * @author Iulian
 */
public class MorphologicalAnalyser {

    static final String noLemma = "_~_";

    WTagging analyzeNoun(String word, String tag) {
        WTagging tagging = new WTagging();

        IndexWord wnWord = null;
        try {
            wnWord = WordNetWrapper.lookup(word, POS.NOUN);
        } catch (JWNLException ex) {
            System.out.println(ex);
            Globals.exit();
        }

        word = word.toLowerCase();
        WTagging pnind = MorphologicalDatabase.pnind.get(word);

        // lemma
        if (wnWord != null && pnind == null) {
            tagging.setLemma(wnWord.getLemma());
        } else if (wnWord == null && pnind != null) {
            tagging.setLemma(pnind.getLemma());
            tagging.setPos(pnind.getPos());
            return tagging;
        } else {
            tagging.setLemma(noLemma);
        }
        if (tag.indexOf("NNP") == 0) {
            tagging.setLemma(word);
            tagging.setLemma(StringUtils.capitalize(word));
        }

        // pos
        if (Character.isUpperCase(tagging.getLemma().charAt(0))) {
            tagging.setPos("NnPRP");
        } else {
            tagging.setPos("NnCOM");
        }

        // number
        if (tag.charAt(tag.length() - 1) == 'S') {
            tagging.setNumber("plu");
        } else {
            tagging.setNumber("sng");
        }

        return tagging;
    }

    WTagging analyzeVerb(String word, String tag) {
        WTagging tagging = new WTagging();

        WTagging vb = MorphologicalDatabase.vb.get(word.toLowerCase());

        if (vb != null) {
            tagging.setLemma(vb.getLemma());
            tagging.setPos(vb.getPos());
            tagging.setMood(vb.getMood());
            tagging.setNumber(vb.getNumber());
            tagging.setPerson(vb.getPerson());
            tagging.setTense(vb.getTense());
        } else {
            IndexWord wnWord = null;
            try {
                wnWord = WordNetWrapper.lookup(word, POS.VERB);
            } catch (JWNLException ex) {
                System.out.println(ex);
            }

            if (wnWord != null) {
                tagging.setLemma(wnWord.getLemma());
            } else {
                tagging.setLemma(noLemma);
            }

            tagging.setPos("Vb");
            if (tag.equals("VBZ")) {
                tagging.setMood("ind");
                tagging.setNumber("sng");
                tagging.setPerson("rd");
                tagging.setTense("ps");
            } else if (tag.equals("VBP")) {
                tagging.setMood("ind");
                tagging.setTense("ps");
            } else if (tag.equals("VB")) {
            } else if (tag.equals("VBD")) {
                tagging.setMood("ind");
                tagging.setTense("pt");
            } else if (tag.equals("VBG")) {
                word = word.toLowerCase();
                if (word.lastIndexOf("ing") == word.length() - 3) {
                    tagging.setMood("ger");
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        return tagging;
    }

    WTagging analyzePrepOrSubConj(String word, String tag) {
        WTagging tagging = new WTagging();

        word = word.toLowerCase();

        WTagging pr = MorphologicalDatabase.pr.get(word);
        WTagging cjsub = MorphologicalDatabase.cjsub.get(word);

        if (pr == null && cjsub == null) {
            tagging.setLemma(noLemma);
            tagging.setPos("PrCjSUB");
        } else if (pr != null && cjsub != null) {
            tagging.setLemma(pr.getLemma());
            tagging.setPos("PrCjSUB");
        } else if (pr != null) {
            tagging.setLemma(pr.getLemma());
            tagging.setPos(pr.getPos());
        } else {
            tagging.setLemma(cjsub.getLemma());
            tagging.setPos(cjsub.getPos());
        }

        return tagging;
    }

    WTagging analyzeDeterminer(String word, String tag) {
        WTagging tagging = new WTagging();

        word = word.toLowerCase();

        WTagging ar = MorphologicalDatabase.ar.get(word);
        WTagging jjind = MorphologicalDatabase.jjind.get(word);
        WTagging jjdem = MorphologicalDatabase.jjdem.get(word);

        if (ar != null && jjind == null && jjdem == null) {
            tagging.setLemma(ar.getLemma());
            tagging.setPos(ar.getPos());
        } else if (ar == null && jjind != null && jjdem == null) {
            tagging.setLemma(jjind.getLemma());
            tagging.setPos(jjind.getPos());
        } else if (ar == null && jjind == null && jjdem != null) {
            tagging.setLemma(jjdem.getLemma());
            tagging.setPos(jjdem.getPos());
            tagging.setNumber(jjdem.getNumber());
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeCoordConj(String word, String tag) {
        WTagging tagging = new WTagging();

        word = word.toLowerCase();

        WTagging cjcrd = MorphologicalDatabase.cjcrd.get(word);

        if (cjcrd != null) {
            tagging.setLemma(cjcrd.getLemma());
            tagging.setPos(cjcrd.getPos());
        } else {
            tagging.setLemma(noLemma);
            tagging.setPos("CjCRD");
        }

        return tagging;
    }

    WTagging analyzePersPron(String word, String tag) {
        WTagging tagging = new WTagging();

        word = word.toLowerCase();
        if (word.equals("i")) {
            word = "I";
        }

        WTagging pnprs = MorphologicalDatabase.pnprs.get(word);
        WTagging pnref = MorphologicalDatabase.pnref.get(word);

        if (pnprs != null && pnref == null) {
            tagging.setLemma(pnprs.getLemma());
            tagging.setPos(pnprs.getPos());
            tagging.setGender(pnprs.getGender());
            tagging.setNumber(pnprs.getNumber());
            tagging.setWcase(pnprs.getWcase());
            tagging.setPerson(pnprs.getPerson());
        } else if (pnprs == null && pnref != null) {
            tagging.setLemma(pnref.getLemma());
            tagging.setPos(pnref.getPos());
            tagging.setGender(pnref.getGender());
            tagging.setNumber(pnref.getNumber());
            tagging.setWcase(pnref.getWcase());
            tagging.setPerson(pnref.getPerson());
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeTo(String word, String tag) {
        WTagging tagging = new WTagging();

        word = word.toLowerCase();

        WTagging pr = MorphologicalDatabase.pr.get(word);

        if (pr != null && pr.getLemma().equals("to")) {
            tagging.setLemma(pr.getLemma());
            tagging.setPos(pr.getPos());
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeAdverb(String word, String tag) {
        WTagging tagging = new WTagging();

        IndexWord wnWord = null;
        try {
            wnWord = WordNetWrapper.lookup(word, POS.ADVERB);
        } catch (JWNLException ex) {
            System.out.println(ex);
        }

        tagging.setPos("Rb");

        // lemma
        if (wnWord != null) {
            word = wnWord.getLemma();
            tagging.setLemma(word);
        } else {
            tagging.setLemma(noLemma);
            word = word.toLowerCase();
        }

        // comparison
        if (!tag.equals("RB")) {
            return null;
        }

        return tagging;
    }

    WTagging analyzeAdjective(String word, String tag) {
        WTagging tagging = new WTagging();

        String nmord = TextToNumber.getValue(word);
        if (nmord != null) {
            tagging.setLemma(nmord);
            tagging.setPos("NmORD");
            return tagging;
        }

        IndexWord wnWord = null;
        try {
            wnWord = WordNetWrapper.lookup(word, POS.ADJECTIVE);
        } catch (JWNLException ex) {
            System.out.println(ex);
        }

        tagging.setPos("Jj");

        // lemma
        if (wnWord != null) {
            word = wnWord.getLemma();
            tagging.setLemma(word);
        } else {
            tagging.setLemma(noLemma);
            word = word.toLowerCase();
        }

        // comparison
        if (!tag.equals("JJ")) {
            return null;
        }

        return tagging;
    }

    WTagging analyzeModal(String word, String tag) {
        WTagging tagging = new WTagging();

        word = word.toLowerCase();

        WTagging md = MorphologicalDatabase.md.get(word);

        if (md != null) {
            tagging.setLemma(md.getLemma());
            tagging.setPos(md.getPos());
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeWhDeterminer(String word, String tag) {
        WTagging tagging = new WTagging();

        word = word.toLowerCase();

        WTagging pnrel = MorphologicalDatabase.pnrel.get(word);

        if (pnrel != null) {
            tagging.setLemma(pnrel.getLemma());
            tagging.setPos(pnrel.getPos());
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

        word = word.toLowerCase();

        WTagging pnpos = MorphologicalDatabase.pnpos.get(word);
        WTagging jjpos = MorphologicalDatabase.jjpos.get(word);

        if (pnpos != null && jjpos == null) {
            tagging.setLemma(pnpos.getLemma());
            tagging.setPos(pnpos.getPos());
            tagging.setGender(pnpos.getGender());
            tagging.setNumber(pnpos.getNumber());
            tagging.setWcase(pnpos.getWcase());
            tagging.setPerson(pnpos.getPerson());
        } else if (pnpos == null && jjpos != null) {
            tagging.setLemma(jjpos.getLemma());
            tagging.setPos(jjpos.getPos());
            tagging.setGender(jjpos.getGender());
            tagging.setNumber(jjpos.getNumber());
            tagging.setWcase(jjpos.getWcase());
            tagging.setPerson(jjpos.getPerson());
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeWhAdverb(String word, String tag) {
        WTagging tagging = new WTagging();

        word = word.toLowerCase();

        WTagging rbint = MorphologicalDatabase.rbint.get(word);

        if (rbint != null) {
            tagging.setLemma(rbint.getLemma());
            tagging.setPos(rbint.getPos());
        } else {
            return null;
        }

        return tagging;
    }

    WTagging analyzeExThere(String word, String tag) {
        if (!word.toLowerCase().equals("there")) {
            return null;
        }
        WTagging tagging = new WTagging();
        tagging.setPos("RbEX");
        tagging.setLemma("there");
        return tagging;
    }

    WTagging analyzeWhPron(String word, String tag) {
        WTagging tagging = new WTagging();

        word = word.toLowerCase();

        WTagging pnint = MorphologicalDatabase.pnint.get(word);

        if (pnint != null) {
            tagging.setLemma(pnint.getLemma());
            tagging.setPos(pnint.getPos());
        } else {
            return null;
        }

        return tagging;
    }
}
