/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.python.util.PythonInterpreter;
import vitaminc.ui.MainWindow;
import vitaminc.ui.ProgramWindow;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class ControllerFile {

    private final Model model;
    private final MainWindow mainWindow;
    private final PythonInterpreter interpreter;
    private final ProgramWindow programWindow;
    private final Controller controller;

    ControllerFile(Model model, MainWindow mainWindow, PythonInterpreter interpreter, ProgramWindow programWindow, Controller controller) {
        this.model = model;
        this.mainWindow = mainWindow;
        this.interpreter = interpreter;
        this.programWindow = programWindow;
        this.controller = controller;
    }

    void newProgram() {
        if (trySaveProgram() != JOptionPane.CANCEL_OPTION) {
            model.setProgramFile(null);
            mainWindow.setTitle(MainWindow.DEFAULT_TITLE);
            interpreter.exec("reset()");
            programWindow.codeEditor.setText("");
            programWindow.codeEditor.setCaretPosition(0);
            controller.markDirty(false);
            programWindow.requestFocus();
        }
    }

    void writeProgram() {
        if (model.isDirty()) {
            if (model.getProgramFile() == null) {
                if (selectSaveProgramFile() != JFileChooser.APPROVE_OPTION) {
                    return;
                }
            }
            writeProgramToFile();
        }
    }

    void saveAsProgram() {
        if (selectSaveProgramFile() == JFileChooser.APPROVE_OPTION) {
            writeProgramToFile();
        }
    }

    private void writeProgramToFile() {
        try {
            model.saveProgramToFile(model.getProgramFromBuffer());
            mainWindow.setTitle(MainWindow.DEFAULT_TITLE + " - " + model.getProgramFile().getName());
            controller.markDirty(false);
            programWindow.requestFocus();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error writing program file", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int selectSaveProgramFile() {
        JFileChooser dlg = new JFileChooser(System.getProperty("user.dir"));
        dlg.setDialogType(JFileChooser.SAVE_DIALOG);
        dlg.setDialogTitle("Write Program");
        dlg.setApproveButtonText("Write");
        dlg.setFileFilter(new PyFileFilter());
        dlg.setMultiSelectionEnabled(false);

        int retVal = dlg.showSaveDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File scriptFile = dlg.getSelectedFile();
            String ext = Model.getExtension(scriptFile);
            if (!Model.PY_EXTENSION.equals(ext)) {
                scriptFile = new File(scriptFile.getAbsolutePath() + "." + Model.PY_EXTENSION);
            }
            model.setProgramFile(scriptFile);
        }

        return retVal;
    }

    void readProgram() {
        if (trySaveProgram() != JOptionPane.CANCEL_OPTION) {
            JFileChooser dlg = new JFileChooser(System.getProperty("user.dir"));
            dlg.setDialogType(JFileChooser.OPEN_DIALOG);
            dlg.setDialogTitle("Read Program");
            dlg.setApproveButtonText("Read");
            dlg.setFileFilter(new PyFileFilter());
            dlg.setMultiSelectionEnabled(false);

            int retVal = dlg.showOpenDialog(null);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File scriptFile = dlg.getSelectedFile();
                model.setProgramFile(scriptFile);
                readProgramFromFile();
            }
        }
    }

    void readProgramFromFile() {
        try {
            String script = model.getProgramFromFile();
            mainWindow.setTitle(MainWindow.DEFAULT_TITLE + " - " + model.getProgramFile().getName());
            interpreter.exec("reset()");
            programWindow.codeEditor.setText(script);
            programWindow.codeEditor.setCaretPosition(0);
            controller.markDirty(false);
            programWindow.requestFocus();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error reading program file", JOptionPane.ERROR_MESSAGE);
        }
    }

    int trySaveProgram() {
        if (model.isDirty()) {
            int opt = JOptionPane.showConfirmDialog(
                    null,
                    "Would you like to save the current program?",
                    "Changes detected",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                writeProgram();
            }
            return opt;
        } else {
            return JOptionPane.YES_OPTION;
        }
    }
}
