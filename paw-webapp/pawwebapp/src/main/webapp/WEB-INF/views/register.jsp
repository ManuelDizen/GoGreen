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

    <div class="container">
        <h4 style="margin: 4vh auto; margin-bottom:1vh;" class="generic-title"><spring:message code="register.title"/></h4>
        <div class="row" style="text-align: center; margin-top:0; margin-bottom:0;">
            <div class="col s6">
                <div class="register-title"><spring:message code="register.buyertitle"/></div>
            </div>
            <div class="col s6">
                <div class="register-title"><spring:message code="register.sellertitle"/></div>
            </div>
        </div>
        <div class="row" style="margin-bottom:0; margin-top:0;">
            <div class="col s6">
                <div class="register-textbody"><spring:message code="register.buyerdescription"/></div>
            </div>
            <div class="col s6">
                <div class="register-textbody"><spring:message code="register.sellerdescription"/></div>
            </div>
        </div>
        <div class="row" style="margin-top:0; margin-bottom:1vh;">
            <div class="col s6" style="text-align:center;">
                <a class="decision-button waves-effect waves-light btn" href="<c:url value="/registerbuyer"/>" style="margin-top:5vh;">
                    <spring:message code="register.userbutton"/></a>
            </div>
            <div class="col s6" style="text-align:center;">
                <a class="decision-button waves-effect waves-light btn" href="<c:url value="/registerseller"/>" style="text-align:center; margin-top:5vh;">
                    <spring:message code="register.sellerbutton"/></a>
            </div>
        </div>
        <div class="row" style="margin-bottom:5vh; margin-top:0;">
            <div class="col s6">
                <div style="height:30vh; text-align:center;">
                    <img class="responsive-img" src="<c:url value="/resources/images/BuyerImage.png"/>" alt="Buyers" style="object-fit:contain; height: 100%; margin:4vh auto;">
                </div>
            </div>
            <div class="col s6">
                <div style="height:30vh; text-align:center;">
                    <img class="responsive-img" src="<c:url value="/resources/images/SellerImage.png"/>" alt="Buyers" style="object-fit:contain; height:100%; margin:4vh auto;">
                </div>
            </div>
        </div>

    </div>

    <%@ include file="footer.jsp"%>
</body>
</html>
