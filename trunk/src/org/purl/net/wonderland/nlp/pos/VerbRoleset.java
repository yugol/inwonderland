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
package org.purl.net.wonderland.nlp.pos;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;
import org.purl.net.wonderland.util.IO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class VerbRoleset {

    private final String pbid; // PropBank id
    private String vncls = null; // VerbNet class
    private final List<String> senses; // WordNet senses
    private final Map<String, ThematicRole> roles; // thematic roles from PropBank and VerbNet

    public VerbRoleset(String lemma, String id, String vncls, Map<String, ThematicRole> roles) throws Exception {
        this.pbid = id;
        if (vncls == null || vncls.length() == 0 || vncls.equals("-")) {
            this.vncls = null;
        } else {
            this.vncls = vncls;
        }
        this.senses = new ArrayList<String>();
        this.roles = roles;
        if (this.vncls != null) {
            readVerbNetData(lemma);
        }
    }

    private void readVerbNetData(String lemma) throws Exception {
        File vnFile = VerbNetWrapper.getClassFile(vncls);
        Document xmlDoc = IO.readXmlFile(vnFile);
        // String classId = vnFile.getName();
        // classId = classId.substring(0, classId.indexOf('-')) + "-" + vncls;
        // Element vnclassElement = xmlDoc.getElementById(classId);
        Element vnclassElement = null;


        // read WordNet senses
        NodeList memberNodes = xmlDoc.getElementsByTagName("MEMBER");
        for (int i = 0; i < memberNodes.getLength(); i++) {
            Element memberElement = (Element) memberNodes.item(i);
            if (memberElement.getAttribute("name").replaceAll("\\?", "").equals(lemma)) {
                String[] wnSenses = memberElement.getAttribute("wn").split(" ");
                for (String sense : wnSenses) {
                    if (sense.length() > 0) {
                        sense = sense.replaceAll("\\?", "");
                        senses.add(WordNetWrapper.senseToId(sense));
                    }
                }
                vnclassElement = (Element) memberElement.getParentNode().getParentNode();
                vncls = vnclassElement.getAttribute("ID");
                break;
            }
        }

        if (vnclassElement == null) {
            vncls = null;
            return;
        }

        // read thematic roles restrictions
        if (vnclassElement.getTagName().equals("VNSUBCLASS")) {
            Element vnSuperClassElement = (Element) vnclassElement.getParentNode().getParentNode();
            System.out.println("[ " + vnSuperClassElement.getAttribute("ID") + " ]");
            NodeList themroleNodes = vnSuperClassElement.getElementsByTagName("THEMROLE");
            readVnThemroles(themroleNodes);
        }
        System.out.println("[ " + vnclassElement.getAttribute("ID") + " ]");
        NodeList themroleNodes = vnclassElement.getElementsByTagName("THEMROLE");
        readVnThemroles(themroleNodes);

    }

    private void readVnThemroles(NodeList themroleNodes) {
        for (int i = 0; i < themroleNodes.getLength(); i++) {
            Element themroleElement = (Element) themroleNodes.item(i);
            String trType = themroleElement.getAttribute("type");
            System.out.println(trType);
            NodeList selrestrsNodes = themroleElement.getElementsByTagName("SELRESTRS");
            for (int j = 0; j < selrestrsNodes.getLength(); j++) {
                Element selrestrsElement = (Element) selrestrsNodes.item(j);
                String logic = selrestrsElement.getAttribute("logic");
                System.out.println(" logic " + logic);
                NodeList selrestrNodes = selrestrsElement.getElementsByTagName("SELRESTR");
                for (int k = 0; k < selrestrNodes.getLength(); k++) {
                    Element selrestrElement = (Element) selrestrNodes.item(k);
                    String value = selrestrElement.getAttribute("Value");
                    String type = selrestrElement.getAttribute("type");
                    System.out.println("  " + value);
                    System.out.println("   " + type);
                }
            }
        }
    }

    public Map<String, ThematicRole> getRoles() {
        return roles;
    }

    public List<String> getSenses() {
        return senses;
    }
}
