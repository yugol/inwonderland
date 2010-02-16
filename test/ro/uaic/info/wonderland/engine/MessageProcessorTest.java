/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.engine;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.File;
import org.junit.Test;
import ro.uaic.info.wonderland.util.Corpus;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class MessageProcessorTest {

    static File candidateFile = new File("test.xml");

    public MessageProcessorTest() {
        candidateFile.delete();
    }

    private void mergeToCandidate(MessageProcessor instance) throws ParserConfigurationException, SAXException, IOException {
        Corpus candidate = new Corpus();
        candidate.buildFrom(candidateFile);
        candidate.addKnowledgeBase(instance.getKb());
        candidate.writeToFile(candidateFile);
    }

    @Test
    public void testArticles() throws Exception {
        MessageProcessor instance = new MessageProcessor();
        String resp;

        resp = instance.processMessage("We go to school every morning.");
        assertEquals("Done.", resp);

        File file = new File("test.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }

}
