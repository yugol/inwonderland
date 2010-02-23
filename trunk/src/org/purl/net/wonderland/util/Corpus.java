/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.util;

import edu.stanford.nlp.util.StringUtils;
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
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.kb.EngineKnowledgeBase;

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
    public static final String wTag = "_pos";
    public static final String pennTag = "_p";
    public static final String maTag = "_ma";
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

        WTagging[] props = ekb.getSentenceWTaggings(idx, true);
        for (int j = 0; j < props.length; ++j) {
            WTagging prop = props[j];
            Element word = xmlDoc.createElement(wordTag);
            word.setAttribute(idxTag, "" + (j + 1));
            word.setAttribute(lemmaTag, prop.getLemma());
            if (prop.getPennTag() != null) {
                word.setAttribute(pennTag, prop.getPennTag());
            }
            if (prop.getPartsOfSpeech() != null) {
                word.setAttribute(maTag, prop.getPartsOfSpeech());
            }
            if (prop.getPos() != null) {
                word.setAttribute(wTag, prop.getPos());
            }
            if (prop.getGender() != null) {
                word.setAttribute(genTag, prop.getGender());
            }
            if (prop.getNumber() != null) {
                word.setAttribute(numTag, prop.getNumber());
            }
            if (prop.getWcase() != null) {
                word.setAttribute(caseTag, prop.getWcase());
            }
            if (prop.getPerson() != null) {
                word.setAttribute(persTag, prop.getPerson());
            }
            if (prop.getComp() != null) {
                word.setAttribute(compTag, prop.getComp());
            }
            if (prop.getMood() != null) {
                word.setAttribute(moodTag, prop.getMood());
            }
            if (prop.getTense() != null) {
                word.setAttribute(tenseTag, prop.getTense());
            }
            word.setTextContent(prop.getForm());
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
            of.setIndent(2);
            of.setIndenting(true);
            of.setLineWidth(1000);
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
            prop.setForm(word.getTextContent());
            prop.setLemma(word.getAttribute(lemmaTag));
            prop.setComp(word.getAttribute(compTag));
            prop.setGender(word.getAttribute(genTag));
            prop.setMood(word.getAttribute(moodTag));
            prop.setNumber(word.getAttribute(numTag));
            prop.setPerson(word.getAttribute(persTag));
            prop.setPos(word.getAttribute(wTag));
            prop.setPennTag(word.getAttribute(pennTag));
            prop.setPartsOfSpeech(word.getAttribute(maTag));
            prop.setTense(word.getAttribute(tenseTag));
            prop.setWcase(word.getAttribute(caseTag));
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

    void removeMappingAttributes() {
        NodeList words = xmlDoc.getElementsByTagName(wordTag);
        for (int i = 0; i < words.getLength(); ++i) {
            Element word = (Element) words.item(i);
            word.removeAttribute(pennTag);
            word.removeAttribute(maTag);
        }
    }
}
