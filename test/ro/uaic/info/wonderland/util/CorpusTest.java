/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
    public void testBuildFrom() throws Exception {
        MessageProcessor msgProc = new MessageProcessor();
        String resp = msgProc.processMessage("This is an interesting book.");
        assertEquals("Done.", resp);

        EngineKnowledgeBase ekb = msgProc.getKb();
        Corpus corp = new Corpus();
        corp.buildFrom(ekb);

        System.out.println(corp);
    }
}
