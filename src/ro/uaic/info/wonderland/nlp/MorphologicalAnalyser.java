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
        }

        // lemma
        if (wnWord != null) {
            tagging.setLemma(wnWord.getLemma());
        }
        if (tag.indexOf("NNP") == 0) {
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
        // mood
        // number
        // person
        // tense

        WTagging tagging = new WTagging();

        IndexWord wnWord = null;
        try {
            wnWord = WordNetWrapper.lookup(word, POS.VERB);
        } catch (JWNLException ex) {
            System.out.println(ex);
        }

        // lemma
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
            tagging.setNumber("sng");
            tagging.setPerson("stnd");
            tagging.setTense("ps");
        } else {
            return null;
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

        if (ar != null && jjind == null) {
            tagging.setLemma(ar.getLemma());
            tagging.setPos(ar.getPos());
        } else if (ar == null && jjind != null) {
            tagging.setLemma(jjind.getLemma());
            tagging.setPos(jjind.getPos());
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

        if (pnprs != null) {
            tagging.setLemma(pnprs.getLemma());
            tagging.setPos(pnprs.getPos());
            tagging.setGender(pnprs.getGender());
            tagging.setNumber(pnprs.getNumber());
            tagging.setWcase(pnprs.getWcase());
            tagging.setPerson(pnprs.getPerson());
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
}
