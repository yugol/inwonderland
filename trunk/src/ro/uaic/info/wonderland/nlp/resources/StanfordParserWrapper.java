/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp.resources;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
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
public abstract class StanfordParserWrapper {

    static LexicalizedParser lp;
    static DocumentPreprocessor dp;
    static GrammaticalStructureFactory gsf;

    static {
        lp = new LexicalizedParser(Globals.getStanfordParserFile().getAbsolutePath());
        lp.setOptionFlags(new String[]{"-retainTmpSubcategories", "-outputFormat", "penn,typedDependencies,collocations", "-outputFormatOptions", "treeDependencies"});
        dp = new DocumentPreprocessor(lp.getOp().tlpParams.treebankLanguagePack().getTokenizerFactory());
        gsf = lp.getOp().langpack().grammaticalStructureFactory();
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
