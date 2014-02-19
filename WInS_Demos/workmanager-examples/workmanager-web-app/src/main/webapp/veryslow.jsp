<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"
         import="java.text.DateFormat" %>
<%
  try
  {
    System.out.print(DateFormat.getDateTimeInstance().format(new java.util.Date())
                     + " - VerySlow JSP Starting to sleep " + Thread.currentThread().getName() + "\n");
    Thread.sleep(30000);
    System.out.print(DateFormat.getDateTimeInstance().format(new java.util.Date())
                     + " - VerySlow JSP awake " + Thread.currentThread().getName() + "\n");
  }
  catch (Exception e)
  {
    e.printStackTrace();
  }
%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
  <title>veryslow</title>
</head>
<body>
<p>This JSP takes a long time.</p>

<p>&nbsp;</p>

<p>It is configured to use a WorkManager with a MaxThreadsConstraint and a
  CapacityConstraint.</p>
</body>
</html>
