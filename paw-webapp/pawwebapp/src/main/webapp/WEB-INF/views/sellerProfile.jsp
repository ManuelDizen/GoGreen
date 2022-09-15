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
<%@ include file="footer.jsp"%>
</body>
</html>
