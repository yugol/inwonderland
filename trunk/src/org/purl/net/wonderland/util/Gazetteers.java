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
package org.purl.net.wonderland.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian
 */
public abstract class Gazetteers {

    private static final String MAP_STRING_WTAGGING = "java.util.Map<java.lang.String, org.purl.net.wonderland.nlp.WTagging>";
    public static Map<String, WTagging> article;
    public static Map<String, WTagging> personalPronoun;
    public static Map<String, WTagging> possessivePronoun;
    public static Map<String, WTagging> demonstrativePronoun;
    public static Map<String, WTagging> reflexivePersonalPronoun;
    public static Map<String, WTagging> indefinitePronoun;
    public static Map<String, WTagging> reciprocalPronoun;
    public static Map<String, WTagging> relativePronoun;
    public static Map<String, WTagging> interrogativePronoun;
    public static Map<String, WTagging> adposition;
    public static Map<String, WTagging> coordinatingConjunction;
    public static Map<String, WTagging> subordinatingConjunction;
    public static Map<String, WTagging> indefiniteDeterminer;
    public static Map<String, WTagging> modal;
    public static Map<String, WTagging> demonstrativeDeterminer;
    public static Map<String, WTagging> verb;
    public static Map<String, WTagging> possessiveAdjective;
    public static Map<String, WTagging> interrogativeAdverb;
    public static Map<String, WTagging> adverb;
    public static Map<String, WTagging> adjective;
    //
    private static final String SET_STRING = "java.util.Set<java.lang.String>";
    public static Set<String> city;
    public static Set<String> country;
    public static Set<String> femaleFirstName;
    public static Set<String> lastName;
    public static Set<String> maleFirstName;
    //
    public static Map<String, String> selRestrs;
    //
    public static Map<String, List<String>> senseFile2wns;
    //
    public static Map<String, String> pb2vn;

    public static void init() {
    }

    static {
        CodeTimer timer = new CodeTimer("Gazetteers");
        String name = null;
        try {
            for (Field f : Gazetteers.class.getDeclaredFields()) {
                name = f.getName();
                String memberName = f.getGenericType().toString();
                if (MAP_STRING_WTAGGING.equals(memberName)) {
                    f.set(null, readMorphologyDataFile(name + ".csv"));
                } else if (SET_STRING.equals(memberName)) {
                    f.set(null, readListDataFile(name + ".txt"));
                } else if ("selRestrs".equals(name)) {
                    f.set(null, readSelRestrs());
                } else if ("senseFile2wns".equals(name)) {
                    f.set(null, readSense2wns());
                } else if ("pb2vn".equals(name)) {
                    f.set(null, readSimpleMapping());
                }
            }

        } catch (Exception ex) {
            System.err.println("Error reading '" + name + "'");
            W.handleException(ex);
        }
        timer.stop();
    }

    private static Map<String, WTagging> readMorphologyDataFile(String formFile) throws IOException {
        Map<String, WTagging> map = new HashMap<String, WTagging>();
        formFile = W.res(W.RES_MORPHOLOGY_FOLDER_PATH, formFile).getAbsolutePath();
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

    private static Set<String> readListDataFile(String formFile) throws IOException {
        Set<String> set = new HashSet<String>();

        File dataFile = W.res(W.RES_SENSES_MANUAL_FOLDER_PATH, formFile);
        dataFile.createNewFile();
        IO.readLemmaSet(set, dataFile);

        dataFile = W.res(W.RES_SENSES_AUTOMATIC_FOLDER_PATH, formFile);
        dataFile.createNewFile();
        IO.readLemmaSet(set, dataFile);

        return set;
    }

    private static Map<String, String> readSelRestrs() throws IOException {
        Map<String, String> map = new Hashtable<String, String>();
        List<String> lines = IO.getFileContentAsStringList(W.res(W.RES_VN_WNSENSE_2_THEMATIC_ROLE_TYPE_FILE_PATH));
        for (String line : lines) {
            String[] chunks = line.split(",");
            String selRestrType = WkbUtil.toConceptTypeId(chunks[0].trim());
            for (int i = 1; i < chunks.length; i++) {
                String senseType = WkbUtil.toConceptTypeId(chunks[i].trim());
                map.put(senseType, selRestrType);
            }
        }
        return map;
    }

    private static Map<String, List<String>> readSense2wns() throws IOException {
        Map<String, List<String>> map = new Hashtable<String, List<String>>();
        List<String> lines = IO.getFileContentAsStringList(W.res(W.RES_SENSE_FILE_2_WNSENSES_MANUAL_FILE_PATH));
        for (String line : lines) {
            String[] chunks = line.split(",");
            List<String> list = new ArrayList<String>();
            for (int i = 1; i < chunks.length; i++) {
                String senseType = WkbUtil.toConceptTypeId(chunks[i].trim());
                list.add(senseType);
            }
            map.put(chunks[0].trim(), list);
        }
        return map;
    }

    private static Map<String, String> readSimpleMapping() throws IOException {
        Map<String, String> map = new Hashtable<String, String>();
        List<String> lines = IO.getFileContentAsStringList(W.res(W.RES_WSD_PB_2_VN_FILE_PATH));
        for (String line : lines) {
            String[] chunks = line.split(",");
            map.put(chunks[0].trim(), chunks[1].trim());
        }
        return map;
    }

    public static List<WTagging> getAllTagings(String word) {
        word = word.toLowerCase();
        List<WTagging> tags = new ArrayList<WTagging>();
        WTagging tagging = null;

        try {
            for (Field f : Gazetteers.class.getDeclaredFields()) {
                if (MAP_STRING_WTAGGING.equals(f.getGenericType().toString())) {
                    tagging = ((Map<String, WTagging>) f.get(null)).get(word);
                    if (tagging != null) {
                        tags.add(tagging);
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Error in MorphologicalDatabase.getAllTagings");
            W.handleException(ex);
        }

        return tags;
    }

    public static Set<String> getAllForms() {
        Set<String> forms = new HashSet<String>();

        try {
            for (Field f : Gazetteers.class.getDeclaredFields()) {
                if (MAP_STRING_WTAGGING.equals(f.getGenericType().toString())) {
                    forms.addAll(((Map<String, WTagging>) f.get(null)).keySet());
                }
            }
        } catch (Exception ex) {
            System.err.println("Error in MorphologicalDatabase.getAllForms");
            W.handleException(ex);
        }

        return forms;
    }

    public static List<String> getWNSensesFor(String lemma) {
        List<String> senses = new ArrayList<String>();

        try {
            for (Field f : Gazetteers.class.getDeclaredFields()) {
                if (SET_STRING.equals(f.getGenericType().toString())) {
                    Set<String> set = (Set<String>) f.get(null);
                    if (set.contains(lemma)) {
                        List<String> list = senseFile2wns.get(f.getName());
                        if (list != null) {
                            senses.addAll(list);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("Error in MorphologicalDatabase.getAllForms");
            W.handleException(ex);
        }


        return senses;
    }
}
