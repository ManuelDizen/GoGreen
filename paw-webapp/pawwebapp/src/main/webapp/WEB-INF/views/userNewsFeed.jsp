<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="header.jsp"%>
  <title><spring:message code="usernewsfeed.title" arguments="${user.firstName}"/></title>
</head>
<body>
  <%@include file="navbar.jsp"%>
  <c:forEach items="${news}" var="article">
    <div>
      <span><c:out value="${article.seller.user.firstName}${article.seller.user.surname}"/></span>
      <span><c:out value="${article.message}"/></span>
    </div>
  </c:forEach>
</body>
</html>
