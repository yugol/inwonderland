/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import ro.uaic.wonderland.util.KB;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;

/**
 *
 * @author Iulian
 */
public class KBTest {

    /**
     * Test of normalizeConceptTypes method, of class KB.
     */
    // @Test
    public void testNormalizeConceptTypes() throws FileNotFoundException, IOException {
        System.out.println("normalizeConceptTypes");
        KB.normalizeConceptTypes(new File(Globals.getDefaultParseKBPath()));
        KB.normalizeRelationTypes(new File(Globals.getDefaultParseKBPath()));
    }
}
