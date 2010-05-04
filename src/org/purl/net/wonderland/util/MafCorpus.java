/*
 *  The MIT License
 * 
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 * 
 *  Permission is hereby granted, free of charge, to any PERSON_CT obtaining a copy
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

import java.io.File;
import java.util.List;
import org.purl.net.wonderland.engine.Engine;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.nlp.Pipeline;
import org.purl.net.wonderland.nlp.WTagging;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class MafCorpus {

    public static final String TAGSET_NAME = "tagset";
    public static final String TAGSET_ATTRIBUTE_REFERENCE = "ref";
    public static final String DESCRIPTION = "description";
    // *************** DCS *************************
    public static final String DCS_NAME = "dcs";
    public static final String DCS_ATTR_LOCALNAME = "local";
    public static final String DCS_ATTR_REGISTERED = "registered";
    public static final String DCS_ATTR_RELATION = "rel";
    // *************** MAF ************************
    public static final String MAF_NAME = "maf";
    public static final String MAF_ATTR_DOCUMENT = "document";
    public static final String MAF_ATTR_ADDRESSING = "addressing";
    // *************** FVLibrary ********************
    public static final String FVLIB_NAME = "fvLib";
    public static final String FVLIB_ATTR_NAME = "n";
    // ************** Symbol **********************
    public static final String STRING_SYMBOL_NAME = "symbol";
    public static final String BOOLEAN_SYMBOL_NAME = "binary";
    public static final String NUMERIC_SYMBOL_NAME = "numeric";
    public static final String SYMBOL_ATTR_VALUE = "value";
    public static final String SYMBOL_ATTR_ID = "id";
    public static final String VALT_NAME = "vAlt";
    // ************ FLibrary ***********************
    public static final String FLIB_NAME = "fLib";
    public static final String FLIB_ATTR_NAME = "n";
    public static final String FEATURE_NAME = "f";
    public static final String FEATURE_ATTR_FVALUE = "fVal";
    public static final String FEATURE_ATTR_ID = "id";
    public static final String FEATURE_ATTR_NAME = "name";
    // ************* FSM ***************************
    public static final String FSM_NAME = "fsm";
    public static final String FSM_ATTR_INIT = "init";
    public static final String FSM_ATTR_FINAL = "final";
    public static final String FSM_ATTR_TINIT = "tinit";
    public static final String FSM_ATTR_TFINAL = "tfinal";
    // *************** Transition ******************
    public static final String TRANSITION_NAME = "transition";
    public static final String TRANSITION_ATTR_SOURCE = "source";
    public static final String TRANSITION_ATTR_TARGET = "target";
    // *************** Word Form ********************
    public static final String WORDFORM_NAME = "wordForm";
    public static final String WFALT_NAME = "wfAlt";
    public static final String WORDFORM_ATTR_ENTRY = "entry";
    public static final String WORDFORM_ATTR_LEMMA = "lemma";
    public static final String WORDFORM_ATTR_FORM = "form";
    public static final String WORDFORM_ATTR_TOKENS = "tokens";
    public static final String WORDFORM_ATTR_TAG = "tag";
    // ************** Token *************************
    public static final String TOKEN_NAME = "token";
    public static final String TOKEN_ATTR_ID = "id";
    public static final String TOKEN_ATTR_FROM = "from";
    public static final String TOKEN_ATTR_TO = "to";
    public static final String TOKEN_ATTR_JOIN = "join";
    public static final String TOKEN_ATTR_TRANSCRIPTION = "transcription";
    public static final String TOKEN_ATTR_TRANSLITERATION = "transliteration";
    public static final String TOKEN_ATTR_PHONETIC = "phonetic";
    public static final String TOKEN_ATTR_FORM = "form";
    //*************** FeatureStructure ******************
    public static final String FS_NAME = "fs";
    public static final String FS_FEATURE_NAME = "f";
    public static final String FS_FEATURE_ATTR_NAME = "name";
    //other
    public static final String WONDERLANDCORPUS = "wonderlandCorpus_";
    public static final String SENTENCE = "sentence_";
    //
    private final File plainCorpus;
    private final File level0Corpus;
    private final File level1Corpus;
    private final File level2Corpus;
    private List<String> plainLines;
    private final Document[] xmlLevel = new Document[3];

    public MafCorpus(File plainCorpus) throws Exception {
        this.plainCorpus = plainCorpus;

        String root = plainCorpus.getAbsolutePath();
        root = root.substring(0, root.lastIndexOf("."));
        level0Corpus = new File(root + ".level0.xml");
        level1Corpus = new File(root + ".level1.xml");
        level2Corpus = new File(root + ".level2.xml");

        plainLines = IO.getFileContentAsStringList(plainCorpus);

        if (level0Corpus.exists()) {
            xmlLevel[0] = XML.readXmlFile(level0Corpus);
        } else {
            buildLevel0Xml();
        }

        if (level1Corpus.exists()) {
            xmlLevel[1] = XML.readXmlFile(level1Corpus);
        }

        if (level2Corpus.exists()) {
            xmlLevel[2] = XML.readXmlFile(level2Corpus);
        }
    }

    int getPlainLineCount() {
        return plainLines.size();
    }

    String getPlainLine(int idx) {
        return plainLines.get(idx);
    }

    private int getLevelDocument(String level) {
        int l = WkbUtil.toLevelIndex(level);
        if (l >= 0) {
            ++l;
        }
        return -1;
    }

    private void buildLevel0Xml() throws Exception {
        String corpus = IO.getFileContentAsString(plainCorpus);

        xmlLevel[0] = XML.createDocument();
        xmlLevel[0].appendChild(xmlLevel[0].createElement(WONDERLANDCORPUS + "level0"));

        int sentIdx = 1;
        for (List<WTagging> sentence : Pipeline.tokenizeAndSplit(corpus)) {
            sentence = (List<WTagging>) Pipeline.parse(sentence)[0];

            Element mafElem = xmlLevel[0].createElement(MAF_NAME);
            mafElem.setAttribute(MAF_ATTR_DOCUMENT, SENTENCE + sentIdx);

            for (int i = 0; i < sentence.size(); ++i) {
                WTagging wt = sentence.get(i);
                Element tokenElem = xmlLevel[0].createElement(TOKEN_NAME);
                tokenElem.setAttribute(TOKEN_ATTR_ID, "" + (i + 1));
                tokenElem.setTextContent(wt.getWrittenForm());
                mafElem.appendChild(tokenElem);
            }

            for (int i = 0; i < sentence.size(); ++i) {
                WTagging wt = sentence.get(i);
                Element wordFormElem = xmlLevel[0].createElement(WORDFORM_NAME);
                wordFormElem.setAttribute(WORDFORM_ATTR_TOKENS, "" + (i + 1));
                wordFormElem.setAttribute(WORDFORM_ATTR_LEMMA, wt.getLemma());
                wordFormElem.setAttribute(WORDFORM_ATTR_TAG, wt.getPartsOfSpeech() + " " + wt.getPennTag());
                mafElem.appendChild(wordFormElem);
            }

            xmlLevel[0].getDocumentElement().appendChild(mafElem);
            ++sentIdx;
        }

        XML.writeXmlFile(xmlLevel[0], level0Corpus);
    }

    void buildLevelXml(Engine engine, String level, int firstSentence, int lastSentence) throws Exception {
        int l = getLevelDocument(level);
        xmlLevel[l] = XML.createDocument();
        xmlLevel[l].appendChild(xmlLevel[l].createElement(WONDERLANDCORPUS + level));

        for (int sentIdx = firstSentence; sentIdx <= lastSentence; ++sentIdx) {
            Element mafElem = xmlLevel[l].createElement(MAF_NAME);
            mafElem.setAttribute(MAF_ATTR_DOCUMENT, SENTENCE + sentIdx);

            WTagging[] words = engine.getFactWTaggings(sentIdx - firstSentence + 1, true, level);
            for (int j = 0; j < words.length; ++j) {
                WTagging wt = words[j];

                Element wordFormElem = xmlLevel[l].createElement(WORDFORM_NAME);
                wordFormElem.setAttribute(WORDFORM_ATTR_LEMMA, wt.getLemma());
                wordFormElem.setAttribute(WORDFORM_ATTR_TOKENS, "" + (j + 1));

                for (String idx : wt.getIndex().split(WkbConstants.ID_SEPARATOR)) {
                    Element tokenElem = xmlLevel[l].createElement(TOKEN_NAME);
                    Element level0tokenElem = getLevel0Token(sentIdx, Integer.parseInt(idx));
                    tokenElem.setAttribute(TOKEN_ATTR_ID, level0tokenElem.getAttribute(TOKEN_ATTR_ID));
                    tokenElem.setTextContent(level0tokenElem.getTextContent());
                    wordFormElem.appendChild(tokenElem);
                }

                Element fsElem = xmlLevel[l].createElement(FS_NAME);
                if (wt.getPartOfSpeech() != null) {
                    Element fElem = xmlLevel[l].createElement(FEATURE_NAME);
                    fElem.setAttribute(FEATURE_ATTR_NAME, WkbConstants.PARTOFSPEECH);
                    Element symbolElem = xmlLevel[l].createElement(STRING_SYMBOL_NAME);
                    symbolElem.setAttribute(SYMBOL_ATTR_VALUE, wt.getPartOfSpeech());
                    fElem.appendChild(symbolElem);
                    fsElem.appendChild(fElem);
                }
                if (wt.getGrammaticalGender() != null) {
                    Element fElem = xmlLevel[l].createElement(FEATURE_NAME);
                    fElem.setAttribute(FEATURE_ATTR_NAME, WkbConstants.GRAMMATICALGENDER);
                    Element symbolElem = xmlLevel[l].createElement(STRING_SYMBOL_NAME);
                    symbolElem.setAttribute(SYMBOL_ATTR_VALUE, wt.getGrammaticalGender());
                    fElem.appendChild(symbolElem);
                    fsElem.appendChild(fElem);
                }
                if (wt.getGrammaticalNumber() != null) {
                    Element fElem = xmlLevel[l].createElement(FEATURE_NAME);
                    fElem.setAttribute(FEATURE_ATTR_NAME, WkbConstants.GRAMMATICALNUMBER);
                    Element symbolElem = xmlLevel[l].createElement(STRING_SYMBOL_NAME);
                    symbolElem.setAttribute(SYMBOL_ATTR_VALUE, wt.getGrammaticalNumber());
                    fElem.appendChild(symbolElem);
                    fsElem.appendChild(fElem);
                }
                if (wt.getGrammaticalCase() != null) {
                    Element fElem = xmlLevel[l].createElement(FEATURE_NAME);
                    fElem.setAttribute(FEATURE_ATTR_NAME, WkbConstants.GRAMMATICALCASE);
                    Element symbolElem = xmlLevel[l].createElement(STRING_SYMBOL_NAME);
                    symbolElem.setAttribute(SYMBOL_ATTR_VALUE, wt.getGrammaticalCase());
                    fElem.appendChild(symbolElem);
                    fsElem.appendChild(fElem);
                }
                if (wt.getPerson() != null) {
                    Element fElem = xmlLevel[l].createElement(FEATURE_NAME);
                    fElem.setAttribute(FEATURE_ATTR_NAME, WkbConstants.PERSON);
                    Element symbolElem = xmlLevel[l].createElement(STRING_SYMBOL_NAME);
                    symbolElem.setAttribute(SYMBOL_ATTR_VALUE, wt.getPerson());
                    fElem.appendChild(symbolElem);
                    fsElem.appendChild(fElem);
                }
                if (wt.getDegree() != null) {
                    Element fElem = xmlLevel[l].createElement(FEATURE_NAME);
                    fElem.setAttribute(FEATURE_ATTR_NAME, WkbConstants.DEGREE);
                    Element symbolElem = xmlLevel[l].createElement(STRING_SYMBOL_NAME);
                    symbolElem.setAttribute(SYMBOL_ATTR_VALUE, wt.getDegree());
                    fElem.appendChild(symbolElem);
                    fsElem.appendChild(fElem);
                }
                if (wt.getVerbFormMood() != null) {
                    Element fElem = xmlLevel[l].createElement(FEATURE_NAME);
                    fElem.setAttribute(FEATURE_ATTR_NAME, WkbConstants.VERBFORMMOOD);
                    Element symbolElem = xmlLevel[l].createElement(STRING_SYMBOL_NAME);
                    symbolElem.setAttribute(SYMBOL_ATTR_VALUE, wt.getVerbFormMood());
                    fElem.appendChild(symbolElem);
                    fsElem.appendChild(fElem);
                }
                if (wt.getGrammaticalTense() != null) {
                    Element fElem = xmlLevel[l].createElement(FEATURE_NAME);
                    fElem.setAttribute(FEATURE_ATTR_NAME, WkbConstants.GRAMMATICALTENSE);
                    Element symbolElem = xmlLevel[l].createElement(STRING_SYMBOL_NAME);
                    symbolElem.setAttribute(SYMBOL_ATTR_VALUE, wt.getGrammaticalTense());
                    fElem.appendChild(symbolElem);
                    fsElem.appendChild(fElem);
                }
                if (wt.getDefiniteness() != null) {
                    Element fElem = xmlLevel[l].createElement(FEATURE_NAME);
                    fElem.setAttribute(FEATURE_ATTR_NAME, WkbConstants.DEFINITNESS);
                    Element symbolElem = xmlLevel[l].createElement(STRING_SYMBOL_NAME);
                    symbolElem.setAttribute(SYMBOL_ATTR_VALUE, wt.getDefiniteness());
                    fElem.appendChild(symbolElem);
                    fsElem.appendChild(fElem);
                }
                if (wt.getAspect() != null) {
                    Element fElem = xmlLevel[l].createElement(FEATURE_NAME);
                    fElem.setAttribute(FEATURE_ATTR_NAME, WkbConstants.ASPECT);
                    Element symbolElem = xmlLevel[l].createElement(STRING_SYMBOL_NAME);
                    symbolElem.setAttribute(SYMBOL_ATTR_VALUE, wt.getAspect());
                    fElem.appendChild(symbolElem);
                    fsElem.appendChild(fElem);
                }
                wordFormElem.appendChild(fsElem);

                mafElem.appendChild(wordFormElem);
            }

            xmlLevel[l].getDocumentElement().appendChild(mafElem);
        }
    }

    void saveLevelXml(File file, String level) throws Exception {
        XML.writeXmlFile(xmlLevel[getLevelDocument(level)], file);
    }

    private Element getLevel0Token(int sentId, int tokId) {
        NodeList mafElems = xmlLevel[0].getElementsByTagName(MAF_NAME);
        Element mafElem = (Element) mafElems.item(sentId - 1);
        NodeList tokenElems = mafElem.getElementsByTagName(TOKEN_NAME);
        return (Element) tokenElems.item(tokId - 1);
    }

    WTagging[] getSentence(int sentId, String level) {
        int l = getLevelDocument(level);
        NodeList mafElems = xmlLevel[l].getElementsByTagName(MAF_NAME);
        Element mafElem = (Element) mafElems.item(sentId - 1);
        NodeList wordFormsElems = mafElem.getElementsByTagName(WORDFORM_NAME);
        WTagging[] wts = new WTagging[wordFormsElems.getLength()];
        for (int i = 0; i < wts.length; i++) {
            Element wordFormElem = (Element) wordFormsElems.item(i);
            wts[i] = createTagging(wordFormElem);
        }
        return wts;
    }

    private WTagging createTagging(Element wordFormElem) {
        WTagging wt = new WTagging();
        wt.setLemma(wordFormElem.getAttribute(WORDFORM_ATTR_LEMMA));
        NodeList fElements = wordFormElem.getElementsByTagName(FEATURE_NAME);
        for (int i = 0; i < fElements.getLength(); i++) {
            Element fElem = (Element) fElements.item(i);
            String name = fElem.getAttribute(FEATURE_ATTR_NAME);
            Element symbolElem = (Element) fElem.getElementsByTagName(STRING_SYMBOL_NAME).item(0);
            String value = symbolElem.getAttribute(SYMBOL_ATTR_VALUE);
            if (WkbConstants.PARTOFSPEECH.equals(name)) {
                wt.setPartOfSpeech(value);
            } else if (WkbConstants.GRAMMATICALGENDER.equals(name)) {
                wt.setGrammaticalGender(value);
            } else if (WkbConstants.GRAMMATICALNUMBER.equals(name)) {
                wt.setGrammaticalNumber(value);
            } else if (WkbConstants.GRAMMATICALCASE.equals(name)) {
                wt.setGrammaticalCase(value);
            } else if (WkbConstants.GRAMMATICALTENSE.equals(name)) {
                wt.setGrammaticalTense(value);
            } else if (WkbConstants.PERSON.equals(name)) {
                wt.setPerson(value);
            } else if (WkbConstants.DEGREE.equals(name)) {
                wt.setDegree(value);
            } else if (WkbConstants.VERBFORMMOOD.equals(name)) {
                wt.setVerbFormMood(value);
            } else if (WkbConstants.DEFINITNESS.equals(name)) {
                wt.setDefiniteness(value);
            } else if (WkbConstants.ASPECT.equals(name)) {
                wt.setAspect(value);
            }
        }
        return wt;
    }
}
