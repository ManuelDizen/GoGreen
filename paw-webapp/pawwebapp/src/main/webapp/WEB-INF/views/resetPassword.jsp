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
    <c:url value="/confirmPassword" var="postUrl"/>
    <form:form modelAttribute="updatePasswordForm" method="post" action="${postUrl}" id="update_password_form" enctype="multipart/form-data">
        <div class="password-body">
            <div class="row">
                <div class="col s12" style="height:fit-content;">
                    <div class="input-field center ">
                        <form:input path="password" id="password" type="password"/>
                        <spring:message var="passCond" code="registerbuyer.form.passwordCondition"/>
                        <label for="password"><spring:message code="password.newPassword"/>
                            <spring:message code="forms.obligatorysign"/>
                            <c:out value="${' ('}${passCond}${') '}"/></label>
                    </div>
                    <div class="errors" style="height:fit-content;">
                        <form:errors path="password" element="p" cssClass="error"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col s12">
                    <div class="input-field">
                        <form:input path="confirmationPassword" id="confirmationPassword" type="password"/>
                        <label for="password"><spring:message code="registerbuyer.form.confirmpassword"/>
                            <spring:message code="forms.obligatorysign"/></label>
                    </div>
                    <div class="errors">
                        <form:errors path="confirmationPassword" element="p" cssClass="error"/>
                    </div>
                </div>
            </div>
            <div style="display: none">
                <label for="token">
                    <spring:message code="forms.obligatorysign"/></label>
                <form:input path="token" id="token" type="text"/>
                <form:errors path="token" cssClass="error" element="p"/>
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
