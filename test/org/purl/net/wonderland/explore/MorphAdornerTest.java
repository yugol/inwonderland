/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.explore;

import edu.northwestern.at.utils.CharUtils;
import edu.northwestern.at.utils.StringUtils;
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
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class MorphAdornerTest {

    public static String lemmaSeparator = "|";

    public MorphAdornerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void adorn() throws Exception {
        WordTokenizer tokenizer = new DefaultWordTokenizer();
        SentenceSplitter splitter = new DefaultSentenceSplitter();
        PartOfSpeechTagger posTagger = new DefaultPartOfSpeechTagger();
        Lexicon wordLexicon = posTagger.getLexicon();
        PartOfSpeechTags posTags = wordLexicon.getPartOfSpeechTags();
        Lemmatizer lemmatizer = new DefaultLemmatizer();
        SpellingStandardizer standardizer = new DefaultSpellingStandardizer();
        WordTokenizer spellingTokenizer = new PennTreebankTokenizer();

        splitter.setPartOfSpeechGuesser(posTagger.getPartOfSpeechGuesser());
        List<List<String>> sentences = splitter.extractSentences("Father smokes too much.", tokenizer);
        List<List<AdornedWord>> taggedSentences = posTagger.tagSentences(sentences);

        for (int i = 0; i < sentences.size(); i++) {
            List<AdornedWord> sentence = taggedSentences.get(i);
            System.out.println("---------- Sentence " + (i + 1) + "----------");

            for (int j = 0; j < sentence.size(); j++) {
                AdornedWord adornedWord = sentence.get(j);
                setStandardSpelling(adornedWord, standardizer, posTags);
                setLemma(adornedWord, wordLexicon, lemmatizer, posTags, spellingTokenizer);

                System.out.println(
                        StringUtils.rpad((j + 1) + "", 3) + ": "
                        + StringUtils.rpad(adornedWord.getSpelling(), 20)
                        + StringUtils.rpad(adornedWord.getPartsOfSpeech(), 8)
                        + StringUtils.rpad(adornedWord.getStandardSpelling(), 20)
                        + adornedWord.getLemmata());
            }
        }
    }

    public static void setLemma(
            AdornedWord adornedWord,
            Lexicon lexicon,
            Lemmatizer lemmatizer,
            PartOfSpeechTags partOfSpeechTags,
            WordTokenizer spellingTokenizer) {
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
        adornedWord.setLemmata(lemmata);
    }

    public static void setStandardSpelling(
            AdornedWord adornedWord,
            SpellingStandardizer standardizer,
            PartOfSpeechTags partOfSpeechTags) {
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
                    standardizer.standardizeSpelling(
                    adornedWord.getSpelling(),
                    partOfSpeechTags.getMajorWordClass(
                    adornedWord.getPartsOfSpeech()));
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
}


