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
    <title><spring:message code="navbar.companyname"/> - <c:out value="${user.firstName}${' '}${user.surname}"/>
    </title>
</head>
<body>
    <%@include file="navbar.jsp"%>
    <div class="row">
        <div class="container">
            <div class="col s3">
                <div class="row"><c:out value="${user.firstName}${' '}${user.surname}"/></div>
                <div class="row"><c:out value="${user.email}"/></div>
                <div class="row">
                    <c:forEach items="${areas}" var="area">
                        <c:if test="${area.id == seller.areaId}">
                            <i class="tiny material-icons separate-icon">location_pin</i><span><c:out value="${area.name}"/></span>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="col s9">
                <div class="row" style="height:50%;">
                    <div>
                        <h3 style="text-align: left; width:50%;"><spring:message code="sellerpage.news.title"/></h3>
                        <c:if test="${loggedEmail != null && user.email == loggedEmail}">
                            <a class="waves-effect waves-light btn-small"
                               href="<c:url value="/createArticle"/>"
                                style="width:50%; text-align:right;">
                                <spring:message code="newspage.create"/>
                            </a>
                        </c:if>
                    </div>
                    <c:forEach items="${news}" var="article">
                        <div>
                            <div class="row" style="text-align: right;">
                                <c:out value="${article.parsedDateTime}"/>
                            </div>
                            <div class="row" style="width:100%; text-align:justify;">
                                <c:out value="${article.message}"/>
                            </div>
                        </div>
                        <div>
                            <a class="waves-effect waves-light btn-small"
                            href="<c:url value="/sellerPage/${seller.id}/news"/>">
                                <spring:message code="explore.products.more"/>
                            </a>
                        </div>
                    </c:forEach>
                </div>
                <div class="row" style="height:50%;">
                    <h3><spring:message code="sellerpage.products.title"/></h3>
                    <c:forEach items="${recentProducts}" var="product">
                        <div>
                            <a href="<c:url value="/product/${product.productId}"/>">
                                <c:out value="${product.name}"/>
                            </a>
                            <div>
                                <div class="center-in-div-with-flex">
                                    <i class="tiny material-icons">category</i>
                                    <c:forEach items="${categories}" var="category">
                                        <c:if test="${category.id == product.categoryId}">
                                            <div><spring:message code="${category.name}"/></div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <c:if test="${product.seller.areaId != null}">
                                    <div class="center-in-div-with-flex">
                                        <i class="tiny material-icons separate-icon">location_pin</i>
                                        <span>
                                            <c:forEach items="${areas}" var="area">
                                                <c:if test="${area.id == product.seller.areaId}">
                                                    <c:out value="${area.name}"/>
                                                </c:if>
                                            </c:forEach>
                                        </span>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
