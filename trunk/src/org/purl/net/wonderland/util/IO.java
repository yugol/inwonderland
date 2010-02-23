/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iulian
 */
public class IO {

    public static String getFileContentAsString(File file) throws IOException {
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

    public static List<String> getFileContentAsStringList(File file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String str;
        List<String> lines = new ArrayList<String>();
        while ((str = in.readLine()) != null) {
            lines.add(str);
        }
        in.close();
        return lines;
    }

    public static void writeStringToFile(String str, File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(str);
        out.close();
    }

    public static String getClassPathRoot(Class<?> c) {
        String cpr = c.getCanonicalName();
        cpr = cpr.substring(0, cpr.lastIndexOf("."));
        return cpr;
    }
}
