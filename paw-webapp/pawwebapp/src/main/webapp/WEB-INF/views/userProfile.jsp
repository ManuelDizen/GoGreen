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
    <title><spring:message code="profile.title"/>${user.firstName} ${user.surname}</title>
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
                        <a class="waves-effect waves-light btn standard-button">
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
                        <a class="waves-effect waves-light btn standard-button">
                            <spring:message code="sellerprofile.changeinfo"/>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <div id="test2" class="col s12">
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
        </div>
<%--    <div class="seller-profile-main-body-container-information">--%>
<%--        <div class="seller-profile-container">--%>
<%--            <h4 class="profile-row">${user.firstName} ${user.surname}</h4>--%>
<%--            <c:if test="${user.imageId == 0}">--%>
<%--                <img src="<c:url value="/resources/images/logo.png"/>" alt="ProfilePictureOf${user.firstName}">--%>
<%--            </c:if>--%>
<%--            <c:if test="${user.imageId != 0}">--%>
<%--                <img src="<c:url value="/image/${user.imageId}"/>" alt="ProfilePictureOf${user.firstName}">--%>
<%--            </c:if>--%>
<%--            <div class="profile-row">--%>
<%--                <span><spring:message code ="sellerprofile.mail"/> ${user.email}</span>--%>
<%--            </div>--%>
<%--            <div class="profile-row">--%>
<%--                <span><spring:message code ="sellerprofile.name"/> ${user.firstName} ${user.surname}</span>--%>
<%--            </div>--%>
<%--            <div class="profile-row">--%>
<%--                <a href="<c:url value="/logout"/>">--%>
<%--                    <button type="submit" class="waves-effect waves-light btn">--%>
<%--                        <spring:message code="logout"/>--%>
<%--                    </button>--%>
<%--                </a>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
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

</script>
</html>
