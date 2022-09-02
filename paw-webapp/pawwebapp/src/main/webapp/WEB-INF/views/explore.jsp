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
    <div style="height:80%;">
        <c:forEach items="${products}" var="product">
            <div>
                <a href="<c:url value="/product/${product.productId}"/>">
                    <h4><c:out value="${product.name}"/></h4>
                </a>
            </div>
        </c:forEach>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
