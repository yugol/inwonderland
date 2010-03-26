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
import java.util.Map;
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
public class VerbFrame {

    // private final String pbid; // PropBank id
    private String vncls = null; // VerbNet class
    private final List<String> senses; // WordNet senses
    private final Map<String, Themrole> roles; // thematic roles from PropBank and VerbNet
    private final List<Example> examples; // roleset examples

    public VerbFrame(String lemma, String id, String vncls, Map<String, Themrole> roles) throws Exception {
        // this.pbid = id;
        if (vncls == null || vncls.length() == 0 || vncls.equals("-")) {
            this.vncls = null;
        } else {
            this.vncls = vncls;
        }
        this.senses = new ArrayList<String>();
        this.roles = roles;
        this.examples = new ArrayList<Example>();
        if (this.vncls != null) {
            readVerbNetData(lemma);
        }
    }

    private void readVerbNetData(String lemma) throws Exception {
        File vnFile = VerbNetWrapper.getClassFile(vncls);
        Document xmlDoc = XML.readXmlFile(vnFile);

        String verbLemma = vnFile.getName();
        verbLemma = verbLemma.substring(0, verbLemma.indexOf('-'));

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

        // read frames
        NodeList frameNodes = vnclassElement.getElementsByTagName("FRAME");
        for (int i = 0; i < frameNodes.getLength(); i++) {
            Element frameElement = (Element) frameNodes.item(i);
            Element syntaxElement = (Element) frameElement.getElementsByTagName("SYNTAX").item(0);
            NodeList exampleNodes = frameElement.getElementsByTagName("EXAMPLE");
            for (int j = 0; j < exampleNodes.getLength(); j++) {
                Element exampleElement = (Element) exampleNodes.item(j);

                Example example = new Example(verbLemma, Example.Type.VerbNet, exampleElement.getTextContent().trim());

                NodeList syntaxNodes = syntaxElement.getChildNodes();
                for (int k = 0; k < syntaxNodes.getLength(); k++) {
                    Node syntaxNode = syntaxNodes.item(k);
                    if (syntaxNode.getNodeType() == Node.ELEMENT_NODE) {

                        syntaxElement = (Element) syntaxNode;
                        String value = syntaxElement.getAttribute("value");

                        WsdManager.syntaxTags.add(syntaxElement.getTagName());

                        StringBuilder synrestrs = new StringBuilder();
                        NodeList synrestrsNodes = syntaxElement.getElementsByTagName("SYNRESTRS");
                        for (int l = 0; l < synrestrsNodes.getLength(); l++) {
                            Element synrestrElement = (Element) synrestrsNodes.item(l);
                            synrestrs.append(synrestrElement.getAttribute("Value"));
                            synrestrs.append(synrestrElement.getAttribute("type"));
                            if (synrestrs.length() > 0) {
                                synrestrs.append(Example.RoleData.sep);
                            }
                        }

                        StringBuilder selrestrs = new StringBuilder();
                        NodeList selrestrNodes = syntaxElement.getElementsByTagName("SELRESTR");
                        for (int l = 0; l < selrestrNodes.getLength(); l++) {
                            Element selrestrElement = (Element) selrestrNodes.item(l);
                            selrestrs.append(selrestrElement.getAttribute("Value"));
                            selrestrs.append(selrestrElement.getAttribute("type"));
                            if (selrestrs.length() > 0) {
                                selrestrs.append(Example.RoleData.sep);
                            }
                        }

                        Example.RoleData entry = new Example.RoleData(syntaxElement.getTagName(), value.toString(), synrestrs.toString(), selrestrs.toString());
                        example.getFrame().add(entry);
                    }
                }

                example.mapArgs(roles);
                examples.add(example);
            }
        }
    }

    private void readVnThemroles(NodeList themroleNodes) {
        for (int i = 0; i < themroleNodes.getLength(); i++) {
            Element themroleElement = (Element) themroleNodes.item(i);
            String trType = Verb.normalizeThematicRoleName(themroleElement.getAttribute("type"));
            Themrole themRole = roles.get(trType);
            if (themRole == null) {
                themRole = new Themrole(null, null, trType);
                roles.put(trType, themRole);
            }
            NodeList selrestrsNodes = themroleElement.getElementsByTagName("SELRESTRS");
            for (int j = 0; j < selrestrsNodes.getLength(); j++) {
                Element selrestrsElement = (Element) selrestrsNodes.item(j);
                NodeList selrestrNodes = selrestrsElement.getElementsByTagName("SELRESTR");
                for (int k = 0; k < selrestrNodes.getLength(); k++) {
                    Element selrestrElement = (Element) selrestrNodes.item(k);
                    String value = selrestrElement.getAttribute("Value");
                    String type = selrestrElement.getAttribute("type");
                    if ("+".equals(value)) {
                        themRole.getPlusRestrs().add(type);
                    } else if ("-".equals(value)) {
                        themRole.getMinusRestrs().add(type);
                    }
                }
            }
        }
    }

    public Map<String, Themrole> getRoles() {
        return roles;
    }

    public List<String> getSenses() {
        return senses;
    }

    public List<Example> getExamples() {
        return examples;
    }
}
