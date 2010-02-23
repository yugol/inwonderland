/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.nlp.resources;

import org.purl.net.wonderland.nlp.resources.MorphAdornerWrapper;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.purl.net.wonderland.nlp.WTagging;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class MorphAdornerWrapperTest {

    public MorphAdornerWrapperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testTagText() {
        String text = "The quick brown fox jumps over the lazy dog.";
        List<List<WTagging>> result = MorphAdornerWrapper.tagText(text);
        for (List<WTagging> sentence : result) {
            System.out.println("----- Sentence -----");
            for (WTagging word : sentence) {
                System.out.println(word.toString());
            }
        }
    }
}
