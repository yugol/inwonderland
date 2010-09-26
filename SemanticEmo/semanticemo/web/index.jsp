
<%@page import="ro.uaic.info.semanticemo.Visible"%>
<%@page import="ro.uaic.info.wonderland.Globals"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



<html>
    <head>
    </head>
    <body>

        <%
                    javax.naming.Context ctx = new javax.naming.InitialContext();
                    javax.naming.Context myenv = (javax.naming.Context) ctx.lookup("java:comp/env");
                    java.lang.String s = (java.lang.String) myenv.lookup(Visible.WONDERLAND_DATA_PATH);
                    Globals.setDataFolder(s);
                    out.println("The value is: " + Globals.getDataFolder());
        %>

    </body>
</html>

<jsp:forward page="search.jsp"/>