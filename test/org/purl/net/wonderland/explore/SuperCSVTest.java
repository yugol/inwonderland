/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.explore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian
 */
public class SuperCSVTest {

    @Test
    public void testReadCsv() throws FileNotFoundException, IOException {
        String dbFile = new File(Globals.getMorphologyFolder(), "pos/ar.csv").getCanonicalPath();
        ICsvBeanReader inFile = new CsvBeanReader(new FileReader(dbFile), CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = inFile.getCSVHeader(true);
            WTagging tagging;
            while ((tagging = inFile.read(WTagging.class, header)) != null) {
                System.out.println(tagging.toCsvString());
            }
        } finally {
            inFile.close();
        }
    }
}
