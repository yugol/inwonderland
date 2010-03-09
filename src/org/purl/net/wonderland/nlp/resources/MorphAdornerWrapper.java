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

import edu.northwestern.at.utils.CharUtils;
import edu.northwestern.at.utils.corpuslinguistics.adornedword.AdornedWord;
import edu.northwestern.at.utils.corpuslinguistics.lemmatizer.DefaultLemmatizer;
import edu.northwestern.at.utils.corpuslinguistics.lemmatizer.Lemmatizer;
import edu.northwestern.at.utils.corpuslinguistics.lexicon.Lexicon;
import edu.northwestern.at.utils.corpuslinguistics.partsofspeech.PartOfSpeechTags;
import edu.northwestern.at.utils.corpuslinguistics.postagger.DefaultPartOfSpeechTagger;
import edu.northwestern.at.utils.corpuslinguistics.postagger.PartOfSpeechTagger;
import edu.northwestern.at.utils.corpuslinguistics.sentencesplitter.DefaultSentenceSplitter;
import edu.northwestern.at.utils.corpuslinguistics.sentencesplitter.SentenceSplitter;
import edu.northwestern.at.utils.corpuslinguistics.spellingstandardizer.DefaultSpellingStandardizer;
import edu.northwestern.at.utils.corpuslinguistics.spellingstandardizer.SpellingStandardizer;
import edu.northwestern.at.utils.corpuslinguistics.tokenizer.DefaultWordTokenizer;
import edu.northwestern.at.utils.corpuslinguistics.tokenizer.PennTreebankTokenizer;
import edu.northwestern.at.utils.corpuslinguistics.tokenizer.WordTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.util.CodeTimer;

public final class MorphAdornerWrapper {

    private static String lemmaSeparator = "|";
    private static String lemmaSeparatorRegex = "\\|";
    private static WordTokenizer tokenizer;
    private static SentenceSplitter splitter;
    private static PartOfSpeechTagger posTagger;
    private static Lexicon wordLexicon;
    private static PartOfSpeechTags partOfSpeechTags;
    private static Lemmatizer lemmatizer;
    private static SpellingStandardizer standardizer;
    private static WordTokenizer spellingTokenizer;

    static {
        try {
            CodeTimer timer = new CodeTimer("MorphAdornerWrapper");
            tokenizer = new DefaultWordTokenizer();
            posTagger = new DefaultPartOfSpeechTagger();
            splitter = new DefaultSentenceSplitter();
            splitter.setPartOfSpeechGuesser(posTagger.getPartOfSpeechGuesser());
            wordLexicon = posTagger.getLexicon();
            lemmatizer = new DefaultLemmatizer();
            standardizer = new DefaultSpellingStandardizer();
            spellingTokenizer = new PennTreebankTokenizer();
            partOfSpeechTags = wordLexicon.getPartOfSpeechTags();
            lemmatizer.setDictionary(new TreeSet<String>());
            timer.stop();
        } catch (Exception ex) {
            System.err.println("Error initializing MorphAdornerWrapper");
            System.err.println(ex);
            Globals.exit();
        }
    }

    public static void init() {
    }

    public static void copyAdornedWord(AdornedWord to, AdornedWord from) {
        to.setToken(from.getToken());
        to.setSpelling(from.getSpelling());
        to.setLemmata(from.getLemmata());
        to.setPartsOfSpeech(from.getPartsOfSpeech());
    }

    public static List<List<WTagging>> tagText(String text) {
        List<List<String>> sentences = splitter.extractSentences(text, tokenizer);
        for (List<String> sentence : sentences) {
            ensureSentenceEnd(sentence);
        }
        List<List<AdornedWord>> taggedSentences = posTagger.tagSentences(sentences);
        List<List<WTagging>> taggingss = new ArrayList<List<WTagging>>();
        for (List<AdornedWord> taggedSentence : taggedSentences) {
            List<WTagging> taggings = new ArrayList<WTagging>();
            for (AdornedWord aWord : taggedSentence) {
                setLemma(aWord);
                aWordToTaggings(taggings, aWord);
            }
            taggingss.add(taggings);
        }
        return taggingss;
    }

