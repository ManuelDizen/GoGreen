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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><c:out value="${product.name}"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div style="height:5vh; width:100%;"></div>
    <c:if test="${formSuccess}">
        <div class="order-success">
            <spring:message code="productpage.orderconfirm"/>
        </div>
    </c:if>
    <c:if test="${formFailure}">
        <div class="order-failure">
            <spring:message code="productpage.orderfail"/>
        </div>
    </c:if>
    <div class="product-page-container" style="height:available;">
        <div class="product-info-container">
            <h4><c:out value="${product.name}"/></h4>
            <div class = "productpage-image-container">
            <img src="<c:url value="/image/${product.imageId}"/>" alt="${product.name}">
            </div>
            <div><c:out value="${product.description}"/></div>
            <div><spring:message code="productpage.prodinfo.price"/><c:out value="${product.price}"/></div>
            <div style="height:2vh; width:100%;"></div>
            <h4><spring:message code="productpage.prodinfo.sellerdatatitle"/></h4>
            <div class="seller-details-container">
                <div style="height:fit-content;">
                    <span><spring:message code="productpage.prodinfo.selleraddress"/></span>
                    <span><c:out value="${seller.address}"/></span>
                </div>
                <div style="height:fit-content;">
                    <span><spring:message code="productpage.prodinfo.sellerphone"/></span>
                    <span><c:out value="${seller.phone}"/></span>
                </div>
            </div>
        </div>
        <div class="product-info-container">
            <sec:authorize access="hasRole('USER')">
                <h4><spring:message code="productpage.orderform.title"/></h4>
                <c:url value="/process/${product.productId}" var="process"/>
                <form:form modelAttribute="orderForm" action="${process}" method="post">
                    <table>
                        <tr>
                            <td><form:label path="message"><spring:message code="productpage.orderform.msgToSeller"/></form:label></td>
                            <td><form:textarea path="message"/></td>
                        </tr>
                        <tr>
                            <td><form:label path="amount"><spring:message code="productpage.orderform.amount"/></form:label></td>
                            <td><form:input path="amount"/></td>
                        </tr>
                        <tr>
                            <td colspan="2"><form:errors path="amount" cssClass="error" element="p"/></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <button type="submit" class="waves-effect waves-light btn">
                                    <spring:message code="productpage.orderform.submit"/>
                                </button>
                            </td>
                        </tr>
                    </table>
                </form:form>
            </sec:authorize>
            <sec:authorize access="hasRole('SELLER')">
                <h4><spring:message code="productpage.orderform.loggedasseller"/></h4>
            </sec:authorize>
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <h4 style="margin: 20px auto auto;">
                    <spring:message code="productpage.orderform.notauthenticated"/>
                </h4>
            </c:if>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
