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
package org.purl.net.wonderland.kb.generators;

import aminePlatform.kernel.lexicons.Identifier;
import aminePlatform.kernel.lexicons.Lexicon;
import aminePlatform.kernel.lexicons.LexiconException;
import aminePlatform.kernel.ontology.CS;
import aminePlatform.kernel.ontology.Ontology;
import aminePlatform.kernel.ontology.OntologyException;
import aminePlatform.kernel.ontology.Type;
import aminePlatform.util.AmineList;
import aminePlatform.util.cg.CG;
import aminePlatform.util.parserGenerator.LFParserGenerator;
import aminePlatform.util.parserGenerator.ParsingException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class GenRuleParser {

    public static class LFCGParser extends LFParserGenerator {

        public static class DummyLexicon extends Lexicon {

            public DummyLexicon() throws LexiconException, OntologyException {
                super(new Identifier(""), new Ontology());
            }

            @Override
            public Type getTypeCS(Identifier typeIdentifier) {
                // System.out.println(typeIdentifier.getName());
                Type t = super.getTypeCS(typeIdentifier);
                if (t == null) {
                    t = addConceptTypeEntry(new Identifier(typeIdentifier.getName()));
                }
                return t;
            }
        }

        public LFCGParser() throws ParsingException, LexiconException, OntologyException {
            super(new DummyLexicon());
        }

        void addRelationType(String rt) {
            getLexicon().addRelationTypeEntry(new Identifier(rt));
        }
    }
    private static final String rootTag = "generators";
    private static final String generatorTag = "generator";
    private static final String nameTag = "name";
    private static final String lhsTag = "lhs";
    private static final String rhsTag = "rhs";
    private LFCGParser lfgcParser;
    private List<String> nameList;
    private List<CG> lhsList;
    private List<CG> rhsList;

    public GenRuleParser() throws ParsingException, LexiconException, OntologyException {
        lfgcParser = new LFCGParser();
    }

    void addRelationType(String rt) {
        // System.out.println(rt);
        lfgcParser.addRelationType(rt);
    }

    void parse(File file) throws ParserConfigurationException, SAXException, IOException {
        nameList = new ArrayList<String>();
        lhsList = new ArrayList<CG>();
        rhsList = new ArrayList<CG>();
        AmineList aList = new AmineList();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmlDoc = db.parse(file);
        xmlDoc.getDocumentElement().normalize();
        NodeList generators = xmlDoc.getElementsByTagName(generatorTag);
        for (int i = 0; i < generators.getLength(); ++i) {
            Element generator = (Element) generators.item(i);
            String name = generator.getAttribute(nameTag).trim();
            String lhs = generator.getElementsByTagName(lhsTag).item(0).getTextContent().trim();
            String rhs = generator.getElementsByTagName(rhsTag).item(0).getTextContent().trim();
            nameList.add(name);
            // System.out.println(name + " lhs:\n" + lhs);
            lhsList.add(lfgcParser.parse(lhs, aList));
            // System.out.println(name + " rhs:\n" + rhs);
            rhsList.add(lfgcParser.parse(rhs, aList));
        }
    }

    public List<CG> getLhsList() {
        return lhsList;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public List<CG> getRhsList() {
        return rhsList;
    }

    public String getTypeName(Object type) {
        return lfgcParser.getLexicon().getIdentifier((CS) type).getName();
    }
}
