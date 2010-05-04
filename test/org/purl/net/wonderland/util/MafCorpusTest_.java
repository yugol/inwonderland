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
package org.purl.net.wonderland.util;

import java.io.File;
import org.junit.Test;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.nlp.WTaggingUtil;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class MafCorpusTest_ {

    @Test
    public void testGetSentence() throws Exception {
        MafCorpus_ corpus = new MafCorpus_(new File(Configuration.getCorporaFolder(), "bedtime/story.txt"));
        WTagging[] sent = corpus.getSentence(1, WkbConstants.LEVEL1);
        assertEquals(5, sent.length);
        String errStr = WTaggingUtil.areConsistent(sent[0], sent[0]);
        assertNull(errStr);
    }
}