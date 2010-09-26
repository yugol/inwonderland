/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class PyFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Model.getExtension(f);
        if (extension != null) {
            if (extension.equals(Model.PY_EXTENSION)) {
                return true;
            } else {
                return false;
            }
        }

        return false;

    }

    @Override
    public String getDescription() {
        return "*.py file";
    }
}
