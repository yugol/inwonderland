/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.ui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Iulian
 */
public class TextFileFilter extends FileFilter {

    @Override
    public String getDescription() {
        return "Text files";
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.txt)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }
}
