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
    <div class="row">
        <div class="container">
            <div class="col s3 sellerpage-info">
                <div class="row sellerpage-info-row sellerpage-title">
                    <spring:message code="userprofile.fullname" arguments="${user.firstName}, ${user.surname}"/>
                </div>
                <div class="row sellerpage-info-row">
                    <c:out value="${user.email}"/>
                </div>
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
            </div>
            <div class="col s8">
                <c:if test="${news.size() != 0}">
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
                    </div>
                </c:if>
                <h3><spring:message code="sellerpage.products.title"/></h3>
                <div class="sellerpage-products" style="height:50%;">
                    <c:forEach items="${recentProducts}" var="product">
                        <%@include file="productCard.jsp"%>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
