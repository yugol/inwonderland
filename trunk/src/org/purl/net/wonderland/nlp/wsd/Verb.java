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

import edu.stanford.nlp.util.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.purl.net.wonderland.nlp.resources.PropBankWrapper;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.util.IO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Verb {

    static String normalizeThematicRoleName(String name) {
        return StringUtils.capitalize(name.toLowerCase());
    }
    private final List<VerbRoleset> rolesets;

    public Verb(String lemma) throws Exception {
        System.out.println("* " + lemma);
        this.rolesets = new ArrayList<VerbRoleset>();
        readPropBankData(lemma);
    }

    private void readPropBankData(String lemma) throws Exception {
        File pbFile = PropBankWrapper.getVerbFile(lemma);
        Document xmlDoc = IO.readXmlFile(pbFile);

        NodeList rolesetNodes = xmlDoc.getElementsByTagName("roleset");
        for (int i = 0; i < rolesetNodes.getLength(); i++) {
            Element rolesetElement = (Element) rolesetNodes.item(i);

            String[] vnclss = rolesetElement.getAttribute("vncls").split(" ");
            for (String vncls : vnclss) {
                if (VerbNetWrapper.getClassFile(vncls) == null) {
                    String[] vnclsParts = vncls.split("-");
                    if (vnclsParts.length > 0) {
                        List<String> possibleClasses = VerbNetWrapper.getClassesLike(vnclsParts[0]);
                        if (possibleClasses != null) {
                            for (String cls : possibleClasses) {
                                readRoleset(rolesetElement, lemma, cls);
                            }
                        } else {
                            readRoleset(rolesetElement, lemma, null);
                        }
                    } else {
                        readRoleset(rolesetElement, lemma, null);
                    }
                } else {
                    readRoleset(rolesetElement, lemma, vncls);
                }
            }
        }
    }

    private void readRoleset(Element rolesetElement, String lemma, String vncls) throws Exception {
        // read roles
        Map<String, ThematicRole> roles = new HashMap<String, ThematicRole>();
        NodeList roleNodes = rolesetElement.getElementsByTagName("role");
        for (int j = 0; j < roleNodes.getLength(); j++) {
            Element roleElement = (Element) roleNodes.item(j);
            String n = roleElement.getAttribute("n");
            String desc = roleElement.getAttribute("descr");
            String vntheta = null;
            NodeList vnroleNodes = roleElement.getElementsByTagName("vnrole");
            if (vnroleNodes.getLength() > 0) {
                Element vnroleElement = (Element) vnroleNodes.item(0);
                vntheta = normalizeThematicRoleName(vnroleElement.getAttribute("vntheta"));
            }
            ThematicRole role = new ThematicRole(n, desc, vntheta);
            roles.put(n, role);
            if (vntheta != null) {
                roles.put(vntheta, role);
            }
        }

        // create roleset
        VerbRoleset roleset = new VerbRoleset(lemma, rolesetElement.getAttribute("id"), vncls, roles);
        rolesets.add(roleset);

        // read PropBank examples
        NodeList exampleNodes = rolesetElement.getElementsByTagName("example");
        for (int i = 0; i < exampleNodes.getLength(); i++) {
            Element exampleElement = (Element) exampleNodes.item(i);
            Element textElement = (Element) exampleElement.getElementsByTagName("text").item(0);
            RolesetExample example = new RolesetExample(textElement.getTextContent().trim());
            NodeList argNodes = exampleElement.getElementsByTagName("arg");
            for (int j = 0; j < argNodes.getLength(); j++) {
                Element argElement = (Element) argNodes.item(j);
                String n = argElement.getAttribute("n");
                String value = argElement.getTextContent();
                ThematicRole role = roles.get(n);
                if (role != null) {
                    example.getArgs().put(role, value.trim());
                }
            }
            roleset.getExamples().add(example);
        }

    }

    public List<VerbRoleset> getRolesets() {
        return rolesets;
    }
}
