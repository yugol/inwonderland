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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.nlp.WTagging;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

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
    public static Map<String, String> wn2themroletype;
    //
    public static Map<String, List<String>> sensefile2wn;
    //
    public static Map<String, String> pb2vn;
    //
    public static Map<String, Map<String, List<String>>> vn2wn;

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

                    String fileName = name + ".csv";
                    fileName = W.res(W.RES_MORPHOLOGY_FOLDER_PATH, fileName).getAbsolutePath();
                    f.set(null, readMorphologyDataFile(fileName));

                } else if (SET_STRING.equals(memberName)) {

                    String fileName = name + ".txt";
                    f.set(null, readSenseData(fileName));

                } else if ("wn2themroletype".equals(name)) {

                    File file = W.res(W.RES_VN_WNSENSE_2_THEMATIC_ROLE_TYPE_FILE_PATH);
                    f.set(null, readWn2themroletype(file));

                } else if ("sensefile2wn".equals(name)) {

                    File file = W.res(W.RES_SENSE_FILE_2_WNSENSES_MANUAL_FILE_PATH);
                    f.set(null, readSensefile2wn(file));

                } else if ("pb2vn".equals(name)) {

                    File file = W.res(W.RES_WSD_PB_2_VN_FILE_PATH);
                    f.set(null, IO.readCsvFileAsMapOneToOne(file));

                } else if ("vn2wn".equals(name)) {

                    File file = W.res(W.RES_WSD_VN_2_WN_FILE_PATH);
                    f.set(null, readCsvFileAsMapMapToMany(file));

                }
            }

        } catch (Exception ex) {
            System.err.println("Error reading '" + name + "'");
            W.handleException(ex);
        }
        timer.stop();
    }

    private static Map<String, WTagging> readMorphologyDataFile(String fileName) throws IOException {
        Map<String, WTagging> map = new HashMap<String, WTagging>();
        ICsvBeanReader inFile = new CsvBeanReader(new FileReader(fileName), CsvPreference.EXCEL_PREFERENCE);
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

    private static Set<String> readSenseData(String fileName) throws IOException {
        Set<String> set = new HashSet<String>();

        File dataFile = W.res(W.RES_SENSES_MANUAL_FOLDER_PATH, fileName);
        dataFile.createNewFile();
        readFileIntoSet(set, dataFile);

        dataFile = W.res(W.RES_SENSES_AUTOMATIC_FOLDER_PATH, fileName);
        dataFile.createNewFile();
        readFileIntoSet(set, dataFile);

        return set;
    }

    private static void readFileIntoSet(Set<String> set, File dataFile) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(dataFile));
        String str;
        while ((str = in.readLine()) != null) {
            set.add(str.trim().toLowerCase());
        }
        in.close();
    }

    private static Map<String, String> readWn2themroletype(File file) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        List<String> lines = IO.readFileAsStringList(file);
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

    private static Map<String, List<String>> readSensefile2wn(File file) throws IOException {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> lines = IO.readFileAsStringList(file);
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

    private static Map<String, Map<String, List<String>>> readCsvFileAsMapMapToMany(File file) throws IOException {
        Map<String, Map<String, List<String>>> mapmap = new HashMap<String, Map<String, List<String>>>();

        List<String> lines = IO.readFileAsStringList(file);
        for (String line : lines) {
            String[] chunks = line.split(",");

            Map<String, List<String>> map = mapmap.get(chunks[0].trim());
            if (map == null) {
                map = new HashMap<String, List<String>>();
                mapmap.put(chunks[0].trim(), map);
            }

            List<String> list = new ArrayList<String>();
            for (int i = 2; i < chunks.length; i++) {
                String senseType = chunks[i].trim();
                list.add(senseType);
            }

            map.put(chunks[1].trim(), list);
        }
        
        return mapmap;
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

    public static List<String> getWnSensesFromNamedEntity(String lemma) {
        List<String> senses = new ArrayList<String>();

        try {
            for (Field f : Gazetteers.class.getDeclaredFields()) {
                if (SET_STRING.equals(f.getGenericType().toString())) {
                    Set<String> set = (Set<String>) f.get(null);
                    if (set.contains(lemma)) {
                        List<String> list = sensefile2wn.get(f.getName());
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

    public static List<String> getWnSensesForVnId(String lemma, String vnId) {
        return vn2wn.get(lemma).get(vnId);
    }
}
