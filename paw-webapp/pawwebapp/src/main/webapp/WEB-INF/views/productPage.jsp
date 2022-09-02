<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 1/9/22
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>Product</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
</head>
<body>
    <%@ include file="header.jsp"%>
    <div class="product-page-container" style="height:100%;">
        <div class="product-info-container">
            <c:out value="${product.description}"/>
        </div>
        <div class="product-info-container"></div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
