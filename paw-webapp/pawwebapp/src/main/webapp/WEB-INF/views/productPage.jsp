<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 1/9/22
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><c:out value="${product.name}"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/images/logo.png"/>"/>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div style="height:5vh; width:100%;"></div>
    <c:if test="${formFailure}">
        <div class="order-failure">
            <spring:message code="productpage.orderfail"/>
        </div>
    </c:if>

    <div class="row product-page-container-2">
        <div class="col s8">
            <h4 style="margin-top:0; text-decoration:underline; font-size:40px;margin-bottom:50px; text-align:center;">
                <c:out value="${product.name}"/></h4>
            <div class="row" style="margin:0;">
                <c:if test="${product.imageId != 0}">
                    <div class="col s6">
                        <div class = "productpage-image-container" style="margin-top:0; height:fit-content;">
                            <img class="materialboxed" src="<c:url value="/image/${product.imageId}"/>" alt="${product.name}" style="border-radius:10px;border:2px solid var(--palette-color-secondary);">
                        </div>
                    </div>
                    <div class="col s6 product-information">
                        <div style="font-size:25px; font-weight:bold; text-align:center;" class="separate"><c:out value="${'$'}${product.price}"/></div>
                        <div class="separate" style="font-size: 25px; text-align:center;"><spring:message code="productpage.prodinfo.stock"/>
                            <c:out value="${' '}${product.stock}"/></div>
                        <c:if test="${product.stock < 6}">
                            <div class="separate" style="text-align:center; margin-top: 2vh;">
                                <a class="btn orange accent-4" style="cursor: default;">
                                    <spring:message code="productpage.orderform.lastunits"/>
                                </a>
                            </div>
                        </c:if>
                        <c:if test="${ecotags.size() != 0}">
                            <div class="productpage-ecotags separating-fields separate">
                                <c:forEach items="${ecotags}" var="ecotag">
                                    <div style="margin-top: 1vh; margin-bottom: 1vh;">
                                        <a class="${ecotag.color} white-text chip" href="<c:url value="/explore?strings=${ecotag.id}"/>">
                                            <i class="tiny material-icons">${ecotag.icon}</i>
                                            <spring:message code="${ecotag.tag}"/>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                </c:if>
                <c:if test="${product.imageId == 0}">
                    <div class="col s12 product-information" style="margin:0;">
                        <div style="font-size:25px; font-weight:bold; text-align:center;"><c:out value="${'$'}${product.price}"/></div>
                        <div style="font-size: 25px; text-align:center;"><spring:message code="productpage.prodinfo.stock"/>
                            <c:out value="${' '}${product.stock}"/></div>
                        <c:if test="${product.stock < 6}">
                            <div style="text-align:center; margin-top: 2vh;">
                                <a class="btn orange accent-4" style="cursor: default;">
                                    <spring:message code="productpage.orderform.lastunits"/>
                                </a>
                            </div>
                        </c:if>
                        <c:if test="${ecotags.size() != 0}">
                            <div class="productpage-ecotags separating-fields separate">
                                <c:forEach items="${ecotags}" var="ecotag">
                                    <div style="margin-top: 1vh; margin-bottom: 1vh;">
                                        <a class="${ecotag.color} white-text chip" href="<c:url value="/explore?strings=${ecotag.id}"/>">
                                            <i class="tiny material-icons">${ecotag.icon}</i>
                                            <spring:message code="${ecotag.tag}"/>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col s4 order-form-container" style="height:100%;">
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <c:url value="/process/${product.productId}" var="process"/>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <c:url value="/login" var="process"/>
            </c:if>
            <form:form modelAttribute="orderForm" action="${process}" method="post" cssClass="order-form">
                <div class="row productpage-orderform">
                    <div class="input-field col s12">
                        <spring:message var="textareaMsg" code="productpage.orderform.message.placeholder"/>
                        <form:textarea placeholder="${textareaMsg}" id="textarea1" class="materialize-textarea" path="message" data-length="300" style="color:white;"/>
                        <form:label for="textarea1" path="message"><spring:message code="productpage.orderform.msgToSeller"/></form:label>
                    </div>
                </div>
                <div class="errors">
                    <form:errors path="message" element="p" cssClass="error"/>
                </div>
                <div class="row">
                    <div class="input-field col s12">
                        <spring:message var="placeholder1" code="productpage.orderform.amount.placeholder"/>
                        <form:input id="amount" path="amount" type="number"
                                    style="color:white;" placeholder="${placeholder1}"/>

                        <form:label path="amount"><spring:message code="productpage.orderform.amount"/></form:label>
                    </div>
                </div>
                <div class="errors">
                    <form:errors path="amount" element="p" cssClass="error"/>
                </div>
                <div class="row" style="text-align:center;">
                    <sec:authorize access="hasRole('SELLER')">
                        <button type="submit" class="waves-effect waves-light btn disabled">
                            <spring:message code="productpage.orderform.sellerblocked"/>
                        </button>
                    </sec:authorize>
                    <sec:authorize access="hasRole('USER')">
                        <button type="submit" class="waves-effect waves-light btn">
                            <spring:message code="productpage.orderform.submit"/>
                        </button>
                    </sec:authorize>
                    <c:if test="${pageContext.request.userPrincipal.name == null}">
                        <a href="<c:url value="/login"/>" class="waves-effect waves-light btn">
                            <spring:message code="productpage.orderform.submit"/>
                        </a>
                    </c:if>
                </div>
            </form:form>
        </div>
    </div>


    <%--div class="product-page-container" style="height:available;">
        <c:if test="${product.imageId != 0}">
            <div class="product-info-container" style="background-color:transparent;">
                    <div class = "productpage-image-container" style="margin-top:0;">
                        <img class="materialboxed" src="<c:url value="/image/${product.imageId}"/>" alt="${product.name}" style="border-radius:10px;border:2px solid var(--palette-color-secondary);">
                    </div>
            </div>
        </c:if>
        <div class="product-info-container center-container">
            <h4 style="margin-top:0; text-decoration:underline; font-size:40px;margin-bottom:4vh;">
                <c:out value="${product.name}"/></h4>
            <div class="separating-fields"><c:out value="${product.description}"/></div>
            <c:if test="${ecotags.size() != 0}">
                <div class="productpage-ecotags separating-fields">
                    <c:forEach items="${ecotags}" var="ecotag">
                        <div style="margin-top: 1vh; margin-bottom: 1vh;">
                            <a class="${ecotag.color} white-text chip" href="<c:url value="/explore?strings=${ecotag.id}"/>">
                                <i class="tiny material-icons">${ecotag.icon}</i>
                                <spring:message code="${ecotag.tag}"/>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <div style="font-size:25px; font-weight:bold;" class="separating-fields"><spring:message code="productpage.prodinfo.price"/><c:out value="${product.price}"/></div>
            <div style="font-size: 25px; width:100%; margin-bottom:0;"><spring:message code="productpage.prodinfo.stock"/>
            <c:out value="${' '}${product.stock}"/></div>
            <c:if test="${product.stock < 6}">
                <div style="text-align:center; margin-top: 2vh;">
                    <a class="btn orange accent-4" style="cursor: default;">
                        <spring:message code="productpage.orderform.lastunits"/>
                    </a>
                </div>
            </c:if>
        </div>
        <div class="product-info-container">
            <h4 style="margin-top:0; font-size:40px;"><spring:message code="productpage.prodinfo.sellerdatatitle"/></h4>
            <div class="seller-details-container" style="margin-top:0; margin-bottom: 5vh;">
                <div style="height:fit-content;">
                    <span><spring:message code="productpage.prodinfo.selleraddress"/></span>
                    <span><c:out value="${seller.address}"/></span>
                </div>
                <div style="height:fit-content;">
                    <span><spring:message code="productpage.prodinfo.sellerphone"/></span>
                    <span><c:out value="${seller.phone}"/></span>
                </div>
            </div>
            <sec:authorize access="hasRole('USER')">
                <div style="font-size: 20px;"><spring:message code="productpage.orderform.title"/></div>
                <c:url value="/process/${product.productId}" var="process"/>
                <form:form modelAttribute="orderForm" action="${process}" method="post">
                    <div class="row productpage-orderform">
                        <div class="input-field col s12">
                            <spring:message var="textareaMsg" code="productpage.orderform.message.placeholder"/>
                            <form:textarea placeholder="${textareaMsg}" id="textarea1" class="materialize-textarea" path="message" data-length="300" style="color:white;"/>
                            <form:label for="textarea1" path="message"><spring:message code="productpage.orderform.msgToSeller"/></form:label>
                        </div>
                    </div>
                    <div class="errors">
                        <form:errors path="message" element="p" cssClass="error"/>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <spring:message var="placeholder1" code="productpage.orderform.amount.placeholder"/>
                            <form:input id="amount" path="amount" type="number"
                                        style="color:white;" placeholder="${placeholder1}"/>

                            <form:label path="amount"><spring:message code="productpage.orderform.amount"/></form:label>
                        </div>
                    </div>
                    <div class="errors">
                        <form:errors path="amount" element="p" cssClass="error"/>
                    </div>
                    <div class="row" style="text-align:center;">
                        <button type="submit" class="waves-effect waves-light btn">
                            <spring:message code="productpage.orderform.submit"/>
                        </button>
                    </div>
                </form:form>
            </sec:authorize>
            <sec:authorize access="hasRole('SELLER')">
                <h4><spring:message code="productpage.orderform.loggedasseller"/></h4>
            </sec:authorize>
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <h4 style="margin: 20px auto auto;">
                    <spring:message code="productpage.orderform.notauthenticated"/>
                </h4>
                <div class="row" style="text-align: center;">
                    <a class="decision-button waves-effect waves-light btn standard-button" href="<c:url value="/login"/>">
                        <spring:message code="login.title"/>
                    </a>
                </div>
            </c:if>
        </div>
    </div --%>
