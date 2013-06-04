<%@page import="java.util.Enumeration" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/main.css" media="screen"/>
    <title>Simple Session JSP</title>
</head>
<body style="width: 1024px;margin: auto; background-image:url('image/bg1.png');background-repeat: repeat-x;">
<table width='100%'>
    <tr>
        <td align='left'>
            <img src='image/oraclelogo.png'style="height:60px; width:1075px;" />
        </td>

    </tr>
</table>

<h1>Session Form</h1>

<%
    if (request.getParameter("sessionName") != null && request.getParameter("sessionValue") != null)
    {
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(request.getParameter("sessionName"), request.getParameter("sessionValue"));
    }
%>
Session ID:
<%
    out.print(request.getSession().getId());
%>
<br/>

<form action="index.jsp" method="POST">
    Attribute name : <input type="text" name="sessionName"/> <br/>
    Attribute value : <input type="text" name="sessionValue"/> <br/>
    <input value="Add Attribute" type="submit"/>

</form>

<h1>Session Attributes</h1>
<table border="1">
    <tr>
        <th>Key</th>
        <th>Value</th>
    </tr>
    <%
        Enumeration sessionNames = session.getAttributeNames();
        int count = 0;

        while (sessionNames.hasMoreElements())
        {
            count++;
            String name = sessionNames.nextElement().toString();
            String value = request.getSession().getAttribute(name).toString();
    %>
    <tr>
        <td>
            <%
                out.print(name);
            %>
        </td>
        <td>
            <%
                out.print(value);
            %>
        </td>
    </tr>
    <%
        }

    %>
</table>
</body>
</html>