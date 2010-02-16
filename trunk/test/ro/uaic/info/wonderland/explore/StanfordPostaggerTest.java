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
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class StanfordPostaggerTest {

    @Test
    public void testPostagger() throws Exception {
        new MaxentTagger(Globals.getStanfordPostaggerPath());
        StringReader r = new StringReader("The world is mine.");
        List<Sentence<? extends HasWord>> sentences = MaxentTagger.tokenizeText(r);
        for (Sentence<? extends HasWord> sentence : sentences) {
            Sentence<TaggedWord> tSentence = MaxentTagger.tagSentence(sentence);
            System.out.println(tSentence.toString(false));
            assertEquals("JJ", tSentence.get(3).tag());
        }
    }
}
