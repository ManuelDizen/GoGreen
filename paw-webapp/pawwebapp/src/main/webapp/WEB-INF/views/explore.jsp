<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 28/8/22
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="explore.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="form-title" style="text-decoration:underline;">
        <h3><spring:message code="explore.title"/></h3>
    </div>
    <div class="explore-container">
        <div class="explore-filter">
            <div class="explore-filter-title"><spring:message code="explore.filterby"/></div>
            <c:url value="/explore" var="explore"/>
            <form action="${explore}" method="get" id="filter_form" style="margin: 0 20px 20px 20px">
                <div class="row">
                    <div class="input-field col s12">
                        <input name="name" id="name" type="text" data-length="50">
                        <label for="name"><spring:message code="explore.filterform.name"/></label>
                    </div>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <input id="maxPrice" name="maxPrice" type="text">
                        <label for="maxPrice"><spring:message code="explore.filterform.maxprice"/></label>
                    </div>
                </div>
                <div class="row">
                    <p class="filter-inputlabel">Ecotags:</p>
                    <c:forEach items="${ecotagList}" var="ecotag">
                        <c:if test="${boolTags[ecotag.id-1]}">
                            <input name="${ecotag.path}" type="checkbox" checked="checked" id="ecotag">
                                <label for="ecotag">${ecotag.tag}</label>
                        </c:if>
                        <c:if test="${!boolTags[ecotag.id-1]}">
                            <input name="${ecotag.path}" type="checkbox" id="ecotag2">
                                <label for="ecotag2">${ecotag.tag}</label>
                        </c:if>
                        <br>
                    </c:forEach>
                </div>
                <div style="display:flex;justify-content: space-around;margin-top:5vh;">
                    <button type="submit" class="decision-button waves-effect waves-light btn"><spring:message code="explore.filterform.submit"/></button>
                </div>
            </form>

        </div>
        <div class="explore-products">
            <c:if test="${isEmpty}">
                <h4><spring:message code="explore.noproducts"/></h4>
                <sec:authorize access="hasRole('SELLER')">
                    <div><spring:message code="explore.noproducts.sellermsg"/></div>
                    <div>
                        <a class="decision-button waves-effect waves-light btn standard-button"
                           href="<c:url value="/createProduct"/>">
                            <spring:message code="explore.createproduct"/>
                        </a>
                    </div>
                </sec:authorize>
            </c:if>
            <c:if test="${products.size() != 0}">
                <c:forEach items="${products}" var="product">
                    <div class="card product-card" style="margin:10px auto;">
                        <div class="card-image">
                            <c:if test="${product.imageId != 0}">
                                <img style="border-radius: 10px 10px 0 0;" src="<c:url value="/image/${product.imageId}"/>">
                            </c:if>
                            <c:if test="${product.imageId == 0}">
                                <img style="border-radius: 10px 10px 0 0;" src="<c:url value="/resources/images/logo.png"/>">
                            </c:if>
                        </div>
                        <div class="card-content">
                            <span class="card-title product-card-title"><c:out value="${product.name}"/></span>
                            <div style="margin-top: 2vh; margin-bottom: 2vh;">
                                <spring:message code="explore.products.price"/><c:out value="${product.price}"/>
                            </div>
                            <div style="margin-top: 1vh; margin-bottom: 1vh;">
                                <c:set var="count" value="0"/>
                                <c:forEach items="${product.tagList}" var="ecotag">
                                    <c:if test="${count == 3}">
                                        <div class="grey black-text chip">
                                            <spring:message code="explore.products.andmore"/>
                                        </div>
                                        <c:set var="count" value="${count + 1}"/>
                                    </c:if>
                                    <c:if test="${count lt 3}">
                                        <div class="${ecotag.color} white-text chip">
                                            <i class="tiny material-icons">${ecotag.icon}</i>
                                                ${ecotag.tag}
                                        </div>
                                        <c:set var="count" value="${count + 1}"/>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <div>
                                <a class="waves-effect waves-light btn standard-button"
                                   href="<c:url value="/product/${product.productId}"/>"
                                    style="text-align: center;">
                                    <spring:message code="explore.product.goto"/>
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${products.size() == 0}">
                <div class="noproducts-container">
                    <h4><spring:message code="explore.noproductsfilter"/></h4>
                    <div class="circle">
                        <img src="<c:url value="/resources/images/logo.png"/>" height="200" width="200"
                             alt="Logo">
                    </div>
                    <div style="margin-bottom: 15px">
                        <div>
                            <button class="decision-button waves-effect waves-light btn" onClick="history.go(-1)"><spring:message code="explore.cleanfilters"/></button>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        var textNeedCount = document.querySelectorAll('#name');
        M.CharacterCounter.init(textNeedCount);
    });
</script>
</html>
