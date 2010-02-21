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
    TagMapper tm = new TagMapper();

    void addWTags(List<WTagging> sentence) {
        for (WTagging tagging : sentence) {
            String tag = tagging.getPennTag();

            if (tagging.isCollocation()) {
                tm.mapCollocation(tagging);
                if (tagging.getPos() != null) {
                    continue;
                }
            }

            if (tag.indexOf("NN") == 0) { // NN, NNP, NNS, NNPS
                tm.mapNN(tagging, tag);
            } else if (tag.indexOf("VB") == 0) { // VBZ, VBP, VB, VBG
                tm.mapVB(tagging, tag);
            } else if (tag.equals("IN")) { // IN
                tm.mapIN(tagging, tag);
            } else if (tag.equals("DT")) { // DT
                tm.mapDT(tagging, tag);
            } else if (tag.equals("CC")) { // CC
                tm.mapCC(tagging, tag);
            } else if (tag.equals("PRP")) { // PRP
                tm.mapPRP(tagging, tag);
            } else if (tag.equals("TO")) { // TO
                tm.mapTO(tagging, tag);
            } else if (tag.indexOf("RB") == 0) { // RB
                tm.mapRB(tagging, tag);
            } else if (tag.indexOf("JJ") == 0) { // JJ
                tm.mapJJ(tagging, tag);
            } else if (tag.equals("MD")) { // MD
                tm.mapMD(tagging, tag);
            } else if (tag.equals("WDT")) { // WDT
                tm.mapWDT(tagging, tag);
            } else if (tag.equals("CD")) { // CD
                tm.mapCD(tagging, tag);
            } else if (tag.equals("PRP$")) { // PRP$
                tm.mapPRPS(tagging, tag);
            } else if (tag.equals("WRB")) { // WRB
                tm.mapWRB(tagging, tag);
            } else if (tag.equals("EX")) { // EX
                tm.mapEX(tagging, tag);
            } else if (tag.equals("WP")) { // WP
                tm.mapWP(tagging, tag);
            } else if (tag.equals("FW")) { // FW
                tm.mapFW(tagging, tag);
            } else if (tag.equals("RP")) { // RP
                tm.mapRP(tagging, tag);
            } else if (tag.equals("POS")) { // POS
                tm.mapPOS(tagging, tag);
            } else if (tag.equals("PDT")) { // PDT
                tm.mapPDT(tagging, tag);
            }
        }
    }

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
            } else if (tag.equals("POS")) { // POS
                tagging = ma.analyzePossEnding(lcWord, tag);
            } else if (tag.equals("PDT")) { // PDT
                tagging = ma.analyzePossEnding(lcWord, tag);
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
