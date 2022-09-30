<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 22/9/22
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="header.jsp"%>
    <title><spring:message code="editproduct.title"/></title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="container">
      <c:url value="/editProduct/{prodId}" var="postUrl"/>
      <form:form modelAttribute="productForm" method="post" action="${postUrl}" id="product_form" enctype="multipart/form-data">
        <div class="center">
          <h4><spring:message code="createproduct.title"/></h4>
        </div>
        <div>
          <form:label path="name"><spring:message code="createproduct.form.name"/>
            <spring:message code="forms.obligatorysign"/></form:label>
          <form:input path="name" type="text" value="${product.name}"/>
          <form:errors path="name" element="p" cssClass="error"/>
        </div>
        <div>
          <form:label path="description"><spring:message code="createproduct.form.description"/></form:label>
          <form:input path="description" type="text" value="${product.description}"/>
          <form:errors path="description" element="p" cssClass="error"/>
        </div>
        <div>
          <form:label path="price"><spring:message code="createproduct.form.price"/>
            <spring:message code="forms.obligatorysign"/></form:label>
          <form:input path="price" value="${product.price}"/>
          <form:errors path="price" element="p" cssClass="error"/>
        </div>
        <div>
          <form:label path="stock"><spring:message code="createproduct.form.stock"/></form:label>
          <form:input path="stock" value="${product.stock}"/>
          <form:errors path="stock" element="p" cssClass="error"/>
        </div>
        <div class="input-field">
          <form:select path="ecotag" multiple="true">
            <c:forEach items="${tagList}" var="ecotag">
              <form:option value="${ecotag.id}" id="ecotag${ecotag.id}"><c:out value="${ecotag.tag}"/></form:option>
            </c:forEach>
          </form:select>
          <form:label for="ecotag" path="ecotag"><spring:message code="createproduct.form.taglist"/></form:label>
          <form:errors path="ecotag" element="p" cssClass="error"/>
        </div>
        <div class="row">
          <div class="file-field input-field">
            <div class="btn">
              <span><spring:message code="createproduct.form.image"/></span>
              <form:input path="image" type="file"/>
            </div>
            <div class="file-path-wrapper">
              <input class="file-path validate" type="text" id="file-path">
            </div>
          </div>
          <form:errors path="image" element="p" cssClass="error"/>
        </div>
        <div class="center" style="padding-top: 1em;">
          <button type="submit" class="decision-button waves-effect waves-light btn">
            <spring:message code="createproduct.form.submit"/>
          </button>
        </div>
      </form:form>
    </div>

</body>
</html>
