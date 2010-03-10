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
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian
 */
public abstract class MorphologicalDatabase {

    static Map<String, WTagging> ar;
    static Map<String, WTagging> pnprs;
    static Map<String, WTagging> pnpos;
    static Map<String, WTagging> pndem;
    static Map<String, WTagging> pnref;
    static Map<String, WTagging> pnidf;
    static Map<String, WTagging> pnrec;
    static Map<String, WTagging> pnrel;
    static Map<String, WTagging> pnint;
    static Map<String, WTagging> pp;
    static Map<String, WTagging> cjcrd;
    static Map<String, WTagging> cjsub;
    static Map<String, WTagging> jjind;
    static Map<String, WTagging> md;
    static Map<String, WTagging> jjdem;
    static Map<String, WTagging> vb;
    static Map<String, WTagging> jjpos;
    static Map<String, WTagging> rbint;
    static Map<String, WTagging> rb;
    static Map<String, WTagging> jj;

    static Map<String, WTagging> readDataFile(String formFile) throws FileNotFoundException, IOException {
        formFile = Globals.getMorphologyFolder().getAbsolutePath() + "/pos/" + formFile;
        Map<String, WTagging> map = new HashMap<String, WTagging>();
        ICsvBeanReader inFile = new CsvBeanReader(new FileReader(formFile), CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = inFile.getCSVHeader(true);
            WTagging tagging;
            while ((tagging = inFile.read(WTagging.class, header)) != null) {
                map.put(tagging.getForm(), tagging);
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
                System.err.println(ex);
                Globals.exit();
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
            System.out.println(ex);
            Globals.exit();
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
            System.out.println(ex);
            Globals.exit();
        }

        return forms;
    }
}
