/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import ro.uaic.info.wonderland.nlp.resources.StanfordParserWrapper;
import ro.uaic.info.wonderland.nlp.resources.StanfordPostaggerWrapper;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.trees.CollocationFinder;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.WordNetInstance;
import java.util.List;
import ro.uaic.info.wonderland.nlp.resources.MorphAdornerWrapper;

/**
 *
 * @author Iulian
 */
public class Pipeline {

    static WTagger wTagger = new WTagger();

    public static List<List<? extends HasWord>> getTokenisedSentences(String text) {
        return StanfordParserWrapper.getSentences(text);
    }

    private static List<TaggedWord> getPennPosTags(List<? extends HasWord> sentence) {
        return StanfordPostaggerWrapper.getPennPosTags(sentence);
    }

    private static List<TaggedWord> getPennPosTags(Tree parse) {
        return StanfordParserWrapper.getPennPosTags(parse);
    }

    private static List<TypedDependency> getPennDependencies(List<TaggedWord> tSentence) {
        Tree parse = StanfordParserWrapper.parse(tSentence);
        return StanfordParserWrapper.getDependencies(parse);
    }

    private static WTagging[] getWTags(List<TaggedWord> tSent) {
        return wTagger.getWTagsByForm(tSent);
    }

    private static Object[] parseSimple(List<? extends HasWord> sent) {
        Tree parse = StanfordParserWrapper.parse(sent);
        List<TaggedWord> pennTags = StanfordParserWrapper.getPennPosTags(parse);
        List<TypedDependency> deps = StanfordParserWrapper.getDependencies(parse);
        WTagging[] wTags = wTagger.getWTagsByForm(pennTags);
        return new Object[]{wTags, deps};
    }

    private static Object[] parseTagger(List<? extends HasWord> sent) {
        List<TaggedWord> pennTags = StanfordPostaggerWrapper.getPennPosTags(sent);
        Tree parse = StanfordParserWrapper.parse(pennTags);
        List<TypedDependency> deps = StanfordParserWrapper.getDependencies(parse);
        WTagging[] wTags = wTagger.getWTagsByForm(pennTags);
        return new Object[]{wTags, deps};
    }

    public static Object[] parse(List<WTagging> sentence) {
        Tree parse = StanfordParserWrapper.parse(sentence);
        CollocationFinder colloFinder = new CollocationFinder(parse, new CollocationManager());
        parse = colloFinder.getMangledTree();
        List<TaggedWord> pennTags = StanfordParserWrapper.getPennPosTags(parse);
        if (pennTags.size() == sentence.size()) {
            for (int i = 0; i < pennTags.size(); ++i) {
                sentence.get(i).setPennTag(pennTags.get(i).tag());
            }
        } else {
            sentence = CollocationManager.buildSentenceWithCollocations(sentence, pennTags);
        }
        wTagger.addWTags(sentence);
        List<TypedDependency> deps = StanfordParserWrapper.getDependencies(parse);
        return new Object[]{sentence, deps};
    }

    public static List<List<WTagging>> getTokens(String text) {
        return MorphAdornerWrapper.tagText(text);
    }
}