<div class="landing-recent-product-container" style="margin-top:20px;">
<c:if test="${recent.size() != 0}">
    <div class="row">
    <div class="col s12">
    <hr class="landing-separator">
    <h4 class="landing-page-title" style="margin-top: 15px; margin-bottom:15px;"><spring:message code="productpage.otherinteresting"/></h4>
    <hr class = "landing-separator">
    </div>
    </div>
    <div class="landing-products">
        <c:forEach items="${interesting}" var="product">
            <div class="card product-card z-depth-1" style="margin:10px auto;">
                <a href="<c:url value="/product/${product.productId}"/>">
                    <div class="card-image">
                        <c:if test="${product.imageId != 0}">
                            <img style="border-radius: 10px 10px 0 0;" src="<c:url value="/image/${product.imageId}"/>">
                        </c:if>
                        <c:if test="${product.imageId == 0}">
                            <img style="border-radius: 10px 10px 0 0;" src="<c:url value="/resources/images/logo.png"/>">
                        </c:if>
                    </div>
                </a>
                <div class="card-content">
                    <a href="<c:url value="/product/${product.productId}"/>" class="card-title product-card-title"><c:out value="${product.name}"/></a>
                    <div class="card-price">
                        <spring:message code="explore.products.price"/><c:out value="${product.price}"/>
                    </div>
                    <div class="card-price">
                        <i class="tiny material-icons">category</i>
                        <c:forEach items="${categories}" var="category">
                            <c:if test="${category.id == product.categoryId}">
                                <spring:message code="${category.name}"/>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="center">
                        <c:set var="count" value="0"/>
                        <c:forEach items="${product.tagList}" var="ecotag">
                            <c:if test="${count == 2}">
                                <br>
                                <div class="yellow-card black-text chip ">
                                    <i class="tiny material-icons">more_horiz</i>
                                </div>
                            </c:if>
                            <c:if test="${count lt 2}">
                                <a class="${ecotag.color} white-text chip eco_chip" href="<c:url value="/explore?strings=${ecotag.id}&sort=${sort}&direction=${direction}"/>">
                                    <i class="tiny material-icons">${ecotag.icon}</i>
                                    <spring:message code="${ecotag.tag}"/>
                                </a>
                            </c:if>
                            <c:set var="count" value="${count + 1}"/>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    </c:if>
    </div>

</body>
<script>
    $('#textarea1').val('New Text');
    M.textareaAutoResize($('#textarea1'));

    document.addEventListener('DOMContentLoaded', function () {
        var textNeedCount = document.querySelectorAll('#textarea1');
        M.CharacterCounter.init(textNeedCount);
    });

    $(document).ready(function() {
        $('textarea#textarea1').characterCounter();
    });

    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.materialboxed');
        var instances = M.Materialbox.init(elems, options);
    });

</script>
</html>
