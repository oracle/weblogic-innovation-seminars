<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="sessionbean.AccountBean" %>

<%
	AccountBean bean;
	if(session.getAttribute("bean") == null){	
		bean = new AccountBean();
		session.setAttribute("bean",bean);
	}else{
		bean = (AccountBean)session.getAttribute("bean");
		session.setAttribute("balance",bean.getBalance());
	}
	bean.deposit(100);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Current Balance: <%= bean.getBalance() %>
</body>
</html>