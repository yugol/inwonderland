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
import java.util.Arrays;
import java.util.List;
import org.purl.net.wonderland.WonderlandRuntimeException;
import org.purl.net.wonderland.cg.Concept;
import org.purl.net.wonderland.cg.ConceptualGraph;
import org.purl.net.wonderland.cg.KnowledgeBase;
import org.purl.net.wonderland.cg.Path;
import org.purl.net.wonderland.nlp.ParseResult;
import org.purl.net.wonderland.nlp.Pipeline;
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class VnExample extends Example {

    private final VnClass vnClass;
    private final Frame frame;
    private ParseResult parseResult;

    VnExample(String text, VnFrame frame) {
        super(text);
        this.frame = new Frame(frame.getSyntax());
        this.vnClass = frame.getVnClass();
    }

    public void makeSense() {
        if (text.indexOf("?") == 0) {
            text = text.substring(1);
        }
        if (text.equals("Susan and Rachel talked.") && frame.size() == 3) {
            text = "Susan and Rachel talked together.";
        }

        List<WTagging> tokens = Pipeline.tokenizeAndSplit(text).get(0);
        parseResult = Pipeline.parse(tokens);

        System.out.println("[" + vnClass.getId() + "]");
        System.out.println(text);
        for (int i = 0; i < parseResult.getSentenceSize(); i++) {
            WTagging word = parseResult.getTaggedWord(i);
            System.out.println("    " + word.getLemma() + " / " + word.getPennTag() + " / " + word.getPartsOfSpeech());
        }

        int frameVerbPos = frame.getVerbIndex();

        int sentenceVerbPos = -1;
        // try find verb by match
        for (int i = 0; i < parseResult.getSentenceSize(); i++) {
            if (areMatch(i, frameVerbPos)) {
                sentenceVerbPos = i;
                break;
            }
        }
        // try find verb by tag
        if (sentenceVerbPos < 0) {
            for (int i = 0; i < parseResult.getSentenceSize(); i++) {
                WTagging word = parseResult.getTaggedWord(i);
                if (word.getPennTag().indexOf("VB") == 0) {
                    sentenceVerbPos = i;
                    break;
                }
            }
        }
        // try find verb by lemma
        if (sentenceVerbPos < 0) {
            for (int i = 0; i < parseResult.getSentenceSize(); i++) {
                WTagging word = parseResult.getTaggedWord(i);
                if (vnClass.getMembers().contains(word.getLemma())) {
                    sentenceVerbPos = i;
                    break;
                }
            }
        }
        // check 'ed forms
        if (sentenceVerbPos < 0) {
            for (int i = 0; i < parseResult.getSentenceSize(); i++) {
                String lemma = parseResult.getTaggedWord(i).getLemma();
                if (lemma.lastIndexOf("ed") == lemma.length() - 2) {
                    lemma = lemma.substring(0, lemma.length() - 2);
                }
                if (vnClass.getMembers().contains(lemma)) {
                    sentenceVerbPos = i;
                    break;
                }
            }
        }

        createMapping(frameVerbPos, sentenceVerbPos);


        int frameCursor = frameVerbPos - 1;
        int sentenceCursor = sentenceVerbPos - 1;
        int lastSentenceMatchPos = sentenceVerbPos;
        if (frameCursor == 0 && sentenceCursor == 0) {
            createMapping(frameCursor, sentenceCursor);
        } else {
            while (frameCursor >= 0 && sentenceCursor >= 0) {
                if (areMatch(sentenceCursor, frameCursor)) {
                    createMapping(frameCursor, sentenceCursor);
                    lastSentenceMatchPos = sentenceCursor;
                    --frameCursor;
                }
                --sentenceCursor;
                if (sentenceCursor < 0) {
                    --frameCursor;
                    sentenceCursor = lastSentenceMatchPos - 1;
                }
            }
        }

        frameCursor = frameVerbPos + 1;
        sentenceCursor = sentenceVerbPos + 1;
        lastSentenceMatchPos = sentenceVerbPos;
        while (frameCursor < frame.size() && sentenceCursor < parseResult.getSentenceSize()) {
            if (areMatch(sentenceCursor, frameCursor)) {
                createMapping(frameCursor, sentenceCursor);
                lastSentenceMatchPos = sentenceCursor;
                ++frameCursor;
            }
            ++sentenceCursor;
            if (sentenceCursor >= parseResult.getSentenceSize()) {
                ++frameCursor;
                sentenceCursor = lastSentenceMatchPos + 1;
            }
        }

        // report
        for (int i = 0; i < frame.size(); i++) {
            VnSyntaxItem item = frame.get(i);
            System.out.print("[" + i + "] " + item.toString());
            WTagging word = item.getWord();
            if (word != null) {
                System.out.print(" -> " + word.getLemma());
            }
            System.out.println("");
        }

        if (!frame.isMappedCompletely()) {
            manualMap();
        }
        if (!frame.isMappedCompletely()) {
            throw new WonderlandRuntimeException("unmatched frame");
        }
    }

    private void createMapping(int frameCursor, int sentenceCursor) {
        VnSyntaxItem item = frame.get(frameCursor);
        WTagging word = parseResult.getTaggedWord(sentenceCursor);
        item.setWord(word);
    }

    private boolean areMatch(int wordIndex, int itemIndex) {
        List<String> members = vnClass.getMembers();
        WTagging word = parseResult.getTaggedWord(wordIndex);
        VnSyntaxItem item = frame.get(itemIndex);

        String itemType = item.getType();
        String pennPos = word.getPennTag();
        String maPos = word.getPartsOfSpeech();
        if (maPos == null) {
            maPos = "";
        }

        if ("NP".equals(itemType)) {
            if (maPos.indexOf("n") == 0) {
                return true;
            }
            if (pennPos.indexOf("NN") == 0) {
                return true;
            }
            if (pennPos.indexOf("PRP") == 0) {
                return true;
            }
            if (pennPos.equals("CD")) {
                return true;
            }
            if (pennPos.equals("RB")) {
                return true;
            }
            if (pennPos.equals("SYM")) {
                return true;
            }
            if (maPos.indexOf("vvg") == 0) {
                return true;
            }
            if (maPos.indexOf("vdi") == 0) {
                return true;
            }
            if (maPos.indexOf("vvi") == 0) {
                return true;
            }
            if (maPos.indexOf("vbi") == 0) {
                return true;
            }
            if (maPos.indexOf("j-jn") == 0) {
                return true;
            }
            if (pennPos.equals("JJ")) {
                return true;
            }
        } else if ("VERB".equals(itemType)) {
            if (maPos.indexOf("vvg") == 0) {
                return false;
            }
            if (maPos.indexOf("v") == 0) {
                if (members.contains(word.getLemma())) {
                    return true;
                }
            }
        } else if ("PREP".equals(itemType)) {

            if (item.getValue() != null) {
                List<String> choices = new ArrayList<String>(Arrays.asList(item.getValue().split(" ")));
                if (choices.contains("in")) {
                    choices.add("into");
                }
                if (choices.contains("about")) {
                    choices.add("on");
                }
                if (checkContains(choices, wordIndex)) {
                    return true;
                } else {
                    return false;
                }
            }

            if (pennPos.equals("IN")) {
                return true;
            } else if (pennPos.equals("TO")) {
                return true;
            } else if (maPos.indexOf("-acp") > 0) {
                return true;
            }

        } else if ("ADV".equals(itemType)) {
            if (pennPos.equals("RB")) {
                return true;
            }
        } else if ("ADJ".equals(itemType)) {
            if (maPos.indexOf("j") == 0) {
                return true;
            }
            if (pennPos.equals("JJ")) {
                return true;
            }
            if (pennPos.equals("RP")) {
                return true;
            }
            if (maPos.indexOf("vvn") == 0) {
                return true;
            }
        } else if ("LEX".equals(itemType)) {
            if (pennPos.equals("POS")) {
                return true;
            }
            if (item.getValue() != null) {
                List<String> choices = new ArrayList<String>(Arrays.asList(item.getValue().split(" ")));
                if (choices.contains("as")) {
                    choices.add("to_be");
                }
                if (checkContains(choices, wordIndex)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean checkContains(List<String> choices, int wordIndex) {
        String lemma = parseResult.getTaggedWord(wordIndex).getLemma();
        for (String choice : choices) {
            if (choice.indexOf("_") < 0) {
                if (choice.equals(lemma)) {
                    return true;
                }
            } else {
                String[] components = choice.split("_");
                if (!components[0].equals(lemma)) {
                    return false;
                }
                ++wordIndex;
                for (int i = 1; (i < components.length) && (wordIndex < parseResult.getSentenceSize()); wordIndex++, i++) {
                    String component = components[i];
                    lemma = parseResult.getTaggedWord(wordIndex).getLemma();
                    if (!component.equals(lemma)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean manualMap() {

        if (text.equals("The clothes dried wrinkled.")) {
            frame.remove(2);
            createMapping(0, 1);
            createMapping(1, 2);
            createMapping(2, 3);
            return true;
        }
        if (text.equals("It's raining.")) {
            createMapping(0, 0);
            createMapping(1, 1);
            createMapping(2, 2);
            return true;
        }
        if (text.equals("It's raining cats and dogs.")) {
            createMapping(0, 0);
            createMapping(1, 1);
            createMapping(2, 2);
            createMapping(3, 3);
            return true;
        }
        if (text.equals("Cynthia quaffed down the mixture.")) {
            createMapping(0, 0);
            createMapping(1, 1);
            createMapping(2, 4);
            createMapping(3, 2);
            return true;
        }
        if (text.equals("Nonperforming assets at these banks declined by %15.")) {
            createMapping(0, 1);
            createMapping(1, 5);
            createMapping(2, 6);
            createMapping(3, 8);
            return true;
        }
        return false;
    }

    @Override
    public KnowledgeBase getProcRule(String lemma) {
        System.out.println("");
        System.out.println("<" + lemma + ">");

        // map syntax elements to text
        makeSense();

        // clean frame
        frame.removeUnmapped();
        int frameVerbIndex = frame.getVerbIndex();
        if (frameVerbIndex < 0) {
            throw new WonderlandRuntimeException("frame does not have a verb");
        }

        // get parse graph
        ConceptualGraph parseGraph = parseResult.getConceptualGraph();

        // find paths
        Concept verbConcept = parseResult.getConcept(frame.get(frameVerbIndex).getWord());
        for (int i = 0; i < frame.size(); i++) {
            VnSyntaxItem item = frame.get(i);
            Concept nonVerbConcept = parseResult.getConcept(item.getWord());
            Path path = parseGraph.findPath(verbConcept, nonVerbConcept);
            item.setPath(path);
        }

        // create the rule in knowledge base and return
        frame.makeProcRule(vnClass, lemma, parseResult.getKb());
        return parseResult.getKb();
    }
}
