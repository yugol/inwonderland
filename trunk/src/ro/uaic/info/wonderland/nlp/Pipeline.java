/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import ro.uaic.info.wonderland.nlp.resources.StanfordParserWrapper;
import ro.uaic.info.wonderland.nlp.resources.StanfordPostaggerWrapper;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import java.util.List;

/**
 *
 * @author Iulian
 */
public class Pipeline {

    static WTagger wTagger = new WTagger();

    public static List<List<? extends HasWord>> getTokenisedSentences(String text) {
        return StanfordParserWrapper.getSentences(text);
    }

    public static List<TaggedWord> getPennPosTags(List<? extends HasWord> sentence) {
        return StanfordPostaggerWrapper.getPennPosTags(sentence);
    }

    public static List<TaggedWord> getPennPosTags(Tree parse) {
        return StanfordParserWrapper.getPennPosTags(parse);
    }

    public static List<TypedDependency> getPennDependencies(List<TaggedWord> tSentence) {
        Tree parse = StanfordParserWrapper.parse(tSentence);
        return StanfordParserWrapper.getDependencies(parse);
    }

    public static WTagging[] getWTags(List<TaggedWord> tSent) {
        return wTagger.getWTagsByForm(tSent);
    }

    public static Object[] parse(List<? extends HasWord> sent) {
        Tree parse = StanfordParserWrapper.parse(sent);
        List<TaggedWord> pennTags = StanfordParserWrapper.getPennPosTags(parse);
        List<TypedDependency> deps = StanfordParserWrapper.getDependencies(parse);
        WTagging[] wTags = wTagger.getWTagsByForm(pennTags);
        return new Object[]{wTags, deps};
    }
}
