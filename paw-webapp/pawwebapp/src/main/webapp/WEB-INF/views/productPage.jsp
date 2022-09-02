<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 1/9/22
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><c:out value="${product.name}"/></title>
    <link rel="icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div style="height:5vh; width:100%;"></div>
    <div class="product-page-container" style="height:available;">
        <div class="product-info-container">
            <h4><c:out value="${product.name}"/></h4>
            <div><c:out value="${product.description}"/></div>
            <div>Precio: <c:out value="$${product.price}"/></div>
            <div style="height:2vh; width:100%;"></div>
            <h4>Datos del vendedor:</h4>
            <div class="seller-details-container">
                <div style="height:fit-content; margin:0; padding: 1px;">
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
        <div class="product-info-container">
            <h4>Para comprar, por favor llenar los siguientes campos:</h4>
            <c:url value="/process/${product.productId}" var="process"/>
            <form:form modelAttribute="orderForm" action="${process}" method="post">
                <table>
                    <tr style="margin:0px; padding:1px;">
                        <td><form:label path="name">Name</form:label></td>
                        <td><form:input path="name"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="mail">Mail</form:label></td>
                        <td><form:input type="email" path="mail"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="phone">Phone</form:label></td>
                        <td><form:input path="phone"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="message">Mensaje para vendedor:</form:label></td>
                        <td><form:textarea path="message"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="amount">Cantidad:</form:label></td>
                        <td><form:input path="amount"/></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Submit"/></td>
                    </tr>
                </table>
            </form:form>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
