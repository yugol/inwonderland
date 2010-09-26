/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vitaminc;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Util {

    public static final String WEB_HELP_PAGE = "http://code.google.com/p/vitamin-c/wiki/AppHelp";
    public static final String LOGO_HOME_PAGE = "http://el.media.mit.edu/logo-foundation/logo/index.html";
    public static final String JYTHON_HOME_PAGE = "http://www.jython.org/";
    public static final String JYTHON_DOC_PAGE = "http://www.jython.org/docs/index.html";

    public static Document createDocument() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.newDocument();
    }

    public static Document readXmlFile(File file) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmlDoc = db.parse(file);
        xmlDoc.getDocumentElement().normalize();
        return xmlDoc;
    }

    public static Document readXml(InputStream is) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmlDoc = db.parse(is);
        xmlDoc.getDocumentElement().normalize();
        return xmlDoc;
    }

    public static void writeXmlFile(Document xmlDoc, File file) throws IOException {
        StringWriter sw = new StringWriter();
        OutputFormat of = new OutputFormat("XML", null, true);
        of.setIndent(2);
        of.setIndenting(true);
        of.setLineWidth(1000);
        XMLSerializer serializer = new XMLSerializer(sw, of);
        serializer.asDOMSerializer();
        serializer.serialize(xmlDoc.getDocumentElement());
        writeStringToFile(sw.toString(), file);
    }

    public static void writeStringToFile(String text, File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(text);
        out.close();
    }

    public static String readFileAsString(File file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = in.readLine()) != null) {
            sb.append(str);
            sb.append("\n");
        }
        in.close();
        return sb.toString();
    }

    public static String readFileAsString(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = in.readLine()) != null) {
            sb.append(str);
            sb.append("\n");
        }
        in.close();
        return sb.toString();
    }

    //see - http://www.mkyong.com/java/open-browser-in-java-windows-or-linux/
    public static void openWebPageInSystemBrowser(String url) {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        try {
            if (os.indexOf("win") >= 0) {
                // this doesn't support showing urls in the form of "page.html#nameLink"
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.indexOf("mac") >= 0) {
                rt.exec("open " + url);
            } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
                // Do a best guess on unix until we get a platform independent way
                // Build a list of browsers to try, in this order.
                String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                    "netscape", "opera", "links", "lynx"};

                // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++) {
                    cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \"" + url + "\" ");
                }

                rt.exec(new String[]{"sh", "-c", cmd.toString()});
            } else {
                return;
            }
        } catch (Exception e) {
            return;
        }
    }

    public static void openWebHelp() {
        openWebPageInSystemBrowser(WEB_HELP_PAGE);
    }
}
