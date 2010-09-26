/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.data;

import edu.stanford.nlp.ling.Word;
import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;
import org.apache.xerces.dom.DocumentImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ro.uaic.info.wonderland.emo.EmoOntology;
import ro.uaic.info.wonderland.emo.Emotion;

/**
 *
 * @author Iulian
 */
public class Article implements Comparable<Article> {

    static String[] intensities = new String[]{"*", "**", "***", "****"};
    String id;
    String url = null;
    String topic;
    String content = "";
    Document emotionMarkup = null; // http://www.w3.org/TR/2009/WD-emotionml-20091029/

    public String getContent() {
        return content;
    }

    public String getTopic() {
        return topic;
    }

    public String getUrl() {
        return url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public Article(String topic) {
        this.topic = topic;
        id = UUID.randomUUID().toString();
    }

    public int compareTo(Article o) {
        return content.compareTo(o.content);
    }

    public void addEmo(Word w, Emotion e, EmoOntology ont) {
        if (emotionMarkup == null) {
            createMarkupDocument();
        }
        Node root = emotionMarkup.getElementsByTagName("emotionml").item(0);
        Element emotion = emotionMarkup.createElement("emotion");

        Element category = emotionMarkup.createElement("category");
        category.setAttribute("set", "gew");
        category.setAttribute("name", ont.getEmoCategory(e).getName());
        emotion.appendChild(category);

        Element intensity = emotionMarkup.createElement("intensity");
        intensity.setAttribute("value", intensities[e.getNominalIntensity() - 1]);
        emotion.appendChild(intensity);

        Element dimesions = emotionMarkup.createElement("dimensions");
        dimesions.setAttribute("set", "valenceControl");
        Element valence = emotionMarkup.createElement("valence");
        valence.setAttribute("value", "" + ((float) e.getValence()) / Emotion.maxValence);
        dimesions.appendChild(valence);
        Element control = emotionMarkup.createElement("control");
        control.setAttribute("value", "" + ((float) e.getControl()) / Emotion.maxControl);
        dimesions.appendChild(control);
        emotion.appendChild(dimesions);

        Element metadata = emotionMarkup.createElement("metadata");
        Element word = emotionMarkup.createElement("word");
        word.setAttribute("value", w.word());
        word.setAttribute("begin", "" + w.beginPosition());
        word.setAttribute("end", "" + w.endPosition());
        metadata.appendChild(word);
        emotion.appendChild(metadata);

        root.appendChild(emotion);
    }

    private void createMarkupDocument() {
        emotionMarkup = new DocumentImpl();
        Element root = emotionMarkup.createElement("emotionml");
        root.setAttribute("xmlns", "http://www.w3.org/2009/10/emotionml");
        emotionMarkup.appendChild(root);
    }

    public String toXmlString() throws IOException {
        if (emotionMarkup == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        OutputFormat of = new OutputFormat("XML", null, true);
        of.setIndent(4);
        of.setIndenting(true);
        XMLSerializer serializer = new XMLSerializer(sw, of);
        serializer.asDOMSerializer();
        serializer.serialize(emotionMarkup.getDocumentElement());
        return sw.toString();
    }

    public String toHtml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");

        sb.append("<h3>Topic: " + getTopic() + "</h3>\n");

        sb.append("<h4><a href=\"" + getUrl() + "\" target=\"_blank\">" + getUrl() + "</a></h4>\n");

        int begin = 0;
        int end = 0;
        NodeList emotions = emotionMarkup.getDocumentElement().getElementsByTagName("emotion");
        for (int i = 0; i < emotions.getLength(); ++i) {
            Element emotion = (Element) emotions.item(i);
            String name = ((Element) emotion.getElementsByTagName("category").item(0)).getAttribute("name");
            name += "<sup>";
            name += ((Element) emotion.getElementsByTagName("intensity").item(0)).getAttribute("value");
            name += "</sup>";
            Element metadata = (Element) emotion.getElementsByTagName("metadata").item(0);
            Element word = (Element) metadata.getElementsByTagName("word").item(0);
            end = Integer.parseInt(word.getAttribute("end"));
            sb.append("<tt>");
            sb.append(content.substring(begin, end));
            sb.append("</tt>");
            sb.append("<sup><b>" + name + "</b></sup>");
            begin = end;
        }
        end = content.length();
        sb.append("<tt>");
        sb.append(content.substring(begin, end));
        sb.append("</tt>");

        sb.append("</body>\n");
        sb.append("</html>\n");
        return sb.toString();
    }
}
