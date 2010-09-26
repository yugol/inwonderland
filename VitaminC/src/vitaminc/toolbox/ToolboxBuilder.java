package vitaminc.toolbox;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import vitaminc.Util;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ToolboxBuilder {

    public static void buildToolbox(Map<String, Tool> tools) throws Exception {
        InputStream is = ToolboxBuilder.class.getClassLoader().getResourceAsStream("vitaminc/toolbox/turtle_pydoc.htm");
        String xmlStr = Util.readFileAsString(is);
        is.close();

        int pos = xmlStr.indexOf("<body");
        xmlStr = "<html>" + xmlStr.substring(pos);
        xmlStr = xmlStr.replace("&mdash;", "--");
        xmlStr = xmlStr.replace("&raquo;", "&gt;&gt;");
        xmlStr = xmlStr.replace("&copy;", "(c)");
        xmlStr = xmlStr.replace("&#8211;", "-");
        xmlStr = xmlStr.replace("&#8217;", "'");
        xmlStr = xmlStr.replace("&#8220;", "&quot;");
        xmlStr = xmlStr.replace("&#8221;", "&quot;");
//        xmlStr = xmlStr.replace("", "");
//        xmlStr = xmlStr.replace("", "");

        is = new ByteArrayInputStream(xmlStr.getBytes());

        Document xhtml = Util.readXml(is);

        // read functionsl
        NodeList functionElements = xhtml.getElementsByTagName("dl");
        for (int i = 0; i < functionElements.getLength(); i++) {
            Element functionElement = (Element) functionElements.item(i);

            // read all the function names and the arguments
            List<String> descnames = new ArrayList<String>();
            List<String> arguments = new ArrayList<String>();
            NodeList ttElements = functionElement.getElementsByTagName("tt");
            for (int j = 0; j < ttElements.getLength(); j++) {
                Element ttElement = (Element) ttElements.item(j);
                String classAttribute = ttElement.getAttribute("class");

                // arguments
                if (classAttribute.equals("descname")) {
                    String descname = ttElement.getTextContent().trim();
                    descnames.add(descname);
                    if (descnames.size() == 1) {
                        Node sibling = ttElement.getNextSibling();
                        while (sibling != null) {
                            String content = sibling.getTextContent().trim();
                            if (content.equals(")")) {
                                break;
                            } else if (content.equals("")) {
                            } else if (content.equals("(")) {
                            } else if (content.equals(",")) {
                            } else if (content.equals("[") || content.equals("]") || content.charAt(0) == '"') {
                                int lastIndex = arguments.size() - 1;
                                String lastArg = arguments.get(lastIndex);
                                arguments.set(lastIndex, lastArg + content);
                            } else {
                                arguments.add(content);
                            }
                            sibling = sibling.getNextSibling();
                        }
                    }
                }
            }

            // read paramaters descriptions
            List<String> argDescs = new ArrayList<String>();
            NodeList tbodyElements = functionElement.getElementsByTagName("tbody");
            if (tbodyElements.getLength() > 0) {

                Element tbodyElement = (Element) tbodyElements.item(0);
                Element tdElement = (Element) tbodyElement.getElementsByTagName("td").item(0);
                NodeList liElements = tdElement.getElementsByTagName("li");
                if (liElements.getLength() > 0) {
                    for (int j = 0; j < liElements.getLength(); j++) {
                        Element liElement = (Element) liElements.item(j);
                        argDescs.add(readParagraph(liElement, true));
                    }
                } else {
                    argDescs.add(readParagraph(tdElement, true));
                }
            }

            // read explanations
            List<String> docPars = new ArrayList<String>();
            NodeList docNodes = functionElement.getElementsByTagName("p");
            if (docNodes.getLength() > 0) {
                Node docNode = docNodes.item(0);
                while (docNode != null) {
                    if (docNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element docElement = (Element) docNode;
                        String tag = docElement.getTagName();
                        if (tag.equals("p")) {
                            docPars.add(readParagraph(docElement, false));
                        } else if (tag.equals("table")) {
                            String classAttribute = docElement.getAttribute("class");
                            if (!"docutils field-list".equals(classAttribute)) {
                                docPars.add(readTable(docElement));
                            }
                        } else if (tag.equals("ul")) {
                            docPars.add(readList(docElement));
                        } else if (tag.equals("div")) {
                            break;
                        }
                    }
                    docNode = docNode.getNextSibling();
                }
            }
            if (docPars.size() == 0) {
                docNodes = functionElement.getElementsByTagName("dd");
                for (int j = 0; j < docNodes.getLength(); j++) {
                    Element docNode = (Element) docNodes.item(j);
                    docPars.add(readParagraph(docNode, false));
                }
            }
            NodeList docutilsElements = functionElement.getElementsByTagName("dl");
            if (docutilsElements.getLength() > 0) {
                Node node = docutilsElements.item(0).getFirstChild();
                while (node != null) {
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        docPars.add(readParagraph((Element) node, false));
                    }
                    node = node.getNextSibling();
                }
            }

            // read example
            List<String> smpCode = new ArrayList<String>();
            NodeList spanElements = functionElement.getElementsByTagName("span");
            StringBuilder sb = null;
            for (int j = 0; j < spanElements.getLength(); j++) {
                Element spanElement = (Element) spanElements.item(j);
                String classAttribute = spanElement.getAttribute("class");
                if (classAttribute.equals("gp") || classAttribute.equals("go")) {
                    if (sb != null) {
                        smpCode.add(formatCodeLine(sb.toString()));
                    }
                    sb = new StringBuilder();
                }
                String text = spanElement.getTextContent();
                if (sb != null) {
                    sb.append(text);
                    sb.append(" ");
                }
            }
            if (sb != null) {
                smpCode.add(formatCodeLine(sb.toString()));
            }

            // storing / showing tools
            if (descnames.size() > 0) {
                if (tools != null) {
                    if (descnames.get(0).equals("back")) {
                        descnames.set(0, "backward");
                        descnames.set(2, "back");
                    }
                    if (descnames.get(0).equals("write")) {
                        arguments.remove(4);
                        arguments.set(3, "font=('Arial', 8, 'normal')");
                    }
                    Tool tool = new Tool(descnames, arguments, argDescs, docPars, smpCode);
                    tools.put(tool.getName(), tool);

                } else {

                    for (String s : descnames) {
                        System.out.print("{");
                        System.out.print(s);
                        System.out.print("}");
                    }
                    System.out.println("");
                    for (String s : arguments) {
                        System.out.print("(");
                        System.out.print(s);
                        System.out.print(")");
                    }
                    System.out.println("");
                    for (String s : argDescs) {
                        System.out.println(s);
                    }
                    for (String s : docPars) {
                        System.out.println(s);
                    }
                    for (String s : smpCode) {
                        System.out.println(s.replace("turtle.", ""));
                    }
                    System.out.println("");
                    System.out.println("");
                }
            }

        }
    }

    private static String readParagraph(Element elt, boolean skipFirst) {
        StringBuilder sb = new StringBuilder();

        Node node = elt.getFirstChild();
        if (skipFirst) {
            node = node.getNextSibling();
        }
        while (node != null) {
            String text = node.getTextContent();

            Node parent = node;//.getParentNode();
            if (parent.getNodeType() == Node.ELEMENT_NODE) {
                Element parentElement = (Element) parent;
                if (parentElement.getTagName().equals("tt")) {
                    sb.append("<tt>");
                    sb.append(text);
                    sb.append("</tt>");
                } else if (parentElement.getTagName().equals("a")) {
                    sb.append("<tt>");
                    sb.append(text);
                    sb.append("</tt>");
                } else if (parentElement.getTagName().equals("em")) {
                    sb.append("<em>");
                    sb.append(text);
                    sb.append("</em>");
                } else {
                    sb.append(text);
                }
            } else {
                sb.append(text);
            }

            node = node.getNextSibling();
        }
        return sb.toString().trim().replace('\n', ' ').replaceAll("\\s+", " ");
    }

    private static String readTable(Element elt) {
        StringBuilder sb = new StringBuilder();
        NodeList thElements = elt.getElementsByTagName("th");
        for (int i = 0; i < thElements.getLength(); i++) {
            Element thElement = (Element) thElements.item(i);
            String content = readParagraph(thElement, false);
            sb.append(content);
            sb.append(Tool.CELL_SEP);
        }
        sb.append(Tool.HEAD_SEP);
        NodeList tdElements = elt.getElementsByTagName("td");
        for (int i = 0; i < tdElements.getLength(); i++) {
            Element tdElement = (Element) tdElements.item(i);
            String content = readParagraph(tdElement, false);
            sb.append(content);
            sb.append(Tool.CELL_SEP);
        }
        return sb.toString();
    }

    private static String readList(Element elt) {
        StringBuilder sb = new StringBuilder();
        NodeList liElements = elt.getElementsByTagName("li");
        for (int i = 0; i < liElements.getLength(); i++) {
            Element liElement = (Element) liElements.item(i);
            String content = readParagraph(liElement, false);
            sb.append(content);
            sb.append(Tool.CELL_SEP);
        }
        return sb.toString();
    }

    private static String formatCodeLine(String line) {
        line = line.replace(" . ", ".");
        line = line.replace(" ( ", "(");
        line = line.replace(" ) ", ")");
        line = line.replace(" )", ")");
        line = line.replace(" (", "(");
        return line;
    }

    public static void main(String[] args) throws Exception {
        buildToolbox(null);
    }
}

