<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="<c:url value="/resources/css/materialize.css"/>" type="text/css"
              media="screen,projection">
        <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" type="text/css"
              media="screen,projection">
        <link href='https://fonts.googleapis.com/css?family=Manrope' rel='stylesheet'>

        <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
        <script src="<c:url value="/resources/js/materialize.js"/>"></script>

        <title><spring:message code="navbar.companyname"/></title>
    </head>
    <body>
        <%@ include file="navbar.jsp"%>
        <%--div class="landing-title-container animate glow delay-1">
            <hr class="landing-separator">
            <h1 class="landing-page-title"><spring:message code="home.greetingmsg"/></h1>
            <hr class = "landing-separator">
            <div class="start-button">
                <a class="decision-button waves-effect waves-light btn standard-button" href="<c:url value="/explore"/>">
                    <spring:message code="home.start"/>
                </a>
            </div>
        </div--%>
        <div class="row animate glow delay-1 separate-50-top">
            <h1 class="text-center" style="margin-bottom:50px;"><spring:message code="landing.search.catchphrase"/></h1>
            <%@ include file="search_bar.jsp"%>
        </div>
        <div class="landing-recent-product-container animate glow delay-2">
            <c:if test="${recent.size() != 0}">
                <div class="row">
                    <div class="col s12">
                        <hr class="landing-separator">
                        <h4 class="landing-page-title"><spring:message code="landing.discoverproducts"/></h4>
                        <hr class = "landing-separator">
                    </div>
                </div>
                <div class="landing-products">
                    <c:forEach items="${recent}" var="product">
                        <div class="card product-card">
                            <a href="<c:url value="/product/${product.productId}"/>">
                                <div class="card-image">
                                    <c:if test="${product.image.id != 0}">
                                        <c:url value="/image/${product.image.id}" var="imageUrl"/>
                                        <img src="${imageUrl}">
                                    </c:if>
                                    <c:if test="${product.image.id == 0}">
                                        <img src="<c:url value="/resources/images/logo.png"/>">
                                    </c:if>
                                </div>
                            </a>
                            <div class="card-content">
                                <a href="<c:url value="/product/${product.productId}"/>" class="card-title product-card-title"><c:out value="${product.name}"/></a>
                                <div class="card-price">
                                    <spring:message code="explore.products.price"/><c:out value="${product.price}"/>
                                </div>
                                <div class="card-price">
                                    <i class="tiny material-icons">category</i>
                                    <c:forEach items="${categories}" var="category">
                                        <c:if test="${category.id == product.categoryId}">
                                            <spring:message code="${category.name}"/>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <div class="center">
                                    <c:set var="count" value="0"/>
                                    <c:forEach items="${product.tagList}" var="ecotag">
                                        <c:if test="${count == 2}">
                                            <br>
                                            <div class="yellow-card black-text chip ">
                                                <i class="tiny material-icons">more_horiz</i>
                                            </div>
                                        </c:if>
                                        <c:if test="${count lt 2}">
                                            <a class="${ecotag.color} white-text chip eco_chip" href="<c:url value="/explore?strings=${ecotag.id}&sort=${sort}&direction=${direction}"/>">
                                                <i class="tiny material-icons">${ecotag.icon}</i>
                                                <spring:message code="${ecotag.tag}"/>
                                            </a>
                                        </c:if>
                                        <c:set var="count" value="${count + 1}"/>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
        <%--div class="landing-categories-container">
            <c:forEach items="${productsPerCategory}" var="categoryList">
                <c:if test="${categoryList.size() != 0}">
                    <c:set var="counter" value="0"/>
                    <div class="category-container">
                        <c:forEach items="${categories}" var="category">
                            <c:if test="${category.id == categoryList.get(0).categoryId}">
                                <h4 class="center">
                                    <spring:message code="landingpage.category.title"/>
                                    <c:out value="${' '}"/>
                                    <spring:message code="${category.name}"/>
                                </h4>
                            </c:if>
                        </c:forEach>
                        <div class="landing-categories-container">
                            <c:forEach items="${categoryList}" var="product">
                                <div class="landing-categories-image-container">
                                    <c:if test="${product.image.id != 0 && counter < 4}">
                                        <c:set var="counter" value="${counter+1}"/>
                                        <a href="<c:url value="/product/${product.productId}"/>">
                                            <img src="<c:url value="/image/${product.image.id}"/>"/>
                                        </a>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div--%>
    </body>
</html>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.carousel');
        var instances = M.Carousel.init(elems, options);
    });
</script>