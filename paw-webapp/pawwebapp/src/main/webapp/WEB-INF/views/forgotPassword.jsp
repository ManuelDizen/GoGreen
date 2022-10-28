<%--
  Created by IntelliJ IDEA.
  User: Franco De Simone
  Date: 28/10/2022
  Time: 09:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><spring:message code="navbar.companyname"/> - <spring:message code="forgotpassword.title"/><c:out value="${' '}${product.name}"/></title>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container" style="margin-top: 20px">
    <c:url value="/updatePassword" var="postUrl"/>
    <form:form modelAttribute="passwordForm" method="post" action="${postUrl}" id="password_form" enctype="multipart/form-data">
        <div class="update-product-body">
            <div class="row">
                <div class="col s12 input-field">
                    <form:input path="email" id="email" type="text"/>
                    <label for="email"><spring:message code="registerbuyer.form.email"/>
                        <spring:message code="forms.obligatorysign"/></label>
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
