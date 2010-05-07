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
import java.util.Set;
import org.purl.net.wonderland.WonderlandRuntimeException;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;
import org.purl.net.wonderland.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class VnClass {

    static List<VnClass> makeClassesFor(String lemma, String idHint) throws Exception {
        List<VnClass> vnClasses = new ArrayList<VnClass>();

        List<Element> vnclassElements = new ArrayList<Element>();

        File vnFile = VerbNetWrapper.getClassFile(idHint);
        Document xmlDoc = XML.readXmlFile(vnFile);

        NodeList memberNodes = xmlDoc.getElementsByTagName("MEMBER");
        for (int i = 0; i < memberNodes.getLength(); i++) {
            Element memberElement = (Element) memberNodes.item(i);

            String name = memberElement.getAttribute("name");
            name = VerbNetWrapper.normalizeName(name);
            if (name.equals(lemma)) {
                vnclassElements.add((Element) memberElement.getParentNode().getParentNode());
            }
        }

        for (Element element : vnclassElements) {
            VnClass vnClass = new VnClass(lemma, element);
            vnClasses.add(vnClass);
        }

        return vnClasses;
    }

    static List<VnClass> makeClassesFor(String lemma) {
        try {
            List<VnClass> vnClasses = new ArrayList<VnClass>();

            Set<String> vnClassIds = VerbNetWrapper.getVerbIndex().get(lemma);
            List<Element> vnclassElements = new ArrayList<Element>(vnClassIds.size());

            for (String classId : vnClassIds) {
                File vnFile = VerbNetWrapper.getClassFile(classId);
                Document xmlDoc = XML.readXmlFile(vnFile);

                NodeList vnclassNodes = xmlDoc.getElementsByTagName("VNCLASS");
                for (int i = 0; i < vnclassNodes.getLength(); i++) {
                    Element vnclassElement = (Element) vnclassNodes.item(i);
                    String id = vnclassElement.getAttribute("ID");
                    id = VerbNetWrapper.normalizeClassId(id);
                    if (vnClassIds.contains(id)) {
                        vnclassElements.add(vnclassElement);
                    }
                }
                vnclassNodes = xmlDoc.getElementsByTagName("VNSUBCLASS");
                for (int i = 0; i < vnclassNodes.getLength(); i++) {
                    Element vnclassElement = (Element) vnclassNodes.item(i);
                    String id = vnclassElement.getAttribute("ID");
                    id = VerbNetWrapper.normalizeClassId(id);
                    if (vnClassIds.contains(id)) {
                        vnclassElements.add(vnclassElement);
                    }
                }
            }

            for (Element element : vnclassElements) {
                VnClass vnClass = new VnClass(lemma, element);
                vnClasses.add(vnClass);
            }

            return vnClasses;
        } catch (Exception ex) {
            throw new WonderlandRuntimeException(ex);
        }
    }
    private final String lemma;
    private final String id;
    private final List<String> members; // all applicable verbs
    private final List<String> wnSenses; // WordNet senses
    private final Map<String, VerbRole> themroles;
    private final List<VnFrame> frames;

    private VnClass(String lemma, Element vnclassElement) throws Exception {
        this.lemma = lemma;
        this.id = vnclassElement.getAttribute("ID");
        this.members = new ArrayList<String>();
        this.wnSenses = new ArrayList<String>();
        this.frames = new ArrayList<VnFrame>();
        this.themroles = new HashMap<String, VerbRole>();
        readVerbNetData(vnclassElement);
    }

    private void readVerbNetData(Element vnclassElement) throws Exception {

        makeMembersAndSenses(vnclassElement);

        makeThemrolesHierarchy(vnclassElement);

        Element framesElement = (Element) vnclassElement.getElementsByTagName("FRAMES").item(0);
        NodeList frameNodes = framesElement.getElementsByTagName("FRAME");
        for (int i = 0; i < frameNodes.getLength(); i++) {
            Element frameElement = (Element) frameNodes.item(i);
            VnFrame frame = new VnFrame(frameElement, themroles, this);
            frames.add(frame);
        }
    }

    private void makeMembersAndSenses(Element vnclassElement) {
        Element membersElement = (Element) vnclassElement.getElementsByTagName("MEMBERS").item(0);
        NodeList memberNodes = membersElement.getElementsByTagName("MEMBER");
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
            }

            members.add(name);
        }
    }

    private void makeThemrolesHierarchy(Element vnclassElement) {
        List<Element> vnclasses = new ArrayList<Element>();

        Node node = vnclassElement;
        do {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String tag = element.getTagName();
                if ("VNCLASS".equals(tag)) {
                    vnclasses.add(0, element);
                    break;
                } else if ("VNSUBCLASS".equals(tag)) {
                    vnclasses.add(0, element);

                }
            }
            node = node.getParentNode();
        } while (true);

        for (Element element : vnclasses) {
            makeThemroles(element);
        }
    }

    private void makeThemroles(Element vnclassElement) {
        Element themrolesElement = (Element) vnclassElement.getElementsByTagName("THEMROLES").item(0);
        NodeList themroleNodes = themrolesElement.getElementsByTagName("THEMROLE");
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
                if (selrestrNodes.getLength() > 0) {
                    themrole.getVnSelrestrs().clear();
                }
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
