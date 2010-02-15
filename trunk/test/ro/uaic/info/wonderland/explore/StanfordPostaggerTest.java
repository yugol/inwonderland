/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.explore;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.StringReader;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class StanfordPostaggerTest {

    /*
     * You need to compile stanford-postagger and change edu.stanford.nlp.util to another name
     * or else util classes will interfere with stanford-parser util classes
     */

    @Test
    public void testPostagger() throws Exception {
        MaxentTagger tagger = new MaxentTagger(Globals.getStanfordPostaggerPath());
        StringReader r = new StringReader("The world is mine.");
        List<Sentence<? extends HasWord>> sentences = MaxentTagger.tokenizeText(r);
        for (Sentence<? extends HasWord> sentence : sentences) {
            Sentence<TaggedWord> tSentence = MaxentTagger.tagSentence(sentence);
            System.out.println(tSentence.toString(false));
            assertEquals("JJ", tSentence.get(3).tag());
        }
    }
}
