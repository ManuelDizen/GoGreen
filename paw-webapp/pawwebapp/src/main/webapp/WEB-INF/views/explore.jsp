<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 28/8/22
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>Explore</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="explore-title">Productos</div>
    <div class="explore-container">
        <div class="explore-filter">
            <div class="explore-filter-title">Filtrar por</div>
            <!--<div class="explore-filter-filters">
                <span>Nombre</span>
                <div class="name-input">
                    <input type="text">
                </div>
                <span>Precio máximo</span>
                <div class="price-input">
                    <input type="text">
                </div>
                <span>Categoría</span>
                <div>
                    <label>
                        <input type="checkbox" name="Higiene">
                    </label>
                </div>
                <div>
                    <label>
                        <input type="checkbox" name="Higiene">
                    </label>
                </div>
                <div>
                    <label>
                        <input type="checkbox" name="Higiene">
                    </label>
                </div>
            </div>-->
            <c:url value="/filter" var="filter"/>

        </div>
        <div class="explore-products">
            <c:forEach items="${products}" var="product">
                <div class="card-container">
                    <div class="product-card-title">${product.name}</div>
                    <div class="description-container">${product.description}</div>
                    <div class="product-card-link-container">
                        <span>Precio: $${product.price}</span>
                        <a href="<c:url value="/product/${product.productId}"/>">Ver más</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
