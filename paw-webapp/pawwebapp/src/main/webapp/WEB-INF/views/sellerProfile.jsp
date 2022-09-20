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
<div class="seller-profile-main-body-container">
    <div class="seller-profile-main-body-container-information">
        <div class="seller-profile-container">
            <h4 class="profile-row">${user.firstName} ${user.surname}</h4>
            <c:if test="${user.imageId == 0}">
                <img src="<c:url value="/resources/images/logo.png"/>" alt="ProfilePictureOf${user.firstName}">
            </c:if>
            <c:if test="${user.imageId != 0}">
                <img src="<c:url value="/image/${user.imageId}"/>" alt="ProfilePictureOf${user.firstName}">
            </c:if>
            <div class="profile-row">
                <span><spring:message code ="sellerprofile.mail"/> ${user.email}</span>
            </div>
            <div class="profile-row">
                <span><spring:message code ="sellerprofile.name"/> ${user.firstName} ${user.surname}</span>
            </div>
            <div class="profile-row">
                <a href="<c:url value="/logout"/>">
                    <button type="submit" class="waves-effect waves-light btn">
                        <spring:message code="logout"/>
                    </button>
                </a>
            </div>
        </div>
    </div>
    <div class="seller-profile-container-orders">
        <h4><spring:message code="sellerprofile.orders"/></h4>
        <c:if test="${orders.size() == 0}">
            <div style="margin-top:5vh;">
                <div style="text-align: center;"><spring:message code="sellerprofile.noorders"/></div>
            </div>
        </c:if>
        <c:if test="${orders.size() != 0}">
            <c:forEach items="${orders}" var="order">
                <div class="seller-profile-card">
                    <div class="seller-profile-card-title">
                            <c:out value="${order.productName}"/>
                    </div>
                    <div class="seller-profile-card-content">
                            <spring:message code="sellerprofile.orders.price"/>
                            <c:out value="${order.price}"/>
                    </div>
                    <div class="seller-profile-card-content">
                            <spring:message code="sellerprofile.orders.amount"/>
                            <c:out value="${order.amount}"/>
                    </div>
                    <div class="seller-profile-card-content">
                        <spring:message code="sellerprofile.orders.time"/>
                            <c:out value="${order.dateTime}"/>
                    </div>
                    <div>
                        <spring:message code="sellerprofile.orders.buyer"/>
                        <c:out value="${order.buyerName}${' '}${order.buyerSurname}"/>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
    <div class="seller-profile-published-products">
        <h4><spring:message code="sellerprofile.productos"/></h4>
        <c:if test="${products.size() == 0}">
            <div><spring:message code="sellerprofile.noproducts"/></div>
            <div style="margin-top:5vh;">
                <a class="waves-effect waves-light btn standard-button"
                   href="<c:url value="/createProduct"/>">
                    <spring:message code="explore.createproduct"/>
                </a>
            </div>
        </c:if>
        <c:if test="${products.size() != 0}">
            <c:forEach items="${products}" var="product">
                <div class="seller-profile-card">
                    <div class="seller-profile-card-title"><c:out value="${product.name}"/></div>
                    <div class="seller-profile-card-content">
                        <spring:message code="sellerprofile.orders.price"/>
                        <c:out value="${product.price}"/>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>
