/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import ro.uaic.info.wonderland.Globals;

/**
 *
 * @author Iulian
 */
public abstract class MorphologicalDatabase {

    private static Map<String, WTagging> ar;
    private static Map<String, WTagging> pnprs;
    private static Map<String, WTagging> pnpos;
    private static Map<String, WTagging> pndem;
    private static Map<String, WTagging> pnref;
    private static Map<String, WTagging> pnind;
    private static Map<String, WTagging> pnrec;
    private static Map<String, WTagging> pnrel;
    private static Map<String, WTagging> pnint;

    static Map<String, WTagging> readDataFile(String formFile) throws FileNotFoundException, IOException {
        formFile = Globals.getMorphologyFolder() + "pos/" + formFile;
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
        try {
            ar = readDataFile("ar.csv");
        } catch (Exception ex) {
            System.out.println("Error reading 'ar'");
            System.out.println(ex);
            Globals.exit();
        }
        try {
            pnprs = readDataFile("pnprs.csv");
        } catch (Exception ex) {
            System.out.println("Error reading 'pnprs'");
            System.out.println(ex);
            Globals.exit();
        }
        try {
            pnpos = readDataFile("pnpos.csv");
        } catch (Exception ex) {
            System.out.println("Error reading 'pnpos'");
            System.out.println(ex);
            Globals.exit();
        }
        try {
            pndem = readDataFile("pndem.csv");
        } catch (Exception ex) {
            System.out.println("Error reading 'pndem'");
            System.out.println(ex);
            Globals.exit();
        }
        try {
            pnref = readDataFile("pnref.csv");
        } catch (Exception ex) {
            System.out.println("Error reading 'pnref'");
            System.out.println(ex);
            Globals.exit();
        }
        try {
            pnind = readDataFile("pnind.csv");
        } catch (Exception ex) {
            System.out.println("Error reading 'pnind'");
            System.out.println(ex);
            Globals.exit();
        }
        try {
            pnrec = readDataFile("pnrec.csv");
        } catch (Exception ex) {
            System.out.println("Error reading 'pnrec'");
            System.out.println(ex);
            Globals.exit();
        }
        try {
            pnrel = readDataFile("pnrel.csv");
        } catch (Exception ex) {
            System.out.println("Error reading 'pnrel'");
            System.out.println(ex);
            Globals.exit();
        }
        try {
            pnint = readDataFile("pnint.csv");
        } catch (Exception ex) {
            System.out.println("Error reading 'pnint'");
            System.out.println(ex);
            Globals.exit();
        }
    }

    public static List<WTagging> getTags(String word, String hint) {
        word = word.toLowerCase();
        List<WTagging> tags = new ArrayList<WTagging>();
        WTagging tagging = null;

        tagging = ar.get(word);
        if (tagging != null) {
            tags.add(tagging);
        }
        tagging = pndem.get(word);
        if (tagging != null) {
            tags.add(tagging);
        }
        tagging = pnind.get(word);
        if (tagging != null) {
            tags.add(tagging);
        }
        tagging = pnint.get(word);
        if (tagging != null) {
            tags.add(tagging);
        }
        tagging = pnpos.get(word);
        if (tagging != null) {
            tags.add(tagging);
        }
        tagging = pnprs.get(word);
        if (tagging != null) {
            tags.add(tagging);
        }
        tagging = pnrec.get(word);
        if (tagging != null) {
            tags.add(tagging);
        }
        tagging = pnref.get(word);
        if (tagging != null) {
            tags.add(tagging);
        }
        tagging = pnrel.get(word);
        if (tagging != null) {
            tags.add(tagging);
        }

        return tags;
    }
}
