<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 13/9/22
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><spring:message code="profile.title"/> ${user.firstName} ${user.surname}</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/images/logo.png"/>"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script type = "text/javascript" src = "https://code.jquery.com/jquery-2.1.1.min.js"></script>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="seller-profile-main-body-container">
    <div class="row">
        <div class="col s12">
            <ul class="tabs tabs-fixed-width" id="sellerprofile_tabs">
                <li class="tab col s3"><a href="#test1"><spring:message code="sellerprofile.information"/></a></li>
                <li class="tab col s3"><a class="active" href="#test2"><spring:message code="sellerprofile.productos"/></a></li>
                <li class="tab col s3"><a href="#test3"><spring:message code="sellerprofile.orderstitle"/></a></li>
            </ul>
        </div>
        <div id="test1" class="col s12">
            <div class="seller-profile-container-2-bis" style="display:flex;">
                <div class="seller-inner-div-1">
                    <div class="text-center" style="font-size:20px;font-weight: bold;"><c:out value="${user.firstName}${' '}${user.surname}"/></div>
                    <div class="seller-profile-pic-container">
                        <c:if test="${user.imageId == 0}">
                            <img src="<c:url value="/resources/images/logo.png"/>" alt="ProfilePictureOf${user.firstName}">
                        </c:if>
                        <c:if test="${user.imageId != 0}">
                            <img src="<c:url value="/image/${user.imageId}"/>" alt="ProfilePictureOf${user.firstName}">
                        </c:if>
                    </div>
                    <%--<div style="width:100%;" class="text-center">
                        <a class="decision-button waves-effect waves-light btn standard-button">
                            <spring:message code="sellerprofile.changepic"/>
                        </a>
                    </div>--%>
                </div>
                <div class="seller-inner-div-2">
                    <div><div style="font-size:20px; font-weight: bold;"><spring:message code="sellerprofile.info"/></div>
                        <ul>
                            <li><spring:message code="sellerprofile.name"/>:<c:out value="${user.firstName}${' '}${user.surname}"/></li>
                            <li><spring:message code="sellerprofile.mail"/>:<c:out value="${user.email}"/></li>
                            <li><spring:message code="sellerprofile.address"/>:<c:out value="${seller.address}"/></li>
                            <li><spring:message code="sellerprofile.phone"/>:<c:out value="${seller.phone}"/></li>
                        </ul>
                    </div>
                    <%--<div>
                        <a class="decision-button waves-effect waves-light btn standard-button">
                            <spring:message code="sellerprofile.changeinfo"/>
                        </a>
                    </div>--%>
                </div>
            </div>
        </div>
        <div id="test2" class="col s12">
            <div class="seller-profile-container-2-lower-bis">
                <div class="seller-profile-products-2-bis">
                    <h4 style="text-align:center;"><spring:message code="sellerprofile.productos"/></h4>
                    <c:if test="${products.size() == 0}">
                        <div><spring:message code="sellerprofile.noproducts"/></div>
                        <div style="margin-top:5vh;">
                            <a style="margin-top:5vh;" class="decision-button waves-effect waves-light btn standard-button"
                               href="<c:url value="/createProduct"/>">
                                <spring:message code="explore.createproduct"/>
                            </a>
                        </div>
                    </c:if>
                    <c:if test="${products.size() != 0}">
                        <c:set var="count" value="0"/>
                        <c:forEach items="${products}" var="product">
                            <c:if test="${count lt 3}">
                                <div class="seller-profile-product-card">
                                    <div class="seller-profile-product-card-info">
                                        <c:if test="${product.stock == 0}">
                                            <a id="stockless" class="waves-effect waves-light btn orange darken-3 modal-trigger" href="#stockModal${product.productId}">
                                                <i class="material-icons">warning</i>
                                                <spring:message code="sellerprofile.warning.nostock"/>
                                            </a>
                                            <a id="productinfo" style="margin-right:2vw; font-size:18px;">
                                                <br><b><c:out value="${product.name}"/></b>  - <spring:message code="sellerprofile.orders.price"/>
                                                <c:out value="${product.price}"/>
                                            </a>
                                        </c:if>
                                        <c:if test="${product.stock != 0}">
                                            <a id="productinfo" style="margin-right:2vw; font-size:18px;" href="<c:url value="/product/${product.productId}"/>">
                                                <br><b><c:out value="${product.name}"/></b> - <spring:message code="sellerprofile.orders.price"/>
                                                <c:out value="${'$'}${product.price}"/> - <spring:message code="sellerprofile.stock"/>
                                                <c:out value="${product.stock}"/>
                                            </a>
                                        </c:if>
                                        <div style="margin-right:1vw; display:inline-flex">
                                            <%--a id="edit" class="waves-effect waves-light btn blue darken-3 modal-trigger" href="#stockModal${product.productId}"--%>
                                            <a id="edit" class="waves-effect waves-light btn blue darken-3 modal-trigger" href="<c:url value="/updateProduct/${product.productId}"/>">
                                                <i class="material-icons">edit</i>
                                                <spring:message code="sellerprofile.updatestock"/>
                                            </a>
                                            <a id="delete" class="waves-effect waves-light btn red accent-4 modal-trigger" href="#modal${product.productId}">
                                                <i class="material-icons">delete_forever</i>
                                                <spring:message code="sellerprofile.delete.confirmbutton"/>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <div id="stockModal${product.productId}" class="modal" style="background-color:var(--palette-color-secondary);">
                                    <div class="modal-content">
                                        <c:if test="${product.stock == 0}">
                                            <h3 style="text-decoration: underline"><spring:message code="sellerprofile.nostock.message"/>:
                                                <c:out value="${product.name}"/></h3>
                                            <div style="text-align: center;">
                                                <spring:message code="sellerprofile.updatestock.note"/>
                                                <b><spring:message code="sellerprofile.updatestock.note2"/>
                                            </div>
                                        </c:if>
                                        <c:if test="${product.stock != 0}">
                                            <h3 style="text-decoration: underline"><spring:message code="sellerprofile.updatestock.message"/>:
                                                <c:out value="${product.name}"/></h3>
                                        </c:if>
                                    </div>
                                    <c:url value="/updateStock/${product.productId}" var="updateStockUrl"/>
                                    <form:form modelAttribute="stockForm" action="${updateStockUrl}" method="post" id="update_form">
                                        <div class="col s12">
                                            <div class="input-field col s12">
                                                <spring:message code="sellerprofile.updatestock.placeholder" var="placeholder"/>
                                                <form:input path="newStock" name="newStock" id="newStock" type="number" cssStyle="color:white;" placeholder="${placeholder}"/>
                                                <form:label path="newStock"><spring:message code="sellerprofile.newstock"/></form:label>
                                                <form:errors path="newStock" element="p" cssClass="error"/>
                                            </div>
                                        </div>
                                        <div class="row s12" style="display:flex; justify-content: center;">
                                            <a class="modal-close waves-effect waves-green btn-flat" style="color:white; margin-right:5px;">
                                                <spring:message code="sellerprofile.delete.cancel"/>
                                            </a>
                                            <button class="btn waves-effect waves-light green accent-4" type="submit" name="newStock" style="margin-left:5px;">
                                                <spring:message code="sellerprofile.update.button"/>
                                                <i class="material-icons right">check</i>
                                            </button>
                                        </div>
                                    </form:form>
                                </div>
                                <div id="modal${product.productId}" class="modal" style="background-color:var(--palette-color-secondary);">
                                    <div class="modal-content">
                                        <h3 style="text-decoration: underline"><spring:message code="sellerprofile.delete.confirm"/></h3>
                                        <h4><c:out value="${product.name}"/></h4>
                                    </div>
                                    <div class="row s12" style="display:flex; justify-content: center;">
                                        <a class="modal-close waves-effect waves-green btn-flat" style="color:white; margin-right:1vw;">
                                            <spring:message code="sellerprofile.delete.cancel"/>
                                        </a>
                                        <a style="margin-left:1vw;" class="waves-effect waves-light btn  red accent-4" href=<c:url value="/deleteProduct/${product.productId}"/>>
                                            <i class="material-icons left">delete</i><spring:message code="sellerprofile.delete.confirmbutton"/>
                                        </a>
                                    </div>
                                </div>
                                <c:set var="count" value="${count + 1}"/>
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
                                <li><a href="?pageP=${currentPageP-1}#test2"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                                <li class="waves-effect"><a href="?pageP=${currentPageP-1}#test2" style="color: #EDFA8B">${previousPage}</a></li>
                            </c:if>
                            <li id="${currentPageP}" class="disabled active"><a class="yellow-card" href="">${currentPageP}</a></li>
                            <c:if test="${currentPageP < productPages.size()}">
                                <li class="waves-effect"><a href="?pageP=${currentPageP+1}#test2" style="color: #EDFA8B">${nextPage}</a></li>
                                <li><a href="?pageP=${currentPageP+1}#test2"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                            </c:if>
                            <c:if test="${currentPageP >= productPages.size()}">
                                <li class="disabled"><a href="" style="display: none"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </c:if>
        </div>
        <div id="test3" class="col s12">
            <div class="seller-profile-container-2-lower-bis">
                <div class="seller-profile-container-orders-2">
                    <h4><spring:message code="sellerprofile.orders"/></h4>
                    <c:if test="${orders.size() == 0}">
                        <div style="margin-top:5vh;">
                            <div style="text-align: center;"><spring:message code="sellerprofile.noorders"/></div>
                        </div>
                    </c:if>
                    <c:if test="${orders.size() != 0}">
                        <c:forEach items="${orders}" var="order">
                            <div class="seller-profile-card">
                                <div class="seller-profile-card-title">
                                    <c:out value="${order.productName}"/>
                                </div>
                                <div class="seller-profile-card-content">
                                    <spring:message code="sellerprofile.orders.price"/>
                                    <c:out value="${order.price}"/>
                                </div>
                                <div class="seller-profile-card-content">
                                    <spring:message code="sellerprofile.orders.amount"/>
                                    <c:out value="${order.amount}"/>
                                </div>
                                <div class="seller-profile-card-content">
                                    <spring:message code="sellerprofile.orders.time"/>
                                    <c:out value="${order.parsedDateTime}"/>
                                </div>
                                <div>
                                    <spring:message code="sellerprofile.orders.buyer"/>
                                    <c:out value="${order.buyerName}${' '}${order.buyerSurname}"/>
                                </div>
                                <div style="margin:5vh auto 2vh auto;">
                                    <a class="waves-effect waves-light btn red accent-4 modal-trigger" href="#ordermodal${order.id}"><i class="material-icons left">cancel</i>
                                    <spring:message code="sellerprofile.cancelorder"/></a>
                                </div>
                            </div>
                            <div id="ordermodal${order.id}" class="modal" style="background-color:var(--palette-color-secondary);">
                                <div class="modal-content">
                                    <h3 style="text-decoration: underline"><spring:message code="sellerprofile.deleteorder.confirm"/></h3>
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
                                <li><a href="?pageO=${currentPageO-1}#test3"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                                <li class="waves-effect"><a href="?pageO=${currentPageO-1}#test3" style="color: #EDFA8B">${previousPage}</a></li>
                            </c:if>
                            <li id="${currentPageO}" class="disabled active"><a class="yellow-card" href="">${currentPageO}</a></li>
                            <c:if test="${currentPageO < orderPages.size()}">
                                <li class="waves-effect"><a href="?pageO=${currentPageO+1}#test3" style="color: #EDFA8B">${nextPage}</a></li>
                                <li><a href="?pageO=${currentPageO+1}#test3"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
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
