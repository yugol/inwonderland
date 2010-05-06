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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;
import org.purl.net.wonderland.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class VnClass {

    private final String lemma;
    private final String id;
    private final List<String> members; // all applicable verbs
    private final List<String> wnSenses; // WordNet senses
    private final Map<String, VerbRole> themroles;
    private final List<VnFrame> frames;

    VnClass(String lemma, String idHint, Map<String, VerbRole> roles) throws Exception {
        this.lemma = lemma;
        this.members = new ArrayList<String>();
        this.wnSenses = new ArrayList<String>();
        this.frames = new ArrayList<VnFrame>();
        this.themroles = (roles == null) ? (new HashMap<String, VerbRole>()) : (roles);
        this.id = readVerbNetData(idHint);
    }

    private String readVerbNetData(String idHint) throws Exception {
        StringBuilder idBuff = new StringBuilder();

        File vnFile = VerbNetWrapper.getClassFile(idHint);
        Document xmlDoc = XML.readXmlFile(vnFile);

        String vnClassLemma = vnFile.getName();
        vnClassLemma = vnClassLemma.substring(0, vnClassLemma.indexOf('-'));

        List<Element> vnclassElements = makeWnSenses(xmlDoc);
        for (Element vnclassElement : vnclassElements) {

            // update id
            String vnclassId = vnclassElement.getAttribute("ID");
            if (idBuff.length() > 0) {
                idBuff.append(WsdUtil.SEP);
            }
            idBuff.append(vnclassId);

            // fill members
            makeMembers(vnclassElement);

            // fill thematic roles
            makeThemroles(vnclassElement);

            // make frames
            NodeList frameNodes = xmlDoc.getElementsByTagName("FRAME");
            for (int i = 0; i < frameNodes.getLength(); i++) {
                Element frameElement = (Element) frameNodes.item(i);
                VnFrame frame = new VnFrame(frameElement, themroles);
                frames.add(frame);
            }
        }

        return ((idBuff.length() > 0) ? (idBuff.toString()) : (null));
    }

    private List<Element> makeWnSenses(Document xmlDoc) {
        List<Element> vnclassElements = new ArrayList<Element>();

        NodeList memberNodes = xmlDoc.getElementsByTagName("MEMBER");
        for (int i = 0; i < memberNodes.getLength(); i++) {
            Element memberElement = (Element) memberNodes.item(i);

            String name = memberElement.getAttribute("name");
            name = VerbNetWrapper.normalizeName(name);

            if (name.equals(lemma)) {
                String[] senses = memberElement.getAttribute("wn").split(" ");
                for (String sense : senses) {
                    if (sense.length() > 0) {
                        sense = VerbNetWrapper.normalizeName(sense);
                        wnSenses.add(WordNetWrapper.senseKeyToOffsetKeyAlpha(sense));
                    }
                }
                vnclassElements.add((Element) memberElement.getParentNode().getParentNode());
            }
        }

        return vnclassElements;
    }

    private void makeMembers(Element vnclassElement) {
        Element membersElement = (Element) vnclassElement.getElementsByTagName("MEMBERS").item(0);
        NodeList memberNodes = membersElement.getElementsByTagName("MEMBER");
        for (int i = 0; i < memberNodes.getLength(); i++) {
            Element memberElement = (Element) memberNodes.item(i);
            String name = memberElement.getAttribute("name");
            name = VerbNetWrapper.normalizeName(name);
            members.add(name);
        }
    }

    private void makeThemroles(Element vnclassElement) {
        NodeList themroleNodes = vnclassElement.getElementsByTagName("THEMROLE");
        for (int i = 0; i < themroleNodes.getLength(); i++) {
            Element themroleElement = (Element) themroleNodes.item(i);

            String themroleType = VerbRole.normalizeThematicRoleName(themroleElement.getAttribute("type"));
            VerbRole themrole = themroles.get(themroleType);
            if (themrole == null) {
                themrole = new VerbRole(null, null, themroleType);
                themroles.put(themroleType, themrole);
            }

            NodeList selrestrsNodes = themroleElement.getElementsByTagName("SELRESTRS");
            for (int j = 0; j < selrestrsNodes.getLength(); j++) {
                Element selrestrsElement = (Element) selrestrsNodes.item(j);

                NodeList selrestrNodes = selrestrsElement.getElementsByTagName("SELRESTR");
                for (int k = 0; k < selrestrNodes.getLength(); k++) {
                    Element selrestrElement = (Element) selrestrNodes.item(k);
                    VnRestr selrestr = new VnRestr(selrestrElement);
                    themrole.getVnSelrestrs().add(selrestr);
                }
            }
        }
    }

    public List<VnFrame> getFrames() {
        return frames;
    }

    public String getId() {
        return id;
    }

    public String getLemma() {
        return lemma;
    }

    public List<String> getMembers() {
        return members;
    }

    public Map<String, VerbRole> getThemroles() {
        return themroles;
    }

    public List<String> getWnSenses() {
        return wnSenses;
    }
}
