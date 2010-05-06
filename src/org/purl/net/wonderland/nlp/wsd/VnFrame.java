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
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class VnFrame {

    private final String primaryDescription;
    private final String secondaryDescription;
    private final List<VnExample> examples;
    private final List<VnSyntaxItem> syntax;

    VnFrame(Element frameElement, Map<String, VerbRole> themroles) {
        Element descriptionElement = (Element) frameElement.getElementsByTagName("DESCRIPTION").item(0);
        this.primaryDescription = descriptionElement.getAttribute("primary");
        this.secondaryDescription = descriptionElement.getAttribute("secondary");
        this.syntax = makeSyntax(frameElement, themroles);
        this.examples = makeExamples(frameElement, syntax);
    }

    private static List<VnSyntaxItem> makeSyntax(Element frameElement, Map<String, VerbRole> themroles) {
        List<VnSyntaxItem> syntax = new ArrayList<VnSyntaxItem>();

        Element syntaxElement = (Element) frameElement.getElementsByTagName("SYNTAX").item(0);

        NodeList syntaxNodes = syntaxElement.getChildNodes();
        for (int k = 0; k < syntaxNodes.getLength(); k++) {
            Node syntaxNode = syntaxNodes.item(k);

            if (syntaxNode.getNodeType() == Node.ELEMENT_NODE) {
                syntaxElement = (Element) syntaxNode;

                String tag = syntaxElement.getTagName();
                String value = syntaxElement.getAttribute("value");
                VnSyntaxItem item = new VnSyntaxItem(tag, value);

                NodeList synrestrsNodes = syntaxElement.getElementsByTagName("SYNRESTRS");
                for (int l = 0; l < synrestrsNodes.getLength(); l++) {
                    Element synrestrElement = (Element) synrestrsNodes.item(l);
                    VnRestr restr = new VnRestr(synrestrElement);
                    item.getSynrestrs().add(restr);
                }

                NodeList selrestrNodes = syntaxElement.getElementsByTagName("SELRESTR");
                for (int l = 0; l < selrestrNodes.getLength(); l++) {
                    Element selrestrElement = (Element) selrestrNodes.item(l);
                    VnRestr restr = new VnRestr(selrestrElement);
                    item.getSelrestrs().add(restr);
                }

                syntax.add(item);
            }
        }

        return syntax;
    }

    private static List<VnExample> makeExamples(Element frameElement, List<VnSyntaxItem> syntax) {
        List<VnExample> examples = new ArrayList<VnExample>();

        NodeList exampleNodes = frameElement.getElementsByTagName("EXAMPLE");
        for (int i = 0; i < exampleNodes.getLength(); i++) {
            Element exampleElement = (Element) exampleNodes.item(i);
            VnExample example = new VnExample(exampleElement.getTextContent(), syntax);
            examples.add(example);
        }

        return examples;
    }

    public List<VnExample> getExamples() {
        return examples;
    }
}
