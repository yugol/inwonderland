
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="ro.uaic.info.semanticemo.Visible"%>
<%@page import="ro.uaic.info.wonderland.data.Harvester"%>
<%@page import="ro.uaic.info.wonderland.data.Article"%>
<%@page import="ro.uaic.info.wonderland.search.EmoSearchEngine"%>
<%@page import="ro.uaic.info.wonderland.emo.EmoSearchResult"%>
<%@page import="ro.uaic.info.wonderland.emo.Emotion"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%

            String topic = null;
            try {
                topic = ((String) request.getParameter(Visible.TOPIC)).trim();
                if ((topic != null) && (topic.length() <= 0)) {
                    topic = null;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            EmoSearchEngine se = Visible.getSearchEngine(application);

            if (topic != null) {
                for (Harvester h : se.getHarvesters()) {
                    String active = request.getParameter(h.getId());
                    if (active == null) {
                        active = "false";
                    }
                    h.setActive(active.equalsIgnoreCase("true"));
                }
            }

%>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="semo.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SemanticEmo - Search</title>
    </head>
    <body>
        <table id="frame">
            <tr>
                <td colspan="2">
                    <table>
                        <tr align="left" valign="baseline">
                            <td><img src="img/logo.png" alt="SemanticEmo" /></td>
                            <td>&nbsp;&nbsp;&nbsp;</td>
                            <td>Search</td>
                            <td>&nbsp;</td>
                            <td><a href="./about.jsp">About</a></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

        <hr />

        <table id="frame">
            <tr>
                <td>
                    <form action="search.jsp" method="get">
                        <table>
                            <tr align="left" valign="middle">
                                <td><h3>Topic:</h3></td>
                                <td>&nbsp;</td>
                                <td><input type="text" name="<% out.print(Visible.TOPIC);%>" value="<% out.print((topic == null) ? "" : topic);%>" size="30" /></td>
                                <td><input type="submit" value=">" /></td>
                            </tr>
                            <%
                                        for (Harvester h : se.getHarvesters()) {
                            %>
                            <tr>
                                <td></td>
                                <td></td>
                                <td>
                                    <input type="checkbox" name="<% out.print(h.getId());%>" <% out.print(h.isActive() ? "checked=\"true\"" : "");%> value="true">
                                    <% out.print(h.getName());%>
                                </td>
                            </tr>
                            <%  }%>
                        </table>
                    </form>
                </td>
            </tr>
            <%
                        if (topic != null) {
                            EmoSearchResult esr = se.search(topic);
                            session.setAttribute(Visible.EMO_SEARCH_RESULT, esr);
            %>
            <tr valign="top">
                <td><br/><img src="ResultEmoWheel" alt="Emotions Map" /></td>
                <td rowspan="2">
                    <br/>
                    <table>
                        <%
                                                    for (int i = 0; i < esr.getArtPool().length; ++i) {
                                                        Set articles = esr.getArtPool()[i];
                                                        if (articles.size() > 0) {
                                                            Emotion emo = esr.getEmoOnt().getEmotions()[i];
                                                            out.print("<tr><td><img src=\"img/" + emo.getCode() + ".jpg\" alt=\"" + emo.getCode() + "\" /></td>");
                                                            out.print("<td></td><td><i>" + emo.getLabel() + "</i></td></tr>");
                                                            Iterator it = articles.iterator();
                                                            while (it.hasNext()) {
                                                                Article art = (Article) it.next();
                                                                out.print("<tr><td></td><td></td>");
                                                                out.print("<td>");
                                                                out.print("<a href=\"ArticleHtml?" + Visible.ARTICLE_ID + "=" + art.getId() + "\" target=\"_blank\">" + art.getUrl() + "</a>");
                                                                out.print(" <sup><small>(<a href=\"ArticleText?" + Visible.ARTICLE_ID + "=" + art.getId() + "\" target=\"_blank\">Text</a>)</small></sup>");
                                                                out.print(" <sup><small>&lt;<a href=\"ArticleEmoMarkup?" + Visible.ARTICLE_ID + "=" + art.getId() + "\" target=\"_blank\">Markup</a>&gt;</small></sup>");
                                                                out.print("</td></tr>");
                                                            }
                                                        }
                                                    }

                        %>
                    </table>
                </td>
            </tr>
            <tr  valign="top">
                <td>
                    <table>
                        <tr><td>Search time:</td><td></td><td><% out.print(esr.getHarvestTime() / 1000. + " s");%></td></tr>
                        <tr><td>Detection time:</td><td></td><td><% out.print(esr.getDetectionTime() / 1000. + " s");%></td></tr>
                        <tr><td>Article count:</td><td></td><td><% out.print(esr.getArticleCount());%></td></tr>
                        <tr><td>Word count:</td><td></td><td><% out.print(esr.getWordCount());%></td></tr>
                    </table>
                </td>
            </tr>
            <% }%>
        </table>
    </body>
</html>
