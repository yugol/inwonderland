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

    static TagMapper tagMapper = new TagMapper();

    public static List<List<? extends HasWord>> getTokenisedSentences(String text) {
        return StanfordParserWrapper.getSentences(text);
    }

    private static List<TaggedWord> getPennPosTags(Tree parse) {
        return StanfordParserWrapper.getPennPosTags(parse);
    }

    private static List<TypedDependency> getPennDependencies(List<TaggedWord> tSentence) {
        Tree parse = StanfordParserWrapper.parse(tSentence);
        return StanfordParserWrapper.getDependencies(parse);
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
        tagMapper.mapWTags(sentence);
        List<TypedDependency> deps = StanfordParserWrapper.getDependencies(parse);
        return new Object[]{sentence, deps};
    }

    public static List<List<WTagging>> tokenizeAndSplit(String text) {
        return MorphAdornerWrapper.tagText(text);
    }
}
