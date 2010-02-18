/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import edu.stanford.nlp.ling.TaggedWord;
import java.util.List;

/**
 *
 * @author Iulian
 */
public class WTagger {

    MorphologicalAnalyser ma = new MorphologicalAnalyser();

    WTagging[] getWTagsByForm(List<TaggedWord> tSent) {
        WTagging[] wTags = new WTagging[tSent.size()];
        for (int i = 0; i < wTags.length; ++i) {
            TaggedWord tWord = tSent.get(i);
            String word = tWord.word();
            String tag = tWord.tag();

            WTagging tagging = null;
            String lcWord = word.toLowerCase();
            if (tag.indexOf("NN") == 0) { // NN, NNP, NNS, NNPS
                tagging = ma.analyzeNoun(lcWord, tag);
            } else if (tag.indexOf("VB") == 0) { // VBZ, VBP, VB, VBG
                tagging = ma.analyzeVerb(lcWord, tag);
            } else if (tag.equals("IN")) { // IN
                tagging = ma.analyzePrepOrSubConj(lcWord, tag);
            } else if (tag.equals("DT")) { // DT
                tagging = ma.analyzeDeterminer(lcWord, tag);
            } else if (tag.equals("CC")) { // CC
                tagging = ma.analyzeCoordConj(lcWord, tag);
            } else if (tag.equals("PRP")) { // PRP
                tagging = ma.analyzePersPron(lcWord, tag);
            } else if (tag.equals("TO")) { // TO
                tagging = ma.analyzeTo(lcWord, tag);
            } else if (tag.indexOf("RB") == 0) { // RB
                tagging = ma.analyzeAdverb(lcWord, tag);
            } else if (tag.indexOf("JJ") == 0) { // JJ
                tagging = ma.analyzeAdjective(lcWord, tag);
            } else if (tag.equals("MD")) { // MD
                tagging = ma.analyzeModal(lcWord, tag);
            } else if (tag.equals("WDT")) { // WDT
                tagging = ma.analyzeWhDeterminer(lcWord, tag);
            } else if (tag.equals("CD")) { // CD
                tagging = ma.analyzeCardinalNumber(lcWord, tag);
            } else if (tag.equals("PRP$")) { // PRP$
                tagging = ma.analyzePossPron(lcWord, tag);
            } else if (tag.equals("WRB")) { // WRB
                tagging = ma.analyzeWhAdverb(lcWord, tag);
            } else if (tag.equals("EX")) { // EX
                tagging = ma.analyzeExThere(lcWord, tag);
            } else if (tag.equals("WP")) { // WP
                tagging = ma.analyzeWhPron(lcWord, tag);
            } else if (tag.equals("FW")) { // FW
                tagging = ma.analyzeForeignWord(lcWord, tag);
            } else if (tag.equals("RP")) { // RP
                tagging = ma.analyzeParticle(lcWord, tag);
            }

            if (tagging == null) {
                tagging = new WTagging();
                tagging.setLemma(lcWord);
            }
            tagging.setForm(word);
            tagging.setPennTag(tag);


            /*
            if (tagging.pennTag.indexOf("NN") == 0) { // NN, NNP, NNS, NNPS
            // prop = ma.analyzeNoun(wordLabel, wordTag);
            } else if (tagging.pennTag.equals("DT") || tagging.pennTag.equals("PDT")) {
            ma.analyzeDeterminer(tagging);
            } else if (tagging.pennTag.indexOf("PRP") == 0) { // PRP, PRP$
            ma.analyzePersonalPronoun(tagging);
            } else if (tagging.pennTag.indexOf("JJ") == 0) { // JJ, JJS, JJC
            ma.analyzeAdjective(tagging);
            }
             *
             */

            wTags[i] = tagging;
        }
        return wTags;
    }
}
