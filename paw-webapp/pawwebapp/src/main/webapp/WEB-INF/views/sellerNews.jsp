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
        <h3 class="text-center"><spring:message code="sellernews.title" arguments="${user.firstName}, ${user.surname}"/></h3>
    </div>
    <div class="row">
        <c:forEach items="${news}" var="article">
            <%@include file="newsCard.jsp"%>
        </c:forEach>
    </div>
</body>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.materialboxed');
        var instances = M.Materialbox.init(elems, options);
    });

    document.ready(function(){
        const cards = document.getElementsByClassName("product-card");
        let i = 0, max = cards.length;
        for(; i < max; i++){
            //cards[i].classList.add("product-card-width-30");
            cards[i].style.width = "30%";
        }
    });


</script>
</html>
