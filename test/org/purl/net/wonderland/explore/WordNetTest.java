/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.purl.net.wonderland.explore;

import edu.stanford.nlp.util.StringUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.BitSet;
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
    public void testNonVerb() throws JWNLException, FileNotFoundException {
        JWNL.initialize(new FileInputStream(Globals.getJwnlPropertiesFile()));
        Dictionary wn = Dictionary.getInstance();
        IndexWord word = wn.lookupIndexWord(POS.NOUN, "dog");
        System.out.println(word.getLemma());
    }

    @Test
    public void testVerb() throws JWNLException, FileNotFoundException {
        JWNL.initialize(new FileInputStream(Globals.getJwnlPropertiesFile()));
        Dictionary wn = Dictionary.getInstance();
        IndexWord word = wn.lookupIndexWord(POS.VERB, "gray");
        String frames = StringUtils.join(word.getSense(1).getVerbFrames(), ", ");
        System.out.println(frames);
    }

}
