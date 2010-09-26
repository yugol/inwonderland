/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.emo;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ro.uaic.info.wonderland.data.Article;

/**
 *
 * @author Iulian
 */
public class EmotionUtilTest {

    public EmotionUtilTest() {
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

    @Test
    public void testGenericGEW() throws Exception {
        byte[] result = EmoUtil.generateGenericEmoMap(new EmoOntology());
        EmoUtil.byteArrayToImageFile(result, "testGenericGEW.jpg");
    }

    @Test
    public void testEmoMap() throws Exception {
        EmoSearchResult esr = new EmoSearchResult(new EmoOntology());
        for (Emotion emo : new EmoOntology().getEmotions()) {
            esr.add(emo, new Article("test"));
        }
        byte[] result = EmoUtil.generateEmoSearchResultMap(esr);
        EmoUtil.byteArrayToImageFile(result, "testEmoMap.jpg");
    }

    // @Test
    public void generateEmoImgs() throws FileNotFoundException, IOException {
        EmoUtil.generateEmoImages();
    }
}
