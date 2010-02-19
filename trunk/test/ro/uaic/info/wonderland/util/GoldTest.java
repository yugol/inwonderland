/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import java.io.File;
import java.util.List;
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
    public void testGoldCorpus() throws Exception {
        System.out.println("testGoldCorpus");

        List<String> plain = IO.getFileContentAsStringList(new File(Globals.getCorporaFolder(), "egcp.train.plain.txt"));
        Corpus level1 = new Corpus();
        level1.buildFrom(new File(Globals.getCorporaFolder(), "egcp.train.level1.xml"));
        MessageProcessor msgProc = new MessageProcessor();

        int errorCount = 0;
        int wordCount = 0;
        int sentenceCount = 0;
        for (int i = 1; i <= level1.getSentenceCount(); ++i) {
            boolean printed = false;
            String sentence = plain.get(i - 1);

            msgProc.processMessage(sentence);
            WTagging[] expected = level1.getSentencePosProps(i);
            WTagging[] actual = msgProc.getKb().getSentencePosProps(i, false);
            assertEquals(expected.length, actual.length);

            for (int j = 0; j < actual.length; ++j) {
                String errStr = WTaggingUtil.areConsistent(expected[j], actual[j]);
                if (errStr != null) {
                    if (!printed) {
                        System.out.println("");
                        System.out.println("ERROR in sentence [" + i + "]:");
                        System.out.println(sentence);
                        printed = true;
                    }
                    System.out.println("    WORD [" + (j + 1) + "]: " + actual[j].getForm());
                    System.out.println(errStr);
                    ++errorCount;
                }
                ++wordCount;
            }
            System.out.print(".");
            ++sentenceCount;
        }
        System.out.println("\n\nResults: " + errorCount + " error(s), for " + wordCount + " words in " + sentenceCount + " sentences.");
        assertEquals(0, errorCount);
    }
}
