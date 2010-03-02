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
package org.purl.net.wonderland.kb.generators;

import org.purl.net.wonderland.kb.inference.*;
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlReader;
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlWriter;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.File;
import java.util.List;
import org.purl.net.wonderland.WonderlandException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class GenKB {

    public static final String level1 = "level1";
    public static final String level2 = "level2";
    private static final String allName = "all";
    private static final String allId = level2 + "_" + allName;
    private final String language;
    private final KnowledgeBase kb;
    private int sentenceCount;

    public String getLanguage() {
        return language;
    }

    public GenKB(File cogxml) throws Exception {
        this(cogxml, "en");
    }

    public GenKB(File cogxml, String lang) throws Exception {
        language = lang;
        sentenceCount = 0;

        Document doc = CogxmlReader.read(cogxml);
        NodeList supportList = doc.getElementsByTagName("support");
        if (supportList.getLength() == 0) {
            throw new WonderlandException("vocabulary is not define in document");
        }
        Element support_elem = (Element) supportList.item(0);
        Vocabulary voc = CogxmlReader.buildVocabulary(support_elem, true, language);
        Element rootElement = CogxmlReader.getRootElement(doc);
        kb = CogxmlReader.buildKB(rootElement, voc, language, true);

        for (CGraph cg : kb.getFactGraphSet().values()) {
            if (cg.getSet().equals(level1)) {
                ++sentenceCount;
            }
        }
    }

    public void save(File cogxml) throws Exception {
        CogxmlWriter.write(cogxml, kb, language);
    }

    public CGraph getResultGraph() {
        CGraph cg = kb.getFactGraph(allId);
        if (cg == null) {
            cg = new CGraph(allId, allName, level2, "fact");
            kb.addGraph(cg);
        }
        return cg;
    }

    public Iterable<CGraph> getFactSetOrderedByNameIterator(String set) {
        return new CGIterable(kb, set);
    }

    public Vocabulary getVocabulary() {
        return kb.getVocabulary();
    }

    public void addGraph(CGraph cg) {
        kb.addGraph(cg);
    }

    public CGraph getFactGraph(String toLevel1FactId) {
        return kb.getFactGraph(toLevel1FactId);
    }
}
