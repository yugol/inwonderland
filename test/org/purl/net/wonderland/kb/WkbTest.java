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
import java.util.ArrayList;
import java.util.List;
import net.didion.jwnl.data.POS;
import static org.junit.Assert.*;
import org.junit.Test;
import org.purl.net.wonderland.W;

/**
 *
 * @author Iulian
 */
public class WkbTest {

    @Test
    public void testImportWordNetHierarchySense() throws Exception {
        Wkb kb = new Wkb(W.getDefaultWkbFile());
        File cogxml = new File("test_senses_import.cogxml");

        List<String> senseTypes = new ArrayList<String>();
        senseTypes.add(WkbUtil.toConceptTypeId("n10787470"));
        senseTypes.add(WkbUtil.toConceptTypeId("n00007846"));

        kb.importWordNetHypernymHierarchy(senseTypes);
        kb.save(cogxml);
    }

    @Test
    public void testImportWordNetHierarchyLemma() throws Exception {
        Wkb kb = new Wkb(W.getDefaultWkbFile());
        File cogxml = new File("test_word_import.cogxml");

        List<String> sTypes = kb.importWordNetHypernymHierarchy("zzzb", POS.NOUN);
        assertNull(sTypes);

        sTypes = kb.importWordNetHypernymHierarchy("door", POS.NOUN);
        kb.save(cogxml);
        assertEquals(5, sTypes.size());
        System.out.println(StringUtils.join(sTypes, ", "));

        sTypes = kb.importWordNetHypernymHierarchy("can", POS.VERB);
        kb.save(cogxml);
        assertEquals(2, sTypes.size());
        System.out.println(StringUtils.join(sTypes, ", "));

        sTypes = kb.importWordNetHypernymHierarchy("big", POS.ADJECTIVE);
        kb.save(cogxml);
        assertEquals(13, sTypes.size());
        System.out.println(StringUtils.join(sTypes, ", "));

        sTypes = kb.importWordNetHypernymHierarchy("good", POS.ADVERB);
        kb.save(cogxml);
        assertEquals(2, sTypes.size());
        System.out.println(StringUtils.join(sTypes, ", "));
    }

    @Test
    public void testImportWordNetBlinkError() throws Exception {
        Wkb kb = new Wkb(W.getDefaultWkbFile());
        File cogxml = new File("test_blink_import.cogxml");
        List<String> sTypes = kb.importWordNetHypernymHierarchy("blink", POS.VERB);
        kb.save(cogxml);
        assertEquals(3, sTypes.size());
        System.out.println(StringUtils.join(sTypes, ", "));
    }
}
