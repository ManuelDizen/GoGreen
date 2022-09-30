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
        <div class="landing-title-container animate glow delay-1">
            <hr class="landing-separator">
            <h1 class="landing-page-title" style="margin-top: 15px; margin-bottom:15px;"><spring:message code="home.greetingmsg"/></h1>
            <hr class = "landing-separator">
            <div style="display:flex; justify-content:center; margin-top:1vh; width: 100%;">
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <a class="decision-button waves-effect waves-light btn standard-button" href="<c:url value="/explore"/>">
                        <spring:message code="landing.discover"/>
                    </a>
                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <a class="decision-button waves-effect waves-light btn standard-button" href="<c:url value="/login"/>">
                        <spring:message code="home.start"/>
                    </a>
                </c:if>
            </div>
        </div>
        <div class="landing-recent-product-container animate glow delay-2" style="margin-top:10vh;">
            <c:if test="${recent.size() != 0}">
                <div class="row">
                    <div class="col s12">
                        <hr class="landing-separator">
                        <h4 class="landing-page-title" style="margin-top: 15px; margin-bottom:15px;"><spring:message code="landing.discoverproducts"/></h4>
                        <hr class = "landing-separator">
                    </div>
                </div>
                <div class="landing-products">
                    <c:forEach items="${recent}" var="product">
                        <div class="card product-card" style="margin:10px auto;">
                            <div class="card-image">
                                <c:if test="${product.imageId != 0}">
                                    <img class="activator" style="border-radius: 10px 10px 0 0;" src="<c:url value="/image/${product.imageId}"/>">
                                </c:if>
                                <c:if test="${product.imageId == 0}">
                                    <img class="activator" style="border-radius: 10px 10px 0 0;" src="<c:url value="/resources/images/logo.png"/>">
                                </c:if>
                            </div>
                            <div class="card-content">
                                <span class="card-title product-card-title activator"><c:out value="${product.name}"/></span>
                                <div class="activator" style="margin-top: 2vh; margin-bottom: 2vh;">
                                    <spring:message code="explore.products.price"/><c:out value="${product.price}"/>
                                </div>
                                <div style="margin-top: 3vh; margin-bottom: 1vh;">
                                    <c:set var="count" value="0"/>
                                    <c:forEach items="${product.tagList}" var="ecotag">
                                        <c:if test="${count lt 2}">
                                            <a class="${ecotag.color} white-text chip" href="<c:url value="/explore?strings=${ecotag.id}&sort=${sort}&direction=${direction}"/>"/>
                                            <i class="tiny material-icons">${ecotag.icon}</i>
                                            <spring:message code="${ecotag.tag}"/>
                                            </a>
                                            <c:set var="count" value="${count + 1}"/>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <div class="submit-button">
                                    <a class="waves-effect waves-light btn standard-button"
                                       href="<c:url value="/product/${product.productId}"/>"
                                       style="text-align: center">
                                        <spring:message code="explore.product.goto"/>
                                    </a>
                                </div>
                            </div>
                            <div class="card-reveal" style="background-color: #1b5e20; color: #ADE28A;">
                                <span class="card-title product-card-title"><i class="material-icons right">close</i></span>
                                <div class="two-line">
                                    <span class="card-title product-card-title"><c:out value="${product.name}"/></span>
                                    <span class="card-title product-card-title"><c:out value="$${product.price}"/></span>
                                </div>
                                <p><c:out value="${product.description}"/></p>
                                <c:forEach items="${product.tagList}" var="ecotag">
                                    <a class="${ecotag.color} white-text chip" href="<c:url value="/explore?name=&maxPrice=&${ecotag.path}=on"/>"/>
                                    <i class="tiny material-icons">${ecotag.icon}</i>
                                    <spring:message code="${ecotag.tag}"/>
                                    </a>
                                    <br>
                                </c:forEach>
                                <div class="submit-button">
                                    <a class="waves-effect waves-light btn standard-button"
                                       href="<c:url value="/product/${product.productId}"/>"
                                       style="text-align: center">
                                        <spring:message code="explore.product.goto"/>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="row">
                    <div class="animate glow delay-2"
                         style="display:flex; justify-content:center; margin-top:1vh; margin-bottom:8vh; width: 100%;">
                        <a class="waves-effect waves-light btn standard-button" href="<c:url value="/explore"/>">
                            <spring:message code="landing.explore"/>
                        </a>
                    </div>
                </div>
            </c:if>
        </div>
        <%@ include file="footer.jsp"%>
    </body>
</html>