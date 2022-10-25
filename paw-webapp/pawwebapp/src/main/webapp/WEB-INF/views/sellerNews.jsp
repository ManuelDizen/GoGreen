<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 25/10/22
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="header.jsp"%>
    <title><spring:message code="navbar.companyname"/> - <spring:message code="newspage.title"/></title>
</head>
<body>
    <%@include file="navbar.jsp"%>
    <div class="row">
        <c:forEach items="${news}" var="article">
            <div class="big-news-card">
                <div class="row" style="text-align: right;">
                    <c:out value="${article.parsedDateTime}"/>
                </div>
                <div class="row" style="width:100%; text-align:justify; margin:auto 0 20px 20px; ">
                    <c:out value="${article.message}"/>
                </div>
                <c:if test="${article.image.id != 0}">
                    <div class="row" style="width:100%; text-align: center;">
                        <div class="news-image">
                            <img src="<c:url value="/image/${article.image.id}"/>" class="image-restrain">
                        </div>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </div>
</body>
</html>
