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

    // @Test
    public void testMessage() throws Exception {
        String msg = "They like each other";
        Globals.testDebug = true;
        // String msg = "Conceptual graphs (CGs) are a system of logic based on the existential graphs of Charles Sanders Peirce and the semantic networks of artificial intelligence. They express meaning in a form that is logically precise, humanly readable, and computationally tractable. With their direct mapping to language, conceptual graphs serve as an intermediate language for translating computer-oriented formalisms to and from natural languages. With their graphic representation, they serve as a readable, but formal design and specification language. CGs have been implemented in a variety of projects for information retrieval, database design, expert systems, and natural language processing.";
        MessageProcessor instance = new MessageProcessor();

        String resp = instance.processMessage(msg);
        assertEquals("Done.", resp);

        File file = new File("test.cogxml");
        instance.saveKb(file);
    }

    // @Test
    public void testNouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        // It  >'s< are probleme la proiectie

        resp = instance.processMessage("The ice melts in the sun."); // NN
        assertEquals("Done.", resp);
        resp = instance.processMessage("It is raining with cats and dogs."); // NNS
        assertEquals("Done.", resp);
        resp = instance.processMessage("Shannon goes to Liverpool."); // NNP
        assertEquals("Done.", resp);
        resp = instance.processMessage("Americans live in Americas."); // NNPS
        assertEquals("Done.", resp);

        File file = new File("nouns.cogxml");
        instance.saveKb(file);
    }

    // @Test
    public void testArticles() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("The boy took a knife and an apple.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Another one bites the dust.");
        assertEquals("Done.", resp);

        File file = new File("articles.cogxml");
        instance.saveKb(file);
    }

    // @Test
    public void testPersonalPronouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("I am, you are, he is, she is, it is, we are, they are, thou are, ye are.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Give it to me, to you, to him, to her, to it, to us, to them, to thee.");
        assertEquals("Done.", resp);

        File file = new File("personal_pronouns.cogxml");
        instance.saveKb(file);
    }

    // @Test
    public void testPossesivePronouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("The car is mine.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The house is yours.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The dog is his.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The jewelry is hers.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The children are ours.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The television is theirs.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("The globe is thine.");
        assertEquals("Done.", resp);

        File file = new File("possesive_pronouns.cogxml");
        instance.saveKb(file);
    }

    @Test
    public void testDemonstrativePronouns() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("This is an interesting book.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("These are the problems you have to do for next time.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("That was a serious problem.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("What are those on the table?");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Andy and Janet are cousins : the former is a student, the latter is a pupil.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I don't need this pencil, give me the other one!");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Some guests drank beer, the others drank wine.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("George learns in the same school as his friend.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("You say that she is a  good pianist but I don't think so.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Such is the present situation.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Let's suppose that you go into such-and-such a shop and ask for such-and-such.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("I don't like books about love, romance and suchlike.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("Mother has brought two blouses today: a white one and a red one.");
        assertEquals("Done.", resp);
        resp = instance.processMessage("These are your tests; they are the ones you wrote yesterday.");
        assertEquals("Done.", resp);

        File file = new File("demonstrative_pronouns.cogxml");
        instance.saveKb(file);
    }
}
