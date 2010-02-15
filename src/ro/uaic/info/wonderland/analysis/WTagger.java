/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.analysis;

import edu.stanford.nlp.ling.TaggedWord;
import java.util.List;

/**
 *
 * @author Iulian
 */
public class WTagger {

    Lemmatiser lemmatiser = new Lemmatiser();
    MorphologicalAnalyser ma = new MorphologicalAnalyser();

    WTagging[] getWTagsByForm(List<TaggedWord> tSent) {
        WTagging[] wTags = new WTagging[tSent.size()];
        for (int i = 0; i < wTags.length; ++i) {
            WTagging tagging = new WTagging();
            TaggedWord word = tSent.get(i);

            tagging.form = word.word();
            tagging.pos = word.tag();
            tagging.lemma = lemmatiser.getLemma(tagging);

            if (tagging.pos.indexOf("NN") == 0) { // NN, NNP, NNS, NNPS
                // prop = ma.analyzeNoun(wordLabel, wordTag);
            } else if (tagging.pos.equals("DT")) {
                ma.analyzeDeterminer(tagging);
            } else if (tagging.pos.indexOf("PRP") == 0) { // PRP, PRP$
                ma.analyzePersonalPronoun(tagging);
            } else if (tagging.pos.indexOf("JJ") == 0) { // JJ, JJS, JJC
                ma.analyzeAdjective(tagging);
            }

            wTags[i] = tagging;
        }
        return wTags;
    }
}
