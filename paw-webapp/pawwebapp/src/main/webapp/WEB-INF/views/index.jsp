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

        <title><spring:message code="navbar.companyname"/></title>
    </head>
    <body>
        <%@ include file="navbar.jsp"%>
        <div class="landing-title-container animate glow delay-1">
            <hr class="landing-separator">
            <h1 class="landing-page-title" style="margin-top: 15px; margin-bottom:15px;"><spring:message code="home.greetingmsg"/></h1>
            <hr class = "landing-separator">
            <div style="display:flex; justify-content:center; margin-top:1vh; width: 100%;">
                <a class="waves-effect waves-light btn standard-button" href="<c:url value="/login"/>">
                    <spring:message code="home.start"/>
                </a>
            </div>
        </div>
        <div class="landing-recent-product-container animate glow delay-2">
            <div class="row">
                <c:if test="${recent.size() != 0}">
                    <div class="col s12">
                        <h3 style="text-align: center;"><spring:message code="landing.discoverproducts"/></h3>
                    </div>
                    <c:forEach items="${recent}" var="product">
                        <div class="col s4">
                            <div class="card" style="margin:4vh auto;">
                                <div class="card-image">
                                    <c:if test="${product.imageId != 0}">
                                        <img style="border-radius: 10px 10px 0;" src="<c:url value="/image/${product.imageId}"/>">
                                    </c:if>
                                    <c:if test="${product.imageId == 0}">
                                        <img style="border-radius: 10px 10px 0;" src="<c:url value="/resources/images/logo.png"/>">
                                    </c:if>
                                    <a class="btn-floating halfway-fab waves-effect waves-light light-green"
                                       href="<c:url value="/product/${product.productId}"/>">
                                        <i class="material-icons">arrow_forward</i>
                                    </a>
                                </div>
                                <div class="card-content">
                                    <span class="card-title"><c:out value="${product.name}"/></span>
                                    <div><c:out value="${product.description}"/></div>
                                    <div style="margin-top: 2vh; margin-bottom: 8vh;">
                                        <spring:message code="explore.products.price"/><c:out value="${product.price}"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
        <div style="display:flex; justify-content:center; margin-top:1vh; margin-bottom:8vh; width: 100%;">
            <a class="waves-effect waves-light btn standard-button" href="<c:url value="/explore"/>">
                <spring:message code="landing.explore"/>
            </a>
        </div>
        <%@ include file="footer.jsp"%>
    </body>
</html>