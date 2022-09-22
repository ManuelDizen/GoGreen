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
    <title>${user.firstName} ${user.surname}</title>
    <%@ include file="header.jsp"%>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css">
    <script type = "text/javascript" src = "https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
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
            <div class="seller-profile-container-2-bis">
                <div class="seller-inner-div-1">
                    <div class="text-center"><c:out value="${user.firstName}${' '}${user.surname}"/></div>
                    <div class="seller-profile-pic-container">
                        <c:if test="${user.imageId == 0}">
                            <img src="<c:url value="/resources/images/logo.png"/>" alt="ProfilePictureOf${user.firstName}">
                        </c:if>
                        <c:if test="${user.imageId != 0}">
                            <img src="<c:url value="/image/${user.imageId}"/>" alt="ProfilePictureOf${user.firstName}">
                        </c:if>
                    </div>
                    <div style="width:100%;" class="text-center">
                        <a class="decision-button waves-effect waves-light btn standard-button">
                            <spring:message code="sellerprofile.changepic"/>
                        </a>
                    </div>
                </div>
                <div class="seller-inner-div-2">
                    <div><spring:message code="sellerprofile.info"/>
                        <ul>
                            <li><c:out value="${user.firstName}${' '}${user.surname}"/></li>
                            <li><c:out value="${user.email}"/></li>
                            <li><c:out value="${seller.address}"/></li>
                            <li><c:out value="${seller.phone}"/></li>
                        </ul>
                    </div>
                    <div>
                        <a class="decision-button waves-effect waves-light btn standard-button">
                            <spring:message code="sellerprofile.changeinfo"/>
                        </a>
                    </div>
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
                            <c:if test="${count == 3}">
                                <a class="waves-effect waves-light btn standard-button"
                                   href="<c:url value="/sellerProfile/products"/>">
                                    <spring:message code="sellerprofile.moreproducts"/>
                                </a>
                                <c:set var="count" value="${count + 1}"/>
                            </c:if>
                            <c:if test="${count lt 3}">
                                <div class="seller-profile-product-card">
                                    <div class="seller-profile-product-card-info">
                                        <a style="margin-right:2vw;" href="<c:url value="/product/${product.productId}"/>">
                                            <br><b><c:out value="${product.name}"/></b> - <spring:message code="sellerprofile.orders.price"/>
                                            <c:out value="${product.price}"/>
                                            </span>
                                        </a>
                                <span style="margin-right:1vw;">
                                    <a href="">
                                        <i class="material-icons">edit</i>
                                    </a>
                                    <a class="modal-trigger" href="#modal${product.productId}">
                                        <i class="material-icons">delete_forever</i>
                                    </a>
                                </span>
                                    </div>
                                </div>
                                <div id="modal${product.productId}" class="modal" style="background-color:var(--palette-color-secondary);">
                                    <div class="modal-content">
                                        <h3 style="text-decoration: underline"><spring:message code="sellerprofile.delete.confirm"/></h3>
                                        <h4><c:out value="${product.name}"/></h4>
                                    </div>
                                    <div class="modal-footer">
                                        <a href="#!" class="modal-close waves-effect waves-green btn-flat">
                                            <spring:message code="sellerprofile.delete.cancel"/>
                                        </a>
                                        <a class="waves-effect waves-light btn  red accent-4" href=<c:url value="/deleteProduct/${product.productId}"/>>
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
            <div class="pagin">
                <c:set var="nextPage" value="${currentPageP+1}"/>
                <c:set var="previousPage" value="${currentPageP-1}"/>
                <div>
                    <ul class="pagination">
                        <c:if test="${currentPageP <= 1}">
                            <li class="disabled"><a href="" style="display: none"><i class="material-icons">navigate_before</i></a></li>
                        </c:if>
                        <c:if test="${currentPageP > 1}">
                            <li id="back"><a href="?pageP=${currentPageP-1}#test2"><i class="material-icons">navigate_before</i></a></li>
                            <%--<li id="back"><a href="">${previousPage}</a></li>--%>
                        </c:if>
                        <li id="${currentPageP}" class="disabled active"><a class="yellow-card" href="">${currentPage}</a></li>
                        <c:if test="${currentPageP < productPages.size()}">
                            <%--<li id="forward"><a href="">${nextPage}</a></li>--%>
                            <li id="forward"><a href="?pageP=${currentPageP+1}#test2"><i class="material-icons">navigate_next</i></a></li>
                        </c:if>
                        <c:if test="${currentPageP >= productPages.size()}">
                            <li class="disabled"><a href="" style="display: none"><i class="material-icons">navigate_next</i></a></li>
                        </c:if>
                    </ul>
                </div>
            </div>
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
                                    <c:out value="${order.dateTime}"/>
                                </div>
                                <div>
                                    <spring:message code="sellerprofile.orders.buyer"/>
                                    <c:out value="${order.buyerName}${' '}${order.buyerSurname}"/>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
            <div class="pagin">
                <c:set var="nextPage" value="${currentPageO+1}"/>
                <c:set var="previousPage" value="${currentPageO-1}"/>
                <div>
                    <ul class="pagination">
                        <c:if test="${currentPageO <= 1}">
                            <li class="disabled"><a href="" style="display: none"><i class="material-icons">navigate_before</i></a></li>
                        </c:if>
                        <c:if test="${currentPageO > 1}">
                            <li id="back"><a href="?pageO=${currentPageO-1}#test3"><i class="material-icons">navigate_before</i></a></li>
                            <%--<li id="back"><a href="">${previousPage}</a></li>--%>
                        </c:if>
                        <li id="${currentPageO}" class="disabled active"><a class="yellow-card" href="">${currentPage}</a></li>
                        <c:if test="${currentPageO < orderPages.size()}">
                            <%--<li id="forward"><a href="">${nextPage}</a></li>--%>
                            <li id="forward"><a href="?pageO=${currentPageO+1}#test3"><i class="material-icons">navigate_next</i></a></li>
                        </c:if>
                        <c:if test="${currentPageO >= orderPages.size()}">
                            <li class="disabled"><a href="" style="display: none"><i class="material-icons">navigate_next</i></a></li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="footer.jsp"%>
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
