/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.analysis;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.util.List;
import ro.uaic.info.wonderland.Globals;

/**
 *
 * @author Iulian
 */
public abstract class StanfordPostaggerWrapper {

    static {
        try {
            new MaxentTagger(Globals.getStanfordPostaggerPath());
        } catch (Exception ex) {
            System.out.println("Error initializing Stanford Parser or Postagger");
            System.out.println(ex);
            Globals.exit();
        }
    }

    public static List<TaggedWord> getPennPosTags(List<? extends HasWord> sent) {
        return MaxentTagger.tagSentence(sent);
    }
}
