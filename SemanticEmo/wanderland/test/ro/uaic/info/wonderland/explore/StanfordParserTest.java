/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.explore;

import ro.uaic.info.wonderland.parse.StanfordParser;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.trees.Dependencies;
import edu.stanford.nlp.trees.Dependency;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.trees.TypedDependency;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class StanfordParserTest {

    public StanfordParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

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
        StanfordParser sp = new StanfordParser();
        for (List<? extends HasWord> sentence : sp.getSentences(text)) {
            assertTrue(sp.getParser().parse(sentence));
            // Globals.lp.getTreePrint().printTree(Globals.lp.getBestParse());
        }
    }

    @Test
    public void testDemo() {
        LexicalizedParser lp = new LexicalizedParser(Globals.getStanfordParserPath());
        lp.setOptionFlags(new String[]{"-maxLength", "80", "-retainTmpSubcategories", "-outputFormat", "penn,typedDependencies", "-outputFormatOptions", "treeDependencies"});

        String[] sent = {"This", "is", "an", "easy", "sentence", "."};
        Tree parse = (Tree) lp.apply(Arrays.asList(sent));
        TreePrint tp = new TreePrint("wordsAndTags,typedDependencies"); //,collocations");
        tp.printTree(parse);


        Sentence<TaggedWord> poss = parse.taggedYield();
        assertEquals(sent.length, poss.size());

        GrammaticalStructureFactory gsf = lp.getOp().langpack().grammaticalStructureFactory();
        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
        List<TypedDependency> deps = gs.typedDependenciesCCprocessed(true);
        assertEquals(5, deps.size());

    }
}


