/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.gjt.sp.jedit.buffer.JEditBuffer;
import org.gjt.sp.jedit.textarea.StandaloneTextArea;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Model {

    static final String PY_EXTENSION = "py";

    static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
    private File programFile = null;
    private final StandaloneTextArea programEditor;

    Model(StandaloneTextArea codeEditor) {
        this.programEditor = codeEditor;
    }

    File getProgramFile() {
        return programFile;
    }

    void setProgramFile(File programFile) {
        this.programFile = programFile;
    }

    boolean isDirty() {
        return programEditor.getBuffer().isDirty();
    }

    void setDirty(boolean b) {
        programEditor.getBuffer().setDirty(b);
    }

    String getProgramFromBuffer() {
        JEditBuffer buffer = programEditor.getBuffer();
        return buffer.getText(0, buffer.getLength());
    }

    String getProgramFromFile() throws IOException {
        return Util.readFileAsString(programFile);
    }

    void saveProgramToFile(String text) throws IOException {
        Util.writeStringToFile(text, programFile);
    }
}
