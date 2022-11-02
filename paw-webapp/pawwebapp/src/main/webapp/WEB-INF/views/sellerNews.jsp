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
                <div class="row" style="text-align: right; padding-right:20px;">
                    <c:out value="${article.parsedDateTime}"/>
                </div>
                <c:if test="${article.image.id != 0}">
                    <div class="row">
                        <div class="col s6" style="width:50%; text-align: justify;
                        min-height:200px; margin-left:0; padding-left:20px;
                        word-break:break-word;
">
                            <c:out value="${article.message}"/>
                        </div>
                        <div class="col s6" style="width:50%; text-align: center; height:200px;">
                            <div class="news-image">
                                <img src="<c:url value="/image/${article.image.id}"/>" class="materialboxed image-restrain">
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </div>
</body>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.materialboxed');
        var instances = M.Materialbox.init(elems, options);
    });
</script>
</html>
