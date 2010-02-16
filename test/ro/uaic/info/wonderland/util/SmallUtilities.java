/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;

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

}
