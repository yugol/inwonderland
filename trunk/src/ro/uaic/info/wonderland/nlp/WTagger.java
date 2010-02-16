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
            WTagging tagging = new WTagging();
            TaggedWord word = tSent.get(i);

            tagging.form = word.word();
            tagging.pennTag = word.tag();
            tagging.lemma = tagging.form.toLowerCase();

            if (tagging.pennTag.indexOf("NN") == 0) { // NN, NNP, NNS, NNPS
                // prop = ma.analyzeNoun(wordLabel, wordTag);
            } else if (tagging.pennTag.equals("DT") || tagging.pennTag.equals("PDT")) {
                ma.analyzeDeterminer(tagging);
            } else if (tagging.pennTag.indexOf("PRP") == 0) { // PRP, PRP$
                ma.analyzePersonalPronoun(tagging);
            } else if (tagging.pennTag.indexOf("JJ") == 0) { // JJ, JJS, JJC
                ma.analyzeAdjective(tagging);
            }

            wTags[i] = tagging;
        }
        return wTags;
    }
}
