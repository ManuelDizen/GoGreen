<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 28/8/22
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="explore.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="form-title" style="text-decoration:underline;">
    <h3><spring:message code="explore.title"/></h3>
</div>
<div class="explore-container">
    <div class="explore-filter z-depth-4">
        <%@ include file="exploreFilter.jsp"%>
    </div>
    <div class="explore-products">
        <%@ include file="exploreProducts.jsp"%>
    </div>
    <div></div>
    <div class="pagin">
        <c:set var="nextPage" value="${currentPage+1}"/>
        <c:set var="previousPage" value="${currentPage-1}"/>
        <div id="paginator">
            <ul class="pagination">
                <li id="previous"><a style="color: white" href="<c:url value="/explore/${previousPage}"/>">${previousPage}</a></li>
                <li class="disabled" id="page"><a style="color: white" href="<c:url value="/explore/${currentPage}"/>">${currentPage}</a></li>
                <li id="next"><a style="color: white" href="<c:url value="/explore/${nextPage}"/>">${nextPage}</a></li>
            </ul>
        </div>
    </div>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>