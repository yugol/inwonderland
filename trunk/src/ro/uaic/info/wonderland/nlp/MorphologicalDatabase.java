/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

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
import ro.uaic.info.wonderland.Globals;

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
    static Map<String, WTagging> pnind;
    static Map<String, WTagging> pnrec;
    static Map<String, WTagging> pnrel;
    static Map<String, WTagging> pnint;
    static Map<String, WTagging> pr;
    static Map<String, WTagging> cjcrd;
    static Map<String, WTagging> cjsub;
    static Map<String, WTagging> jjind;
    static Map<String, WTagging> md;
    static Map<String, WTagging> jjdem;
    static Map<String, WTagging> vb;
    static Map<String, WTagging> jjpos;

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

    static {
        for (Field f : MorphologicalDatabase.class.getDeclaredFields()) {
            String name = f.getName();
            try {
                f.set(null, readDataFile(name + ".csv"));
            } catch (Exception ex) {
                System.out.println("Error reading '" + name + "'");
                System.out.println(ex);
                Globals.exit();
            }
        }
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
