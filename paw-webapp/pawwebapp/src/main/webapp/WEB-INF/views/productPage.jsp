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
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
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
                    <div class = "productpage-image-container">
                        <img class="materialboxed" src="<c:url value="/image/${product.imageId}"/>" alt="${product.name}">
                    </div>
            </div>
        </c:if>
        <div class="product-info-container">
            <h4><c:out value="${product.name}"/></h4>
            <div><c:out value="${product.description}"/></div>
            <c:if test="${ecotags.size() != 0}">
                <div class="productpage-ecotags">
                    <c:forEach items="${ecotags}" var="ecotag">
                        <div style="margin-top: 1vh; margin-bottom: 1vh;">
                            <div class="${ecotag.color} white-text chip">
                                <i class="tiny material-icons">${ecotag.icon}</i>
                                <spring:message code="${ecotag.tag}"/>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
            <div style="font-size:25px;"><spring:message code="productpage.prodinfo.price"/><c:out value="${product.price}"/></div>
            <div style="font-size: 20px; height:2vh; width:100%;"><spring:message code="productpage.prodinfo.stock"/>
            <c:out value="${' '}${product.stock}"/></div>
            <h4><spring:message code="productpage.prodinfo.sellerdatatitle"/></h4>
            <div class="seller-details-container">
                <div style="height:fit-content;">
                    <span><spring:message code="productpage.prodinfo.selleraddress"/></span>
                    <span><c:out value="${seller.address}"/></span>
                </div>
                <div style="height:fit-content;">
                    <span><spring:message code="productpage.prodinfo.sellerphone"/></span>
                    <span><c:out value="${seller.phone}"/></span>
                </div>
            </div>
        </div>
        <div class="product-info-container">
            <sec:authorize access="hasRole('USER')">
                <h4><spring:message code="productpage.orderform.title"/></h4>
                <c:url value="/process/${product.productId}" var="process"/>
                <form:form modelAttribute="orderForm" action="${process}" method="post">
                    <div class="row">
                        <div class="input-field col s12">
                            <form:textarea id="textarea1" class="materialize-textarea" path="message" data-length="300"/>
                            <form:label for="textarea1" path="message"><spring:message code="productpage.orderform.msgToSeller"/></form:label>
                        </div>
                    </div>
                    <div class="errors">
                        <form:errors path="message" element="p" cssClass="error"/>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <form:input id="amount" path="amount" type="number"/>
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
            </c:if>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
<script>
    $('#textarea1').val('New Text');
    M.textareaAutoResize($('#textarea1'));

    document.addEventListener('DOMContentLoaded', function () {
        var textNeedCount = document.querySelectorAll('#textarea1');
        M.CharacterCounter.init(textNeedCount);
    });

    $('textarea#textarea1').characterCounter();

    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.materialboxed');
        var instances = M.Materialbox.init(elems, options);
    });

</script>
</html>
