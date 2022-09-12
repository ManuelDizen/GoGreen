<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 11/9/22
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="profile.title"/>${user.firstName}</title>
</head>
<body>
<%@ include file="navbar.jsp"%>
    <div>${user.firstName} ${user.surname}</div>
    <div>${user.email}</div>
<%@ include file="footer.jsp"%>
</body>
</html>
