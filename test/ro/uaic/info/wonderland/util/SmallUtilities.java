/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;
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
        KB.normalizeConceptTypes(Globals.getDefaultParseKBFile());
        KB.normalizeRelationTypes(Globals.getDefaultParseKBFile());
    }

    @Test
    public void reIndexGoldCorpus() throws Exception {
        System.out.println("reIndexGoldCorpus");
        File goldFile = new File(Globals.getCorporaFolder(), "egcp.train.level1.xml");
        Corpus gold = new Corpus();
        gold.buildFrom(goldFile);
        gold.reIndexSentences();
        gold.removePennAttributes();
        gold.writeToFile(goldFile);
        System.out.println("Reindexed " + gold.getSentenceCount() + " sentences.");
    }

    // @Test
    public void findTaggingDuplicates() {
        System.out.println("Find polysemy entries in morphological database");
        for (String form : MorphologicalDatabase.getAllForms()) {
            List<WTagging> taggings = MorphologicalDatabase.getAllTagings(form);
            if (taggings.size() > 1) {
                displayTaggings(taggings, form);
            }
        }
    }

    // @Test
    public void findFormInDatabase() {
        System.out.println("Find form in morphological database");
        String form = "whatever";
        List<WTagging> taggings = MorphologicalDatabase.getAllTagings(form);
        if (taggings.size() > 0) {
            displayTaggings(taggings, form);
        }
    }

    // @Test
    public void getSentencesFromCorpus() throws ParserConfigurationException, SAXException {
        System.out.println("Getting sentences from corpus");
        Corpus gold = new Corpus();
        File goldFile = new File(Globals.getCorporaFolder(), "gold.xml");
        gold.buildFrom(goldFile);

        for (int i = 1; i <= gold.getSentenceCount(); ++i) {
            String sentence = gold.getSentenceStringByIndex(i);
            System.out.println(sentence);
        }
    }

    private void displayTaggings(List<WTagging> taggings, String form) {
        System.out.println("");
        System.out.println(form);
        for (WTagging tagging : taggings) {
            System.out.println(tagging.toCsvString());
        }
    }
}
