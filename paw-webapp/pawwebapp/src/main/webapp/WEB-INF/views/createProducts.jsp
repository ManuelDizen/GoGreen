<%--
  Created by IntelliJ IDEA.
  User: Franco De Simone
  Date: 11/9/2022
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="createproduct.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js"></script>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div>
    <c:url value="/createProduct" var="postUrl"/>
    <form:form modelAttribute="productForm" method="post" action="${postUrl}" id="product_form" enctype="multipart/form-data">
        <div>
            <h4><spring:message code="createproduct.title"/></h4>
        </div>
        <div>
            <form:errors path="name" element="p" cssClass="error"/>
            <form:label path="name"><spring:message code="createproduct.form.name"/>
                <spring:message code="forms.obligatorysign"/></form:label>
            <form:input path="name" type="text"/>
        </div>
        <div>
            <form:errors path="description" element="p" cssClass="error"/>
            <form:label path="description"><spring:message code="createproduct.form.description"/></form:label>
            <form:input path="description" type="text"/>
        </div>
        <div>
            <form:errors path="price" element="p" cssClass="error"/>
            <form:label path="price"><spring:message code="createproduct.form.price"/>
                <spring:message code="forms.obligatorysign"/></form:label>
            <form:input path="price"/>
        </div>
        <div>
            <form:errors path="stock" element="p" cssClass="error"/>
            <form:label path="stock"><spring:message code="createproduct.form.stock"/></form:label>
            <form:input path="stock"/>
        </div>
        <div class="input-field">
            <form:label path="ecotag"><spring:message code="createproduct.form.taglist"/></form:label>
            <form:select path="ecotag" multiple="true">
                <c:forEach items="${tagList}" var="ecotag">
                    <form:option value="${ecotag.id}"><c:out value="${ecotag.tag}"/></form:option>
                </c:forEach>
            </form:select>
            <form:errors path="ecotag" element="p" cssClass="error"/>
        </div>
        <div>
            <form:errors path="image" element="p" cssClass="error"/>
            <form:label path="image"><spring:message code="createproduct.form.image"/></form:label>
            <form:input path="image" type="file"/>
        </div>
        <div>
            <button type="submit" class="waves-effect waves-light btn">
                <spring:message code="createproduct.form.submit"/>
            </button>
        </div>
    </form:form>
</div>
<%@ include file="footer.jsp"%>
</body>
</html>