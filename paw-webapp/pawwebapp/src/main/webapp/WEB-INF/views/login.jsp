<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 28/8/22
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <c:if test="${param.failure}">
        <div class="error">
            <spring:message code="loginpage.error"/>
        </div>
    </c:if>
    <div style="height:fit-content;">
        <c:url value="/login" var="postUrl"/>
        <form method="post" action="${postUrl}" id="login_form">
            <div class="login-container">
                <h1><spring:message code="loginpage.title"/></h1>
            </div>
            <div>
                <label><spring:message code="loginpage.email"/></label>
                <input type="text" name="email"/>
            </div>
            <div>
                <label><spring:message code="loginpage.password"/></label>
                <input type="password" name="password"/>
            </div>
            <div>
                <input type="checkbox" name="remember-me">
                <label><spring:message code="loginpage.rememberme"/></label>
            </div>
            <div>
                <button type="submit" class="waves-effect waves-light btn">
                    <spring:message code="loginpage.submit"/>
                </button>
            </div>
        </form>
    </div>
    <div>
        <span><spring:message code="loginpage.redirectregister"/></span>
        <a href="<c:url value="/register"/>">
            <button class="waves-effect waves-light btn">
                <spring:message code="loginpage.registerbutton"/>
            </button>
        </a>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
