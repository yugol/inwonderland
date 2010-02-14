/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.uaic.info.wonderland.ui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Iulian
 */
public class KbFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.cogxml)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        return "Knowledge base files";
    }

}
