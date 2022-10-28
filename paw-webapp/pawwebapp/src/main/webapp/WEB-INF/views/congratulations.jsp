<%--
  Created by IntelliJ IDEA.
  User: Franco De Simone
  Date: 28/10/2022
  Time: 09:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="header.jsp"%>
  <title><spring:message code="navbar.companyname"/> - <spring:message code="forgotpassword.title"/><c:out value="${' '}${product.name}"/></title>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="update-form-container container" style="margin-top: 20px">
  <div class="update"></div>
  <div class="center update-margins">
    <h5><spring:message code="congratulations.title"/></h5>
  </div>
  <div class="center update-margins">
    <p><spring:message code="congratulations.instructions"/></p>
  </div>
</div>
</body>
</html>
