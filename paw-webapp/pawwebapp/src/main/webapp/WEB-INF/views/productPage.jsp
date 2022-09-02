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
    <div style="height:5vh; width:100%;"></div>
    <div class="product-page-container" style="height:available;">
        <div class="product-info-container">
            <h2><c:out value="${product.name}"/></h2>
            <span><c:out value="${product.description}"/></span>
            <div style="height:2vh; width:100%;"></div>
            <h4>Datos del vendedor:</h4>
            <div class="seller-details-container">

            </div>
        </div>
        <div class="product-info-container"></div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
