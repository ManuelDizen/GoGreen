<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><spring:message code="navbar.companyname"/> - <spring:message code="profile.title"/> ${user.firstName} ${user.surname}</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/images/logo.png"/>"/>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="seller-profile-main-body-container">
        <div class="row">
            <div id="orders" class="col s12">
                <div class="user-profile-container-2">
                    <div class="information row">
                        <div class="col s6">
                            <div class="userprofile-info-1">
                                <spring:message code="fullname" arguments="${user.firstName}, ${user.surname}"/>
                            </div>
                        </div>
                        <div class="col s6">
                            <div class="userprofile-info-2">
                                <c:out value="${user.email}"/>
                            </div>
                        </div>
                        <c:choose>
                            <c:when test="${user.image != null}">
                                <div class="col s12 separate-20-top">
                                    <div class="flex-center">
                                        <img src="<c:url value="/image/${user.image.id}"/>"
                                             class="image-restrain profile-pic flex-column-center-align-vertical" alt=""/>
                                    </div>
                                    <div class="flex-center separate-12-top">
                                        <a class="space-icons" href="<c:url value="/deleteProfilePic"/>">
                                            <i class="center-align small material-icons white-text">
                                                delete
                                            </i>
                                        </a>
                                        <a class="modal-trigger space-icons" href="#updatePicModal">
                                            <i class="center-align small material-icons white-text">
                                                edit
                                            </i>
                                        </a>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="col s12 text-center separate-20-top">
                                    <a class="waves-effect waves-light btn modal-trigger" href="#updatePicModal">
                                        <spring:message code="updateprofilepic.newpic.msg"/>
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <div id="updatePicModal" class="modal green-modal">
                            <div class="modal-content">
                                <h3 class="underline text-center">
                                    <spring:message code="updateprofilepic.msg"/>
                                </h3>
                                <div class="col s12 separate-20-top">
                                    <c:url value="/updateProfilePic" var="url"/>
                                    <form:form modelAttribute="profilePicForm" method="post" action="${url}"
                                               enctype="multipart/form-data">
                                        <div class="file-field input-field flex-center">
                                            <div class="waves-effect waves-light btn image_button" style="margin: auto 10px;">
                                                <span><spring:message code="createproduct.form.image"/></span>
                                                <form:input path="image" type="file"/>
                                            </div>
                                            <div class="file-path-wrapper-style" style="margin: auto 10px;">
                                                <input class="file-path validate" type="text" readonly id="readonly-input">
                                            </div>
                                        </div>
                                        <form:errors path="image" element="p" cssClass="error"/>
                                        <div class="center create-submit flex-center">
                                            <button type="submit" class="waves-effect waves-light btn flex-center">
                                                <spring:message code="updateprofilepic.form.submit"/>
                                            </button>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="purchase underline center"><spring:message code="userprofile.orderstitle"/></div>
                    <div class="seller-profile-container-orders-2">
                        <c:if test="${orders.size() == 0}">
                            <div class="seller-margin">
                                <div class="text-center">
                                    <spring:message code="userprofile.noorders"/>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${orders.size() != 0}">
                            <c:set var="num" value="0"/>
                            <c:forEach items="${orders}" var="order">
                                <div class="user-profile-card">
                                    <div style="text-align: right">
                                        <c:out value="${order.parsedDateTime}"/>
                                    </div>
                                    <div class="center user-profile-card-title">
                                        <c:out value="${order.productName}"/>
                                    </div>
                                    <div class="seller-profile-card-content">
                                        <div>
                                            <spring:message code="sellerprofile.orders.price"
                                            arguments="${order.price}"/>
                                        </div>
                                        <div>
                                            <spring:message code="sellerprofile.orders.amount"
                                            arguments="${order.amount}"/>
                                        </div>
                                        <div>
                                            <spring:message code="sellerprofile.orders.seller"
                                            arguments="${order.sellerName}, ${order.sellerSurname}"/>:
                                        </div>
                                        <div>
                                            <spring:message code="sellerprofile.neworder.sellermail"
                                                            arguments="${order.sellerEmail}"/>:
                                        </div>
                                        <c:forEach items="${users}" var="user">
                                            <c:if test="${user.email == order.sellerEmail}">
                                                <c:forEach items="${sellers}" var="seller">
                                                    <c:if test="${user.id == seller.user.id}">
                                                        <div>
                                                            <spring:message code="userprofile.orders.selleraddress" arguments="${seller.address}"/>
                                                        </div>
                                                        <div>
                                                            <spring:message code="userprofile.orders.sellerphone" arguments="${seller.phone}"/>
                                                        </div>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${fromSale && num == 0 && currentPage == 1}">
                                            <div class="center user-margin">
                                                <div class="chip green chip-font">
                                                    <spring:message code="profile.newpurchase"/>
                                                </div>
                                            </div>
                                            <c:set var="num" value="${num+1}"/>
                                        </c:if>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
                <c:if test="${pages.size() > 1}">
                    <div class="pagin">
                        <c:set var="nextPage" value="${currentPage+1}"/>
                        <c:set var="previousPage" value="${currentPage-1}"/>
                        <div>
                            <ul class="pagination">
                                <c:if test="${currentPage <= 1}">
                                    <li class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                                </c:if>
                                <c:if test="${currentPage > 1}">
                                    <li><a href="?page=${currentPage-1}"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                                    <li class="waves-effect"><a href="?page=${currentPage-1}" style="color: #EDFA8B">${previousPage}</a></li>
                                </c:if>
                                <li id="${currentPage}" class="disabled active"><a class="yellow-card" href="">${currentPage}</a></li>
                                <c:if test="${currentPage < pages.size()}">
                                    <li class="waves-effect"><a href="?page=${currentPage+1}" style="color: #EDFA8B">${nextPage}</a></li>
                                    <li><a href="?page=${currentPage+1}"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                                </c:if>
                                <c:if test="${currentPage >= pages.size()}">
                                    <li class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </c:if>
            </div>
            <div id="orderSuccess" class="modal green-modal center fit-modal-content">
                <%-- TODO: Unclear solution to get the 1st element of a collection,
                        but it seems JSP does not offer an alternative
                        to pick one element and store it as a var--%>
                <c:forEach var="order" items="${orders}" end="0">
                    <div class="modal-content" style="padding-bottom:0;">
                        <h3 class="underline"><spring:message code="userprofile.ordercompleted"/></h3>
                        <div>
                            <c:choose>
                                <c:when test="${order.amount > 1}">
                                    <p class="orderSuccessModal">
                                        <spring:message code="userprofile.ordercompleted.message"
                                         arguments="${user.firstName}, ${user.surname},
                                        ${order.productName}, ${order.amount}"/>
                                    </p>
                                </c:when>
                                <c:otherwise>
                                    <p class="orderSuccessModal">
                                        <spring:message code="userprofile.ordercompleted.oneunit.message"
                                        arguments="${user.firstName}, ${user.surname},
                                        ${order.productName}, ${order.amount}"/>
                                    </p>
                                </c:otherwise>
                            </c:choose>
                            <ul class="seller-info">
                                <li>
                                    <spring:message code="sellerprofile.orders.seller"
                                    arguments="${order.sellerName}, ${order.sellerSurname}"/>
                                </li>
                                <li>
                                    <spring:message code="sellerprofile.neworder.sellermail"
                                    arguments="${order.sellerEmail}"/>
                                </li>
                                <c:forEach items="${users}" var="user">
                                    <c:if test="${user.email == order.sellerEmail}">
                                        <c:forEach items="${sellers}" var="seller">
                                            <c:if test="${user.id == seller.user.id}">
                                                <li>
                                                    <spring:message code="registerbuyer.form.address"
                                                    arguments="${seller.address}"/>
                                                </li>
                                                <li>
                                                    <spring:message code="registerbuyer.form.phone"
                                                    arguments="${seller.phone}"/>
                                                </li>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="modal-footer custom-modal-footer">
                        <a href="#!" class="modal-close waves-effect waves-green btn-flat accept">
                            <spring:message code="accept"/>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
<script>
    let shown = 0;
    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.modal');
        var instances = M.Modal.init(elems, options);
    });

    if(${fromSale} && (${currentPage} == 1) && shown === 0){
        shown = 1;
        document.addEventListener('DOMContentLoaded', function () {
            var Modalelem = document.querySelector('#orderSuccess');
            var instance = M.Modal.init(Modalelem);
            instance.open();
        });
    }

    var instance = M.Tabs.init(el, options);


</script>
</html>
