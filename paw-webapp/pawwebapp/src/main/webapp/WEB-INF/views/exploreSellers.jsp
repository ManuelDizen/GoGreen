<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="header.jsp"%>
    <title><spring:message code="navbar.companyname"/> - <spring:message code="explore.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <c:if test="${sellers.items.size() !=0}">
        <div class="sort">
            <c:if test="${direction == 0}">
                <a class="custom-chip"
                   href="?${favoritePath}name=${name}&areaId=${chosenArea}${path}&sort=${sort}&direction=1">
                    <i class="tiny material-icons sort-arrow" style="font-size:1.3rem;height:100%;
            width:100%;
            display:flex;
            flex-direction: column;
            justify-content: center;
            color:white;">north</i></a>
            </c:if>
            <c:if test="${direction == 1}">
                <a class="custom-chip"
                   href="?${favoritePath}name=${name}&areaId=${chosenArea}${path}&sort=${sort}&direction=0">
                    <i class="tiny material-icons sort-arrow" style="font-size:1.3rem;height:100%;
            width:100%;
            display:flex;
            flex-direction: column;
            justify-content: center;
            color:white;">south</i></a>
            </c:if>
            <!-- Dropdown Trigger -->
            <a class='dropdown-trigger btn waves-effect waves-light btn standard-button drop-align'
               href='#' data-target='dropdown1'><spring:message code="${sortName}"/></a>

            <!-- Dropdown Structure -->
            <ul id='dropdown1' class='dropdown-content'>
                <c:forEach items="${sorting}" var="sortVal">
                    <li>
                        <a href="?${favoritePath}name=${name}&areaId=${chosenArea}${path}&sort=${sortVal.id}&direction=${direction}">
                            <spring:message code="${sortVal.name}"/>
                        </a>
                    </li>
                </c:forEach>
            </ul>
            <span class="sortby">
                    <spring:message code="exploreproducts.sortby"/>
            </span>
        </div>
    </c:if>
    <div class="explore-container">
        <div class="explore-filter z-depth-1">
            <%@ include file="exploreSellersFilter.jsp"%>
        </div>
        <div class="explore-sellers">
            <%@ include file="exploreSellersItems.jsp"%>
        </div>
        <div></div>
        <c:if test="${sellers.pageCount > 1}">
        <div class="pagin">
            <c:set var="nextPage" value="${currentPage+1}"/>
            <c:set var="previousPage" value="${currentPage-1}"/>
            <div>
                <ul class="pagination">
                    <c:if test="${currentPage <= 1}">
                        <li class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                    </c:if>
                    <c:if test="${currentPage > 1}">
                        <li><a href="?page=${previousPage}&name=${name}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                        <li class="waves-effect"><a href="?page=${previousPage}&name=${name}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}" style="color: #EDFA8B">${previousPage}</a></li>
                    </c:if>
                    <li id="${currentPage}" class="disabled active"><a class="yellow-card" href="">${currentPage}</a></li>
                    <c:if test="${currentPage < sellers.pageCount}">
                        <li class="waves-effect"><a href="?page=${nextPage}&name=${name}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}" style="color: #EDFA8B">${nextPage}</a></li>
                        <li><a href="?page=${nextPage}&name=${name}&areaId=${chosenArea}${path}&sort=${sort}&direction=${direction}"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                    </c:if>
                    <c:if test="${currentPage >= sellers.pageCount}">
                        <li id="forward" class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                    </c:if>
                </ul>
            </div>
        </div>
        </c:if>

        <%-- TODO: Build pagination --%>

        <%--c:if test="${pages.size() > 1}">
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
    </div--%>

</body>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        var textNeedCount = document.querySelectorAll('#name');
        M.CharacterCounter.init(textNeedCount);
    });
</script>
</html>