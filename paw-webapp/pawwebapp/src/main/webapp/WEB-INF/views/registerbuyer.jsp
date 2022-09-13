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
    <title>Title</title>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="register-buyer-form-container">
        <c:url value="/registerbuyerprocess" var="postUrl"/>
        <form:form modelAttribute="userForm" method="post" action="${postUrl}" id="user_form">
            <div>
                <h4><spring:message code="registerbuyer.title"/></h4>
            </div>
            <div>
                <form:errors path="firstName" element="p" cssClass="error"/>
                <form:label path="firstName"><spring:message code="registerbuyer.form.name"/>
                <spring:message code="forms.obligatorysign"/></form:label>
                <form:input path="firstName" type="text"/>
            </div>
            <div>
                <form:errors path="surname" element="p" cssClass="error"/>
                <form:label path="surname"><spring:message code="registerbuyer.form.surname"/>
                    <spring:message code="forms.obligatorysign"/></form:label>
                <form:input path="surname" type="text"/>
            </div>
            <div>
                <form:errors path="email" element="p" cssClass="error"/>
                <form:label path="email"><spring:message code="registerbuyer.form.email"/>
                    <spring:message code="forms.obligatorysign"/></form:label>
                <form:input path="email" type="text"/>
            </div>
            <div>
                <form:errors path="username" element="p" cssClass="error"/>
                <form:label path="username"><spring:message code="registerbuyer.form.username"/>
                    <spring:message code="forms.obligatorysign"/></form:label>
                <form:input path="username" type="text"/>
            </div>
            <div>
                <form:errors path="password" element="p" cssClass="error"/>
                <form:label path="password"><spring:message code="registerbuyer.form.password"/>
                    <spring:message code="forms.obligatorysign"/></form:label>
                <form:input path="password" type="password"/>
            </div>
            <div>
                <button type="submit" class="waves-effect waves-light btn">
                    <spring:message code="registerbuyer.form.submit"/>
                </button>
            </div>
        </form:form>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
