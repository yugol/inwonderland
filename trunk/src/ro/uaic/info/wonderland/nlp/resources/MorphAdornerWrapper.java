/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp.resources;

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
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.nlp.WTagging;
import ro.uaic.info.wonderland.util.CodeTimer;

public class MorphAdornerWrapper {

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
            CodeTimer timer = new CodeTimer("initializing MorphAdornerWrapper");
            tokenizer = new DefaultWordTokenizer();
            posTagger = new DefaultPartOfSpeechTagger();
            splitter = new DefaultSentenceSplitter();
            splitter.setPartOfSpeechGuesser(posTagger.getPartOfSpeechGuesser());
            wordLexicon = posTagger.getLexicon();
            lemmatizer = new DefaultLemmatizer();
            standardizer = new DefaultSpellingStandardizer();
            spellingTokenizer = new PennTreebankTokenizer();
            partOfSpeechTags = wordLexicon.getPartOfSpeechTags();
            timer.stop();
        } catch (Exception ex) {
            System.out.println("Error initializing MorphAdorner");
            System.out.println(ex);
            Globals.exit();
        }
    }

    public static void copyAdornedWord(AdornedWord to, AdornedWord from) {
        to.setToken(from.getToken());
        to.setSpelling(from.getSpelling());
        to.setLemmata(from.getLemmata());
        to.setPartsOfSpeech(from.getPartsOfSpeech());
    }

    public static List<List<WTagging>> tagText(String text) {
        List<List<String>> sentences = splitter.extractSentences(text, tokenizer);
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
            if (pos[0].indexOf("ng") == 0) {
                splitPosesive(taggings, tOkEn, pos, aWord.getLemmata());
            } else if (token.equals("i'm")) {
                splitPos(taggings, tOkEn, pos, "be");
            } else if (token.equals("you're")) {
                splitPos(taggings, tOkEn, pos, "be");
            } else if (token.equals("he's")) {
                splitPos(taggings, tOkEn, pos, "be");
            } else if (token.equals("she's")) {
                splitPos(taggings, tOkEn, pos, "be");
            } else if (token.equals("it's")) {
                splitPos(taggings, tOkEn, pos, "be");
            } else if (token.equals("we're")) {
                splitPos(taggings, tOkEn, pos, "be");
            } else if (token.equals("they're")) {
                splitPos(taggings, tOkEn, pos, "be");

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

            } else if (token.equals("hasn't")) {
                splitNeg(taggings, tOkEn, pos, "have");
            } else if (token.equals("haven't")) {
                splitNeg(taggings, tOkEn, pos, "have");
            } else if (token.equals("hadn't")) {
                splitNeg(taggings, tOkEn, pos, "have");

            } else if (token.equals("we'll")) {
                splitPos(taggings, tOkEn, pos, "will");

            } else if (token.equals("o'clock")) {
                justCopy(taggings, aWord);

            } else {
                throw new RuntimeException("Unhandeled contraction: " + tOkEn + " -> " + aWord.getLemmata());
            }
        } else {
            justCopy(taggings, aWord);
        }
    }

    private static void justCopy( List<WTagging> taggings, AdornedWord aWord) {
        WTagging tagging = new WTagging();
        copyAdornedWord(tagging, aWord);
        taggings.add(tagging);
    }

    private static void splitPos(List<WTagging> taggings, String contraction, String[] pos, String lemmata) {
        String[] parts = contraction.split("'");
        WTagging tagging = new WTagging();
        tagging.setToken(parts[0]);
        tagging.setLemma(parts[0].toLowerCase());
        tagging.setPartsOfSpeech(pos[0]);
        taggings.add(tagging);

        tagging = new WTagging();
        tagging.setToken("'" + parts[1]);
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
        tagging.setLemma(lemmata);
        tagging.setPartsOfSpeech(pos[0]);
        taggings.add(tagging);

        tagging = new WTagging();
        tagging.setToken("'" + parts[1]);
        tagging.setLemma(tagging.getToken().toLowerCase());
        tagging.setPartsOfSpeech(null);
        taggings.add(tagging);
    }
}
