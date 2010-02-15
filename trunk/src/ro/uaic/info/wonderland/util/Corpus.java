/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.util;

import edu2.stanford.nlp.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ro.uaic.info.wonderland.nlp.WTagging;
import ro.uaic.info.wonderland.kb.EngineKnowledgeBase;

/**
 *
 * @author Iulian
 */
public class Corpus {

    public static final String rootTag = "wonderland_corpus";
    public static final String sentenceTag = "sentence";
    public static final String wordTag = "word";
    public static final String stcIdxTag = "idx";
    public static final String idxTag = "_idx";
    public static final String lemmaTag = "lemma";
    public static final String posTag = "_pos";
    public static final String genTag = "gen";
    public static final String numTag = "num";
    public static final String caseTag = "case";
    public static final String persTag = "pers";
    public static final String compTag = "comp";
    public static final String moodTag = "mood";
    public static final String tenseTag = "tense";
    Document xmlDoc;

    public Corpus() {
    }

    public void addSentence(EngineKnowledgeBase ekb, int idx) {
        Node root = xmlDoc.getDocumentElement();
        Element sentence = xmlDoc.createElement(sentenceTag);
        sentence.setAttribute(stcIdxTag, "" + (getSentenceCount() + 1));
        root.appendChild(sentence);

        WTagging[] props = ekb.getSentencePosProps(idx, true);
        for (int j = 0; j < props.length; ++j) {
            WTagging prop = props[j];
            Element word = xmlDoc.createElement(wordTag);
            word.setAttribute(idxTag, "" + (j + 1));
            word.setAttribute(lemmaTag, prop.lemma);
            if (prop.pos != null) {
                word.setAttribute(posTag, prop.pos);
            }
            if (prop.gender != null) {
                word.setAttribute(genTag, prop.gender);
            }
            if (prop.number != null) {
                word.setAttribute(numTag, prop.number);
            }
            if (prop.theCase != null) {
                word.setAttribute(caseTag, prop.theCase);
            }
            if (prop.person != null) {
                word.setAttribute(persTag, prop.person);
            }
            if (prop.comparison != null) {
                word.setAttribute(compTag, prop.comparison);
            }
            if (prop.mood != null) {
                word.setAttribute(moodTag, prop.mood);
            }
            if (prop.tense != null) {
                word.setAttribute(tenseTag, prop.tense);
            }
            word.setTextContent(prop.form);
            sentence.appendChild(word);
        }
    }

    public void addKnowledgeBase(EngineKnowledgeBase ekb) {
        for (int i = 1; i <= ekb.getSentenceFactCount(); ++i) {
            addSentence(ekb, i);
        }
    }

    public void buildFrom(EngineKnowledgeBase ekb) {
        createMarkupDocument();
        addKnowledgeBase(ekb);
    }

    public void buildFrom(File corpusFile) throws ParserConfigurationException, SAXException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            xmlDoc = db.parse(corpusFile);
            xmlDoc.getDocumentElement().normalize();
        } catch (IOException ex) {
            createMarkupDocument();
        }
    }

    public void writeToFile(File corpusFile) throws IOException {
        IO.writeStringToFile(toString(), corpusFile);
    }

    private void createMarkupDocument() {
        xmlDoc = new DocumentImpl();
        Element root = xmlDoc.createElement(rootTag);
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

    private Element getSentenceByIndex(int idx) {
        NodeList sentences = xmlDoc.getElementsByTagName(sentenceTag);
        for (int i = 0; i < sentences.getLength(); ++i) {
            Element sentence = (Element) sentences.item(i);
            int sIdx = Integer.parseInt(sentence.getAttribute(stcIdxTag));
            if (idx == sIdx) {
                return sentence;
            }
        }
        return null;
    }

    private Element getWordByIndex(Element sentence, int idx) {
        NodeList words = sentence.getElementsByTagName(wordTag);
        for (int i = 0; i < words.getLength(); ++i) {
            Element word = (Element) words.item(i);
            int sIdx = Integer.parseInt(word.getAttribute(idxTag));
            if (idx == sIdx) {
                return word;
            }
        }
        return null;
    }

    public String getSentenceStringByIndex(int idx) {
        Element sentence = getSentenceByIndex(idx);
        if (sentence != null) {
            NodeList words = sentence.getElementsByTagName(wordTag);
            String[] chunks = new String[words.getLength()];
            for (int i = 0; i < words.getLength(); ++i) {
                Element word = (Element) words.item(i);
                int sIdx = Integer.parseInt(word.getAttribute(idxTag));
                chunks[sIdx - 1] = word.getTextContent();
            }
            return StringUtils.join(chunks, " ");
        }
        return null;
    }

    int getSentenceCount() {
        return xmlDoc.getElementsByTagName(sentenceTag).getLength();
    }

    public WTagging[] getSentencePosProps(int idx) {
        Element sentence = getSentenceByIndex(idx);
        NodeList words = sentence.getElementsByTagName(wordTag);
        WTagging[] props = new WTagging[words.getLength()];
        for (int i = 1; i <= props.length; ++i) {
            Element word = getWordByIndex(sentence, i);
            WTagging prop = new WTagging();
            prop.form = word.getTextContent();
            prop.lemma = word.getAttribute(lemmaTag);
            prop.comparison = word.getAttribute(compTag);
            if (prop.comparison.length() == 0) {
                prop.comparison = null;
            }
            prop.gender = word.getAttribute(genTag);
            if (prop.gender.length() == 0) {
                prop.gender = null;
            }
            prop.mood = word.getAttribute(moodTag);
            if (prop.mood.length() == 0) {
                prop.mood = null;
            }
            prop.number = word.getAttribute(numTag);
            if (prop.number.length() == 0) {
                prop.number = null;
            }
            prop.person = word.getAttribute(persTag);
            if (prop.person.length() == 0) {
                prop.person = null;
            }
            prop.pos = word.getAttribute(posTag);
            if (prop.pos.length() == 0) {
                prop.pos = null;
            }
            prop.tense = word.getAttribute(tenseTag);
            if (prop.tense.length() == 0) {
                prop.tense = null;
            }
            prop.theCase = word.getAttribute(caseTag);
            if (prop.theCase.length() == 0) {
                prop.theCase = null;
            }
            props[i - 1] = prop;
        }
        return props;
    }

    public void reIndexSentences() {
        NodeList sentences = xmlDoc.getElementsByTagName(sentenceTag);
        for (int i = 0; i < sentences.getLength(); ++i) {
            Element sentence = (Element) sentences.item(i);
            sentence.setAttribute(stcIdxTag, "" + (i + 1));
        }
    }
}
