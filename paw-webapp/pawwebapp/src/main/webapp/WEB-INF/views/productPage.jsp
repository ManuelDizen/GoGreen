<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><spring:message code="navbar.companyname"/> - <c:out value="${product.name}"/></title>
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
    <c:if test="${created}">
        <sec:authorize access="hasRole('SELLER')">
        <div class="created-product">
            <spring:message code="productpage.created"/>
            <div style="text-align: center; margin-top: 20px; margin-bottom: 20px">
                <a class="waves-effect waves-light btn-small standard-button"
                   href="<c:url value="/createArticle"/>">
                    <spring:message code="navbar.createarticle"/>
                </a>
            </div>
        </div>
        </sec:authorize>
    </c:if>
    <div class="row">
        <div class="col s8">
            <div class="container productpage-info-container">
                <div class="productpage-info-title">
                    <c:out value="${product.name}"/>
                </div>
                <div class="row productpage-info-content">
                    <c:if test="${product.image.id != 0}">
                        <div class="col s7">
                            <div class = "productpage-image-container productpage-img">
                                <img class="materialboxed alt limit" src="<c:url value="/image/${product.image.id}"/>" alt="${product.name}">
                            </div>
                        </div>
                        <div class="col s5 productpage-info-text">
                            <div class="productpage-info-price"><c:out value="${'$'}${product.price}"/></div>
                            <div class="productpage-info-nobold">
                                <c:out value="${product.description}"/>
                            </div>
                            <div class="productpage-info">
                                <div class="center-in-div-with-flex">
                                    <i class="tiny material-icons">category</i>
                                    <a class="productpage-link" href="<c:url value="/explore?category=${category.id}&sort=${sort}&direction=${direction}"/>">
                                        <spring:message code="${category.name}"/>
                                    </a>
                                </div>
                            </div>
                            <div class="productpage-info">
                                <c:if test="${area != null}">
                                    <div class="center-in-div-with-flex">
                                        <a class="productpage-link" href="<c:url value="/explore?areaId=${area.id}&sort=${sort}&direction=${direction}"/>">
                                            <i class="tiny material-icons separate-icon">location_pin</i><span><c:out value="${area.name}"/></span>
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                            <div class="productpage-info-nobold"><spring:message code="productpage.prodinfo.stock"
                                                                                 arguments="${product.stock}"/></div>
                            <c:if test="${product.stock < 6}">
                                <div style="margin-top: 10px; margin-bottom: 10px">
                                    <a class="btn orange accent-4 cursor-default">
                                        <spring:message code="productpage.orderform.lastunits"/>
                                    </a>
                                </div>
                            </c:if>
                            <c:if test="${ecotags.size() != 0}">
                                <div class="productpage-ecotags separating-fields">
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
                            <div class="productpage-info">
                                <div class="center-in-div-with-flex">
                                    <a class="productpage-link" href="<c:url value="/sellerPage/${seller.id}"/>">
                                        <i class="tiny material-icons separate-icon">person</i><span><c:out value="${seller.user.firstName} ${seller.user.surname}"/></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:if>
                <c:if test="${product.image.id == 0}">
                    <div class="col s12 productpage-info-text">
                        <div class="productpage-info-price">
                            <spring:message code="productpage.price" arguments="${product.price}"/>
                        </div>
                        <div class="productpage-info-nobold" style="font-size:18px;">
                            <c:out value="${product.description}"/>
                        </div>
                        <div class="productpage-info" style="font-size:20px;">
                            <div class="center-in-div-with-flex">
                                <i class="tiny material-icons">category</i>
                                <a class="productpage-link" href="<c:url value="/explore?category=${category.id}&sort=${sort}&direction=${direction}"/>">
                                    <spring:message code="${category.name}"/>
                                </a>
                            </div>
                            <c:if test="${area != null}">
                                <div class="center-in-div-with-flex">
                                    <a class="productpage-link" href="<c:url value="/explore?areaId=${area.id}&sort=${sort}&direction=${direction}"/>">
                                        <i class="tiny material-icons separate-icon">location_pin</i><span><c:out value="${area.name}"/></span>
                                    </a>
                                </div>
                            </c:if>
                        </div>
                        <div class="productpage-info-nobold space-around">
                            <spring:message code="productpage.prodinfo.stock" arguments="${product.stock}"/>
                            <c:if test="${product.stock < 6}">
                                <div>
                                    <a class="btn orange accent-4 cursor-default">
                                        <spring:message code="productpage.orderform.lastunits"/>
                                    </a>
                                </div>
                            </c:if>
                            <c:if test="${ecotags.size() != 0}">
                                <div class="productpage-ecotags separating-fields">
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
                            <div class="productpage-info">
                                <div class="center-in-div-with-flex">
                                    <a class="productpage-link" href="<c:url value="/sellerPage/${seller.id}"/>">
                                        <i class="tiny material-icons separate-icon">person</i><span><c:out value="${seller.user.firstName} ${seller.user.surname}"/></span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${product.image.id == 0}">
                        <div class="col s12 productpage-info-text">
                            <div class="productpage-info-price">
                                <spring:message code="productpage.price" arguments="${product.price}"/>
                            </div>
                            <div class="productpage-info-nobold">
                                <c:out value="${product.description}"/>
                            </div>
                            <div class="productpage-info">
                                <div class="center-in-div-with-flex">
                                    <i class="tiny material-icons">category</i>
                                    <a class="productpage-link" href="<c:url value="/explore?category=${category.id}&sort=${sort}&direction=${direction}"/>">
                                        <spring:message code="${category.name}"/>
                                    </a>
                                </div>
                                <c:if test="${area != null}">
                                    <div class="center-in-div-with-flex">
                                        <a class="productpage-link" href="<c:url value="/explore?areaId=${area.id}&sort=${sort}&direction=${direction}"/>">
                                            <i class="tiny material-icons separate-icon">location_pin</i><span><c:out value="${area.name}"/></span>
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                            <div class="row center flex-center" style="margin:auto;">
                                <a class="productpage-link underline text-center" href="<c:url value="/sellerPage/${seller.id}"/>">
                                    <div class="text-center">
                                        <spring:message code="productpage.linktoseller"/>
                                    </div>
                                </a>
                            </div>
                        </c:if>
                </div>
            </div>
        </div>
        <div class="col s4">
            <div class="container productpage-purchase-container">
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <c:url value="/process/${product.productId}" var="process"/>
                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <c:url value="/login" var="process"/>
                </c:if>
                <c:if test="${product.status.id == availableId}">
                    <form:form modelAttribute="orderForm" action="${process}" method="post">
                        <div class="margin-left-20">
                            <div class="row">
                                <div class="input-field">
                                    <spring:message var="textareaMsg" code="productpage.orderform.message.placeholder"/>
                                    <form:textarea placeholder="${textareaMsg}" id="sellerMsg" class="materialize-textarea" path="message"
                                                   data-length="255" style="color:white;"/>
                                    <form:label for="sellerMsg" cssStyle="margin-left:10px; left:0;" path="message">
                                        <spring:message code="productpage.orderform.msgToSeller"/></form:label>
                                    <div class="errors">
                                        <form:errors path="message" element="p" cssClass="error"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="input-field" id="orderamount">
                                    <form:select path="amount">
                                        <form:option value="0" disabled="true">
                                            <spring:message code="productpage.orderform.amount.placeholder"/></form:option>
                                        <c:forEach var="i" begin="1" end="5">
                                            <c:if test="${i <= product.stock}">
                                                <form:option value="${i}"><c:out value="${i}"/></form:option>
                                            </c:if>
                                        </c:forEach>
                                    </form:select>
                                    <form:label for="amount" path="amount" cssStyle="left:0;"><spring:message code="productpage.orderform.amount"/></form:label>
                                    <form:errors path="amount" element="p" cssClass="error"/>
                                    <div class="errors">
                                        <form:errors path="amount" element="p" cssClass="error"/>
                                        <div class="errors">
                                            <form:errors path="amount" element="p" cssClass="error"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 center">
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
                            </div>
                        </div>
                    </form:form>
                </c:if>
                <c:if test="${product.status.id != availableId}">
                    <c:choose>
                        <c:when test="${product.status.id == pausedId}">
                            <div class="flex-column-center-align">
                                <span class="center"><spring:message code="productpage.pausedmsg"/></span>
                                <a class="waves-effect waves-light btn-small center-block separate-20-top" href="<c:url value="/explore"/>">
                                    <spring:message code="navbar.explore"/>
                                </a>
                            </div>
                        </c:when>
                        <c:when test="${product.status.id == outofstockId}">
                            <div class="flex-column-center-align">
                                <span class="center"><spring:message code="productpage.outofstockmsg"/></span>
                                <a class="waves-effect waves-light btn-small center-block separate-20-top" href="<c:url value="/explore"/>">
                                    <spring:message code="navbar.explore"/>
                                </a>
                            </div>
                        </c:when>
                        <c:when test="${product.status.id == deletedId}">
                            <div class="flex-column-center-align">
                                <span class="center"><spring:message code="productpage.deletedmsg"/></span>
                                <a class="waves-effect waves-light btn-small center-block separate-20-top" href="<c:url value="/explore"/>">
                                    <spring:message code="navbar.explore"/>
                                </a>
                            </div>
                        </c:when>
                    </c:choose>
                </c:if>
            </div>
        </div>
    </div>
    <div class="landing-recent-product-container" style="margin-top:20px;">
        <c:if test="${interesting.size() != 0}">
            <div class="row">
                <div class="col s12">
                    <hr class="landing-separator">
                    <h5 class="landing-page-title"><spring:message code="productpage.otherinteresting"/></h5>
                    <hr class = "landing-separator">
                </div>
            </div>
            <div class="landing-products">
                <c:forEach items="${interesting}" var="product">
                    <%@include file="productCard.jsp"%>
                </c:forEach>
            </div>
        </c:if>
    </div>
    <div class="container comments-container">
        <h4 class="center comments"><spring:message code="productpage.comments"/></h4>
        <c:if test="${comments.size() == 0}">
            <span><spring:message code="nocommentsyet"/></span>
        </c:if>
        <sec:authorize access="hasRole('USER')">
            <div class="center">
                <a id="button" style="margin-bottom: 20px" class="comment-write waves-effect waves-light btn-small gray accent-4 modal-trigger">
                    <spring:message code="productpage.commentmsg"/></a>
            </div>
        </sec:authorize>
        <div id="newform" class="comment-box comment-write" style="display: none">
            <c:url value="/newComment/${product.productId}" var="postUrl"/>
            <form:form modelAttribute="commentForm" action="${postUrl}" method="post">
                <div class="" style="">
                    <div class="input-field col s12">
                        <spring:message var="textareaMsg" code="comment.message.placeholder"/>
                        <form:textarea placeholder="${textareaMsg}" id="message" class="materialize-textarea" path="message"
                                       data-length="300" style="color:white;"/>
                        <form:label for="message" cssStyle="margin-left:10px" path="message"></form:label>
                        <div class="errors">
                            <form:errors path="message" element="p" cssClass="error"/>
                        </div>
                    </div>
                    <div style="display: none">
                        <form:input path="parentId" value="-1" type="number"/>
                    </div>
                    <div class="center">
                        <button type="submit" class="waves-effect waves-light btn">
                            <spring:message code="productpage.comment.submit"/>
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
        <c:forEach items="${comments}" var="comment">
            <div class="comment-box">
                <div  style="align-self: start; width:50%; text-align:left; margin: 5px 0 10px 0; color:black; border-radius:10px;
            background-color: #ffffff;">
                    <div class="comment-user">
                        <i class="tiny comment-icon material-icons">
                            person
                        </i><p class="comment-username">${comment.user.firstName}</p><p class="comment-username">${comment.user.surname}</p>
                    </div>
                    <div><p class="comment-message">${comment.message}</p></div>
                </div>
                <c:if test="${comment.reply != null}">
                    <div class="comment-reply" style="align-self:end    ; width:50%; text-align:right; margin: 10px 0 5px 0;
            color:black; border-radius:10px;
            background-color: #aaaaaa;">
                        <div class="comment-user">
                            <i class="tiny comment-icon material-icons">
                                person
                            </i><p class="comment-username">${user.firstName}</p><p class="comment-username">${user.surname}</p>
                        </div>
                        <div><p class="comment-message">${comment.reply}</p></div>
                    </div>
                </c:if>
                <c:if test="${user.email == loggedEmail}">
                    <c:if test="${comment.reply == null}">
                        <div id="newform${comment.id}" class="comment-reply">
                            <c:url value="/reply/${product.productId}" var="postUrl"/>
                            <form:form modelAttribute="commentForm" action="${postUrl}" method="post">
                                <div class="" style="display:flex; flex-direction:column;">
                                    <div class="input-field col s12">
                                        <spring:message var="textareaMsg" code="comment.reply.placeholder"/>
                                        <form:textarea placeholder="${textareaMsg}" id="message" class="materialize-textarea" path="message"
                                                       data-length="300" style="color:white;"/>
                                        <form:label for="message" cssStyle="margin-left:10px" path="message"></form:label>
                                        <div class="errors">
                                            <form:errors path="message" element="p" cssClass="error"/>
                                        </div>
                                    </div>
                                    <div style="display: none">
                                        <form:input path="parentId" value="${comment.id}" type="number"/>
                                    </div>
                                    <div class="flex-right-align">
                                        <button type="submit" class="waves-effect waves-light btn comment-btn">
                                            <spring:message code="productpage.comment.respond"/>
                                        </button>
                                    </div>
                                </div>
                            </form:form>
                        </div>

                    </c:if>
                </c:if>
            </div>
        </c:forEach>

    </div>
    <c:if test="${commentPages.size() > 1}">
        <div class="pagin">
            <c:set var="nextPage" value="${currentPage+1}"/>
            <c:set var="previousPage" value="${currentPage-1}"/>
            <div>
                <ul class="pagination">
                    <c:if test="${currentPage <= 1}">
                        <li class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                    </c:if>
                    <c:if test="${currentPage > 1}">
                        <li><a href="?page=${previousPage}"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                        <li class="waves-effect"><a href="?page=${previousPage}" style="color: #EDFA8B">${previousPage}</a></li>
                    </c:if>
                    <li id="${currentPage}" class="disabled active"><a class="yellow-card" href="">${currentPage}</a></li>
                    <c:if test="${currentPage < commentPages.size()}">
                        <li class="waves-effect"><a href="?page=${nextPage}" style="color: #EDFA8B">${nextPage}</a></li>
                        <li><a href="?page=${nextPage}"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                    </c:if>
                    <c:if test="${currentPage >= commentPages.size()}">
                        <li id="forward" class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </c:if>
</body>
<script>

    var elems = document.querySelectorAll('.materialize-textarea');
    M.CharacterCounter.init(elems);

    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.materialboxed');
        var instances = M.Materialbox.init(elems, options);
    });

    var button = document.getElementById('button');

    button.onclick = function() {
        var div = document.getElementById('newform');
        if (div.style.display !== 'none') {
            div.style.display = 'none';
        }
        else {
            div.style.display = 'block';
        }
        if (button.style.display !== 'none') {
            button.style.display = 'none';
        }
    };



</script>
</html>
