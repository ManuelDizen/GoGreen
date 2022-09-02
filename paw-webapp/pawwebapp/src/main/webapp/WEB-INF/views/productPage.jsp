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
<!-- TODO: La data de seller está mezclada, en algún lado hice lio. La trae bien pero no
se en que parte mezclé el orden-->
    <%@ include file="header.jsp"%>
    <div style="height:5vh; width:100%;"></div>
    <div class="product-page-container" style="height:available;">
        <div class="product-info-container">
            <h2><c:out value="${product.name}"/></h2>
            <span><c:out value="${product.description}"/></span>
            <div style="height:2vh; width:100%;"></div>
            <h4>Datos del vendedor:</h4>
            <div class="seller-details-container">
                <div style="height:fit-content;">
                    <span>Nombre del vendedor:</span>
                    <span><c:out value="${seller.name}"/></span>
                </div>
                <div style="height:fit-content;">
                    <span>Mail del vendedor:</span>
                    <span><c:out value="${seller.mail}"/></span>
                </div>
                <div style="height:fit-content;">
                    <span>Dirección del vendedor:</span>
                    <span><c:out value="${seller.address}"/></span>
                </div>
                <div style="height:fit-content;">
                    <span>Teléfono del vendedor:</span>
                    <span><c:out value="${seller.phone}"/></span>
                </div>
            </div>
        </div>
        <div class="product-info-container"></div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
