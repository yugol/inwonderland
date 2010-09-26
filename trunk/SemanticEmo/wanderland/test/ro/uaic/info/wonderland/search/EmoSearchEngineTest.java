/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.search;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ro.uaic.info.wonderland.emo.EmoSearchResult;
import ro.uaic.info.wonderland.emo.EmoUtil;
import ro.uaic.info.wonderland.parse.StanfordParser;

/**
 *
 * @author Iulian
 */
public class EmoSearchEngineTest {

    public EmoSearchEngineTest() {
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

    /**
     * Test of search method, of class EmoSearchEngine.
     */
    @Test
    public void testEmoSearchEngine() throws FileNotFoundException, IOException {
        String topic = "new york";
        EmoSearchEngine instance = new EmoSearchEngine();
        EmoSearchResult result = instance.search(topic);
        System.out.println("");
        System.out.println(result.toTextSummary());
        EmoUtil.byteArrayToImageFile(EmoUtil.generateEmoSearchResultMap(result), "testEmoSearchEngine.jpg");
    }
}
