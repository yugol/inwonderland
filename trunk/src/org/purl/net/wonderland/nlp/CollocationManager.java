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
package org.purl.net.wonderland.nlp;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.trees.WordNetConnection;
import edu.stanford.nlp.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.nlp.resources.MorphAdornerWrapper;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian
 */
public class CollocationManager implements WordNetConnection {

    private static Map<String, String> collocations = new Hashtable<String, String>();

    static {
        try {
            CodeTimer timer = new CodeTimer("CollocationManager");
            readCollocationsFile("wordnet_collocations.csv");
            readCollocationsFile("extra_collocations.csv");
            timer.stop();
        } catch (Exception ex) {
            System.err.println("Error reading collocations");
            ex.printStackTrace(System.err);
            Configuration.exit();
        }
    }

    private static void readCollocationsFile(String file) throws IOException, FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(Configuration.getCollocationsFolder(), file)));
        String item = null;
        while ((item = reader.readLine()) != null) {
            String[] entry = item.split(",");
            if (entry.length == 2) {
                collocations.put(entry[0], entry[1]);
            } else {
                System.err.println("Warning: reading collocation '" + item + "' in " + file);
            }
        }
        reader.close();
    }

    public static void init() {
    }

    static List<WTagging> buildSentenceWithCollocations(List<WTagging> sentence, List<TaggedWord> pennTags) {
        List<WTagging> newSentence = new ArrayList<WTagging>();
        int p = 0;
        for (int s = 0; s < sentence.size(); ++s) {
            WTagging wt = sentence.get(s);
            TaggedWord pt = pennTags.get(p);
            String wWord = wt.word();
            String pWord = pt.word();
            if (wWord.equals(pWord)) {
                WTagging tagging = new WTagging();
                MorphAdornerWrapper.copyAdornedWord(tagging, wt);
                tagging.setPennTag(pt.tag());
                newSentence.add(tagging);
                ++p;
            } else {
                WTagging tagging = new WTagging();
                tagging.setForm(pWord);

                String[] words = pWord.split("_");
                words[0] = wt.getLemma();
                StringBuilder ma = new StringBuilder(wt.getPartsOfSpeech());
                for (int i = 1; i < words.length; ++i) {
                    ++s;
                    wt = sentence.get(s);
                    ma.append(MorphAdornerWrapper.lemmaSeparator);
                    ma.append(wt.getPartsOfSpeech());
                    words[i] = wt.getLemma();
                }

                tagging.setLemma(StringUtils.join(words, "_"));
                tagging.setPennTag(pt.tag());
                tagging.setPartsOfSpeech(ma.toString());
                tagging.setCollocation(true);
                newSentence.add(tagging);
                ++p;
            }
        }
        return newSentence;
    }

    public static String getTypes(String lemma) {
        return collocations.get(lemma);
    }

    public boolean wordNetContains(String s) {
        // System.out.println(s);
        return collocations.containsKey(s.toLowerCase());
    }
}
