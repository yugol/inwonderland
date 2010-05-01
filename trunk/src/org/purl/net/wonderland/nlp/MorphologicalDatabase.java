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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian
 */
public abstract class MorphologicalDatabase {

    static Map<String, WTagging> article;
    static Map<String, WTagging> personalPronoun;
    static Map<String, WTagging> possessivePronoun;
    static Map<String, WTagging> demonstrativePronoun;
    static Map<String, WTagging> reflexivePersonalPronoun;
    static Map<String, WTagging> indefinitePronoun;
    static Map<String, WTagging> reciprocalPronoun;
    static Map<String, WTagging> relativePronoun;
    static Map<String, WTagging> interrogativePronoun;
    static Map<String, WTagging> adposition;
    static Map<String, WTagging> coordinatingConjunction;
    static Map<String, WTagging> subordinatingConjunction;
    static Map<String, WTagging> indefiniteDeterminer;
    static Map<String, WTagging> modal;
    static Map<String, WTagging> demonstrativeDeterminer;
    static Map<String, WTagging> verb;
    static Map<String, WTagging> possessiveAdjective;
    static Map<String, WTagging> interrogativeAdverb;
    static Map<String, WTagging> adverb;
    static Map<String, WTagging> adjective;

    static Map<String, WTagging> readDataFile(String formFile) throws FileNotFoundException, IOException {
        formFile = Configuration.getMorphologyFolder().getAbsolutePath() + "/pos/" + formFile;
        Map<String, WTagging> map = new HashMap<String, WTagging>();
        ICsvBeanReader inFile = new CsvBeanReader(new FileReader(formFile), CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = inFile.getCSVHeader(true);
            WTagging tagging;
            while ((tagging = inFile.read(WTagging.class, header)) != null) {
                map.put(tagging.getWrittenForm(), tagging);
            }
        } finally {
            inFile.close();
        }
        return map;
    }

    public static void init() {
    }

    static {
        CodeTimer timer = new CodeTimer("MorphologicalDatabase");
        for (Field f : MorphologicalDatabase.class.getDeclaredFields()) {
            String name = f.getName();
            try {
                f.set(null, readDataFile(name + ".csv"));
            } catch (Exception ex) {
                System.err.println("Error reading '" + name + "'");
                Configuration.handleException(ex);
            }
        }
        timer.stop();
    }

    public static List<WTagging> getAllTagings(String word) {
        word = word.toLowerCase();
        List<WTagging> tags = new ArrayList<WTagging>();
        WTagging tagging = null;

        try {
            for (Field f : MorphologicalDatabase.class.getDeclaredFields()) {
                tagging = ((Map<String, WTagging>) f.get(null)).get(word);
                if (tagging != null) {
                    tags.add(tagging);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error in MorphologicalDatabase.getAllTagings");
            Configuration.handleException(ex);
        }

        return tags;
    }

    public static Set<String> getAllForms() {
        Set<String> forms = new HashSet<String>();

        try {
            for (Field f : MorphologicalDatabase.class.getDeclaredFields()) {
                forms.addAll(((Map<String, WTagging>) f.get(null)).keySet());
            }
        } catch (Exception ex) {
            System.out.println("Error in MorphologicalDatabase.getAllForms");
            Configuration.handleException(ex);
        }

        return forms;
    }
}