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
package org.purl.net.wonderland.nlp.resources;

import org.purl.net.wonderland.nlp.resources.VNClassFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.xml.sax.SAXException;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public final class VNUtil {

    public static void buildVerbNetFileList(File indexFile) throws FileNotFoundException {
        int fileCount = 0;
        PrintWriter fout = new PrintWriter(indexFile);
        File dataFolder = Configuration.getVerbNetDataFolder();
        for (File f : dataFolder.listFiles()) {
            if (f.isFile()) {
                String name = f.getName();
                if (name.lastIndexOf(".xml") == (name.length() - 4)) {
                    fout.println(name);
                    ++fileCount;
                }
            }
        }
        fout.close();
        System.out.println("Found " + fileCount + " VerbNet verb class files.");
    }

    public static void buildVerbNetVerbIndex(File verbIndex) throws Exception {
        PrintWriter fout = new PrintWriter(verbIndex);
        Map<String, VerbForm> verbForms = new Hashtable<String, VerbForm>();
        for (String name : VerbNetWrapper.getFileList()) {
            VNClassFile verbClass = new VNClassFile(name);
            for (VerbForm member : verbClass.getMembers()) {
                VerbForm vf = verbForms.get(member.getLemma());
                if (vf == null) {
                    verbForms.put(member.getLemma(), member);
                } else {
                    String vc = member.getVnClasses().iterator().next();
                    for (String vs : member.getWnSenses(vc)) {
                        vf.addWnSense(vc, vs);
                    }
                }
            }
        }
        for (VerbForm vf : verbForms.values()) {
            for (String vc : vf.getVnClasses()) {
                Set<String> senses = vf.getWnSenses(vc);
                if (senses.size() > 0) {
                    for (String vs : senses) {
                        fout.println(vf.getLemma() + "," + vc + "," + vs);
                    }
                } else {
                    fout.println(vf.getLemma() + "," + vc + ",");
                    System.err.println(vf.getLemma());
                }
            }
        }
        fout.close();
    }
}
