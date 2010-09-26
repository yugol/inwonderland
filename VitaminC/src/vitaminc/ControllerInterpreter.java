/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.python.core.PyException;
import org.python.util.InteractiveConsole;
import vitaminc.ui.DrawingWindow;
import vitaminc.ui.MainWindow;
import vitaminc.ui.ConsoleWindow;
import vitaminc.ui.ProgramWindow;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class ControllerInterpreter {

    private final MainWindow mainWindow;
    private final ProgramWindow programWindow;
    private final ConsoleWindow consoleWindow;
    private final DrawingWindow drawingWindow;
    private final InteractiveConsole interpreter;
    private final ConsoleWriter pyOutWriter;
    private final ConsoleWriter pyErrWriter;
    private final ConsoleRunner consoleRunner;
    private ScriptRunner scriptRunner;

    public ControllerInterpreter(MainWindow mainWindow, ProgramWindow programWindow, ConsoleWindow consoleWindow, DrawingWindow drawingWindow, InteractiveConsole interpreter) {
        this.mainWindow = mainWindow;
        this.programWindow = programWindow;
        this.consoleWindow = consoleWindow;
        this.drawingWindow = drawingWindow;
        this.interpreter = interpreter;

        this.pyOutWriter = new ConsoleWriter(consoleWindow.console);
        this.pyErrWriter = new ConsoleWriter(consoleWindow.console);

        // this.interpreter.setI
        this.interpreter.setOut(pyOutWriter);
        this.interpreter.setErr(pyErrWriter);

        this.consoleRunner = new ConsoleRunner(interpreter, consoleWindow.console, pyOutWriter);
    }

    void setRunEnabled(boolean b) {
        mainWindow.runButton.setEnabled(b);
        mainWindow.runMenuItem.setEnabled(b);
        mainWindow.stopButton.setEnabled(!b);
        mainWindow.stopMenuItem.setEnabled(!b);
        consoleWindow.console.setEnabled(b);
    }

    void startRunningScript() {
        try {
            setRunEnabled(false);
            pyOutWriter.write("\n");
            drawingWindow.requestFocus();
            scriptRunner = new ScriptRunner(programWindow.codeEditor.getText(), interpreter, this);
            scriptRunner.start();
        } catch (IOException ex) {
            Main.handleException(ex);
        }
    }

    void doneRunningScript() {
        if (!scriptRunner.isUserInterrupt()) {
            Throwable t = scriptRunner.getInterruptException();
            if (t != null) {
                if (t instanceof PyException) {
                    pyErrWriter.justWriteIt(t.toString());
                }
            }
        }
        setRunEnabled(true);
        scriptRunner = null;
    }

    void stopRunningScript() {
        if (scriptRunner != null) {
            scriptRunner.setUserInterrupt(true);
            Thread.State state = scriptRunner.getState();
            // System.out.println(state.name());
            while (scriptRunner.getInterruptException() == null && (state == Thread.State.RUNNABLE || state == Thread.State.TIMED_WAITING || state == Thread.State.WAITING)) {
                scriptRunner.interrupt();
                state = scriptRunner.getState();
                // System.out.println(state.name());
            }
        }

        doneRunningScript();

        JOptionPane.showMessageDialog(
                null,
                "The program was interrupted by user.",
                "Program interrupted",
                JOptionPane.INFORMATION_MESSAGE);
    }

    void initializeConsole() {
        consoleRunner.start();
    }
}
