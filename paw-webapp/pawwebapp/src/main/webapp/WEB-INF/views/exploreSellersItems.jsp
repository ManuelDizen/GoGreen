<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Explore Products</title>
</head>
<body>
<div class="explore-products">
    <c:if test="${isEmpty}">
        <div>
            <h4><spring:message code="explore.nosellersfilter"/></h4>
        </div>
    </c:if>
    <c:if test="${sellers.items.size() != 0}">
        <c:forEach items="${sellers.items}" var="seller">
            <%@include file="sellerCard.jsp"%>
        </c:forEach>
    </c:if>
    <c:if test="${sellers.items.size() == 0 && !isEmpty}">
        <div class="noproducts-container">
            <h4><spring:message code="explore.nosellersfilter"/></h4>
            <div class="circle">
                <img src="<c:url value="/resources/images/logo.png"/>" height="200" width="200"
                     alt="Logo">
            </div>
            <div class="clean-filters">
                <div>
                    <a class="decision-button waves-effect waves-light btn" href="<c:url value="/exploreSellers"/>">
                        <spring:message code="explore.cleanfilters"/>
                    </a>
                </div>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
