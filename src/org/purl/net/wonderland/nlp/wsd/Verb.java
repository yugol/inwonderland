/*
 *  The MIT License
 * 
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 * 
 *  Permission is hereby granted, free of charge, to any PERSON_CT obtaining a copy
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
package org.purl.net.wonderland.nlp.wsd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.purl.net.wonderland.cg.CogxmlIO;
import org.purl.net.wonderland.cg.KnowledgeBase;
import org.purl.net.wonderland.cg.Rule;
import org.purl.net.wonderland.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class Verb {

    private final String lemma;
    private List<VnClass> vnClases = null;

    public Verb(String lemma) throws Exception {
        this.lemma = lemma;
    }

    public List<VnClass> getVnClases() {
        if (vnClases == null) {
            vnClases = VnClass.makeClassesFor(lemma);
        }
        return vnClases;
    }

    public String getLemma() {
        return lemma;
    }

    public List<KnowledgeBase> buildProcRules() {
        List<KnowledgeBase> kbs = new ArrayList<KnowledgeBase>();
        if (getVnClases() != null) {
            for (VnClass vnClass : getVnClases()) {
                for (VnFrame vnFrame : vnClass.getFrames()) {
                    for (VnExample example : vnFrame.getExamples()) {
                        kbs.add(example.getProcRule(lemma));
                    }
                }
            }
        }
        return kbs;
    }

    public void storeProcRules(File file) throws Exception {
        Document xmlDoc = XML.createDocument();
        Element cogxmlElement = xmlDoc.createElement(CogxmlIO.COGXML_NAME);
        xmlDoc.appendChild(cogxmlElement);

        for (KnowledgeBase kb : buildProcRules()) {
            for (String set : kb.getRules().keySet()) {
                for (Rule rule : kb.getRules().get(set)) {
                    Element ruleElement = CogxmlIO.xmlRule(xmlDoc, rule);
                    cogxmlElement.appendChild(ruleElement);
                }
            }
        }

        XML.writeXmlFile(xmlDoc, file);
    }
}
