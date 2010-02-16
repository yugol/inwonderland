/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.kb;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.File;
import static org.junit.Assert.*;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.engine.MessageProcessor;

/**
 *
 * @author Iulian
 */
public class EngineKnowledgeBaseTest {

    @Test
    public void testKbWorks() throws Exception {
        MessageProcessor instance = new MessageProcessor();

        KnowledgeBase kb = instance.getKb().getKb();
        Vocabulary voc = kb.getVocabulary();

        voc.addIndividual("someIndividual", "the one", "_c25", "en");

        CGraph cg = new CGraph("_gs001", "001", "sentences", "fact");
        Concept c = new Concept("someConcept");
        c.addType("_ct25");
        c.setIndividual("someIndividual");

        cg.addVertex(c);
        Relation r = new Relation("someRelation");
        r.addType("_rt25");
        cg.addVertex(r);
        cg.addEdge("someConcept", "someRelation", 1);

        kb.addGraph(cg);
        assertEquals(1, kb.getFactGraphSet().values().size());
    }

    @Test
    public void testMessage() throws Exception {
        String msg = "They like each other";
        Globals.testDebug = true;
        MessageProcessor instance = new MessageProcessor();

        String resp = instance.processMessage(msg);
        assertEquals("Done.", resp);

        File file = new File("test.cogxml");
        instance.saveKb(file);
    }
}
