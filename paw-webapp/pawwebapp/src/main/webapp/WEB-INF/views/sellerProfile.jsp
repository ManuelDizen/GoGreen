<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><spring:message code="navbar.companyname"/> - <spring:message code="profile.title"/> ${user.firstName} ${user.surname}</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/images/logo.png"/>"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type = "text/javascript" src = "https://code.jquery.com/jquery-2.1.1.min.js"></script>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="seller-profile-main-body-container">
    <div class="seller-profile-container-2-bis seller-data" style="display:flex;">
        <div class="information row">
            <div class="col s6">
                <div class="sellerprofile-info-1"><c:out value="${user.firstName}${' '}${user.surname}"/></div>
                <div class="sellerprofile-info-1"><c:out value="${user.email}"/></div>
            </div>
            <div class="col s6">
                <div class="sellerprofile-info-2">
                    <c:out value="${seller.address}"/>
                </div>
                <div class="sellerprofile-info-2">
                    <c:out value="${seller.phone}"/>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col s12">
            <ul class="tabs tabs-fixed-width" id="sellerprofile_tabs">
                <li class="tab col s3"><a class="active" href="#products"><spring:message code="sellerprofile.productos"/></a></li>
                <li class="tab col s3"><a href="#orders"><spring:message code="sellerprofile.orderstitle"/></a></li>
            </ul>
        </div>
        <div id="products" class="col s12">
            <div class="seller-profile-container-2-lower-bis">
                <div class="seller-profile-products-2-bis">
                    <c:if test="${products.size() == 0}">
                        <div class="seller-profile-no-products-container">
                            <div><spring:message code="sellerprofile.noproducts"/></div>
                            <div class="seller-margin">
                                <a class="decision-button waves-effect waves-light btn standard-button seller-margin"
                                   href="<c:url value="/createProduct"/>">
                                    <spring:message code="explore.createproduct"/>
                                </a>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${products.size() != 0}">
                        <c:forEach items="${products}" var="product">
                            <c:if test="${product.status.id != deletedId}">
                                <div class="seller-profile-product-card">
                                    <div class="seller-profile-product-card-info">
                                        <c:if test="${product.stock == 0}">
                                            <div class="seller-inline-flex">
                                                    <%--a id="edit" class="waves-effect waves-light btn blue darken-3 modal-trigger" href="#stockModal${product.productId}"--%>
                                                <a id="stockless" class="waves-effect waves-light btn orange darken-3 modal-trigger" href="#stockModal${product.productId}">
                                                    <i class="material-icons">warning</i>
                                                    <spring:message code="sellerprofile.warning.nostock"/>
                                                </a>
                                            </div>
                                        </c:if>
                                        <div class="seller-profile-card-title">
                                            <a class="seller-profile-card-title" href="<c:url value="/product/${product.productId}"/>">
                                                <c:out value="${product.name}"/>
                                            </a>
                                        </div>
                                        <div style="display:flex; justify-content: space-evenly">
                                            <div class="seller-profile-card-content">
                                                <spring:message code="sellerprofile.orders.price"/>
                                                <c:out value="${'$'}${product.price}"/>
                                            </div>
                                            <div class="seller-profile-card-content">
                                                <spring:message code="sellerprofile.stock"/>
                                                <c:out value="${product.stock}"/>
                                            </div>
                                        </div>
                                        <div class="sellerprofile-action-buttons">
                                            <a id="edit" class="waves-effect waves-light btn modal-trigger edit-btn" href="<c:url value="/updateProduct/${product.productId}"/>">
                                                <i class="material-icons">edit</i>
                                                <spring:message code="sellerprofile.updatestock"/>
                                            </a>
                                            <a id="delete" class="waves-effect waves-light btn red accent-4 modal-trigger" href="#modal${product.productId}">
                                                <i class="material-icons">delete_forever</i>
                                                <spring:message code="sellerprofile.delete.confirmbutton"/>
                                            </a>
                                            <c:if test="${product.status.id == availableId}">
                                                <a id="pause" class="waves-effect waves-light btn grey lighten-1 black-text" href="<c:url value="/pauseProduct/${product.productId}"/>">
                                                    <i class="material-icons">pause_circle_outline</i>
                                                    <spring:message code="sellerprofile.pause.confirmbutton"/>
                                                </a>
                                            </c:if>
                                            <c:if test="${product.status.id == pausedId}">
                                                <a id="republish" class="waves-effect waves-light btn green accent-4 modal-trigger" href="<c:url value="/republishProduct/${product.productId}"/>">
                                                    <i class="material-icons">play_circle_outline</i>
                                                    <spring:message code="sellerprofile.play.confirmbutton"/>
                                                </a>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                                <div id="stockModal${product.productId}" class="modal green-modal">
                                    <div class="modal-content">
                                        <h3 class="underline"><spring:message code="sellerprofile.nostock.message"/>:
                                            <c:out value="${product.name}"/></h3>
                                        <div class="text-center">
                                            <spring:message code="sellerprofile.updatestock.note"/>
                                            <spring:message code="sellerprofile.updatestock.note2"/>
                                        </div>
                                        <div class="row s12 center-buttons">
                                            <a id="edit2" class="waves-effect waves-light btn edit-btn modal-trigger accomodate-button" href="<c:url value="/updateProduct/${product.productId}"/>">
                                                <i class="material-icons">edit</i>
                                                <spring:message code="sellerprofile.updatestock"/>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <div id="modal${product.productId}" class="modal green-modal">
                                    <div class="modal-content">
                                        <h3 class="underline"><spring:message code="sellerprofile.delete.confirm"/></h3>
                                        <h4><c:out value="${product.name}"/></h4>
                                    </div>
                                    <div class="row s12" style="display:flex; justify-content: center;">
                                        <a class="modal-close waves-effect waves-green btn-flat white-margin">
                                            <spring:message code="sellerprofile.delete.cancel"/>
                                        </a>
                                        <a class="waves-effect waves-light btn  red accent-4 left-margin" href=<c:url value="/deleteProduct/${product.productId}"/>>
                                            <i class="material-icons left">delete</i><spring:message code="sellerprofile.delete.confirmbutton"/>
                                        </a>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
            <c:if test="${productPages.size() > 1}">
                <div class="pagin">
                    <c:set var="nextPage" value="${currentPageP+1}"/>
                    <c:set var="previousPage" value="${currentPageP-1}"/>
                    <div>
                        <ul class="pagination">
                            <c:if test="${currentPageP <= 1}">
                                <li class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                            </c:if>
                            <c:if test="${currentPageP > 1}">
                                <li><a href="?pageP=${currentPageP-1}#products"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                                <li class="waves-effect"><a href="?pageP=${currentPageP-1}#products" style="color: #EDFA8B">${previousPage}</a></li>
                            </c:if>
                            <li id="${currentPageP}" class="disabled active"><a class="yellow-card" href="">${currentPageP}</a></li>
                            <c:if test="${currentPageP < productPages.size()}">
                                <li class="waves-effect"><a href="?pageP=${currentPageP+1}#products" style="color: #EDFA8B">${nextPage}</a></li>
                                <li><a href="?pageP=${currentPageP+1}#products"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                            </c:if>
                            <c:if test="${currentPageP >= productPages.size()}">
                                <li class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </c:if>
        </div>
        <div id="orders" class="col s12">
            <div class="seller-profile-container-2-lower-bis">
                <div class="seller-profile-container-orders-2">
                    <c:if test="${orders.size() == 0}">
                        <div class="seller-margin">
                            <div class="underline"><spring:message code="sellerprofile.noorders"/></div>
                        </div>
                    </c:if>
                    <c:if test="${orders.size() != 0}">
                        <c:forEach items="${orders}" var="order">
                            <div class="seller-profile-card">
                                <div style="text-align: right">
                                    <c:out value="${order.parsedDateTime}"/>
                                </div>
                                <div class="center seller-profile-card-title">
                                    <c:out value="${order.productName}"/>
                                </div>
                                <div class="seller-profile-card-content">
                                    <spring:message code="sellerprofile.orders.price"/>
                                    <c:out value="${'$'}${order.price}"/>
                                </div>
                                <div class="seller-profile-card-content">
                                    <spring:message code="sellerprofile.orders.amount"/>
                                    <c:out value="${order.amount}"/>
                                </div>
                                <div>
                                    <spring:message code="sellerprofile.orders.buyer"/>
                                    <c:out value="${order.buyerName}${' '}${order.buyerSurname}"/>
                                </div>
                                <div>
                                    <spring:message code="sellerprofile.orders.buyermail"/>:
                                    <c:out value="${order.buyerEmail}"/>
                                </div>
