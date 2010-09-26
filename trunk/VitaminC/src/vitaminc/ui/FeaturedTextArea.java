/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc.ui;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import org.gjt.sp.jedit.IPropertyManager;
import org.gjt.sp.jedit.Mode;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.buffer.JEditBuffer;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.syntax.ModeProvider;
import org.gjt.sp.jedit.syntax.TokenMarker;
import org.gjt.sp.jedit.syntax.XModeHandler;
import org.gjt.sp.jedit.textarea.StandaloneTextArea;
import org.gjt.sp.util.IOUtilities;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import vitaminc.Main;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class FeaturedTextArea extends StandaloneTextArea {

    static final Properties props = new Properties();
    static IPropertyManager propertyManager;

    static {
        try {
            props.load(FeaturedTextArea.class.getClassLoader().getResourceAsStream("config/jedit.props"));
            props.load(FeaturedTextArea.class.getClassLoader().getResourceAsStream("config/jedit_gui.props"));
            props.load(FeaturedTextArea.class.getClassLoader().getResourceAsStream("config/jedit_keys.props"));
        } catch (Exception ex) {
            Main.handleException(ex);
        }
        propertyManager = new IPropertyManager() {

            public String getProperty(String name) {
                return props.getProperty(name);
            }
        };
    }

    private static Mode loadMode(String nodeName, String resName) {
        final Mode mode = new Mode(nodeName);

        XModeHandler xmh = new XModeHandler(mode.getName()) {

            @Override
            public void error(String what, Object subst) {
                System.err.println(what + " " + subst.toString());
            }

            @Override
            public TokenMarker getTokenMarker(String modeName) {
                return mode.getTokenMarker();
            }
        };

        XMLReader parser = null;
        try {
            parser = XMLReaderFactory.createXMLReader();
        } catch (SAXException saxe) {
            Main.handleException(saxe);
        }
        mode.setTokenMarker(xmh.getTokenMarker());

        InputStream resource = FeaturedTextArea.class.getClassLoader().getResourceAsStream(resName);
        if (resource == null) {
            Main.handleException(new FileNotFoundException("Could not load resource: " + resName));
        }
        InputStream grammar = new BufferedInputStream(resource);

        try {
            InputSource isrc = new InputSource(grammar);
            isrc.setSystemId("jedit.jar");
            parser.setContentHandler(xmh);
            parser.setDTDHandler(xmh);
            parser.setEntityResolver(xmh);
            parser.setErrorHandler(xmh);
            parser.parse(isrc);

            mode.setProperties(xmh.getModeProperties());
        } catch (Throwable e) {
            Main.handleException(e);
        } finally {
            IOUtilities.closeQuietly(grammar);
        }

        return mode;
    }

    public FeaturedTextArea() {
        super(propertyManager);
        Mode py = loadMode("python", "config/python.xml");
        ModeProvider.instance.addMode(py);
        JEditBuffer buf = new JEditBuffer();
        buf.setMode(py);
        setBuffer(buf);
    }
}
