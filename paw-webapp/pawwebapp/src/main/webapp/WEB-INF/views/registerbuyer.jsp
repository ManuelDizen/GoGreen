<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 10/9/22
  Time: 09:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="registerbuyer.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="register-form-container">
        <c:url value="/registerbuyerprocess" var="postUrl"/>
        <form:form modelAttribute="userForm" method="post" action="${postUrl}" id="user_form">
            <div style="margin:5vh auto; display:block;">
                <h4 style="margin:4vh auto; text-align: center; text-decoration: underline;">
                    <spring:message code="registerbuyer.title"/>
                </h4>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <form:input path="firstName" id="firstName" type="text"/>
                    <label for="firstName"><spring:message code="registerbuyer.form.name"/>
                        <spring:message code="forms.obligatorysign"/></label>
                </div>
            </div>
            <div class="errors">
                <form:errors path="firstName" element="p" cssClass="error"/>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <form:input path="surname" id="surname" type="text"/>
                    <label for="surname"><spring:message code="registerbuyer.form.surname"/>
                        <spring:message code="forms.obligatorysign"/></label>
                </div>
            </div>
            <div class="errors">
                <form:errors path="surname" element="p" cssClass="error"/>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <form:input path="email" id="email" type="text"/>
                    <label for="email"><spring:message code="registerbuyer.form.email"/>
                        <spring:message code="forms.obligatorysign"/></label>
                </div>
            </div>
            <div class="errors">
                <form:errors path="email" element="p" cssClass="error"/>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <form:input path="password" id="pasword" type="password"/>
                    <label for="password"><spring:message code="registerbuyer.form.password"/>
                        <spring:message code="forms.obligatorysign"/></label>
                </div>
            </div>
            <div class="errors">
                <form:errors path="password" element="p" cssClass="error"/>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <form:input path="confirmationPassword" id="confirmationPassword" type="password"/>
                    <label for="password"><spring:message code="registerbuyer.form.confirmpassword"/>
                        <spring:message code="forms.obligatorysign"/></label>
                </div>
            </div>
            <div class="errors">
                <form:errors path="confirmationPassword" element="p" cssClass="error"/>
            </div>
            <div style="display:flex;">
                <button type="submit" class="waves-effect waves-light btn">
                    <spring:message code="registerbuyer.form.submit"/>
                </button>
            </div>
        </form:form>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
