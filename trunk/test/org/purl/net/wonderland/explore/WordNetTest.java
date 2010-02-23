/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.explore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.Dictionary;
import org.junit.Test;
import org.purl.net.wonderland.Globals;

/**
 *
 * @author Iulian
 */
public class WordNetTest {

    @Test
    public void testAirplane() throws JWNLException, FileNotFoundException {
        JWNL.initialize(new FileInputStream(Globals.getJwnlPropertiesFile()));
        Dictionary wn = Dictionary.getInstance();
        IndexWord word = wn.lookupIndexWord(POS.NOUN, "dog");
        System.out.println(word.getLemma());
    }
}
