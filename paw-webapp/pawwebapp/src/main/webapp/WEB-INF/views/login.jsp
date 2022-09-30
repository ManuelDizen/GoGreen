<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 28/8/22
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><spring:message code="login.title"/></title>
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
                    <div class="login-container" style="text-align: center">
                        <h3><spring:message code="loginpage.title"/></h3>
                    </div>
                    <div class="input-field col s12">
                        <label><spring:message code="loginpage.email"/></label>
                        <input type="email" name="email" class="validate" id="email_input"/>
                    </div>
                    <div class="input-field col s12">
                        <label><spring:message code="loginpage.password"/></label>
                        <input type="password" name="password" id="password_input" class="validate"/>
                    </div>
                    <div class="col s12" style="align-content: center;" id="rememberme">
                        <label>
                            <input type="checkbox" name="remember-me">
                            <span><spring:message code="loginpage.rememberme"/></span>
                        </label>
                    </div>
                    <div class="col s12" style="padding-top: 1em; padding-bottom: 1em; text-align: right;">
                        <span><spring:message code="loginpage.redirectregister"/></span>
                        <a href="<c:url value="/register"/>">
                            <span><spring:message code="loginpage.registerbutton"/></span>
                        </a>
                    </div>
                    <div class="col s12 center">
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

    <%--div class="login-page-container">
        <c:if test="${param.failure}">
            <div class="error" style="font-size:20px; width:60%; margin: 5vh auto; text-align:center;">
                <spring:message code="loginpage.error"/>
            </div>
        </c:if>
        <div style="height:fit-content;" class="login-form-container">
            <c:url value="/login" var="postUrl"/>
            <form method="post" action="${postUrl}" id="login_form">
                <div class="login-container">
                    <h1><spring:message code="loginpage.title"/></h1>
                </div>
                <div>
                    <label style="color:white;"><spring:message code="loginpage.email"/></label>
                </div>
                <div>
                    <input type="text" name="email" style="margin-bottom: 0; margin-left:4vw; width:60%; align-self: end;"/>
                </div>
                <div>
                    <label><spring:message code="loginpage.password"/></label>
                </div>
                <div>
                    <input type="password" name="password" style="margin-bottom: 0; margin-left:4vw; width:60%;align-self:end;"/>
                </div>
                <div style="display:flex; align-content: center;" id="login-checkbox">
                    <label>
                        <input name="remember-me" type="checkbox" class="filled-in" checked="checked"/>
                        <span><spring:message code="loginpage.rememberme"/></span>
                    </label>
                </div>
                <div>
                    <button type="submit" class="waves-effect waves-light btn">
                        <spring:message code="loginpage.submit"/>
                    </button>
                </div>
            </form>
        </div>
        <div class="login-register-container" style="margin-top:10px;">
            <div><spring:message code="loginpage.redirectregister"/></div>
            <div>
                <a href="<c:url value="/register"/>">
                    <button class="waves-effect waves-light btn">
                        <spring:message code="loginpage.registerbutton"/>
                    </button>
                </a>
            </div>
        </div>
    </div--%>

</body>
</html>
