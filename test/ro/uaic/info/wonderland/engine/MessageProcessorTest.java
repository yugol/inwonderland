/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.engine;

import ro.uaic.info.wonderland.engine.MessageProcessor;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.File;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class MessageProcessorTest {

    // @Test
    public void testKbWorks() throws Exception {
        File file = null;
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

        file = new File("test.cogxml");
        file.createNewFile();
        instance.saveKb(file);
    }

    @Test
    public void testMessage() throws Exception {
        String msg = "They like each other";
        Globals.testDebug = true;
        // String msg = "Conceptual graphs (CGs) are a system of logic based on the existential graphs of Charles Sanders Peirce and the semantic networks of artificial intelligence. They express meaning in a form that is logically precise, humanly readable, and computationally tractable. With their direct mapping to language, conceptual graphs serve as an intermediate language for translating computer-oriented formalisms to and from natural languages. With their graphic representation, they serve as a readable, but formal design and specification language. CGs have been implemented in a variety of projects for information retrieval, database design, expert systems, and natural language processing.";
        MessageProcessor instance = new MessageProcessor();

        String resp = instance.processMessage(msg);
        assertEquals("Done.", resp);

        File file = new File("test.cogxml");
        file.createNewFile();
        instance.saveKb(file);
    }

    // @Test
    public void testPOS() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        // It  >'s< are probleme la proiectie

        // 001
        resp = instance.processMessage("The ice melts in the sun."); // NN
        assertEquals("Done.", resp);
        resp = instance.processMessage("It is raining with cats and dogs."); // NNS
        assertEquals("Done.", resp);
        resp = instance.processMessage("Shannon goes to Liverpool."); // NNP
        assertEquals("Done.", resp);
        resp = instance.processMessage("Americans live in Americas."); // NNPS
        assertEquals("Done.", resp);

        // 005
        resp = instance.processMessage("The boy took a knife and an apple."); // DT
        assertEquals("Done.", resp);
        resp = instance.processMessage("Another one bites the dust."); // DT
        assertEquals("Done.", resp);

        // 007
        resp = instance.processMessage("I am, you are, he is, she is, it is, we are, they are, thou are, ye are."); // PRP
        assertEquals("Done.", resp);
        resp = instance.processMessage("Give it to me, to you, to him, to her, to it, to us, to them, to thee."); // PRP
        assertEquals("Done.", resp);

        // 009
        resp = instance.processMessage("The car is mine."); // PRP$
        assertEquals("Done.", resp);
        resp = instance.processMessage("The house is yours."); // PRP$
        assertEquals("Done.", resp);
        resp = instance.processMessage("The dog is his."); // PRP$
        assertEquals("Done.", resp);
        resp = instance.processMessage("The jewelry is hers."); // PRP$
        assertEquals("Done.", resp);
        resp = instance.processMessage("The children are ours."); // PRP$
        assertEquals("Done.", resp);
        resp = instance.processMessage("The television is theirs."); // PRP$
        assertEquals("Done.", resp);
        resp = instance.processMessage("The globe is thine."); // PRP$
        assertEquals("Done.", resp);

        File file = new File("test.cogxml");
        file.createNewFile();
        instance.saveKb(file);
    }
}
