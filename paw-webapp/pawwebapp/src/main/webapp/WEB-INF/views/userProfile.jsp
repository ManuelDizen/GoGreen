<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 11/9/22
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="header.jsp"%>
    <title><spring:message code="profile.title"/> ${user.firstName} ${user.surname}</title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
</head>
<body>
<%@ include file="navbar.jsp"%>
<!-- TODO: Literalmente reworkear esta vista, esta deplorable-->
<div class="seller-profile-main-body-container">
    <div class="row">
        <div class="col s12">
            <ul class="tabs tabs-fixed-width" id="sellerprofile_tabs">
                <li class="tab col s3"><a href="#test1"><spring:message code="sellerprofile.information"/></a></li>
                <li class="tab col s3"><a href="#test2"><spring:message code="userprofile.orderstitle"/></a></li>
            </ul>
        </div>
        <div id="test1" class="col s12">
            <div class="seller-profile-container-2-bis" style="display:flex;">
                <div class="seller-inner-div-1">
                    <div class="text-center" style="font-weight: bold; font-size: 20px;"><c:out value="${user.firstName}${' '}${user.surname}"/></div>
                    <div class="seller-profile-pic-container">
                        <c:if test="${user.imageId == 0}">
                            <img src="<c:url value="/resources/images/logo.png"/>" alt="ProfilePictureOf${user.firstName}">
                        </c:if>
                        <c:if test="${user.imageId != 0}">
                            <img src="<c:url value="/image/${user.imageId}"/>" alt="ProfilePictureOf${user.firstName}">
                        </c:if>
                    </div>
                    <%--div style="width:100%;" class="text-center">
                        <a class="waves-effect waves-light btn standard-button">
                            <spring:message code="sellerprofile.changepic"/>
                        </a>
                    </div--%>
                </div>
                <div class="seller-inner-div-2">
                    <div>
                        <div style="font-size: 20px;"><b><spring:message code="sellerprofile.info"/></b></div>
                        <ul>
                            <li><spring:message code="sellerprofile.name"/>:<c:out value="${user.firstName}${' '}${user.surname}"/></li>
                            <li><spring:message code="sellerprofile.mail"/>:<c:out value="${user.email}"/></li>
                        </ul>
                    </div>
                    <%--iv>
                        <a class="waves-effect waves-light btn standard-button">
                            <spring:message code="sellerprofile.changeinfo"/>
                        </a>
                    </div--%>
                </div>
            </div>
        </div>
        <div id="test2" class="col s12">
            <div class="seller-profile-container-2-lower-bis">
                <div class="seller-profile-container-orders-2">
                    <h4><spring:message code="userprofile.orders"/></h4>
                    <c:if test="${orders.size() == 0}">
                        <div style="margin-top:5vh;">
                            <div style="text-align: center;"><spring:message code="userprofile.noorders"/></div>
                        </div>
                    </c:if>
                    <c:if test="${orders.size() != 0}">
                        <c:set var="num" value="0"/>
                        <c:forEach items="${orders}" var="order">
                            <div class="row" style="width:80%;border:1px solid var(--palette-details-light);display:inline-block;">
                                <div class="col s6 userPurchase">
                                    <div style="font-size: 20px;"><b><c:out value="${order.productName}"/></b></div>
                                    <c:if test="${fromSale && num == 0}">
                                        <div class="center" style="margin-top: 8px">
                                            <div class="chip green" style="color: white; font-weight: bold; max-width: 100px">
                                                <spring:message code="profile.newpurchase"/>
                                            </div>
                                        </div>
                                        <c:set var="num" value="${num+1}"/>
                                    </c:if>
                                </div>
                                <div class="col s6 userPurchase">
                                    <div>
                                        <spring:message code="sellerprofile.orders.time"/>
                                        <c:out value="${order.parsedDateTime}"/>
                                    </div>
                                    <div>
                                        <spring:message code="userprofile.orders.seller"/>
                                        <c:out value="${order.sellerName}${' '}${order.sellerSurname}"/>
                                    </div>
                                    <div><spring:message code="sellerprofile.orders.price"/><c:out value="${order.price}"/></div>
                                    <div><spring:message code="sellerprofile.orders.amount"/><c:out value="${order.amount}"/></div>
                                </div>
                            </div>
                            <%--div class="seller-profile-card">
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
                                    <spring:message code="userprofile.orders.seller"/>
                                    <c:out value="${order.sellerName}${' '}${order.sellerSurname}"/>
                                </div>
                            </div--%>
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
                                <li><a href="?page=${currentPage-1}#test2"><i class="material-icons pagination-arrow">navigate_before</i></a></li>
                                <li class="waves-effect"><a href="?page=${currentPage-1}#test2" style="color: #EDFA8B">${previousPage}</a></li>
                            </c:if>
                            <li id="${currentPage}" class="disabled active"><a class="yellow-card" href="">${currentPage}</a></li>
                            <c:if test="${currentPage < pages.size()}">
                                <li class="waves-effect"><a href="?page=${currentPage+1}#test2" style="color: #EDFA8B">${nextPage}</a></li>
                                <li><a href="?page=${currentPage+1}#test2"><i class="material-icons pagination-arrow">navigate_next</i></a></li>
                            </c:if>
                            <c:if test="${currentPage >= pages.size()}">
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

</script>
</html>
