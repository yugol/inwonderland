/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.util;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;
import org.purl.net.wonderland.kb.EngineKnowledgeBase;
import org.purl.net.wonderland.engine.MessageProcessor;

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
