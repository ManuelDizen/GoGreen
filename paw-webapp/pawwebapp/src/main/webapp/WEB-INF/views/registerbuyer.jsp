<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><spring:message code="navbar.companyname"/> - <spring:message code="registerbuyer.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="register-form-container">
        <c:url value="/registerbuyerprocess" var="postUrl"/>
        <form:form modelAttribute="userForm" method="post" action="${postUrl}" id="user_form">
            <div class="title-block">
                <h4 class="registerbuyer-title">
                    <spring:message code="registerbuyer.title"/>
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
                    <div class="col s12">
                        <div class="input-field">
                            <form:input path="email" id="email" type="text"/>
                            <label for="email"><spring:message code="registerbuyer.form.email"/>
                                <spring:message code="forms.obligatorysign"/></label>
                        </div>
                        <div class="errors">
                            <form:errors path="email" element="p" cssClass="error"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col s12" style="height:fit-content;">
                        <div class="input-field">
                            <form:input path="password" id="password" type="password"/>
                            <spring:message var="passCond" code="registerbuyer.form.passwordCondition"/>
                            <label for="password">
                                <spring:message code="registerbuyer.form.password"
                                arguments="${passCond}"/>
                            </label>
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
                            <label for="password">
                                <spring:message code="registerbuyer.form.confirmpassword"/>
                                <spring:message code="forms.obligatorysign"/>
                            </label>
                        </div>
                        <div class="errors">
                            <form:errors path="confirmationPassword" element="p" cssClass="error"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="center">
                <button type="submit" class="waves-effect waves-light btn">
                    <spring:message code="registerbuyer.form.submit"/>
                </button>
            </div>
        </form:form>
    </div>

</body>


<script>
    function myFunction() {
        var pass1 = document.getElementById("password").value;
        var pass2 = document.getElementById("confirmationPassword").value;
        if (pass1 !== pass2) {
            alert("Passwords Do not match");
            document.getElementById("pass1").style.borderColor = "#E34234";
            document.getElementById("pass2").style.borderColor = "#E34234";
        }
        else {
            alert("Passwords Match!!!");
        }
    }
</script>


</html>
