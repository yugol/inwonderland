/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import java.io.IOException;
import org.python.core.Py;
import org.python.core.PySystemState;
import org.python.util.InteractiveConsole;
import vitaminc.ui.ConsoleTextArea;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class ConsoleRunner extends Thread {

    private final InteractiveConsole interpreter;
    private final ConsoleLineReader consoleReader;
    private final ConsoleWriter consoleWriter;

    ConsoleRunner(InteractiveConsole interpreter, ConsoleTextArea textArea, ConsoleWriter pyOutWriter) {
        super();
        this.interpreter = interpreter;
        this.consoleWriter = pyOutWriter;
        this.consoleReader = new ConsoleLineReader(textArea);
        textArea.setText(String.format("Jython %s on %s\n", PySystemState.version, PySystemState.platform));
    }

    @Override
    public void run() {
        String p1 = Py.getSystemState().ps1.asString();
        String p2 = Py.getSystemState().ps2.asString();

        interpreter.exec("2");

        boolean more = false;
        while (true) {
            String prompt = more ? p2 : p1;
            String line = null;
            try {
                consoleWriter.write(prompt);
                line = consoleReader.readLine();
            } catch (IOException ex) {
                Main.reportException(ex);
            }
            more = interpreter.push(line);
        }

    }
}
