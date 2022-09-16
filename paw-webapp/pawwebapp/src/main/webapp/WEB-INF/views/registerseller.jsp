<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 10/9/22
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><spring:message code="registerseller.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="register-form-container">
    <c:url value="/registersellerprocess" var="postUrl"/>
    <form:form modelAttribute="sellerForm" method="post" action="${postUrl}" id="user_form">
        <div>
            <h4 style="margin:4vh auto;"><spring:message code="registerseller.title"/></h4>
        </div>
        <div class="register-form-row">
            <form:label style="color:white; width:fit-content;" path="firstName"><spring:message code="registerbuyer.form.name"/>
                <spring:message code="forms.obligatorysign"/></form:label>
            <form:input path="firstName" type="text" style="margin-bottom: 0; margin-left:4vw; align-self: end;"/>
        </div>
        <div class="errors">
            <form:errors path="firstName" element="p" cssClass="error"/>
        </div>
        <div class="register-form-row">
            <form:label style="color:white;" path="surname"><spring:message code="registerbuyer.form.surname"/>
                <spring:message code="forms.obligatorysign"/></form:label>
            <form:input path="surname" type="text" style="margin-bottom: 0; margin-left:4vw; align-self: end;"/>
        </div>
        <div class="errors">
            <form:errors path="surname" element="p" cssClass="error"/>
        </div>
        <div class="register-form-row">
            <form:label style="color:white;" path="email"><spring:message code="registerbuyer.form.email"/>
                <spring:message code="forms.obligatorysign"/></form:label>
            <form:input path="email" type="text" style="margin-bottom: 0; margin-left:4vw; align-self: end;"/>
        </div>
        <div class="errors">
            <form:errors path="email" element="p" cssClass="error"/>
        </div>
        <div class="register-form-row">
            <form:errors element="p" cssClass="error"/>
            <form:label style="color:white;" path="username"><spring:message code="registerbuyer.form.username"/>
                <spring:message code="forms.obligatorysign"/></form:label>
            <form:input path="username" type="text" style="margin-bottom: 0; margin-left:4vw; align-self: end;"/>
        </div>
        <div class="errors">
            <form:errors path="username" element="p" cssClass="error"/>
        </div>
        <div class="register-form-row">
            <form:label style="color:white;" path="password"><spring:message code="registerbuyer.form.password"/>
                <spring:message code="forms.obligatorysign"/></form:label>
            <form:input path="password" type="password" style="margin-bottom: 0; margin-left:4vw; align-self: end;"/>
        </div>
        <div class="errors">
            <form:errors path="password" element="p" cssClass="error"/>
        </div>

        <div class="register-form-row">
            <form:label path="address"><spring:message code="registerbuyer.form.address"/>
                <spring:message code="forms.obligatorysign"/></form:label>
            <form:input path="address" type="text"/>
        </div>
        <div class="errors">
            <form:errors path="address" element="p" cssClass="error"/>
        </div>

        <div>
            <form:label path="phone"><spring:message code="registerbuyer.form.phone"/>
                <spring:message code="forms.obligatorysign"/></form:label>
            <form:input path="phone" type="text"/>
        </div>
        <div class="errors">
            <form:errors path="phone" element="p" cssClass="error"/>
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
