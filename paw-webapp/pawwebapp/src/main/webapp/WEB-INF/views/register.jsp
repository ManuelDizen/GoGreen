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
    <h4 style="margin: 4vh auto;" class="generic-title"><spring:message code="register.title"/></h4>
    <div class="register-container">
        <div class="register-buyer-container">
            <div class="register-title"><spring:message code="register.buyertitle"/></div>
            <div class="register-textbody"><spring:message code="register.buyerdescription"/></div>
            <div>
                <img src="<c:url value="/resources/images/BuyerImage.png"/>" alt="HOla" style="width:50%; margin:4vh auto;">
            </div>
            <a class="waves-effect waves-light btn" href="<c:url value="/registerbuyer"/>">Registrarse</a>
        </div>
        <div class="register-seller-container">
            <div class="register-title"><spring:message code="register.sellertitle"/></div>
            <div class="register-textbody"><spring:message code="register.sellerdescription"/></div>
            <div>
                <img src="<c:url value="/resources/images/SellerImage.png"/>" alt="HOla" style="width:50%; margin:4vh auto;">
            </div>
            <a class="waves-effect waves-light btn" href="<c:url value="/registerseller"/>">Registrarse</a>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