    private static void setLemma(AdornedWord adornedWord) {
        String spelling = adornedWord.getSpelling();
        String partOfSpeech = adornedWord.getPartsOfSpeech();
        String lemmata = spelling;
// Get lemmatization word class
// for part of speech.
        String lemmaClass = partOfSpeechTags.getLemmaWordClass(partOfSpeech);
// Do not lemmatize words which
// should not be lemmatized,
// including proper names.
        if (lemmatizer.cantLemmatize(spelling) || lemmaClass.equals("none")) {
        } else {
// Try compound word exceptions
// list first.
            lemmata = lemmatizer.lemmatize(spelling, "compound");
// If lemma not found, keep trying.
            if (lemmata.equals(spelling)) {
// Extract individual word parts.
// May be more than one for a
// contraction.
                List wordList = spellingTokenizer.extractWords(spelling);
// If just one word part,
// get its lemma.
                if (!partOfSpeechTags.isCompoundTag(partOfSpeech) || (wordList.size() == 1)) {
                    if (lemmaClass.length() == 0) {
                        lemmata = lemmatizer.lemmatize(spelling);
                    } else {
                        lemmata = lemmatizer.lemmatize(spelling, lemmaClass);
                    }
                } // More than one word part.
                // Get lemma for each part and
                // concatenate them with the
                // lemma separator to form a
                // compound lemma.
                else {
                    lemmata = "";
                    String lemmaPiece = "";
                    String[] posTags = partOfSpeechTags.splitTag(partOfSpeech);
                    if (posTags.length == wordList.size()) {
                        for (int i = 0; i < wordList.size(); i++) {
                            String wordPiece = (String) wordList.get(i);
                            if (i > 0) {
                                lemmata = lemmata + lemmaSeparator;
                            }
                            lemmaClass = partOfSpeechTags.getLemmaWordClass(posTags[i]);
                            lemmaPiece =
                                    lemmatizer.lemmatize(
                                    wordPiece,
                                    lemmaClass);
                            lemmata = lemmata + lemmaPiece;
                        }
                    }
                }
            }
        }
        adornedWord.setLemmata(lemmata.toLowerCase());
    }

    private static void setStandardSpelling(AdornedWord adornedWord) {
// Get the spelling.
        String spelling = adornedWord.getSpelling();
        String standardSpelling = spelling;
        String partOfSpeech = adornedWord.getPartsOfSpeech();
// Leave proper nouns alone.
        if (partOfSpeechTags.isProperNounTag(partOfSpeech)) {
        } // Leave nouns with internal
        // capitals alone.
        else if (partOfSpeechTags.isNounTag(partOfSpeech) && CharUtils.hasInternalCaps(spelling)) {
        } // Leave foreign words alone.
        else if (partOfSpeechTags.isForeignWordTag(partOfSpeech)) {
        } // Leave numbers alone.
        else if (partOfSpeechTags.isNumberTag(partOfSpeech)) {
        } // Anything else -- call the
        // standardizer on the spelling
        // to get the standard spelling.
        else {
            standardSpelling =
                    standardizer.standardizeSpelling(adornedWord.getSpelling(),
                    partOfSpeechTags.getMajorWordClass(adornedWord.getPartsOfSpeech()));
// If the standard spelling
// is the same as the original
// spelling except for case,
// use the original spelling.
            if (standardSpelling.equalsIgnoreCase(spelling)) {
                standardSpelling = spelling;
            }
        }
// Set the standard spelling.
        adornedWord.setStandardSpelling(standardSpelling);
    }

