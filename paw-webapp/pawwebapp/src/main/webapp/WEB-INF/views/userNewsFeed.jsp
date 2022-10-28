<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="header.jsp"%>
  <title><spring:message code="usernewsfeed.title" arguments="${user.firstName}"/></title>
</head>
<body>
  <%@include file="navbar.jsp"%>
  <h3 class="center"><spring:message code="usernewsfeed.body.title"/></h3>
  <div class="row">
    <c:forEach items="${news}" var="article">
      <%@include file="newsCard.jsp"%>
    </c:forEach>
    <c:if test="${news.size() == 0}">
      <div class="center">
        <spring:message code="usernewsfeed.nonews"/>
      </div>
    </c:if>
  </div>
</body>
</html>
