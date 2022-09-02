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
    <div class="explore-container">
        <div class="explore-filter">
            <div class="explore-filter-title">Filtros</div>
            <div class="explore-filter-filters">
                <span>Nombre</span>
                <div class="name-input"><input type="text"></div>
                <span>Precio máximo</span>
                <input type="range">
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

            </div>
        </div>
        <div class="explore-products">
            <c:set var="count" value="1"/>
            <c:forEach items="${products}" var="product" step="1" begin="0">
                <c:if test="${count==5}">
                    <c:set var="count" value="1"/>
                </c:if>
                <div id="col${count}">
                    <div class="card small">
                        <div class="card blue-grey darken-1">
                            <div class="card-content">
                                <span class="card-title">${product.name}</span>
                                <p>${product.description}</p>
                                <p>${product.price}</p>
                            </div>
                            <div class="card-action">
                                <a href="<c:url value="/product/${product.productId}"/>">Ver más</a>
                            </div>
                        </div>
                    </div>
                </div>
                <c:set var="count" value="${count+1}"/>
            </c:forEach>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
