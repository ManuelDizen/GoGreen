<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><spring:message code="navbar.companyname"/> - <spring:message code="login.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/materialize.css"/>" type="text/css"
          media="screen,projection">
    <script src="<c:url value="/resources/js/materialize.js"/>"></script>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="container">
        <c:url value="/login" var="postUrl"/>
        <div class="login">
            <div class="row">
                <form method="post" action="${postUrl}" id="login_form">
                    <div class="login-container">
                        <h3><spring:message code="loginpage.title"/></h3>
                    </div>
                    <div class="input-field col s12">
                        <label for="email_input"><spring:message code="loginpage.email"/></label>
                        <input type="email" name="email" class="validate" id="email_input"/>
                    </div>
                    <div class="input-field col s12">
                        <label for="password_input"><spring:message code="loginpage.password"/></label>
                        <input type="password" name="password" id="password_input" class="validate"/>
                    </div>
                    <div class="col s12 remember-me" id="rememberme">
                        <label>
                            <input type="checkbox" name="remember-me">
                            <span><spring:message code="loginpage.rememberme"/></span>
                        </label>
                    </div>
                    <div class="col s12 redirect-register">
                        <span><spring:message code="loginpage.redirectregister"/></span>
                        <a href="<c:url value="/register"/>">
                            <span><spring:message code="loginpage.registerbutton"/></span>
                        </a>
                    </div>
                    <div class="col s12 redirect-register">
                        <a href="<c:url value="/forgotPassword"/>">
                            <span>Olviaste tu contraseña?</span>
                        </a>
                    </div>
                    <div class="col s12 center login-submit">
                        <button type="submit" class="waves-effect waves-light btn decision-button">
                            <spring:message code="loginpage.submit"/>
                        </button>
                    </div>
                </form>
                <c:if test="${param.failure}">
                    <div class="error">
                        <spring:message code="loginpage.error"/>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
