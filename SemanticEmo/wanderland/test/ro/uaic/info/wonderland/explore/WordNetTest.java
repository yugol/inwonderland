/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.explore;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class WordNetTest {

    public WordNetTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testAirplane() {
        String wordForm = "airplane";
        //  Get the synsets containing the wrod form
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] synsets = database.getSynsets(wordForm);
        //  Display the word forms and definitions for synsets retrieved
        if (synsets.length > 0) {
            System.out.println("The following synsets contain '"
                    + wordForm + "' or a possible base form "
                    + "of that text:");
            for (int i = 0; i < synsets.length; i++) {
                System.out.println("");
                String[] wordForms = synsets[i].getWordForms();
                for (int j = 0; j < wordForms.length; j++) {
                    System.out.print((j > 0 ? ", " : "") + wordForms[j]);
                }
                System.out.println(": " + synsets[i].getDefinition());
            }
        } else {
            System.err.println("No synsets exist that contain the word form '" + wordForm + "'");
            assertTrue(false);
        }
    }
}
