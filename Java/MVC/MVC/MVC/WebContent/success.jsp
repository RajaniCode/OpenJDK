<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.text.SimpleDateFormat" %>
	<%@ page import="java.util.Date" %>
	<%@ page import="org.mvc.helloworld.dto.User" %>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Success</title>
</head>
<body>

<p>Date and Time: <%= new SimpleDateFormat("EEEE, MMMM d, yyyy, h:mm a z").format(new Date()) %></p>

<br>

<% 
String placeholder;
%>

<%-- <% 
placeholder = (String) session.getAttribute("sessionMessage");
%> --%>

<% 
placeholder = (String) request.getAttribute("dispatchMessage");
%>

<%=placeholder %>

<% 
User usr;
%>

<%-- <% 
usr = (User) session.getAttribute("user");
%> --%>
 
 
<% 
usr = (User) request.getAttribute("user");
%>
  
 
Hello <%=usr.getUserName() %>!

</body>
</html>