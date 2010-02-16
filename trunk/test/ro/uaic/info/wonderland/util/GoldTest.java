/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import org.junit.Test;
import static org.junit.Assert.*;
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.nlp.WTagging;
import ro.uaic.info.wonderland.engine.MessageProcessor;

/**
 *
 * @author Iulian
 */
public class GoldTest {

    @Test
    public void reIndexGoldCorpus() throws Exception {
        System.out.println("reIndexGoldCorpus");
        Corpus gold = new Corpus();
        gold.buildFrom(Globals.getGoldCorpusFile());
        gold.reIndexSentences();
        gold.removePennAttributes();
        gold.writeToFile(Globals.getGoldCorpusFile());
    }

    @Test
    public void testGoldCorpus() throws Exception {
        System.out.println("testGoldCorpus");
        MessageProcessor msgProc = new MessageProcessor();
        Corpus gold = new Corpus();
        gold.buildFrom(Globals.getGoldCorpusFile());

        int errors = 0;
        for (int i = 1; i < gold.getSentenceCount(); ++i) {
            boolean printed = false;
            String sentence = gold.getSentenceStringByIndex(i);

            msgProc.processMessage(sentence);
            WTagging[] expected = gold.getSentencePosProps(i);
            WTagging[] actual = msgProc.getKb().getSentencePosProps(i, false);
            assertEquals(expected.length, actual.length);

            for (int j = 0; j < actual.length; ++j) {
                String err = WTaggingUtil.areConsistent(expected[j], actual[j]);
                if (err != null) {
                    if (!printed) {
                        System.out.println("");
                        System.out.println("ERROR in sentence [" + i + "]:");
                        System.out.println(sentence);
                        printed = true;
                    }
                    System.out.println("    WORD [" + (j + 1) + "]: " + actual[j].form);
                    System.out.println(err);
                    ++errors;
                }
            }
            System.out.print(".");
        }
        assertEquals(0, errors);
    }
}
