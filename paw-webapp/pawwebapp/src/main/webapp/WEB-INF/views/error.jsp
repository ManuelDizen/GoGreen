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
<div style="height:80%;" class="error-container">
    <div class="error-container-block">
        <h4><spring:message code="error.message"/></h4>
        <h4><spring:message code="error.code"/><c:out value="${error}"/></h4>
        <div class="circle">
            <img src="<c:url value="/resources/images/logo.png"/>" height="200" width="200"
                 alt="Logo">
        </div>
        <div style="margin-bottom: 15px">
            <div>
                <button class="decision-button waves-effect waves-light btn" onClick="history.go(-1)"><spring:message code="error.goback"/></button>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>


</body>
</html>
