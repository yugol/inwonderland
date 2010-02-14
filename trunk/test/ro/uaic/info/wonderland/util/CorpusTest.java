/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import ro.uaic.info.wonderland.engine.EngineKnowledgeBase;
import ro.uaic.info.wonderland.engine.MessageProcessor;

/**
 *
 * @author Iulian
 */
public class CorpusTest {

    @Test
    public void testCorpusSimpleOperations() throws Exception {
        MessageProcessor msgProc = new MessageProcessor();
        String resp = msgProc.processMessage("This is an interesting book.");
        assertEquals("Done.", resp);
        resp = msgProc.processMessage("This is the second message.");
        assertEquals("Done.", resp);

        EngineKnowledgeBase ekb = msgProc.getKb();
        Corpus corpus = new Corpus();
        corpus.buildFrom(ekb);

        String xml = corpus.toString();
        File cospusFile = new File("corpus.xml");
        corpus.writeToFile(cospusFile);
        corpus.buildFrom(cospusFile);
        assertEquals(xml, corpus.toString());

        String sentence = corpus.getSentenceStringByIndex(2);
        assertEquals("This is the second message .", sentence);
    }
}
