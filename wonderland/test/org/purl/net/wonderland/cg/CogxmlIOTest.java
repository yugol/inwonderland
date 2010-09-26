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
package org.purl.net.wonderland.cg;

import java.io.File;
import org.junit.Test;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.util.XML;
import org.w3c.dom.Document;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class CogxmlIOTest {

    public CogxmlIOTest() {
    }

    @Test
    public void testReadSupport() throws Exception {
        File kbFile = W.getDefaultWkbFile();
        Wkb wkb1 = new Wkb(kbFile);

        KnowledgeBase kb = CogxmlIO.readCogxmlFile(kbFile);

        /*
        List<String> conceptTypeLabels = new ArrayList<String>();
        for (String id : kb.getSupport().getConceptTypes().keySet()) {
        ConceptType ct = kb.getSupport().getConceptTypes().get(id);
        conceptTypeLabels.add(ct.getLabel());
        }
        Collections.sort(conceptTypeLabels);
        String prev = null;
        for (String label : conceptTypeLabels) {
        if (label.equals(prev)) {
        System.out.println(label);
        }
        prev = label;
        }
         */

        Document xmlDoc = CogxmlIO.xmlDoc(kb);
        File testFile = new File("test.cogxml");
        XML.writeXmlFile(xmlDoc, testFile);

        Wkb wkb2 = new Wkb(testFile);

        assertEquals(wkb1.getConceptTypeCount(), wkb2.getConceptTypeCount());
        assertEquals(wkb1.getRelationTypeCount(), wkb2.getRelationTypeCount());
        assertEquals(wkb1.getFactCount(), wkb2.getFactCount());
        assertEquals(wkb1.getRuleCount(), wkb2.getRuleCount());
    }
}
