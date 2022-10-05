<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><spring:message code="updateProducts.title"/><c:out value="${' '}${product.name}"/></title>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="container" style="margin-top: 20px">
        <c:url value="/updateProduct/${product.productId}" var="postUrl"/>
        <form:form modelAttribute="updateProdForm" method="post" action="${postUrl}" id="product_form" enctype="multipart/form-data">
            <div class="center update-margins">
                <h5><spring:message code="updateProducts.title"/><c:out value="${': '}${product.name}"/></h5>
            </div>
            <div class="update-product-body">
                <div class="row">
                    <div class="col s12 input-field light-input">
                        <form:label path="newStock"><spring:message code="createproduct.form.stock"/>
                            <spring:message code="forms.obligatorysign"/></form:label>
                        <form:input path="newStock" type="number" value="${product.stock}" cssClass="light-input"/>
                        <form:errors path="newStock" element="p" cssClass="error"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12 input-field light-input">
                        <form:label path="newPrice"><spring:message code="createproduct.form.price"/>
                            <spring:message code="forms.obligatorysign"/></form:label>
                        <form:input path="newPrice" type="number" value="${product.price}" cssClass="light-input"/>
                        <form:errors path="newPrice" element="p" cssClass="error"/>
                    </div>
                </div>
            </div>
            <div class="center separated-button">
                <button type="submit" class="decision-button waves-effect waves-light btn publish-button">
                    <spring:message code="updateproduct.submit"/>
                </button>
            </div>
        </form:form>
    </div>
</body>
</html>
