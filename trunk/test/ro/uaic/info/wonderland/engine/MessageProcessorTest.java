/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.engine;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.File;
import java.util.List;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.util.Corpus;
import ro.uaic.info.wonderland.util.IO;
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

   // @Test
    public void testOne() throws Exception {
        MessageProcessor instance = new MessageProcessor();

        String resp = instance.processMessage("I'm seeing things.");
        assertEquals("Done.", resp);

        File file = new File("test.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }

    @Test
    public void testMany() throws Exception {
        int from = 791;
        int to = 0;

        List<String> lines = IO.getFileContentAsStringList(new File(Globals.getCorporaFolder(), "egcp.train.plain.txt"));
        MessageProcessor instance = new MessageProcessor();

        from -= 1;
        to -= 1;
        if (to < 0) {
            to = lines.size() - 1;
        }
        if (to < from) {
            to = from;
        }

        int kbIndex = 0;
        for (int i = from; i <= to; ++i) {
            ++kbIndex;
            System.out.println("At line: [" + (i + 1) + "] -> (" + kbIndex + ")");
            String line = lines.get(i);
            System.out.println("  " + line);
            String resp = instance.processMessage(line);
            assertEquals("Done.", resp);
        }

        File file = new File("test.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }
}
