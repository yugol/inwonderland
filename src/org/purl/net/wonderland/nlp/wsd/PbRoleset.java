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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * corresponds to <roleset> tag in PropBank
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class PbRoleset {

    private final String lemma;
    private final String id;
    private final String name;
    private final Map<String, VerbRole> roles; // all thematic roles applicable (including PropBank and VerbNet)
    private final List<PbExample> examples; // PropBank examples
    private final VnClass vnClass;

    public PbRoleset(String lemma, Element rolesetElement, String vncls, int vnclsIdx) throws Exception {
        String vnClassId = makeVnClass(vncls);
        vncls = (vnClassId == null) ? (null) : (rolesetElement.getAttribute("vncls").split(" ")[vnclsIdx]);

        this.lemma = lemma;
        this.id = rolesetElement.getAttribute("id");
        this.name = rolesetElement.getAttribute("name");
        this.roles = makeRoles(rolesetElement, vncls);
        this.examples = makeExamples(lemma, roles, rolesetElement);
        this.vnClass = (vnClassId == null) ? (null) : (new VnClass(lemma, vnClassId, roles));
    }

    private static String makeVnClass(String vncls) {
        if (vncls == null || vncls.length() == 0 || vncls.equals("-")) {
            return null;
        } else {
            return vncls;
        }
    }

    private static Map<String, VerbRole> makeRoles(Element rolesetElement, String vncls) {
        Map<String, VerbRole> roles = new HashMap<String, VerbRole>();

        NodeList roleNodes = rolesetElement.getElementsByTagName("role");
        for (int i = 0; i < roleNodes.getLength(); i++) {
            Element roleElement = (Element) roleNodes.item(i);

            String n = roleElement.getAttribute("n");
            String desc = roleElement.getAttribute("descr");

            String vntheta = null;
            if (vncls != null) {
                NodeList vnroleNodes = roleElement.getElementsByTagName("vnrole");
                for (int j = 0; j < vnroleNodes.getLength(); j++) {
                    Element vnroleElement = (Element) vnroleNodes.item(j);
                    String vnroleVncls = vnroleElement.getAttribute("vncls");
                    if (vncls.equals(vnroleVncls)) {
                        vntheta = VerbRole.normalizeThematicRoleName(vnroleElement.getAttribute("vntheta"));
                        break;
                    }
                }
            }

            VerbRole role = new VerbRole(n, desc, vntheta);
            roles.put(n, role);

            if (vntheta != null) {
                roles.put(vntheta, role);
            }
        }

        return roles;
    }

    private static List<PbExample> makeExamples(String lemma, Map<String, VerbRole> roles, Element rolesetElement) {
        List<PbExample> examples = new ArrayList<PbExample>();

        NodeList exampleNodes = rolesetElement.getElementsByTagName("example");
        for (int i = 0; i < exampleNodes.getLength(); i++) {
            Element exampleElement = (Element) exampleNodes.item(i);

            Element textElement = (Element) exampleElement.getElementsByTagName("text").item(0);
            String text = textElement.getTextContent().trim();

            PbExample example = new PbExample(lemma, PbExample.Type.PropBank, text);

            NodeList argNodes = exampleElement.getElementsByTagName("arg");
            for (int j = 0; j < argNodes.getLength(); j++) {
                Element argElement = (Element) argNodes.item(j);

                String n = argElement.getAttribute("n");
                String value = argElement.getTextContent();

                VerbRole role = roles.get(n);
                if (role != null) {
                    example.getArgs().put(role, value.trim());
                }
            }

            examples.add(example);
        }

        return examples;
    }

    public List<PbExample> getExamples() {
        return examples;
    }

    public String getId() {
        return id;
    }

    public String getLemma() {
        return lemma;
    }

    public String getName() {
        return name;
    }

    public Map<String, VerbRole> getRoles() {
        return roles;
    }

    public VnClass getVnClass() {
        return vnClass;
    }
}
