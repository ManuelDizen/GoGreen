<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
  <%@include file="header.jsp"%>
  <title><spring:message code="usernewsfeed.title" arguments="${user.firstName}"/></title>
</head>
<body>
  <%@include file="navbar.jsp"%>
  <h4 class="center"><spring:message code="usernewsfeed.body.title"/></h4>
  <div class="row flex-center">
    <div class="col s3 user-favs-container">
      <div class="separate-12-top" style="display:flex;">
        <div class="left-align flex-column-center-align-vertical" >
          <spring:message code="newsfeed.notifications"/>
        </div>
        <c:choose>
          <c:when test="${user.notifications}">
            <i class="material-icons green-text flex-column-center-align-vertical" style="margin:auto 20px;">check_circle</i>
            <a class="waves-effect waves-light btn-small" href="<c:url value="/toggleNotifications"/>">
              <spring:message code="newsfeed.deactivatenotifs"/>
            </a>
          </c:when>
          <c:otherwise>
            <i class="material-icons red-text flex-column-center-align-vertical" style="margin:auto 20px;">do_not_disturb</i>
            <a class="waves-effect waves-light btn-small" href="<c:url value="/toggleNotifications"/>">
              <spring:message code="newsfeed.activatenotifs"/>
            </a>
          </c:otherwise>
        </c:choose>
      </div>
      <div class="left-align separate-12-top"><spring:message code="newsfeed.userfavs"/></div>
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
      <div class="separate-12-top flex-center">
        <a class="waves-effect waves-light btn-small" href="<c:url value="/exploreSellers"/>">
          <spring:message code="newsfeed.exploresellers"/>
        </a>
      </div>
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
  <c:if test="${pages.pageCount > 1}">
    <div class="pagin">
      <c:set var="nextPage" value="${currentPage+1}"/>
      <c:set var="previousPage" value="${currentPage-1}"/>
      <div>
        <ul class="pagination">
          <c:if test="${currentPage <= 1}">
            <li class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
          </c:if>
          <c:if test="${currentPage > 1}">
            <li><a href="?page=${previousPage}&name=${name}&category=${chosenCategory}&maxPrice=${maxPrice}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
            <li class="waves-effect"><a href="?page=${previousPage}&name=${name}&category=${chosenCategory}&maxPrice=${maxPrice}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}" style="color: #EDFA8B">${previousPage}</a></li>
          </c:if>
          <li id="${currentPage}" class="disabled active"><a class="yellow-card" href="">${currentPage}</a></li>
          <c:if test="${currentPage < pages.pageCount}">
            <li class="waves-effect"><a href="?page=${nextPage}&name=${name}&category=${chosenCategory}&maxPrice=${maxPrice}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}" style="color: #EDFA8B">${nextPage}</a></li>
            <li><a href="?page=${nextPage}&name=${name}&category=${chosenCategory}&maxPrice=${maxPrice}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
          </c:if>
          <c:if test="${currentPage >= pages.pageCount}">
            <li id="forward" class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
          </c:if>
        </ul>
      </div>
    </div>
  </c:if>
</body>
</html>
