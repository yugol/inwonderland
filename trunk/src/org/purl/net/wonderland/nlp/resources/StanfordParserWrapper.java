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

package org.purl.net.wonderland.nlp.resources;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import java.io.StringReader;
import java.util.List;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian
 */
public final class StanfordParserWrapper {

    static LexicalizedParser lp;
    static DocumentPreprocessor dp;
    static GrammaticalStructureFactory gsf;

    static {
        CodeTimer timer = new CodeTimer("StanfordParserWrapper");
        lp = new LexicalizedParser(Globals.getStanfordParserFile().getAbsolutePath());
        lp.setOptionFlags(new String[]{"-retainTmpSubcategories", "-outputFormat", "penn,typedDependencies,collocations", "-outputFormatOptions", "treeDependencies"});
        dp = new DocumentPreprocessor(lp.getOp().tlpParams.treebankLanguagePack().getTokenizerFactory());
        gsf = lp.getOp().langpack().grammaticalStructureFactory();
        timer.stop();
    }

    public static LexicalizedParser getParser() {
        return lp;
    }

    public static List<List<? extends HasWord>> getSentences(String text) {
        return dp.getSentencesFromText(new StringReader(text));
    }

    public static Tree parse(List<? extends HasWord> sent) {
        return lp.apply(sent);
    }

    public static List<TypedDependency> getDependencies(Tree parseTree) {
        GrammaticalStructure gs = gsf.newGrammaticalStructure(parseTree);
        return gs.typedDependencies(true);
    }

    public static List<Word> tokenize(String text) {
        return dp.getWordsFromString(text);
    }

    public static List<TaggedWord> getPennPosTags(Tree parse) {
        return parse.taggedYield();
    }
}
