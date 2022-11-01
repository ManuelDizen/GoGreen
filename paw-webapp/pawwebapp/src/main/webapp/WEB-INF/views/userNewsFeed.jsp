<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@include file="header.jsp"%>
  <title><spring:message code="usernewsfeed.title" arguments="${user.firstName}"/></title>
</head>
<body>
  <%@include file="navbar.jsp"%>
  <h3 class="center"><spring:message code="usernewsfeed.body.title"/></h3>
  <div class="row flex-center">
    <div class="col s3 user-favs-container">
      <c:forEach items="${favs}" var="fav">
        <div class="user-fav-card">
          <a class="fav-card-seller-name underline white-text" href="<c:url value="/sellerPage/${fav.id}"/>">
            <spring:message code="newsfeed.sellername" arguments="${fav.user.firstName}, ${fav.user.surname}"/>
          </a>
          <div class="fav-card-star-container">
            <a href="<c:url value="/setFav/${fav.id}/false"/>">
              <i class="material-icons star-icon">star</i>
            </a>
          </div>
        </div>
      </c:forEach>
    </div>
    <div class="col s8">
      <c:set var="displayName" value="1"/>
      <c:forEach items="${news}" var="article">
        <%@include file="newsCard.jsp"%>
      </c:forEach>
      <c:if test="${news.size() == 0}">
        <div class="center">
          <spring:message code="usernewsfeed.nonews"/>
        </div>
      </c:if>
    </div>
  </div>
</body>
</html>
