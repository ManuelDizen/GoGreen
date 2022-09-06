<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 28/8/22
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Explore</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="explore-title"><spring:message code="explore.title"/></div>
    <div class="explore-container">
        <div class="explore-filter">
            <div class="explore-filter-title"><spring:message code="explore.filterby"/></div>
            <c:url value="/explore" var="explore"/>
            <form action="${explore}" method="get" id="filter_form">
                <table>
                    <tr>
                        <!--<td><label path="name">Name</label></td>-->
                        <td><spring:message code="explore.filterform.name"/></td>
                        <td><input name="name" type="text"/></td>
                    </tr>
                    <tr>
                        <td><spring:message code="explore.filterform.category"/></td>
                        <td><input name="category" type="text"/><td>
                    </tr>
                    <tr>
                        <!--<td><label path="price">Max price</label></td>-->
                        <td><spring:message code="explore.filterform.maxprice"/></td>
                        <td><input name="maxPrice" type="number"/></td>
                    </tr>
                    <tr>
                        <td colspan="2"><button type="submit"><spring:message code="explore.filterform.submit"/></button></td>
                    </tr>
                </table>
            </form>

        </div>
        <div class="explore-products">
            <c:forEach items="${products}" var="product">
                <div class="card-container">
                    <div class="product-card-title">${product.name}</div>
                    <div class="description-container">${product.description}</div>
                    <div class="product-card-link-container">
                        <span><spring:message code="explore.products.price"/>${product.price}</span>
                        <a href="<c:url value="/product/${product.productId}"/>">
                            <spring:message code="explore.products.more"/>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
