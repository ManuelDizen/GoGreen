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
        <div style="margin:5vh auto; display:block;">
            <h4 style="margin:4vh auto; text-align: center; text-decoration: underline;">
                <spring:message code="registerseller.title"/>
            </h4>
        </div>
        <div class="registerbody">
            <div class="row">
                <div class="col s6">
                    <div class="input-field">
                        <form:input path="firstName" id="firstName" type="text"/>
                        <label for="firstName"><spring:message code="registerbuyer.form.name"/>
                            <spring:message code="forms.obligatorysign"/></label>
                    </div>
                    <div class="errors">
                        <form:errors path="firstName" element="p" cssClass="error"/>
                    </div>
                </div>
                <div class="col s6">
                    <div class="input-field">
                        <form:input path="surname" id="surname" type="text"/>
                        <label for="surname"><spring:message code="registerbuyer.form.surname"/>
                            <spring:message code="forms.obligatorysign"/></label>
                    </div>
                    <div class="errors">
                        <form:errors path="surname" element="p" cssClass="error"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col s6">
                    <div class="input-field">
                        <form:input path="email" id="email" type="text"/>
                        <label for="email"><spring:message code="registerbuyer.form.email"/>
                            <spring:message code="forms.obligatorysign"/></label>
                    </div>
                    <div class="errors">
                        <form:errors path="email" element="p" cssClass="error"/>
                    </div>
                </div>
                <div class="col s6 input-field">
                    <div>
                        <form:select path="area">
                            <form:option value="0" disabled="true"><spring:message code="explore.select"/></form:option>
                            <c:forEach items="${areas}" var="area">
                                <form:option value="${area.id}">${area.name}</form:option>
                            </c:forEach>
                        </form:select>
                        <form:label for="area" path="area"><spring:message code="registerseller.area"/></form:label>
                        <form:errors path="area" element="p" cssClass="error"/>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col s6">
                    <div class="input-field">
                        <form:input path="password" id="password" type="password"/>
                        <label for="password"><spring:message code="registerbuyer.form.password"/>
                            <spring:message code="forms.obligatorysign"/></label>
                    </div>
                    <div class="errors">
                        <form:errors path="password" element="p" cssClass="error"/>
                    </div>
                </div>
                <div class="col s6">
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
            <div class="row">
                <div class="col s6">
                    <div class="input-field">
                        <form:input path="address" id="address" type="text"/>
                        <label for="address"><spring:message code="registerbuyer.form.address"/>
                            <spring:message code="forms.obligatorysign"/></label>
                    </div>
                    <div class="errors">
                        <form:errors path="address" element="p" cssClass="error"/>
                    </div>
                </div>
                <div class="col s6">
                    <div class="input-field">
                        <form:input path="phone" id="phone" type="text"/>
                        <label for="phone"><spring:message code="registerbuyer.form.phone"/>
                            <spring:message code="forms.obligatorysign"/></label>
                    </div>
                    <div class="errors">
                        <form:errors path="phone" element="p" cssClass="error"/>
                    </div>
                </div>
            </div>


        </div>
        <div class="center" style="margin-top:3vh;">
            <button type="submit" class="waves-effect waves-light btn">
                <spring:message code="registerbuyer.form.submit"/>
            </button>
        </div>
    </form:form>
    </div>


</body>
</html>
