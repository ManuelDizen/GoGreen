<%--
  Created by IntelliJ IDEA.
  User: Franco De Simone
  Date: 9/9/2022
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Error!</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div style="height:80%;" class="container">
    <h4><c:out value="Error ${error}"/></h4>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>


</body>
</html>
