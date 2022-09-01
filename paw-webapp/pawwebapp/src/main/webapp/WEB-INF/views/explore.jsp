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
    <%@ include file="header.jsp"%>
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
        <div class="explore-products"></div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
