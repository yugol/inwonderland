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
package org.purl.net.wonderland.nlp.resources;

import org.purl.net.wonderland.nlp.ilf.PredFactory;
import java.io.File;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.nlp.ilf.IlfRep;
import org.purl.net.wonderland.nlp.ilf.Pred;
import org.purl.net.wonderland.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class IlfWnWrapper {

    public static File getSenseFile(String offsetKeyNum) {
        String path = "";
        switch (offsetKeyNum.charAt(0)) {
            case '1':
                path = "ilfnoun";
                break;
            case '2':
                path = "ilfverb";
                break;
            case '3':
                path = "ilfadj";
                break;
            case '4':
                path = "ilfadv";
                break;
        }
        path = path + "/" + offsetKeyNum + ".xml";
        return new File(Globals.getIlfWnFolder(), path);
    }

    public static IlfRep getPrettyIlf(String offsetKeyNum) throws Exception {
        IlfRep ilf = new IlfRep();
        Document xmlDoc = XML.readXmlFile(getSenseFile(offsetKeyNum));

        StringBuilder text = new StringBuilder();
        NodeList wordNodes = xmlDoc.getElementsByTagName("word");
        for (int i = 0; i < wordNodes.getLength(); i++) {
            Element wordElement = (Element) wordNodes.item(i);
            text.append(wordElement.getTextContent());
            text.append(" ");
        }
        ilf.setText(text.toString());

        Element prettyIlfElement = (Element) xmlDoc.getElementsByTagName("pretty-ilf").item(0);
        String ilfs = prettyIlfElement.getTextContent();
        String[] formStrings = prettyIlfElement.getTextContent().split(" ");
        for (String rep : formStrings) {
            rep = rep.trim();
            int pos = rep.indexOf("(");
            if (pos < 0) {
                continue;
            }
            String name = rep.substring(0, pos);
            Pred pred = new Pred(name);
            String[] args = rep.substring(pos + 1, rep.lastIndexOf(")")).split(",");
            if (args.length == 1) {
                pred.set("gov", args[0]);
            } else if (args.length == 2) {
                pred.set("gov", args[0]);
                pred.set("dep", args[1]);
            }
            ilf.addPrettyIlf(pred);
        }

        Element ilfElement = (Element) xmlDoc.getElementsByTagName("ilf").item(0);
        ilfs = ilfElement.getTextContent();
        int pos = ilfs.lastIndexOf(")");
        if (pos > 0) {
            ilfs = ilfs.substring(1, pos).replaceAll(" *", "");
            formStrings = ilfs.split("\\),");
            for (String rep : formStrings) {
                rep = rep.trim();
                Pred pred = PredFactory.createPredicate(rep);
                ilf.addIlf(pred);
            }
        }

        ilf.sort();

        return ilf;
    }
}
