/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.parse;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
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
import ro.uaic.info.wonderland.Globals;

/**
 *
 * @author Iulian
 */
public final class StanfordParser {

    LexicalizedParser lp;
    DocumentPreprocessor dp;
    GrammaticalStructureFactory gsf;

    public StanfordParser() {
        lp = new LexicalizedParser(Globals.getStanfordParserPath());
        lp.setOptionFlags(new String[]{"-retainTmpSubcategories", "-outputFormat", "penn,typedDependencies", "-outputFormatOptions", "treeDependencies"});
        dp = new DocumentPreprocessor(lp.getOp().tlpParams.treebankLanguagePack().getTokenizerFactory());
        gsf = lp.getOp().langpack().grammaticalStructureFactory();
    }

    public LexicalizedParser getParser() {
        return lp;
    }

    public List<List<? extends HasWord>> getSentences(String text) {
        return dp.getSentencesFromText(new StringReader(text));
    }

    public Tree parse(List<? extends HasWord> sent) {
        return lp.apply(sent);
    }

    public Sentence<TaggedWord> getPOSTags(Tree parseTree) {
        return parseTree.taggedYield();
    }

    public List<TypedDependency> getDependencies(Tree parseTree) {
        GrammaticalStructure gs = gsf.newGrammaticalStructure(parseTree);
        return gs.typedDependencies(true);
    }

    public List<Word> tokenize(String text) {
        return dp.getWordsFromString(text);
    }
}
