package aibroker.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class StringPrintStream extends PrintStream {

    public static StringPrintStream getNewInstance() {
        return new StringPrintStream(new ByteArrayOutputStream());
    }

    private final ByteArrayOutputStream baos;

    private StringPrintStream(final ByteArrayOutputStream baos) {
        super(baos);
        this.baos = baos;
    }

    @Override
    public String toString() {
        try {
            return baos.toString("UTF8");
        } catch (final UnsupportedEncodingException e) {
            return null;
        }
    }

}
