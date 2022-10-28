<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="navbar.companyname"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/materialize.css"/>" type="text/css"
          media="screen,projection">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" type="text/css"
          media="screen,projection">
    <link href='https://fonts.googleapis.com/css?family=Manrope' rel='stylesheet'>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>

<body>

<nav>
    <div class="nav-wrapper custom-nav">
        <a href="<c:url value="/"/>" class="brand-logo logo">
            <img src="<c:url value="/resources/images/logo.png"/>" height="50"
                 class="circle img-logo" alt="Logo">
            <span class="custom-title img-logo"><spring:message code="navbar.companyname"/></span>
        </a>
        <a href="#" class="sidenav-trigger" data-target="menu-responsive">
            <i class="material-icons">menu</i>
        </a>
        <ul id="nav-mobile" class="right nav-mobile hide-on-med-and-down">
            <li class="nav-li">
                <a href="<c:url value="/explore"/>"><spring:message code="navbar.explore"/></a>
            </li>
            <sec:authorize access="hasRole('SELLER')">
                <li class="nav-li">
                    <a href="<c:url value="/createProduct"/>"><spring:message code="navbar.create"/></a>
                </li>
            </sec:authorize>
            <sec:authorize access="hasRole('USER')">
                <li class="nav-li">
                    <a href="<c:url value="/newsFeed"/>"><spring:message code="navbar.news"/></a>
                </li>
            </sec:authorize>
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <li class="nav-li">
                    <a href="<c:url value="/login"/>"><spring:message code="navbar.login"/></a>
                </li>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <li class="nav-li">
                    <a href="<c:url value="/profile"/>">
                        <i class="material-icons">account_circle</i>
                        <%-- TODO span><c:out value="${pageContext.request.userPrincipal.name}"/></span --%>
                    </a>
                </li>
                <li class="nav-li">
                    <a href="<c:url value="/logout"/>">
                        <i class="material-icons">logout</i>
                    </a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>

<ul id="menu-responsive" class="sidenav">
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <li>
            <a href="<c:url value="/profile"/>">
                <i class="material-icons">account_circle</i>
            </a>
        </li>
    </c:if>
    <li>
        <a href="<c:url value="/explore"/>"><spring:message code="navbar.explore"/></a>
    </li>
    <sec:authorize access="hasRole('SELLER')">
        <li>
            <a href="<c:url value="/createProduct"/>"><spring:message code="navbar.create"/></a>
        </li>
    </sec:authorize>
    <c:if test="${pageContext.request.userPrincipal.name == null}">
        <li>
            <a href="<c:url value="/login"/>"><spring:message code="navbar.login"/></a>
        </li>
    </c:if>

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <li>
            <a href="<c:url value="/logout"/>">
                <i class="material-icons">logout</i>
            </a>
        </li>
    </c:if>
</ul>

<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded',function() {
        M.AutoInit();
    });
</script>
</body>
</html>
