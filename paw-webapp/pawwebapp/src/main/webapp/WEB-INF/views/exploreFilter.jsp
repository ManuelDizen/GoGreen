<%--
  Created by IntelliJ IDEA.
  User: Franco De Simone
  Date: 20/9/2022
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="explore-filter-title"><spring:message code="explore.filterby"/></div>
<c:url value="/explore" var="explore"/>
<form action="${explore}" method="get" id="filter_form" style="margin: 0 20px 20px 20px">
    <div class="row">
        <div class="input-field col s12">
            <input id="name" name="name" type="text" class="validate" value="${name}">
            <label for="name"><spring:message code="explore.filterform.name"/></label>
        </div>
    </div>
    <div class="row">
        <div class="input-field col s12">
            <input id="maxPrice" name="maxPrice" type="number" class="validate" value="${maxPrice}">
            <label for="maxPrice"><spring:message code="explore.filterform.maxprice"/></label>
        </div>
    </div>
    <div class="row">
        <p class="filter-inputlabel">Ecotags:</p>
        <c:forEach items="${ecotagList}" var="ecotag">
            <c:if test="${boolTags[ecotag.id-1]}">
                <label>
                    <input type="checkbox" checked="checked" name="${ecotag.path}" id="ecotag"/>
                    <span style="color:var(--palette-color-dark)"><c:out value="${ecotag.tag}"/></span>
                </label>
            </c:if>
            <c:if test="${!boolTags[ecotag.id-1]}">
                <label>
                    <input type="checkbox" name="${ecotag.path}" id="ecotag2"/>
                    <span style="color:var(--palette-color-dark)"><c:out value="${ecotag.tag}"/></span>
                </label>
            </c:if>
            <br>
        </c:forEach>
    </div>
    <div style="display:flex;justify-content: space-around;margin-top:5vh;">
        <button type="submit" class="waves-effect waves-light btn"><spring:message code="explore.filterform.submit"/></button>
    </div>
</form>
</body>
</html>