<%--                                <div style="height:fit-content; overflow-wrap: break-word;">--%>
<%--                                    <c:if test="${fn:length(order.message) > 0}">--%>
<%--                                        <spring:message code="sellerprofile.orders.message"/>--%>
<%--                                        <c:out value="${': '}${order.message}"/>--%>
<%--                                    </c:if>--%>
<%--                                </div>--%>
                                <div class="seller-two-margin">
                                    <c:if test="${fn:length(order.message) > 0}">
                                        <a class="waves-effect waves-light btn-small gray accent-4 modal-trigger" href="#messagemodal${order.id}">
                                            <spring:message code="sellerprofile.seemessage"/></a>
                                    </c:if>
                                </div>
                                <div class="seller-two-margin">
                                    <a class="waves-effect waves-light btn-small red accent-4 modal-trigger" href="#ordermodal${order.id}"><i class="material-icons left">cancel</i>
                                    <spring:message code="sellerprofile.cancelorder"/></a>
                                </div>
                            </div>
                            <div id="messagemodal${order.id}" class="modal green-modal">
                                <div class="modal-content">
                                    <h3 class="underline"><spring:message code="sellerprofile.orders.message"/></h3>
                                    <div>
                                        <h4><c:out value="${order.productName}"/></h4>
                                        <p class="buyer-message"><c:out value="${order.message}"/></p>
                                    </div>
                                </div>
                            </div>
                            <div id="ordermodal${order.id}" class="modal green-modal">
                                <div class="modal-content">
                                    <h3 class="underline"><spring:message code="sellerprofile.deleteorder.confirm"/></h3>
                                    <div>
                                        <h4><c:out value="${order.productName}"/></h4>
                                        <ul>
                                            <li>
                                                <spring:message code="sellerprofile.deleteorder.buyer"/>
                                                <c:out value="${order.buyerName}${' '}${order.buyerSurname}"/>
                                            </li>
                                            <li>
                                                <spring:message code="sellerprofile.deleteorder.amount"/>
                                                <c:out value="${order.amount}"/>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="row s12" style="display:flex; justify-content: center;">
                                    <a class="modal-close waves-effect waves-green btn-flat" style="color:white;">
                                        <spring:message code="sellerprofile.delete.cancel"/>
                                    </a>
                                    <a class="waves-effect waves-light btn  red accent-4" href="<c:url value="/deleteOrder/${order.id}"/>">
                                        <i class="material-icons left">delete</i><spring:message code="sellerprofile.delete.confirmbutton"/>
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
            <c:if test="${orderPages.size() > 1}">
                <div class="pagin">
                    <c:set var="nextPage" value="${currentPageO+1}"/>
                    <c:set var="previousPage" value="${currentPageO-1}"/>
                    <div>
                        <ul class="pagination">
                            <c:if test="${currentPageO <= 1}">
                                <li class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                            </c:if>
                            <c:if test="${currentPageO > 1}">
                                <li><a href="?pageO=${currentPageO-1}#orders"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                                <li class="waves-effect"><a href="?pageO=${currentPageO-1}#orders" style="color: #EDFA8B">${previousPage}</a></li>
                            </c:if>
                            <li id="${currentPageO}" class="disabled active"><a class="yellow-card" href="">${currentPageO}</a></li>
                            <c:if test="${currentPageO < orderPages.size()}">
                                <li class="waves-effect"><a href="?pageO=${currentPageO+1}#orders" style="color: #EDFA8B">${nextPage}</a></li>
                                <li><a href="?pageO=${currentPageO+1}#orders"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                            </c:if>
                            <c:if test="${currentPageO >= orderPages.size()}">
                                <li class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>

</body>
<script>
    $(document).ready(function() {
        $("#changePictureButton").click(function() {
            $("#pp_form").toggle();
        });
    });

    var instance = M.Tabs.init(el, options);

    document.addEventListener('DOMContentLoaded', function() {
        var elems = document.querySelectorAll('.modal');
        var instances = M.Modal.init(elems, options);
    });


</script>
</html>
