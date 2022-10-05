<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><c:out value="${product.name}"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/images/logo.png"/>"/>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="form-failure"></div>
    <c:if test="${formFailure}">
        <div class="order-failure">
            <spring:message code="productpage.orderfail"/>
        </div>
    </c:if>

    <div class="row product-page-container-2">
        <div class="col s8">
            <h4 class="product-page-title">
                <c:out value="${product.name}"/></h4>
            <div class="row no-margin">
                <c:if test="${product.imageId != 0}">
                    <div class="col s6">
                        <div class = "productpage-image-container productpage-img">
                            <img class="materialboxed alt" src="<c:url value="/image/${product.imageId}"/>" alt="${product.name}">
                        </div>
                    </div>
                    <div class="col s6 product-information">
                        <div class="separate productpage-info"><c:out value="${'$'}${product.price}"/></div>
                        <div class="separate productpage-info-nobold"><spring:message code="productpage.prodinfo.stock"/>
                            <c:out value="${' '}${product.stock}"/></div>
                        <c:if test="${product.stock < 6}">
                            <div class="separate productpage-info-margin">
                                <a class="btn orange accent-4 cursor-default">
                                    <spring:message code="productpage.orderform.lastunits"/>
                                </a>
                            </div>
                        </c:if>
                        <c:if test="${ecotags.size() != 0}">
                            <div class="productpage-ecotags separating-fields separate">
                                <c:forEach items="${ecotags}" var="ecotag">
                                    <div class="productpage-ecotag">
                                        <a class="${ecotag.color} white-text chip" href="<c:url value="/explore?strings=${ecotag.id}"/>">
                                            <i class="tiny material-icons">${ecotag.icon}</i>
                                            <spring:message code="${ecotag.tag}"/>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                        <c:if test="${area != null}">
                            <div class="location-pin">
                                <i class="tiny material-icons separate-icon">location_pin</i><span><c:out value="${area.name}"/></span>
                            </div>
                        </c:if>
                    </div>
                </c:if>
                <c:if test="${product.imageId == 0}">
                    <div class="col s12 product-information no-margin">
                        <div class="productpage-info"><c:out value="${'$'}${product.price}"/></div>
                        <div class="productpage-info-nobold"><spring:message code="productpage.prodinfo.stock"/>
                            <c:out value="${' '}${product.stock}"/></div>
                        <c:if test="${product.stock < 6}">
                            <div class="productpage-info-margin">
                                <a class="btn orange accent-4 cursor-default">
                                    <spring:message code="productpage.orderform.lastunits"/>
                                </a>
                            </div>
                        </c:if>
                        <c:if test="${ecotags.size() != 0}">
                            <div class="productpage-ecotags separating-fields separate">
                                <c:forEach items="${ecotags}" var="ecotag">
                                    <div class="productpage-ecotag">
                                        <a class="${ecotag.color} white-text chip" href="<c:url value="/explore?strings=${ecotag.id}"/>">
                                            <i class="tiny material-icons">${ecotag.icon}</i>
                                            <spring:message code="${ecotag.tag}"/>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                        <c:if test="${area != null}">
                            <div class="location-pin">
                                <i class="tiny material-icons separate-icon">location_pin</i><span><c:out value="${area.name}"/></span>
                            </div>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col s4 order-form-container">
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
                        <div class="input-field col s12" id="orderamount">
                            <form:select path="amount">
                                <form:option value="0" disabled="true"><spring:message code="productpage.orderform.amount.placeholder"/></form:option>
                                <c:forEach var="i" begin="1" end="5">
                                    <c:if test="${i <= product.stock}">
                                        <form:option value="${i}"><c:out value="${i}"/></form:option>
                                    </c:if>
                                </c:forEach>
                            </form:select>
                            <form:label for="amount" path="amount"><spring:message code="createproduct.form.category"/></form:label>
                            <form:errors path="amount" element="p" cssClass="error"/>
                        </div>
                    </div>
                </div>
                <div class="errors">
                    <form:errors path="amount" element="p" cssClass="error"/>
                </div>
                <div class="row text-center">
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



    <div class="landing-recent-product-container" style="margin-top:20px;">
        <c:if test="${recent.size() != 0}">
            <div class="row">
                <div class="col s12">
                    <hr class="landing-separator">
                    <h4 class="landing-page-title"><spring:message code="productpage.otherinteresting"/></h4>
                    <hr class = "landing-separator">
                </div>
            </div>
            <div class="landing-products">
                <c:forEach items="${interesting}" var="product">
                    <div class="card product-card z-depth-1">
                        <a href="<c:url value="/product/${product.productId}"/>">
                            <div class="card-image">
                                <c:if test="${product.imageId != 0}">
                                    <img src="<c:url value="/image/${product.imageId}"/>">
                                </c:if>
                                <c:if test="${product.imageId == 0}">
                                    <img src="<c:url value="/resources/images/logo.png"/>">
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
