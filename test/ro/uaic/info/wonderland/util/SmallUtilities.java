/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.nlp.MorphologicalDatabase;
import ro.uaic.info.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian
 */
public class SmallUtilities {

    @Test
    public void announcement() {
        System.out.println("These procedures are used for maintenance only.");
    }

    // @Test
    public void testNormalizeConceptTypes() throws FileNotFoundException, IOException {
        System.out.println("normalizeConceptTypes");
        KB.normalizeConceptTypes(new File(Globals.getDefaultParseKBPath()));
        KB.normalizeRelationTypes(new File(Globals.getDefaultParseKBPath()));
    }

    // @Test
    public void reIndexGoldCorpus() throws Exception {
        System.out.println("reIndexGoldCorpus");
        Corpus gold = new Corpus();
        gold.buildFrom(Globals.getGoldCorpusFile());
        gold.reIndexSentences();
        gold.removePennAttributes();
        gold.writeToFile(Globals.getGoldCorpusFile());
    }

    @Test
    public void findTaggingDuplicates() {
        for (String form : MorphologicalDatabase.getAllForms()) {
            List<WTagging> taggings = MorphologicalDatabase.getAllTagings(form);
            if (taggings.size() > 1) {
                System.out.println("");
                System.out.println(form);
                for (WTagging tagging : taggings) {
                    System.out.println(tagging.toCsvString());
                }
            }
        }
    }
}
