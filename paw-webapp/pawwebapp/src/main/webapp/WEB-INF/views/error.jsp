<%--
  Created by IntelliJ IDEA.
  User: Franco De Simone
  Date: 9/9/2022
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Error!</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="error-container error-height">
    <div class="error-container-block">
        <h4><spring:message code="error.message"/></h4>
        <h4><spring:message code="error.code"/><c:out value="${error}"/></h4>
        <div class="circle">
            <img src="<c:url value="/resources/images/logo.png"/>" height="200" width="200"
                 alt="Logo">
        </div>
        <div class="error-margin">
            <div>
                <a class="decision-button waves-effect waves-light btn" href="<c:url value="/"/>"><spring:message code="error.goback"/></a>
            </div>
        </div>
    </div>
</div>
</body>
</html>


</body>
</html>
