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
package org.purl.net.wonderland.nlp.resources.verbnet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;
import org.purl.net.wonderland.util.IO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class VNClassFile {

    private final Document xmlDoc;

    public VNClassFile(String verbClass) throws Exception {
        File classFile = new File(Globals.getVerbNetDataFolder(), verbClass);
        xmlDoc = IO.readXmlFile(classFile);
    }

    public List<VerbForm> getMembers() {
        List<VerbForm> members = new ArrayList<VerbForm>();
        NodeList memberNodes = xmlDoc.getElementsByTagName("MEMBER");
        for (int i = 0; i < memberNodes.getLength(); ++i) {
            Element member = (Element) memberNodes.item(i);
            VerbForm vf = new VerbForm(member.getAttribute("name").replaceAll("\\?", ""));
            Element verbClass = (Element) member.getParentNode().getParentNode();
            String vc = verbClass.getAttribute("ID");
            vf.addVnClass(vc);
            String[] wnSenses = member.getAttribute("wn").split(" ");
            for (String wnSense : wnSenses) {
                if (wnSense.length() > 0) {
                    wnSense = wnSense.replaceAll("\\?", "");
                    vf.addWnSense(vc, WordNetWrapper.senseToId(wnSense));
                }
            }
            vf.addVnClass(vc);
            members.add(vf);
        }
        return members;
    }
}