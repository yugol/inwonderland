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
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.engine.Engine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.purl.net.wonderland.nlp.WTagging;

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
    public static final String articleTag = "art";

    public static void normalizeCorpusFile(File file) throws Exception {
        CodeTimer timer = new CodeTimer("normalizing corpus file");
        Corpus gold = new Corpus();
        gold.buildFrom(file);
        gold.reIndexSentences();
        gold.removeMappingAttributes();
        gold.writeToFile(file);
        timer.stop();
        System.out.println(" Reindexed " + gold.getSentenceCount() + " sentences in '" + file.getName() + "'");
    }
    private Document xmlDoc;

    public Corpus() {
    }

    public void addSentence(Engine mp, int idx, String level) {
        Node root = xmlDoc.getDocumentElement();
        Element sentence = xmlDoc.createElement(sentenceTag);
        sentence.setAttribute(stcIdxTag, "" + (getSentenceCount() + 1));
        root.appendChild(sentence);

        WTagging[] props = mp.getFactWTaggings(idx, true, level);
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
            if (prop.getPartOfSpeech() != null) {
                word.setAttribute(wTag, prop.getPartOfSpeech());
            }
            if (prop.getGrammaticalGender() != null) {
                word.setAttribute(genTag, prop.getGrammaticalGender());
            }
            if (prop.getGrammaticalNumber() != null) {
                word.setAttribute(numTag, prop.getGrammaticalNumber());
            }
            if (prop.getGrammaticalCase() != null) {
                word.setAttribute(caseTag, prop.getGrammaticalCase());
            }
            if (prop.getPerson() != null) {
                word.setAttribute(persTag, prop.getPerson());
            }
            if (prop.getDegree() != null) {
                word.setAttribute(compTag, prop.getDegree());
            }
            if (prop.getVerbFormMood() != null) {
                word.setAttribute(moodTag, prop.getVerbFormMood());
            }
            if (prop.getGrammaticalTense() != null) {
                word.setAttribute(tenseTag, prop.getGrammaticalTense());
            }
            if (prop.getDefiniteness() != null) {
                word.setAttribute(articleTag, prop.getDefiniteness());
            }
            word.setTextContent(prop.getWrittenForm());
            sentence.appendChild(word);
        }
    }

    public void addKnowledgeBase(Engine mp, String level) {
        for (int i = 1; i <= mp.getFactCount(level); ++i) {
            addSentence(mp, i, level);
        }
    }

    public void buildFrom(Engine ekb, String level) {
        createMarkupDocument();
        addKnowledgeBase(ekb, level);
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
            Configuration.reportExceptionConsole(ex);
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
            prop.setWrittenForm(word.getTextContent());
            prop.setLemma(word.getAttribute(lemmaTag));
            prop.setDegree(word.getAttribute(compTag));
            prop.setGrammaticalGender(word.getAttribute(genTag));
            prop.setVerbFormMood(word.getAttribute(moodTag));
            prop.setGrammaticalNumber(word.getAttribute(numTag));
            prop.setPerson(word.getAttribute(persTag));
            prop.setPartOfSpeech(word.getAttribute(wTag));
            prop.setPennTag(word.getAttribute(pennTag));
            prop.setPartsOfSpeech(word.getAttribute(maTag));
            prop.setGrammaticalTense(word.getAttribute(tenseTag));
            prop.setGrammaticalCase(word.getAttribute(caseTag));
            prop.setDefiniteness(word.getAttribute(articleTag));
            props[i - 1] = prop;
        }
        return props;
    }

    public void reIndexSentences() {
        NodeList sentences = xmlDoc.getElementsByTagName(sentenceTag);
        for (int i = 0; i < sentences.getLength(); ++i) {
            Element sentence = (Element) sentences.item(i);
            sentence.setAttribute(stcIdxTag, "" + (i + 1));
            NodeList words = sentence.getElementsByTagName(wordTag);
            for (int j = 0; j < words.getLength(); ++j) {
                Element word = (Element) words.item(j);
                word.setAttribute(idxTag, "" + (j + 1));
            }
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