    private static void aWordToTaggings(List<WTagging> taggings, AdornedWord aWord) {
        String tOkEn = aWord.getToken();
        if (tOkEn.indexOf('\'') >= 0) {
            String[] pos = aWord.getPartsOfSpeech().split(lemmaSeparatorRegex);
            String token = tOkEn.toLowerCase();
            if ((pos[0].indexOf("n") == 0) && (pos[0].indexOf("g") > 0)) {
                splitPosesive(taggings, tOkEn, pos, aWord.getLemmata());

            } else if (token.equals("i'm")) {
                splitPos(taggings, tOkEn, pos, "am", "be");
            } else if (token.equals("you're")) {
                splitPos(taggings, tOkEn, pos, "are", "be");
            } else if (token.equals("he's")) {
                splitPos(taggings, tOkEn, pos, "is", "be");
            } else if (token.equals("she's")) {
                splitPos(taggings, tOkEn, pos, "is", "be");
            } else if (token.equals("it's")) {
                splitPos(taggings, tOkEn, pos, "is", "be");
            } else if (token.equals("we're")) {
                splitPos(taggings, tOkEn, pos, "are", "be");
            } else if (token.equals("they're")) {
                splitPos(taggings, tOkEn, pos, "are", "be");

            } else if (token.equals("isn't")) {
                splitNeg(taggings, tOkEn, pos, "be");
            } else if (token.equals("aren't")) {
                splitNeg(taggings, tOkEn, pos, "be");
            } else if (token.equals("weren't")) {
                splitNeg(taggings, tOkEn, pos, "be");
            } else if (token.equals("wasn't")) {
                splitNeg(taggings, tOkEn, pos, "be");

            } else if (token.equals("don't")) {
                splitNeg(taggings, tOkEn, pos, "do");
            } else if (token.equals("doesn't")) {
                splitNeg(taggings, tOkEn, pos, "do");
            } else if (token.equals("didn't")) {
                splitNeg(taggings, tOkEn, pos, "do");

            } else if (token.equals("i've")) {
                splitPos(taggings, tOkEn, pos, "have", "have");
            } else if (token.equals("hasn't")) {
                splitNeg(taggings, tOkEn, pos, "have");
            } else if (token.equals("haven't")) {
                splitNeg(taggings, tOkEn, pos, "have");
            } else if (token.equals("hadn't")) {
                splitNeg(taggings, tOkEn, pos, "have");

            } else if (token.equals("shan't")) {
                splitNeg(taggings, tOkEn, pos, "shall");
                WTagging sha = taggings.get(taggings.size() - 2);
                sha.setForm("wo"); // a little trick to help the parser

            } else if (token.equals("i'll")) {
                splitPos(taggings, tOkEn, pos, "will", "will");
            } else if (token.equals("we'll")) {
                splitPos(taggings, tOkEn, pos, "will", "will");
            } else if (token.equals("you'll")) {
                splitPos(taggings, tOkEn, pos, "will", "will");
            } else if (token.equals("he'll")) {
                splitPos(taggings, tOkEn, pos, "will", "will");
            } else if (token.equals("she'll")) {
                splitPos(taggings, tOkEn, pos, "will", "will");
            } else if (token.equals("it'll")) {
                splitPos(taggings, tOkEn, pos, "will", "will");
            } else if (token.equals("they'll")) {
                splitPos(taggings, tOkEn, pos, "will", "will");
            } else if (token.equals("won't")) {
                splitNeg(taggings, tOkEn, pos, "will");
            } else if (token.equals("i'd")) {
                splitPos(taggings, tOkEn, pos, "would", "will");

            } else if (token.equals("can't")) {
                splitNeg(taggings, tOkEn, pos, "can");

            } else if (token.equals("o'clock")) {
                justCopy(taggings, aWord);
            } else if (token.equals("'")) {
                justCopy(taggings, aWord);

            } else {
                throw new RuntimeException("Unhandeled contraction: " + tOkEn + " -> " + aWord.getLemmata() + "/" + aWord.getPartsOfSpeech());
            }
        } else if (tOkEn.equalsIgnoreCase("cannot")) {
            splitNeg(taggings, tOkEn, new String[] {aWord.getPartsOfSpeech()}, "can");
        } else {
            justCopy(taggings, aWord);
        }
    }

    private static void justCopy(List<WTagging> taggings, AdornedWord aWord) {
        WTagging tagging = new WTagging();
        copyAdornedWord(tagging, aWord);
        taggings.add(tagging);
    }

    private static void splitPos(List<WTagging> taggings, String contraction, String[] pos, String token, String lemmata) {
        String[] parts = contraction.split("'");
        WTagging tagging = new WTagging();
        tagging.setToken(parts[0]);
        tagging.setLemma(parts[0].toLowerCase());
        tagging.setPartsOfSpeech(pos[0]);
        taggings.add(tagging);

        tagging = new WTagging();
        tagging.setToken(token);
        tagging.setLemma(lemmata);
        tagging.setPartsOfSpeech(pos[1]);
        taggings.add(tagging);
    }

    private static void splitNeg(List<WTagging> taggings, String contraction, String[] pos, String lemmata) {
        WTagging tagging = new WTagging();
        tagging.setToken(contraction.substring(0, contraction.length() - 3));
        tagging.setLemma(lemmata);
        tagging.setPartsOfSpeech(pos[0]);
        taggings.add(tagging);

        tagging = new WTagging();
        tagging.setToken("n't");
        tagging.setLemma("not");
        tagging.setPartsOfSpeech("xx");
        taggings.add(tagging);
    }

    private static void splitPosesive(List<WTagging> taggings, String contraction, String[] pos, String lemmata) {
        String[] parts = contraction.split("'");
        WTagging tagging = new WTagging();
        tagging.setToken(parts[0]);
        tagging.setLemma(lemmata.split("'")[0]);
        tagging.setPartsOfSpeech(pos[0]);
        taggings.add(tagging);

        tagging = new WTagging();
        tagging.setToken("'" + parts[1]);
        tagging.setLemma(tagging.getToken().toLowerCase());
        tagging.setPartsOfSpeech(null);
        taggings.add(tagging);
    }

    private static void ensureSentenceEnd(List<String> sentence) {
        int lastWordIndex = sentence.size() - 1;
        String lastWord = sentence.get(lastWordIndex);
        int lastCharIndex = lastWord.length() - 1;
        if (lastCharIndex > 0 && lastWord.charAt(lastCharIndex) == '.') {
            if (Character.isDigit(lastWord.charAt(lastCharIndex - 1))) {
                lastWord = lastWord.substring(0, lastCharIndex);
                sentence.remove(lastWordIndex);
                sentence.add(lastWord);
                sentence.add(".");
            }
        }
    }
}
