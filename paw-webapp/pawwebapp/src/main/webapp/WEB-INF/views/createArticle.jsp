<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 25/10/22
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="header.jsp"%>
    <title><spring:message code="navbar.companyname"/> - <spring:message code="createarticle.title"/></title>
</head>
<body>
  <%@include file="navbar.jsp"%>
  <div class="container createarticle">
    <div class="center">
      <h4><spring:message code="createarticle.title"/></h4>
    </div>
  </div>
  <div class="row">
    <div class="container">
      <c:url value="/createArticle" var="postUrl"/>
      <form:form modelAttribute="articleForm" method="post" action="${postUrl}"
                 id="article_form" enctype="multipart/form-data">
        <div class="input-field col s12">
          <spring:message var="textareaMsg" code="createarticle.message.placeholder"/>
          <form:textarea placeholder="${textareaMsg}" id="message" class="materialize-textarea" path="message"
                         data-length="1023" style="color:white;"/>
          <div class="errors">
            <form:errors path="message" element="p" cssClass="error"/>
          </div>
        </div>
        <div class="input-field col s12">
          <div class="file-field input-field">
            <div class="decision-button waves-effect waves-light btn image_button">
              <form:label path="image" id="image-label-createarticle"><spring:message code="createproduct.form.image"/>
              </form:label>
              <form:input path="image" type="file"/>
            </div>
            <div class="file-path-wrapper">
              <input class="file-path validate" type="text" readonly id="readonly-input"/>
            </div>
          </div>
          <form:errors path="image" element="p" cssClass="error"/>
        </div>
        <div class="center create-submit">
          <button type="submit" class="decision-button waves-effect waves-light btn publish-button">
            <spring:message code="createproduct.form.submit"/>
          </button>
        </div>
      </form:form>
    </div>
  </div>
</body>
<script>
  var elems = document.querySelectorAll('.materialize-textarea');
  M.CharacterCounter.init(elems);
</script>
</html>
