<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"
         import="java.text.DateFormat" %>
<%
  try
  {
    System.out.print(DateFormat.getDateTimeInstance().format(new java.util.Date())
                     + " - Normal JSP hit " + Thread.currentThread().getName() + "\n");
  }
  catch (Exception e)
  {
    e.printStackTrace();
  }
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  <title>normal</title>
</head>
<body>This JSP should render very quickly.</body>
</html>
