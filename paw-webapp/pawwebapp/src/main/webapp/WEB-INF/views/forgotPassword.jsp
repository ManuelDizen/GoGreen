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
    <title><spring:message code="navbar.companyname"/> - <spring:message code="forgotpassword.title"/></title>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="update-form-container container" style="margin-top: 20px">
    <div class="update"></div>
    <c:url value="/updatePassword" var="postUrl"/>
    <form:form modelAttribute="passwordForm" method="post" action="${postUrl}" id="password_form" enctype="multipart/form-data">
        <div class="center update-margins">
            <h5><spring:message code="forgotpassword.title"/></h5>
        </div>
        <div class="center update-margins">
            <p><spring:message code="password.instructions"/></p>
        </div>
        <c:if test="${notFound}">
            <div class="center update-margins">
                <p class="red error-sign">
                    <spring:message code="password.noEmail"/>
                </p>
            </div>
        </c:if>
        <div class="update-product-body">
            <div class="row">
                <div class="col s12">
                    <div class="input-field">
                        <form:input path="email" id="email" type="text"/>
                        <label for="email"><spring:message code="registerbuyer.form.email"/>
                            <spring:message code="forms.obligatorysign"/>
                        </label>
                    </div>
                    <div class="errors">
                        <form:errors path="email" element="p" cssClass="error"/>
                    </div>
            </div>
        </div>
        <div class="center separated-button">
            <button type="submit" class="decision-button waves-effect waves-light btn publish-button">
                <spring:message code="password.send"/>
            </button>
        </div>
    </form:form>
</div>
</body>
</html>
