/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import java.io.File;
import ro.uaic.info.wonderland.engine.MessageProcessor;

/**
 *
 * @author Iulian
 */
public class TestUtil {

    public static void saveKbAndMarkings(MessageProcessor mp) throws Exception {
        File file = new File("test.cogxml");
        mp.saveKb(file);

        File candidateFile = new File("test.xml");
        candidateFile.delete();
        Corpus candidate = new Corpus();
        candidate.buildFrom(candidateFile);
        candidate.addKnowledgeBase(mp.getKb());
        candidate.writeToFile(candidateFile);
    }
}
