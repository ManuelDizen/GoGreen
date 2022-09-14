<%--faqs
  Created by IntelliJ IDEA.
  User: manuel
  Date: 28/8/22
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>FAQ</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/materialize.css"/>" type="text/css" media="screen,projection">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" type="text/css" media="screen,projection">
    <link href='https://fonts.googleapis.com/css?family=Manrope' rel='stylesheet'>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js"></script>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <h3>FAQs</h3>
    <div style="height: 80%;">
        <c:forEach items="${faqs}" var="faq">
            <tr>
                <ul id="dropdown" class="dropdown-content">
                    <li><a href="#"><td>${faq.answer}</td></a></li>
                    <li class="divider"></li>
                </ul>
                <a class="btn dropdown-button" href="#" data-activates="dropdown" style="width: 100%;"><td>${faq.question}</td><i class="mdi-navigation-arrow-drop-down right"></i></a>
            </tr>
        </c:forEach>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
