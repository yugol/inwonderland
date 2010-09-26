/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import vitaminc.Controller;
import vitaminc.Main;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ConsoleTextArea extends JTextArea {

    private class History {

        private final int maxLength;
        private LinkedList<String> buffer = new LinkedList<String>();
        private int current;

        public History(int maxLength) {
            this.maxLength = maxLength;
            current = 0;
        }

        public void push(String item) {
            buffer.add(item);
            if (buffer.size() > maxLength) {
                buffer.poll();
            }
            current = buffer.size();
        }

        public String previous() {
            --current;
            if (current < 0) {
                current = 0;
                return null;
            }
            String item = buffer.get(current);
            return item;
        }

        public String next() {
            ++current;
            if (current >= buffer.size()) {
                current = buffer.size() - 1;
                return null;
            }
            String item = buffer.get(current);
            return item;
        }
    }
    private int lastWritePos;
    private int lastCarretPosition;
    private final History history;
    private String lastLine;

    public ConsoleTextArea() {
        this.lastWritePos = 0;
        this.lastCarretPosition = 0;
        this.history = new History(100);
        setEditable(false);
    }

    public String getLastLine() {
        return lastLine;
    }

    synchronized public void setLastLine(String lastLine) {
        this.lastLine = lastLine;
    }

    synchronized public void write(String str) {
        append(str);
        lastWritePos = getDocument().getLength();
        setSelectionStart(lastWritePos);
    }

    synchronized public void setReadonly(boolean b) {
        setEditable(!b);
    }

    public void initialize(final Controller controller) {

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                String item = null;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_BACK_SPACE:
                        if (getCaretPosition() <= lastWritePos) {
                            e.consume();
                        }
                        break;
                    case KeyEvent.VK_ENTER:
                        processEnter();
                        break;
                    case KeyEvent.VK_UP:
                        item = history.previous();
                        if (item != null) {
                            putHistoryLine(item);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        item = history.next();
                        if (item != null) {
                            putHistoryLine(item);
                        }
                        break;
                }
                controller.checkAndHandleShortcut(e, null);
            }
        });

        addCaretListener(new CaretListener() {

            public void caretUpdate(CaretEvent e) {
                int carretPosition = e.getDot();
                if (carretPosition < lastWritePos) {
                    setCaretPosition(lastCarretPosition);
                } else {
                    lastCarretPosition = carretPosition;
                }
            }
        });
    }

    private void processEnter() {
        setReadonly(true);

        String line = "";
        Document doc = getDocument();
        int length = doc.getLength();
        if (length > lastWritePos) {
            try {
                line = doc.getText(lastWritePos, length - lastWritePos);
            } catch (Exception ex) {
                Main.handleException(ex);
            }
        }
        setLastLine(line);

        if (lastLine.trim().length() > 0) {
            history.push(line);
        }

        write("\n");
    }

    private void putHistoryLine(String item) {
        try {
            Document doc = getDocument();
            int length = doc.getLength();
            doc.remove(lastWritePos, length - lastWritePos);
            doc.insertString(lastWritePos, item, null);
        } catch (BadLocationException ex) {
            Main.handleException(ex);
        }
    }
}
