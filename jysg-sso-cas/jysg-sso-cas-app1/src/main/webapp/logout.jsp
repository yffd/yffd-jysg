<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>CAS Example Java Web App</title>
</head>
<body>
<%
    session.invalidate();
    response.sendRedirect(application
            .getInitParameter("casServerLogoutUrl")
            + "?service="
            + application.getInitParameter("serverName") + "/index.jsp");
%>
<h1>CAS Example Java Web App</h1>
<p>Application session is now invalidated. You may also issue a request to "/cas/logout" to destroy the CAS SSO Session as well.</p>
<hr>

<a href="index.jsp">Back to Home</a>
</body>
</html>
