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

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.IndexWordSet;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian
 */
public final class WordNetWrapper {

    static Dictionary dict;

    static {
        try {
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
}
