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
package org.purl.net.wonderland.kb;

import edu.stanford.nlp.util.StringUtils;
import java.io.File;
import net.didion.jwnl.data.POS;
import static org.junit.Assert.*;
import org.junit.Test;
import org.purl.net.wonderland.Configuration;

/**
 *
 * @author Iulian
 */
public class WKnowledgeBaseTest {

    @Test
    public void testImportWordNetHierarchy() throws Exception {
        WKnowledgeBase kb = new WKnowledgeBase(Configuration.getDefaultParseKBFile());
        File cogxml = new File("test_word_import.cogxml");

        String[] sTypes = kb.importWordNetHypernymHierarchy("zzzb", POS.NOUN);
        assertNull(sTypes);

        sTypes = kb.importWordNetHypernymHierarchy("door", POS.NOUN);
        kb.save(cogxml);
        assertEquals(5, sTypes.length);
        System.out.println(StringUtils.join(sTypes, ", "));

        sTypes = kb.importWordNetHypernymHierarchy("can", POS.VERB);
        kb.save(cogxml);
        assertEquals(2, sTypes.length);
        System.out.println(StringUtils.join(sTypes, ", "));

        sTypes = kb.importWordNetHypernymHierarchy("big", POS.ADJECTIVE);
        kb.save(cogxml);
        assertEquals(13, sTypes.length);
        System.out.println(StringUtils.join(sTypes, ", "));

        sTypes = kb.importWordNetHypernymHierarchy("good", POS.ADVERB);
        kb.save(cogxml);
        assertEquals(2, sTypes.length);
        System.out.println(StringUtils.join(sTypes, ", "));
    }

    @Test
    public void testImportVerbNetHierarchy() throws Exception {
        WKnowledgeBase kb = new WKnowledgeBase(Configuration.getDefaultParseKBFile());
        File cogxml = new File("test_verb_import.cogxml");

        String[] sTypes = kb.importVerbNetHierarchy("zzzb");
        assertNull(sTypes);

        sTypes = kb.importVerbNetHierarchy("atomize");
        kb.save(cogxml);
        assertEquals(1, sTypes.length);
        System.out.println(StringUtils.join(sTypes, ", "));

        sTypes = kb.importVerbNetHierarchy("blab");
        kb.save(cogxml);
        assertEquals(2, sTypes.length);
        System.out.println(StringUtils.join(sTypes, ", "));

        sTypes = kb.importVerbNetHierarchy("make");
        kb.save(cogxml);
        assertEquals(15, sTypes.length);
        System.out.println(StringUtils.join(sTypes, ", "));
    }
}
