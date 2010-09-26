/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Iulian
 */
public class Utils {

    public static String getFileContentAsString(File file) throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String str;
        StringBuffer sb = new StringBuffer();
        while ((str = in.readLine()) != null) {
            sb.append(str);
            sb.append("\n");
        }
        in.close();
        return sb.toString();
    }
}
