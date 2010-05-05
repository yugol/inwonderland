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

import edu.stanford.nlp.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.IndexWordSet;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.WonderlandException;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian
 */
public final class WordNetWrapper {

    private static final NumberFormat senseNumberFormatter = new DecimalFormat("00000000");
    private static Dictionary dict;
    private static Map<String, String> senseOffset = null;
    private static Map<String, String> offsetSense = null;

    static {
        try {
            // W.init();
            CodeTimer timer = new CodeTimer("WordNetWrapper");
            JWNL.initialize(new FileInputStream(W.getJwnlPropertiesFile()));
            dict = Dictionary.getInstance();
            timer.stop();
        } catch (Exception ex) {
            System.err.println("Error initializing WordNetWrapper");
            W.handleException(ex);
        }
    }

    public static void init() {
    }

    public static IndexWord lookup(String word, POS posType) {
        try {
            return dict.lookupIndexWord(posType, word);
        } catch (JWNLException ex) {
            W.handleException(ex);
            return null;
        }
    }

    public static Synset[] senses(IndexWord word) {
        try {
            return word.getSenses();
        } catch (JWNLException ex) {
            W.handleException(ex);
            return null;
        }
    }

    public static boolean contains(String str) {
        try {
            str = str.replace('_', ' ');
            IndexWordSet findings = dict.lookupAllIndexWords(str);
            for (Object o : findings.getIndexWordCollection()) {
                String lemma = ((IndexWord) o).getLemma();
                if (lemma.equalsIgnoreCase(str)) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            // System.err.println(ex);
            return false;
        }
    }

    public static Synset lookup(long offset, POS posType) {
        try {
            return dict.getSynsetAt(posType, offset);
        } catch (JWNLException ex) {
            W.handleException(ex);
            return null;
        }
    }

    public static Synset[] getSenses(String word, POS posType) {
        try {
            return dict.lookupIndexWord(posType, word).getSenses();
        } catch (Exception ex) {
            // System.err.println(ex);
            return null;
        }
    }

    public static void listCollocations() {
        try {
            Map<String, List<String>> all = new Hashtable<String, List<String>>();

            Iterator<IndexWord> it = dict.getIndexWordIterator(POS.ADJECTIVE);
            while (it.hasNext()) {
                IndexWord w = it.next();
                String lemma = w.getLemma();
                if (lemma.indexOf(" ") >= 0) {
                    lemma = StringUtils.join(lemma.split(" "), "_");
                    if (!all.containsKey(lemma)) {
                        all.put(lemma, new ArrayList<String>());
                    }
                    all.get(lemma).add("adjective");
                }
            }

            it = dict.getIndexWordIterator(POS.NOUN);
            while (it.hasNext()) {
                IndexWord w = it.next();
                String lemma = w.getLemma();
                if (lemma.indexOf(" ") >= 0) {
                    lemma = StringUtils.join(lemma.split(" "), "_");
                    if (!all.containsKey(lemma)) {
                        all.put(lemma, new ArrayList<String>());
                    }
                    all.get(lemma).add("Nn");
                }
            }

            it = dict.getIndexWordIterator(POS.ADVERB);
            while (it.hasNext()) {
                IndexWord w = it.next();
                String lemma = w.getLemma();
                if (lemma.indexOf(" ") >= 0) {
                    lemma = StringUtils.join(lemma.split(" "), "_");
                    if (!all.containsKey(lemma)) {
                        all.put(lemma, new ArrayList<String>());
                    }
                    all.get(lemma).add("adverb");
                }
            }

            it = dict.getIndexWordIterator(POS.VERB);
            while (it.hasNext()) {
                IndexWord w = it.next();
                String lemma = w.getLemma();
                if (lemma.indexOf(" ") >= 0) {
                    lemma = StringUtils.join(lemma.split(" "), "_");
                    if (!all.containsKey(lemma)) {
                        all.put(lemma, new ArrayList<String>());
                    }
                    all.get(lemma).add("verb");
                }
            }

            for (String lemma : all.keySet()) {
                List<String> types = all.get(lemma);
                System.out.println(lemma + "," + StringUtils.join(types.toArray(new String[]{}), "|"));
            }

        } catch (JWNLException ex) {
            W.handleException(ex);
        }
    }

    public static String senseKeyToOffsetKeyAlpha(String senseKey) {
        String offsetKey = null;

        try {
            if (senseOffset == null) {
                CodeTimer timer = new CodeTimer("WordNetWrapper sense-offset map");
                senseOffset = new Hashtable<String, String>();
                File indexSense = new File(W.getWordNetFolder(), "index.sense");
                BufferedReader reader = new BufferedReader(new FileReader(indexSense));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    int endIndex = line.indexOf("::");
                    if (endIndex >= 0) {
                        senseOffset.put(line.substring(0, endIndex), line);
                    }
                }
                reader.close();
                timer.stop();
            }

            String[] chunks = senseOffset.get(senseKey).split(" ");
            offsetKey = getSenseAlpha(senseKey) + chunks[1];

        } catch (Exception ex) {
            System.err.println("In WordNetWrapper.senseToId");
            W.handleException(ex);
        }

        return offsetKey;
    }

