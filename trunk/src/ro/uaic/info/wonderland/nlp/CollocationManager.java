/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.trees.WordNetConnection;
import edu.stanford.nlp.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import ro.uaic.info.wonderland.nlp.resources.MorphAdornerWrapper;

/**
 *
 * @author Iulian
 */
class CollocationManager implements WordNetConnection {

    static List<WTagging> buildSentenceWithCollocations(List<WTagging> sentence, List<TaggedWord> pennTags) {
        List<WTagging> newSentence = new ArrayList<WTagging>();
        int p = 0;
        for (int s = 0; s < sentence.size(); ++s) {
            WTagging wt = sentence.get(s);
            TaggedWord pt = pennTags.get(p);
            String wWord = wt.word();
            String pWord = pt.word();
            if (wWord.equals(pWord)) {
                WTagging tagging = new WTagging();
                MorphAdornerWrapper.copyAdornedWord(tagging, wt);
                tagging.setPennTag(pt.tag());
                newSentence.add(tagging);
                ++p;
            } else {
                WTagging tagging = new WTagging();
                tagging.setForm(pWord);

                String[] words = pWord.split("_");
                words[0] = wt.getLemma();
                for (int i = 1; i < words.length; ++i) {
                    ++s;
                    words[i] = sentence.get(s).getLemma();
                }
                tagging.setLemma(StringUtils.join(words, "_"));

                tagging.setPennTag(pt.tag());
                tagging.setCollocation(true);
                newSentence.add(tagging);
                ++p;
            }
        }
        return newSentence;
    }

    public boolean wordNetContains(String s) {
        return (MorphologicalDatabase.collocations.get(s) != null);
    }
}
