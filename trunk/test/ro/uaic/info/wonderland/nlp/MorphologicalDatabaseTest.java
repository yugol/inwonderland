/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class MorphologicalDatabaseTest {

    @Test
    public void testReadDataFile() throws Exception {
        String formFile = "ar.csv";
        Map result = MorphologicalDatabase.readDataFile(formFile);
        assertEquals(3, result.keySet().size());
    }
}
