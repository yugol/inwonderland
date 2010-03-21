/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.purl.net.wonderland.nlp;

import org.purl.net.wonderland.nlp.resources.StanfordParserWrapper;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.trees.CollocationFinder;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import java.util.List;
import org.purl.net.wonderland.nlp.resources.MorphAdornerWrapper;

/**
 *
 * @author Iulian
 */
public class Pipeline {

    static MaToPennTagMapper preTagMapper = new MaToPennTagMapper();
    static WTagMapper wTagMapper = new WTagMapper();

    public static List<List<? extends HasWord>> getTokenisedSentences(String text) {
        return StanfordParserWrapper.getSentences(text);
    }

    public static Object[] parse(List<WTagging> sentence) {
        // pre-tag sentence
        preTagMapper.map(sentence);

        // parse the sentence
        Tree parse = StanfordParserWrapper.parse(sentence);

        // find collocations
        CollocationFinder colloFinder = new CollocationFinder(parse, new CollocationManager());
        parse = colloFinder.getMangledTree();

        // get Penn tags
        List<TaggedWord> pennTags = StanfordParserWrapper.getPennPosTags(parse);
        if (pennTags.size() == sentence.size()) {
            for (int i = 0; i < pennTags.size(); ++i) {
                sentence.get(i).setPennTag(pennTags.get(i).tag());
            }
        } else {
            sentence = CollocationManager.buildSentenceWithCollocations(sentence, pennTags);
        }

        // map Penn and MorphAdorner tags to W tags
        wTagMapper.mapWTags(sentence);

        // get parsing dependencies
        List<TypedDependency> deps = StanfordParserWrapper.getDependencies(parse);

        // return tags and dependencies
        return new Object[]{sentence, deps};
    }

    public static List<List<WTagging>> tokenizeAndSplit(String text) {
        return MorphAdornerWrapper.tagText(text);
    }

}
