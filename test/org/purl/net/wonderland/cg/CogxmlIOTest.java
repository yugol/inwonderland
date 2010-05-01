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
import org.purl.net.wonderland.kb.WKB;
import org.purl.net.wonderland.util.XML;
import org.w3c.dom.Document;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class CogxmlIOTest {

    public CogxmlIOTest() {
    }

    @Test
    public void testReadSupport() throws Exception {
        Document xmlDoc = XML.readXmlFile(new File("C:\\Users\\Iulian\\Documents\\Bedtime-Story.cogxml"));
        KnowledgeBase kb = CogxmlIO.createKnowledgeBase(xmlDoc.getDocumentElement(), "en");

        File testFile = new File("test.cogxml");

        xmlDoc = XML.createDocument();
        xmlDoc.appendChild(CogxmlIO.xmlKnowledgeBase(xmlDoc, kb));
        XML.writeXmlFile(xmlDoc, testFile);

        WKB wkb = new WKB(testFile);

        System.out.println("");
    }
}
