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
        <div class="register-buyer-container">
            <div class="register-buyer-title">Comprador</div>
            <div>
                <img src="<c:url value="/resources/images/landingImage1.png"/>" alt="HOla" style="width:50%;">
            </div>
            <a class="waves-effect waves-light btn" href="<c:url value="/registerbuyer"/>">Registrarse</a>
        </div>
        <div class="register-seller-container">
            <div class="register-buyer-title">Comprador</div>
            <div>
                <img src="<c:url value="/resources/images/landingImage1.png"/>" alt="HOla" style="width:50%;">
            </div>
            <a class="waves-effect waves-light btn">button</a>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
