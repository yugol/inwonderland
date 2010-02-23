/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

        String[] sent = {"They", "left", "one", "by", "one", "."};
        // String[] sent = "The woman assured us that , in less than half an hour , her baby would be sleeping.".split(" ");
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


