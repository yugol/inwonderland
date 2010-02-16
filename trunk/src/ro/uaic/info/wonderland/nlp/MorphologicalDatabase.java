/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    static Map<String, WTagging> readDataFile(String formFile) throws FileNotFoundException, IOException {
        formFile = Globals.getMorphologyFolder() + "pos/" + formFile;
        Map<String, WTagging> map = new HashMap<String, WTagging>();
        ICsvBeanReader inFile = new CsvBeanReader(new FileReader(formFile), CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = inFile.getCSVHeader(true);
            WTagging tagging;
            while ((tagging = inFile.read(WTagging.class, header)) != null) {
                map.put(tagging.form, tagging);
            }
        } finally {
            inFile.close();
        }
        return map;
    }
    public static String[] articles = new String[]{"a", "an", "the"};
    public static String[] personalPronouns = new String[]{"I", "me", "we", "us", "you", "he", "she", "it", "him", "her", "they", "them", "thou", "thee", "ye"};
    public static String[] possesivePronouns = new String[]{"mine", "yours", "thine", "his", "hers", "ours", "theirs"};
    public static String[] demonstrativePronouns = new String[]{"this", "these", "that", "those", "former", "latter", "other", "others", "same", "so", "such", "such-and-such", "suchlike", "one", "ones"};
    public static String[] reflexivePronouns = new String[]{"myself", "yourself", "himself", "herself", "itself", "oneself", "ourselves", "yourselves", "themselves"};
    public static String[] indefinitePronouns = new String[]{"all", "both", "either", "neither", "each", "much", "many", "more", "most", "another", "others", "several", "enough", "everybody", "everything", "everyone", "some", "any", "none", "somebody", "something", "someone", "anybody", "anything", "anyone", "nobody", "nothing", "enough", "several", "one", "you", "they"};
    public static String[] relativePronouns = new String[]{"who", "whom", "whose", "whoever", "which", "whichever", "that", "what", "as"};
    public static String[] interrogativePronouns = new String[]{"who", "whose", "whom", "what", "which"};

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
            pnind = readDataFile("pnrec.csv");
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

    public static WTagging tagPronoun(WTagging prop) {

        // personal pronoun
        if (prop.lemma.equals("I")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.person = "st";
        } else if (prop.lemma.equals("me")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.person = "st";
        } else if (prop.lemma.equals("we")) {
            prop.pos = "PnPRS";
            prop.number = "plu";
            prop.person = "st";
        } else if (prop.lemma.equals("us")) {
            prop.pos = "PnPRS";
            prop.number = "plu";
            prop.person = "st";
        } else if (prop.lemma.equals("you")) {
            prop.pos = "PnPRS";
            prop.person = "nd";
        } else if (prop.lemma.equals("he")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.gender = "msc";
            prop.person = "rd";
        } else if (prop.lemma.equals("she")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.gender = "fem";
            prop.person = "rd";
        } else if (prop.lemma.equals("it")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.gender = "neu";
            prop.person = "rd";
        } else if (prop.lemma.equals("him")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.gender = "msc";
            prop.person = "rd";
        } else if (prop.lemma.equals("her")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.gender = "fem";
            prop.person = "rd";
        } else if (prop.lemma.equals("they")) {
            prop.pos = "PnPRS";
            prop.number = "plu";
            prop.person = "rd";
        } else if (prop.lemma.equals("them")) {
            prop.pos = "PnPRS";
            prop.number = "plu";
            prop.person = "rd";
        } else if (prop.lemma.equals("thou")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.person = "nd";
        } else if (prop.lemma.equals("thee")) {
            prop.pos = "PnPRS";
            prop.number = "sng";
            prop.person = "nd";
        } else if (prop.lemma.equals("ye")) {
            prop.pos = "PnPRS";
            prop.number = "plu";
            prop.person = "nd";
            //
            //
            // possessive pronoun
        } else if (prop.lemma.equals("mine")) {
            prop.pos = "PnPOS";
            prop.number = "sng";
            prop.person = "st";
        } else if (prop.lemma.equals("yours")) {
            prop.pos = "PnPOS";
            prop.person = "nd";
        } else if (prop.lemma.equals("thine")) {
            prop.pos = "PnPOS";
            prop.pos = "PnPOS";
            prop.person = "nd";
        } else if (prop.lemma.equals("his")) {
            prop.pos = "PnPOS";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "msc";
        } else if (prop.lemma.equals("hers")) {
            prop.pos = "PnPOS";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "fem";
        } else if (prop.lemma.equals("ours")) {
            prop.pos = "PnPOS";
            prop.number = "plu";
            prop.person = "st";
        } else if (prop.lemma.equals("theirs")) {
            prop.pos = "PnPOS";
            prop.number = "plu";
            prop.person = "rd";
            //
            //
            // demonstrative pronouns
        } else if (prop.lemma.equals("this")) {
            prop.pos = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("these")) {
            prop.pos = "PnDEM";
            prop.number = "plu";
        } else if (prop.lemma.equals("that")) {
            prop.pos = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("those")) {
            prop.pos = "PnDEM";
            prop.number = "plu";
        } else if (prop.lemma.equals("former")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("latter")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("other")) {
            prop.pos = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("others")) {
            prop.pos = "PnDEM";
            prop.number = "plu";
        } else if (prop.lemma.equals("same")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("so")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("such")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("such-and-such")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("suchlike")) {
            prop.pos = "PnDEM";
        } else if (prop.lemma.equals("one")) {
            prop.pos = "PnDEM";
            prop.number = "sng";
        } else if (prop.lemma.equals("ones")) {
            prop.pos = "PnDEM";
            prop.number = "plu";
            //
            //
            // reflexive pronouns
        } else if (prop.lemma.equals("myself")) {
            prop.pos = "PnREF";
            prop.number = "sng";
            prop.person = "st";
        } else if (prop.lemma.equals("yourself")) {
            prop.pos = "PnREF";
            prop.number = "sng";
            prop.person = "nd";
        } else if (prop.lemma.equals("himself")) {
            prop.pos = "PnREF";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "msc";
        } else if (prop.lemma.equals("herself")) {
            prop.pos = "PnREF";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "fem";
        } else if (prop.lemma.equals("itself")) {
            prop.pos = "PnREF";
            prop.number = "sng";
            prop.person = "rd";
            prop.gender = "neu";
        } else if (prop.lemma.equals("oneself")) {
            prop.pos = "PnREF";
            prop.number = "sng";
            prop.person = "rd";
        } else if (prop.lemma.equals("ourselves")) {
            prop.pos = "PnREF";
            prop.number = "plu";
            prop.person = "st";
        } else if (prop.lemma.equals("yourselves")) {
            prop.pos = "PnREF";
            prop.number = "plu";
            prop.person = "nd";
        } else if (prop.lemma.equals("themselves")) {
            prop.pos = "PnREF";
            prop.number = "plu";
            prop.person = "rd";
            //
            //
            // reflexive pronouns
        } else if (Arrays.binarySearch(indefinitePronouns, prop.lemma) >= 0) {
            tagIndefinitePronoun(prop);
        }
        return prop;
    }

    public static void tagIndefinitePronoun(WTagging prop) {
        prop.pos = "PnIDF";
    }

    public static void tagRelativePronoun(WTagging prop) {
        prop.pos = "PnREL";
    }

    public static void tagInterrogativePronoun(WTagging prop) {
        prop.pos = "PnINT";
    }
}
