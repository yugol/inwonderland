/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp.resources;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.util.List;
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian
 */
public final class StanfordPostaggerWrapper {

    static {
        try {
            CodeTimer timer = new CodeTimer("StanfordPostaggerWrapper");
            new MaxentTagger(Globals.getStanfordPostaggerFile().getAbsolutePath());
            timer.stop();
        } catch (Exception ex) {
            System.err.println("Error initializing StanfordPostaggerWrapper");
            System.err.println(ex);
            Globals.exit();
        }
    }

    public static List<TaggedWord> getPennPosTags(List<? extends HasWord> sent) {
        return MaxentTagger.tagSentence(sent);
    }
}
