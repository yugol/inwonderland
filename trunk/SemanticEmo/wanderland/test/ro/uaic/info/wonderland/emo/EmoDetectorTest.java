/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.emo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.data.Article;
import ro.uaic.info.wonderland.io.Utils;

/**
 *
 * @author Iulian
 */
public class EmoDetectorTest {

    public EmoDetectorTest() {
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
     * Test of findEmotions method, of class EmoDetector.
     */
    // @Test
    public void testFindEmotions() throws FileNotFoundException, IOException {
        String str = Utils.getFileContentAsString(new File(Globals.getDataFolder() + "test/bhch1.txt"));
        System.out.println(str);
        System.out.println("");

        Article art = new Article("test");
        art.setContent(str);

        EmoDetector instance = new EmoDetector(new EmoOntology());
        instance.initDetection();
        long t0 = System.currentTimeMillis();
        instance.findEmotions(art);
        long t1 = System.currentTimeMillis();

        EmoSearchResult esr = instance.getResult();
        esr.setArticleCount(1);
        esr.setDetectionTime(t1 - t0);

        System.out.println("");
        System.out.println(esr.toTextSummary());

        byte[] result = EmoUtil.generateEmoSearchResultMap(esr);
        EmoUtil.byteArrayToImageFile(result, "testFindEmotions.jpg");
    }

    @Test
    public void testMarkup() throws FileNotFoundException, IOException {
        Article art = new Article("test");
        art.setContent("Colorless green ideas sleep furiously.");
        EmoDetector instance = new EmoDetector(new EmoOntology());
        instance.initDetection();
        instance.findEmotions(art);
        System.out.println(art.toHtml());
    }
}
