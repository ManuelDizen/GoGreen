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
    <div class="product-page-container" style="height:available;">
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
                <c:if test="${product.stock == 1}">
                    <spring:message code="productpage.orderform.lastunit" var="alertmessage"/>
                </c:if>
                <c:if test = "${product.stock != 1}">
                    <spring:message code="productpage.orderform.lastunits" var="alertmessage"/>
                </c:if>
                <div style="text-align:center; margin-top: 2vh;">
                    <a class="btn orange accent-4" style="cursor: default;">
                        <c:out value="${alertmessage}"/>
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
                        <div class="input-field col s12" id="orderamount">
                            <form:select path="amount">
                                <form:option value="0" disabled="true"><spring:message code="productpage.orderform.amount.placeholder"/></form:option>
                                <c:forEach var="i" begin="1" end="5">
                                    <form:option value="${i}"><c:out value="${i}"/></form:option>
                                </c:forEach>
                            </form:select>
                            <form:label for="amount" path="amount"><spring:message code="createproduct.form.category"/></form:label>
                            <form:errors path="amount" element="p" cssClass="error"/>
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
