<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 28/8/22
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
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
        <div class="navbar">
            <nav class="custom-nav">
                <div class="nav-wrapper">
                    <a href="<c:url value="/"/>" class="brand-logo" style="margin-left:4vw;">
                        <img src="<c:url value="/resources/images/logo.png"/>" height="50"
                             style="vertical-align: middle;" class="circle" alt="Logo">
                        <span class="custom-title"><spring:message code="navbar.companyname"/></span>
                    </a>
                    <a href="#" class="sidenav-trigger" data-target="menu-responsive">
                        <i class="material-icons">menu</i>
                    </a>
                    <ul id="nav-mobile" class="right hide-on-med-and-down">
                        <li style="padding-right:2vw; display:flex; flex-direction: column; justify-content: space-around; height:100%;">
                            <a href="<c:url value="/explore"/>"><spring:message code="navbar.explore"/></a>
                        </li>
                        <sec:authorize access="hasRole('SELLER')">
                            <li style="padding-right:2vw; display:flex; flex-direction: column; justify-content: space-around; height:100%;">
                                <a href="<c:url value="/createProduct"/>"><spring:message code="navbar.create"/></a>
                            </li>
                        </sec:authorize>
                        <c:if test="${pageContext.request.userPrincipal.name == null}">
                            <li style="padding-right:2vw; display:flex; flex-direction: column; justify-content: space-around; height:100%;">
                                <a href="<c:url value="/login"/>"><spring:message code="navbar.login"/></a>
                            </li>
                        </c:if>

                        <c:if test="${pageContext.request.userPrincipal.name != null}">
                            <li style="padding-right: 2vw; display:flex; flex-direction: column; justify-content: space-around; height:100%;">
                                <a href="<c:url value="/profile"/>">
                                    <i class="material-icons">account_circle</i>
                                </a>
                            </li>
                            <li style="padding-right: 2vw; display:flex; flex-direction: column; justify-content: space-around; height:100%;">
                                <a href="<c:url value="/logout"/>">
                                    <button type="submit" class="waves-effect waves-light btn">
                                        <spring:message code="logout"/>
                                    </button>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </nav>
        </div>
        <ul id="menu-responsive" class="sidenav">
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
                    <a href="<c:url value="/profile"/>">
                        <i class="material-icons">account_circle</i>
                    </a>
                </li>
                <li>
                    <a href="<c:url value="/logout"/>">
                        <button type="submit" class="waves-effect waves-light btn">
                            <spring:message code="logout"/>
                        </button>
                    </a>
                </li>
            </c:if>
        </ul>

        <!-- Compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <script>
            document.addEventListener('DOMContentLoaded',function() {
                M.AutoInit();
            });
        </script>
    </body>
</html>
