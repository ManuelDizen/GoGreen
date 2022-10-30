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
    <title><spring:message code="navbar.companyname"/> -
        <spring:message code="sellerpage.title" arguments="${user.firstName}, ${user.surname}"/>
    </title>
</head>
<body>
    <%@include file="navbar.jsp"%>
    <div class="row">
        <div class="container">
            <div class="col s3 sellerpage-info">
                <div class="row sellerpage-info-row sellerpage-title"><c:out value="${user.firstName}${' '}${user.surname}"/></div>
                <div class="row sellerpage-info-row"><c:out value="${user.email}"/></div>
                <div class="row sellerpage-info-row">
                    <c:forEach items="${areas}" var="area">
                        <c:if test="${area.id == seller.areaId}">
                            <i class="tiny material-icons separate-icon">location_pin</i><span><c:out value="${area.name}"/></span>
                        </c:if>
                    </c:forEach>
                </div>
                <div class="row sellerpage-info-row">
                    <spring:message code="sellerpage.totalorders" arguments="${orders.size()}"/>
                </div>
                <sec:authorize access="hasRole('USER')">
                    <div class="separate-20-top separate-20-bottom" style="width:100%; text-align:center;">
                        <a class="waves-effect waves-light btn"
                           href="<c:url value="/setFav/${seller.id}/${!isFavorite}"/>" style="height:fit-content; line-height: 1.5;">
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
                <div class="row fit-content">
                    <div style="display:flex; margin-bottom:30px;">
                        <h3 class="sellerpage-heading"><spring:message code="sellerpage.news.title"/></h3>
                        <c:if test="${loggedEmail != null}">
                            <c:if test="${user.email == loggedEmail}">
                                <div style="width:50%; text-align:right; margin:auto auto 0 auto;">
                                    <a class="waves-effect waves-light btn-small"
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
                            <a class="waves-effect waves-light btn-small"
                               href="<c:url value="/sellerPage/${seller.id}/news"/>">
                                <spring:message code="explore.products.more"/>
                            </a>
                        </div>
                    </c:if>
                    <c:if test="${news.size() == 0}">
                        <span><spring:message code="sellerpage.nonewsyet"/></span>
                    </c:if>
                </div>

                <h3><spring:message code="sellerpage.products.title"/></h3>
                <div class="sellerpage-products" style="height:50%;">
                    <c:forEach items="${recentProducts}" var="product">
                        <%--%@include file="productCard.jsp"%--%>
                        <div class="card product-card z-depth-1 product-card-width-30">
                            <a style="height:50%" href="<c:url value="/product/${product.productId}"/>">
                                <div class="product-card-image-container">
                                    <c:if test="${product.image.id != 0}">
                                        <img class="image-restrain" src="<c:url value="/image/${product.image.id}"/>">
                                    </c:if>
                                    <c:if test="${product.image.id == 0}">
                                        <img class="image-restrain" src="<c:url value="/resources/images/logo.png"/>">
                                    </c:if>
                                </div>
                            </a>
                            <div class="card-content">
                                <a href="<c:url value="/product/${product.productId}"/>"
                                   class="card-title product-card-title"><c:out value="${product.name}"/></a>
                                <div class="card-price">
                                    <spring:message code="explore.products.price"/><c:out value="${product.price}"/>
                                </div>
                                <div class="card-category">
                                    <i class="tiny material-icons">category</i>
                                    <c:forEach items="${categories}" var="category">
                                        <c:if test="${category.id == product.categoryId}">
                                            <a href="<c:url value="/explore?category=${category.id}&sort=${sort}&direction=${direction}"/>"><spring:message code="${category.name}"/></a>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <div class="product-card-tags">
                                    <c:forEach items="${product.tagList}" var="ecotag">
                                        <a class="${ecotag.color} white-text chip eco_chip" href="<c:url value="/explore?strings=${ecotag.id}&sort=${sort}&direction=${direction}"/>">
                                            <i class="tiny material-icons">${ecotag.icon}</i>
                                        </a>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
