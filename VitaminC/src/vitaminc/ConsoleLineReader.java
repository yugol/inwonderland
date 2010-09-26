/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import java.io.IOException;
import vitaminc.ui.ConsoleTextArea;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class ConsoleLineReader {

    private final ConsoleTextArea console;

    public ConsoleLineReader(ConsoleTextArea textArea) {
        this.console = textArea;
    }

    public String readLine() throws IOException {
        console.setReadonly(false);

        while (console.getLastLine() == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }
            Thread.yield();
        }

        String lastLine = console.getLastLine();
        console.setLastLine(null);
        return lastLine;
    }
}
