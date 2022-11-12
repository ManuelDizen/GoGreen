<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 24/10/22
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="header.jsp"%>
    <title><spring:message code="navbar.companyname.title2" arguments="${user.firstName}, ${user.surname}"/>
    </title>
</head>
<body>
    <%@include file="navbar.jsp"%>
    <div class="sellerpage-container">
        <div class="row">
            <div class="col s3 sellerpage-info">
                <c:if test="${user.image != null}">
                    <div class="flex-center separate-20-top">
                        <img src="<c:url value="/image/${user.image.id}"/>"
                             class="image-restrain profile-pic-public flex-column-center-align-vertical" alt=""/>
                    </div>
                </c:if>
                <div class="row sellerpage-info-row sellerpage-title">
                    <spring:message code="userprofile.fullname" arguments="${user.firstName}, ${user.surname}"/>
                </div>
                <div class="row sellerpage-info-row">
                    <c:out value="${user.email}"/>
                </div>
                <div class="row sellerpage-info-row">
                    <c:forEach items="${areas}" var="area">
                        <c:if test="${area.id == seller.area.id}">
                            <i class="tiny material-icons separate-icon">location_pin</i><span><c:out value="${area.name}"/></span>
                        </c:if>
                    </c:forEach>
                </div>
                <div class="row sellerpage-info-row">
                    <spring:message code="sellerpage.totalorders" arguments="${n_orders}"/>
                </div>
                <sec:authorize access="hasRole('USER')">
                    <div class="separate-20-top separate-20-bottom margin-auto" style="width:80%; text-align:center;">
                        <a class="waves-effect waves-light btn"
                           href="<c:url value="/setFav/${seller.id}/${!isFavorite}"/>" style="line-height:1.5;
                           height:fit-content;">
                            <c:choose>
                                <c:when test="${isFavorite}">
                                    <i class="material-icons left star-icon">star</i>
                                    <spring:message code="removefromfavorites"/>
                                </c:when>
                                <c:otherwise>
                                    <i class="material-icons left star-icon">star_border</i>
                                    <spring:message code="addtofavorites"/>
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </div>
                </sec:authorize>
            </div>
            <div class="col s8">
                <div class="row news-col">
                    <div style="display:flex; margin-bottom:30px;">
                        <h4 class="sellerpage-heading"><spring:message code="sellerpage.news.title"/></h4>
                        <c:if test="${loggedEmail != null}">
                            <c:if test="${user.email == loggedEmail}">
                                <div style="width:50%; text-align:right; margin:auto auto 0 auto;">
                                    <a class="waves-effect waves-light btn-small standard-button"
                                       href="<c:url value="/createArticle"/>">
                                        <spring:message code="newspage.create"/>
                                    </a>
                                </div>
                            </c:if>
                        </c:if>
                    </div>
                    <c:if test="${news.size() != 0}">
                        <c:forEach items="${news}" var="article">
                            <div class="news-card">
                                <div class="row" style="text-align: right;">
                                    <c:out value="${article.parsedDateTime}"/>
                                </div>
                                <div class="row" style="width:100%; text-align:justify;
                            padding: 0 20px 20px;
                            text-overflow:ellipsis; overflow:hidden; margin:auto;">
                                    <c:out value="${article.message}"/>
                                </div>
                            </div>
                        </c:forEach>
                        <div style="text-align: center;">
                            <a class="waves-effect waves-light btn-small standard-button"
                               href="<c:url value="/sellerPage/${seller.id}/news"/>">
                                <spring:message code="explore.products.more"/>
                            </a>
                        </div>
                    </c:if>
                    <c:if test="${news.size() == 0}">
                        <span><spring:message code="sellerpage.nonewsyet"/></span>
                    </c:if>
                </div>
            </div>
        </div>
        <c:if test="${recentProducts.size() != 0}">
            <div class="row">
                <h4><spring:message code="sellerpage.products.title"/></h4>
                <div class="sellerpage-products">
                    <c:forEach items="${recentProducts}" var="product">
                        <%@include file="productCard.jsp"%>
                    </c:forEach>
                </div>
                <c:if test="${pages.size() > 1}">
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
                                <c:if test="${currentPage < pages.size()}">
                                    <li class="waves-effect"><a href="?page=${nextPage}&name=${name}&category=${chosenCategory}&maxPrice=${maxPrice}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}" style="color: #EDFA8B">${nextPage}</a></li>
                                    <li><a href="?page=${nextPage}&name=${name}&category=${chosenCategory}&maxPrice=${maxPrice}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                                </c:if>
                                <c:if test="${currentPage >= pages.size()}">
                                    <li id="forward" class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </c:if>
            </div>
        </c:if>
    </div>
</body>
</html>
