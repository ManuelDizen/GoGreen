<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="header.jsp"%>
  <title><spring:message code="navbar.companyname"/> - <spring:message code="forgotpassword.title"/></title>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="update-form-container container" style="margin-top: 20px">
  <div class="update"></div>
  <div class="center update-margins" style="margin:50px auto;">
    <p style="font-size: 25px;"><spring:message code="congratulations.instructions"/></p>
  </div>
</div>
</body>
</html>
