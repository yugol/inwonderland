/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import java.io.IOException;
import java.io.Writer;
import vitaminc.ui.ConsoleTextArea;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class ConsoleWriter extends Writer {

    private final ConsoleTextArea console;

    ConsoleWriter(ConsoleTextArea textArea) {
        super(textArea);
        this.console = textArea;
    }

    @Override
    public void write(String str) throws IOException {
        console.write(str);
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

    //
    // only so far
    //
    //
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        System.out.println("write(char[] cbuf, int off, int len)");
    }

    @Override
    public Writer append(CharSequence csq) throws IOException {
        System.out.println("append(CharSequence csq)");
        return this;
    }

    @Override
    public Writer append(CharSequence csq, int start, int end) throws IOException {
        System.out.println("append(CharSequence csq, int start, int end)");
        return this;
    }

    @Override
    public Writer append(char c) throws IOException {
        System.out.println("append(char c)");
        return this;
    }

    @Override
    public void write(int c) throws IOException {
        System.out.println("write(int c)");
    }

    @Override
    public void write(char[] cbuf) throws IOException {
        System.out.println("write(char[] cbuf)");
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        System.out.println("write(String str, int off, int len)");
    }

    void justWriteIt(String toString) {
        try {
            write(toString);
        } catch (IOException ex) {
            Main.handleException(ex);
        }
    }
}
