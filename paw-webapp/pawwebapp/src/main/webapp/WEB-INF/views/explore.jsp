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
            <div>
                <ul class="pagination">
                    <c:if test="${currentPage <= 1}">
                        <li class="disabled"><a href="" style="display: none"><i class="material-icons">navigate_before</i></a></li>
                    </c:if>
                    <c:if test="${currentPage > 1}">
                        <li><a href="?page=${previousPage}"><i class="material-icons">navigate_before</i></a></li>
                        <li class="waves-effect"><a href="?page=${previousPage}" style="color: #EDFA8B">${previousPage}</a></li>
                    </c:if>
                    <li id="${currentPage}" class="disabled active"><a class="yellow-card" href="">${currentPage}</a></li>
                    <c:if test="${currentPage < pages.size()}">
                        <li class="waves-effect"><a href="?page=${nextPage}" style="color: #EDFA8B">${nextPage}</a></li>
                        <li><a href="?page=${nextPage}"><i class="material-icons">navigate_next</i></a></li>
                    </c:if>
                    <c:if test="${currentPage >= pages.size()}">
                        <li id="forward" class="disabled"><a href="" style="display: none"><i class="material-icons">navigate_next</i></a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        var textNeedCount = document.querySelectorAll('#name');
        M.CharacterCounter.init(textNeedCount);
    });
</script>
</html>
