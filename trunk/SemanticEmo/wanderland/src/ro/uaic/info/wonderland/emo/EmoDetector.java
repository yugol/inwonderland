/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.emo;

import edu.stanford.nlp.ling.Word;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import ro.uaic.info.wonderland.data.Article;
import ro.uaic.info.wonderland.parse.StanfordParser;
import ro.uaic.info.wonderland.pm.TreeTextPatternMatcher;

/**
 *
 * @author Iulian
 */
public class EmoDetector {

    EmoOntology emoOnt;
    StanfordParser sp = new StanfordParser();
    TreeTextPatternMatcher emoPatterns = null;
    EmoSearchResult esr = null;

    public EmoDetector(EmoOntology emoOnt) {
        this.emoOnt = emoOnt;
        emoPatterns = buildEmoPatternTree(emoOnt);
    }

    TreeTextPatternMatcher buildEmoPatternTree(EmoOntology emoOnt) {
        TreeTextPatternMatcher emoPattTree = new TreeTextPatternMatcher();
        for (Emotion emo : emoOnt.getEmotions()) {
            for (String marker : emo.getMarkers()) {
                // System.out.println(marker + " -> " + emo.getLabel());
                if (!emoPattTree.add(marker, emo)) {
                    System.out.println("error adding '" + marker + "' for '" + emo.getLabel() + "' - skipping");
                }
            }
        }
        return emoPattTree;
    }

    public void initDetection() {
        esr = new EmoSearchResult(emoOnt);
    }

    public void findEmotions(Article art) {
        String content = removeUnknownCharacters(art.getContent());
        List<Word> words = sp.tokenize(content);
        for (Word word : words) {
            Emotion emo = (Emotion) emoPatterns.match(word.word().toLowerCase());
            if (emo != null) {
                esr.add(emo, art);
                art.addEmo(word, emo, emoOnt);
            }
        }
        esr.setWordCount(esr.getWordCount() + words.size());
    }

    public EmoSearchResult getResult() {
        return esr;
    }

    private String removeUnknownCharacters(String content) {
        StringBuilder sb = new StringBuilder();
        StringCharacterIterator it = new StringCharacterIterator(content);
        for (char ch = it.first(); ch != CharacterIterator.DONE; ch = it.next()) {
            if ((int) ch > 127) {
                sb.append(' ');
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
