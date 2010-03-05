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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.IndexWordSet;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.WonderlandException;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian
 */
public final class WordNetWrapper {

    private static Dictionary dict;
    private static Map<String, String> senseOffset = null;

    static {
        try {
            Globals.init();
            CodeTimer timer = new CodeTimer("WordNetWrapper");
            JWNL.initialize(new FileInputStream(Globals.getJwnlPropertiesFile()));
            dict = Dictionary.getInstance();
            timer.stop();
        } catch (Exception ex) {
            System.err.println("Error initializing WordNetWrapper");
            System.err.println(ex);
            Globals.exit();
        }
    }

    public static void init() {
    }

    public static IndexWord lookup(String word, POS posType) {
        try {
            return dict.lookupIndexWord(posType, word);
        } catch (JWNLException ex) {
            System.err.println(ex);
            Globals.exit();
            return null;
        }
    }

    public static Synset[] senses(IndexWord word) {
        try {
            return word.getSenses();
        } catch (JWNLException ex) {
            System.err.println(ex);
            Globals.exit();
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
            System.err.println(ex);
            return false;
        }
    }

    public static Synset lookup(long offset, POS posType) {
        try {
            return dict.getSynsetAt(posType, offset);
        } catch (JWNLException ex) {
            System.err.println(ex);
            Globals.exit();
            return null;
        }
    }

    public static Synset[] getSenses(String word, POS posType) {
        try {
            return dict.lookupIndexWord(posType, word).getSenses();
        } catch (JWNLException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public static void listIndexWords(POS posType) {
        try {
            String pos = null;

            if (posType == POS.ADJECTIVE) {
                pos = "Jj";
            } else if (posType == POS.ADVERB) {
                pos = "Rb";
            } else if (posType == POS.NOUN) {
                pos = "Nn";
            } else if (posType == POS.VERB) {
                pos = "Vb";
            }

            Iterator<IndexWord> it = dict.getIndexWordIterator(posType);
            int count = 0;
            while (it.hasNext()) {
                IndexWord w = it.next();
                String lemma = w.getLemma();
                if (lemma.indexOf(" ") >= 0) {
                    ++count;
                    lemma = StringUtils.join(lemma.split(" "), "_");
                    System.out.println(lemma + "," + lemma + "," + pos + ",,,,,,,");
                }
            }
            // System.out.println("Total " + count + " collocations");
        } catch (JWNLException ex) {
            System.err.println(ex);
            Globals.exit();
        }
    }

    public static String senseToId(String senseKey) {
        String id = null;

        try {
            if (senseOffset == null) {
                CodeTimer timer = new CodeTimer("WordNetWrapper sense-offset map");
                senseOffset = new Hashtable<String, String>();
                File indexSense = new File(Globals.getWordNetFolder(), "index.sense");
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

            char senseType = senseKey.charAt(senseKey.indexOf("%") + 1);
            switch (senseType) {
                case '1':
                    id = "n";
                    break;
                case '2':
                    id = "v";
                    break;
                case '3':
                    id = "a";
                    break;
                case '4':
                    id = "r";
                    break;
                default:
                    throw new WonderlandException("Unsupported type " + senseType);
            }
            String[] chunks = senseOffset.get(senseKey).split(" ");
            id = id + chunks[1];
        } catch (Exception ex) {
            System.err.println("In WordNetWrapper.senseToId");
            ex.printStackTrace(System.err);
            Globals.exit();
        }

        return id;
    }

    public static Synset lookup(String id) {
        char senseType = id.charAt(0);
        POS posType = null;
        switch (senseType) {
            case 'n':
                posType = POS.NOUN;
                break;
            case 'v':
                posType = POS.VERB;
                break;
            case 'a':
                posType = POS.ADJECTIVE;
                break;
            case 'r':
                posType = POS.ADVERB;
                break;
        }
        long offset = Long.parseLong(id.substring(1));
        return lookup(offset, posType);
    }
}
