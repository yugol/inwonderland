/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import org.junit.Test;
import static org.junit.Assert.*;
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.analysis.PosProp;
import ro.uaic.info.wonderland.engine.MessageProcessor;

/**
 *
 * @author Iulian
 */
public class GoldTest {

    @Test
    public void testGoldCorpus() throws Exception {
        MessageProcessor msgProc = new MessageProcessor();
        Corpus gold = new Corpus();
        gold.buildFrom(Globals.getGoldCorpusFile());
        for (int i = 1; i < gold.getSentenceCount(); ++i) {
            msgProc.processMessage(gold.getSentenceStringByIndex(i));
            PosProp[] expected = gold.getSentencePosProps(i);
            PosProp[] actual = msgProc.getKb().getSentencePosProps(i);
            assertEquals(expected.length, actual.length);
            for (int j = 0; j < actual.length; ++j) {
                assertTrue("Sentence " + i + " word " + (j + 1), PosUtil.areConsistent(expected[j], actual[j]));
            }
        }

    }
}
