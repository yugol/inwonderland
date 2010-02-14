/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import java.io.StringWriter;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import ro.uaic.info.wonderland.analysis.PosProp;
import ro.uaic.info.wonderland.engine.EngineKnowledgeBase;

/**
 *
 * @author Iulian
 */
public class Corpus {

    Document xmlDoc;

    public Corpus() {
    }

    public int addSentence(EngineKnowledgeBase ekb, int idx) {
        Node root = xmlDoc.getDocumentElement();
        CGraph cg = ekb.getSentenceFact(idx);

        Element sentence = xmlDoc.createElement("sentence");
        sentence.setAttribute("idx", "" + (root.getChildNodes().getLength() + 1));
        root.appendChild(sentence);

        for (int j = 1; j <= cg.getConcepts().size(); ++j) {
            PosProp prop = ekb.conceptLabels2posProp(getConcept(cg, j));
            Element word = xmlDoc.createElement("word");
            word.setAttribute("idx", "" + j);
            word.setAttribute("lemma", prop.lemma);
            if (prop.posType != null) {
                word.setAttribute("pos", prop.posType);
            }
            if (prop.gender != null) {
                word.setAttribute("gen", prop.gender);
            }
            if (prop.number != null) {
                word.setAttribute("num", prop.number);
            }
            if (prop.theCase != null) {
                word.setAttribute("case", prop.theCase);
            }
            if (prop.person != null) {
                word.setAttribute("pers", prop.person);
            }
            if (prop.comparison != null) {
                word.setAttribute("comp", prop.comparison);
            }
            if (prop.mood != null) {
                word.setAttribute("mood", prop.mood);
            }
            word.setTextContent(prop.form);
            sentence.appendChild(word);
        }
        return idx;
    }

    public void buildFrom(EngineKnowledgeBase ekb) {
        createMarkupDocument();
        for (int i = 1; i <= ekb.getSentenceFactCount(); ++i) {
            addSentence(ekb, i);
        }
    }

    private void createMarkupDocument() {
        xmlDoc = new DocumentImpl();
        Element root = xmlDoc.createElement("wanderland_corpus");
        xmlDoc.appendChild(root);
    }

    @Override
    public String toString() {
        try {
            StringWriter sw = new StringWriter();
            OutputFormat of = new OutputFormat("XML", null, true);
            of.setIndent(4);
            of.setIndenting(true);
            XMLSerializer serializer = new XMLSerializer(sw, of);
            serializer.asDOMSerializer();
            serializer.serialize(xmlDoc.getDocumentElement());
            return sw.toString();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }

    private Concept getConcept(CGraph cg, int j) {
        for (Concept c : cg.getConcepts()) {
            int index = Integer.parseInt(c.getId().split("-")[1]);
            if (index == j) {
                return c;
            }
        }
        return null;
    }
}
