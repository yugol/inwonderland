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
package org.purl.net.wonderland.kb.inference;

import fr.lirmm.rcr.cogui2.kernel.io.CogxmlReader;
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlWriter;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.purl.net.wonderland.kb.CGIterable;
import org.purl.net.wonderland.kb.inference.InfManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class InfKB {

    private static final String infLhsSerName = "INF_LHS";
    private static final String level3 = "level3";
    private static final String allName = "all";
    private static final String allId = level3 + "_" + allName;
    private final String language;
    private final KnowledgeBase kb;
    private final InfManager infMgr;

    public InfManager getInfMgr() {
        return infMgr;
    }

    public KnowledgeBase getKb() {
        return kb;
    }

    public String getLanguage() {
        return language;
    }

    public InfKB(File cogxml, InfFactory infFactory) throws Exception {
        this(cogxml, "en", infFactory);
    }

    public InfKB(File cogxml, String lang, InfFactory infFactory) throws Exception {
        language = lang;
        Document doc = CogxmlReader.read(cogxml);
        NodeList supportList = doc.getElementsByTagName("support");
        if (supportList.getLength() == 0) {
            throw new Exception("vocabulary is not define in document");
        }
        Element support_elem = (Element) supportList.item(0);
        Vocabulary voc = CogxmlReader.buildVocabulary(support_elem, true, language);
        Element rootElement = CogxmlReader.getRootElement(doc);
        kb = CogxmlReader.buildKB(rootElement, voc, language, true);

        infMgr = new InfManager(kb, infFactory);
        for (CGraph cg : kb.getFactGraphSet().values()) {
            if (cg.getSet().equals(infLhsSerName)) {
                infMgr.add(cg);
            }
        }
    }

    public void save(File cogxml) throws Exception {
        CogxmlWriter.write(cogxml, kb, language);
    }

    public CGraph getResultGraph() {
        CGraph cg = kb.getFactGraph(allId);
        if (cg == null) {
            cg = new CGraph(allId, allName, level3, "fact");
            kb.addGraph(cg);
        }
        return cg;
    }

    public Iterable<CGraph> getFactSetOrderedByNameIterator(String set) {
        return new CGIterable(kb, set);
    }

    public List<Inference> findMatches(String infSet, CGraph cg) throws Exception {
        return infMgr.findMatches(infSet, cg);
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
