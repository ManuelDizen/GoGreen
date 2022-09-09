<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>


<html>
<head>
    <title><spring:message code="register.webtitle"/></title>
    <link rel="icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="register-container">
        <div class="register-buyer-container"></div>
        <div class="register-seller-container"></div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
