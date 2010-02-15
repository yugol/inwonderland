/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.analysis;

import ro.uaic.info.wonderland.nlp.resources.WordNetWrapper;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class NounLookupTest {

    @Test
    public void testLookupNoun() throws JWNLException {
        IndexWord word = WordNetWrapper.lookup("bubla", POS.NOUN);
        assertNull(word);

        word = WordNetWrapper.lookup("Ice", POS.NOUN);
        assertEquals("ice", word.getLemma());

        word = WordNetWrapper.lookup("ICE", POS.NOUN);
        assertEquals("ice", word.getLemma());
    }
}
