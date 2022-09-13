<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 13/9/22
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.firstName} ${user.surname}</title>
    <%@ include file="header.jsp"%>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div>${user.firstName} ${user.surname}</div>
<div>${user.email}</div>
<div>
    <a href="<c:url value="/logout"/>">
        <button type="submit" class="waves-effect waves-light btn">
            <spring:message code="logout"/>
        </button>
    </a>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>