    private static String getSenseAlpha(String senseKey) throws WonderlandException {
        char senseType = senseKey.charAt(senseKey.indexOf("%") + 1);
        switch (senseType) {
            case '1':
                return "n";
            case '2':
                return "v";
            case '3':
                return "a";
            case '4':
                return "r";
            case '5':
                return "a";
            default:
                System.err.println(senseType);
                throw new WonderlandException("Unsupported type " + senseType);
        }
    }

    public static String offsetKeyAlphaToSenseKey(String offsetKey) {
        String sense = null;

        try {
            if (offsetSense == null) {
                CodeTimer timer = new CodeTimer("WordNetWrapper offset-sense map");
                offsetSense = new Hashtable<String, String>();
                File indexSense = new File(W.getWordNetFolder(), "index.sense");
                BufferedReader reader = new BufferedReader(new FileReader(indexSense));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] chunks = line.split(" ");
                    String senseKey = chunks[0];
                    String offsetKeyAlpha = getSenseAlpha(senseKey) + chunks[1];
                    offsetSense.put(offsetKeyAlpha, senseKey);
                }
                reader.close();
                timer.stop();
            }

            sense = offsetSense.get(offsetKey);

        } catch (Exception ex) {
            System.err.println("In WordNetWrapper.idToSense");
            W.handleException(ex);
        }

        return sense;
    }

    public static Synset lookup(String id) {
        POS posType = null;
        char senseType = id.charAt(0);
        if (Character.isDigit(senseType)) {
            posType = getPosNum(senseType);
        } else if (Character.isLetter(senseType)) {
            posType = getPosAlpha(senseType);
        }
        long offset = Long.parseLong(id.substring(1));
        return lookup(offset, posType);
    }

    private static POS getPosAlpha(char senseType) {
        switch (senseType) {
            case 'n':
                return POS.NOUN;
            case 'v':
                return POS.VERB;
            case 'a':
                return POS.ADJECTIVE;
            case 'r':
                return POS.ADVERB;
            default:
                return null;
        }
    }

    private static POS getPosNum(char senseType) {
        switch (senseType) {
            case '1':
                return POS.NOUN;
            case '2':
                return POS.VERB;
            case '3':
                return POS.ADJECTIVE;
            case '4':
                return POS.ADVERB;
            default:
                return null;
        }
    }

    public static String toWordNetOffset(long offset) {
        return senseNumberFormatter.format(offset);
    }

    public static String toWordNetOffsetKeyAlpha(String particle, long offset) {
        return particle + toWordNetOffset(offset);
    }

    public static String toWordNetOffsetKeyAlpha(POS pos, long offset) {
        return getAlpha(pos) + toWordNetOffset(offset);
    }

    public static String toWordNetOffsetKeyNum(POS pos, long offset) {
        return getNumber(pos) + toWordNetOffset(offset);
    }

    public static String getAlpha(POS pos) {
        if (pos == POS.NOUN) {
            return "n";
        }
        if (pos == POS.ADJECTIVE) {
            return "a";
        }
        if (pos == POS.ADVERB) {
            return "r";
        }
        if (pos == POS.VERB) {
            return "v";
        }
        return null;
    }

    public static String getNumber(POS pos) {
        if (pos == POS.NOUN) {
            return "1";
        }
        if (pos == POS.ADJECTIVE) {
            return "3";
        }
        if (pos == POS.ADVERB) {
            return "4";
        }
        if (pos == POS.VERB) {
            return "2";
        }
        return null;
    }

    public static boolean isSenseKey(String item) {
        if (item.length() == 9) {
            char car = item.charAt(0);
            if (car == 'n' || car == 'v' || car == 'a' || car == 'r'
                    || car == '1' || car == '2' || car == '3' || car == '4') {
                String cdr = item.substring(1);
                try {
                    Long.parseLong(cdr);
                    return true;
                } catch (Exception ex) {
                }
            }
        }
        return false;
    }
}
