<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 28/8/22
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Header</title>
        <link rel="stylesheet" href="<c:url value="/resources/css/materialize.css"/>" type="text/css"
              media="screen,projection">
        <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" type="text/css"
              media="screen,projection">
    </head>
    <body>
        <div class="navbar-fixed">
            <nav>
                <div class="nav-wrapper custom-nav">
                    <a href="#" class="left brand-logo" style="padding-left:2vw;">
                        <img src="<c:url value="/resources/images/logo.png"/>" height="50"
                             style="vertical-align: middle;" class="circle z-depth-2" alt="Logo">
                    </a>
                    <ul id="nav-mobile" class="right">
                        <li style="padding-right:2vw;"><a href="explore.jsp">Explorar</a></li>
                        <li style="padding-right:2vw;"><a href="FAQ.jsp">FAQ</a></li>
                        <li style="padding-right:5vw;"><a href="login.jsp">Iniciar Sesión/Registrarse</a></li>
                    </ul>
                </div>
            </nav>
        </div>
    </body>
</html>
