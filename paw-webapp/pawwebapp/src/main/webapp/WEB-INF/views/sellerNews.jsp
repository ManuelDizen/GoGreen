<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="header.jsp"%>
    <title><spring:message code="navbar.companyname"/> - <spring:message code="newspage.title"/></title>
</head>
<body>
    <%@include file="navbar.jsp"%>
    <div class="row">
        <h4 class="text-center"><spring:message code="sellernews.title" arguments="${user.firstName}, ${user.surname}"/></h4>
    </div>
    <div class="row">
        <c:set var="displayName" value="0"/>
        <c:forEach items="${news}" var="article">
            <%@include file="newsCard.jsp"%>
        </c:forEach>
    </div>
    <c:if test="${pages > 1}">
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
                    <c:if test="${currentPage < pages}">
                        <li class="waves-effect"><a href="?page=${nextPage}&name=${name}&category=${chosenCategory}&maxPrice=${maxPrice}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}" style="color: #EDFA8B">${nextPage}</a></li>
                        <li><a href="?page=${nextPage}&name=${name}&category=${chosenCategory}&maxPrice=${maxPrice}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                    </c:if>
                    <c:if test="${currentPage >= pages}">
                        <li id="forward" class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </c:if>
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
