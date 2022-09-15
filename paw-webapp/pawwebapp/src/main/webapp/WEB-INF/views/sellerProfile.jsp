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
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="seller-profile-container">
    <h4>${user.firstName} ${user.surname}</h4>
    <c:if test="${user.}
    <div>
        <span><spring:message code ="sellerprofile.mail"/> ${user.email}</span>
    </div>
    <div>
        <span><spring:message code ="sellerprofile.name"/> ${user.email}</span>
    </div>
</div>
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
