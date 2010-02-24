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

package org.purl.net.wonderland.explore;

import org.purl.net.wonderland.nlp.resources.StanfordParserWrapper;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.CollocationFinder;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TypedDependency;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.purl.net.wonderland.Globals;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class StanfordParserTest {

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    // @Test
    public void testParse() {
        String text = "The colourless green ideas sleep furiously. This is a text."
                + "The colourless green ideas sleep furiously."
                + "The colourless green ideas sleep furiously."
                + "The colourless green ideas sleep furiously."
                + "The colourless green ideas sleep furiously."
                + "The colourless green ideas sleep furiously."
                + "The colourless green ideas sleep furiously."
                + "The colourless green ideas sleep furiously."
                + "The colourless green ideas sleep furiously."
                + "The colourless green ideas sleep furiously."
                + "The colourless green ideas sleep furiously.";
        for (List<? extends HasWord> sentence : StanfordParserWrapper.getSentences(text)) {
            assertTrue(StanfordParserWrapper.getParser().parse(sentence));
            // Globals.lp.getTreePrint().printTree(Globals.lp.getBestParse());
        }
    }

    @Test
    public void testDemo() {
        LexicalizedParser lp = new LexicalizedParser(Globals.getStanfordParserFile().getAbsolutePath());
        lp.setOptionFlags(new String[]{"-maxLength", "80", "-retainTmpSubcategories", "-outputFormat", "penn,typedDependencies", "-outputFormatOptions", "treeDependencies"});

        // String[] sent = {"They", "left", "one", "by", "one", "."};
        String[] sent = "They looked exactly alike .".split(" ");
        Tree parse = (Tree) lp.apply(Arrays.asList(sent));
        CollocationFinder collo = new CollocationFinder(parse, new WordNetInstance());
        parse = collo.getMangledTree();
        TreePrint tp = new TreePrint("wordsAndTags,typedDependencies");
        tp.printTree(parse);

        Sentence<TaggedWord> poss = parse.taggedYield();
        assertEquals(4, poss.length());

        GrammaticalStructureFactory gsf = lp.getOp().langpack().grammaticalStructureFactory();
        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
        List<TypedDependency> deps = gs.typedDependenciesCCprocessed(true);
        assertEquals(2, deps.size());
    }
}


