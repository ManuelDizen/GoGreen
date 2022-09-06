<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <head>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link rel="stylesheet" href="<c:url value="/resources/css/materialize.css"/>" type="text/css"
              media="screen,projection">
        <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" type="text/css"
              media="screen,projection">
        <link href='https://fonts.googleapis.com/css?family=Manrope' rel='stylesheet'>

        <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>

        <title>GoGreen</title>
    </head>
    <body>
        <%@ include file="navbar.jsp"%>
        <div class="row">
            <div class="column column1  animate glow delay-1">
                <div class="landing-title-container">
                    <hr class="landing-separator">
                    <span class="landing-page-title"><spring:message code="home.greetingmsg"/></span>
                    <hr class = "landing-separator">
                </div>
                <div style="display:flex; justify-content:center; margin-top:3vh;">
                    <a class="waves-effect waves-light btn standard-button" href="
                        <c:url value="explore"/>"><spring:message code="home.start"/></a>
                </div>
            </div>
            <div class="column column2 animate glow delay-2">
                <img style="width:60%; position:relative;" src="<c:url value="/resources/images/imagenLanding.png"/>" alt="Sustainability for all!">
            </div>
            <span class="stretch"></span>
        </div>
        <div class="introduction-container animate glow delay-3">
            <div class="introduction-box">
                <hr class="landing-separator">
                <div class="introduction-title"><spring:message code="home.introduction.title"/></div>
                <hr class="landing-separator" style="margin-bottom: 5vh;">
                <div class="introduction-body"><spring:message code="home.introduction.body"/></div>
            </div>
        </div>
        <%@ include file="footer.jsp"%>
    </body>
</html>