<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 28/8/22
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Footer</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/materialize.css"/>" type="text/css"
          media="screen,projection">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" type="text/css"
          media="screen,projection">
    <link href='https://fonts.googleapis.com/css?family=Manrope' rel='stylesheet'>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
<body>
    <footer class="page-footer custom-footer">
        <div class="container">
            <div class="container" style="display: flex; justify-content: space-around;">
                <a style="color:white" href="<c:url value="/faq"/>">
                    <spring:message code="footer.faq"/>
                </a>
            </div>
            <div class="footer-copyright">
                <spring:message code="footer.signature"/>
            </div>
        </div>
    </footer>
</body>
</html>
