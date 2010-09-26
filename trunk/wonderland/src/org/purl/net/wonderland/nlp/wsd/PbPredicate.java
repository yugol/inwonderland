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
package org.purl.net.wonderland.nlp.wsd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.purl.net.wonderland.nlp.resources.PropBankWrapper;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class PbPredicate {

    static List<PbPredicate> makePredicatesFor(String lemma) throws Exception {
        List<PbPredicate> pbPredicates = new ArrayList<PbPredicate>();

        File pbFile = PropBankWrapper.getVerbFile(lemma);
        Document xmlDoc = XML.readXmlFile(pbFile);

        NodeList predicateNodes = xmlDoc.getElementsByTagName("predicate");
        for (int i = 0; i < predicateNodes.getLength(); i++) {
            Element predicateElement = (Element) predicateNodes.item(i);
            PbPredicate pbPredicate = new PbPredicate(predicateElement);
            pbPredicates.add(pbPredicate);
        }

        return pbPredicates;
    }
    private final String lemma;
    private final List<PbRoleset> rolesets;

    private PbPredicate(Element predicateElement) throws Exception {
        this.lemma = predicateElement.getAttribute("lemma");
        this.rolesets = new ArrayList<PbRoleset>();
        readPropBankRolesets(predicateElement);
    }

    private void readPropBankRolesets(Element predicateElement) throws Exception {
        NodeList rolesetNodes = predicateElement.getElementsByTagName("roleset");
        for (int i = 0; i < rolesetNodes.getLength(); i++) {
            Element rolesetElement = (Element) rolesetNodes.item(i);

            String[] vnclss = rolesetElement.getAttribute("vncls").split(" ");
            for (int j = 0; j < vnclss.length; j++) {
                String vncls = vnclss[j];

                if (VerbNetWrapper.getClassFile(vncls) == null) {
                    String[] vnclsParts = vncls.split("-");
                    if (vnclsParts.length > 0) {
                        List<String> possibleClasses = VerbNetWrapper.getClassesLike(vnclsParts[0]);
                        if (possibleClasses != null) {
                            for (String cls : possibleClasses) {
                                readPropBankRoleset(rolesetElement, lemma, cls, j);
                            }
                        } else {
                            readPropBankRoleset(rolesetElement, lemma, null, j);
                        }
                    } else {
                        readPropBankRoleset(rolesetElement, lemma, null, j);
                    }
                } else {
                    readPropBankRoleset(rolesetElement, lemma, vncls, j);
                }
            }
        }
    }

    private void readPropBankRoleset(Element rolesetElement, String lemma, String vncls, int vnclsIdx) throws Exception {
        PbRoleset roleset = new PbRoleset(lemma, rolesetElement, vncls, vnclsIdx);
        rolesets.add(roleset);
    }
}
