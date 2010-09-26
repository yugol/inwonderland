/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.engine;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class MessageProcessorTest {

    public MessageProcessorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // @Test
    public void testKbWorks() throws Exception {
        File file = null;
        MessageProcessor instance = new MessageProcessor();

        KnowledgeBase kb = instance.getKb();
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


        file = new File("test.cogxml");
        file.createNewFile();
        instance.saveKb(file);
    }

    @Test
    public void testMessage() throws Exception {
        // String msg = "This is an easy sentence.";
        Globals.testDebug = true;
        String msg = "Conceptual graphs (CGs) are a system of logic based on the existential graphs of Charles Sanders Peirce and the semantic networks of artificial intelligence. They express meaning in a form that is logically precise, humanly readable, and computationally tractable. With their direct mapping to language, conceptual graphs serve as an intermediate language for translating computer-oriented formalisms to and from natural languages. With their graphic representation, they serve as a readable, but formal design and specification language. CGs have been implemented in a variety of projects for information retrieval, database design, expert systems, and natural language processing.";
        MessageProcessor instance = new MessageProcessor();

        String resp = instance.processMessage(msg);
        assertEquals("Done.", resp);

        File file = new File("test.cogxml");
        file.createNewFile();
        instance.saveKb(file);
    }
}
