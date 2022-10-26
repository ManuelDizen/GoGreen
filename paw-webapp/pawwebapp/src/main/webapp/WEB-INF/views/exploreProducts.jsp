<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Explore Products</title>
</head>
<body>
<div class="explore-products">
    <c:if test="${isEmpty}">
        <div>
        <h4><spring:message code="explore.noproducts"/></h4>
        <sec:authorize access="hasRole('SELLER')">
            <div style="margin:10px auto; text-align:center;"><spring:message code="explore.noproducts.sellermsg"/></div>
            <div>
                <a class="decision-button waves-effect waves-light btn standard-button"
                   href="<c:url value="/createProduct"/>" style="margin:20px auto; text-align:center;">
                    <spring:message code="explore.createproduct"/>
                </a>
            </div>
        </sec:authorize>
        </div>
    </c:if>
    <c:if test="${products.size() != 0}">
        <c:forEach items="${products}" var="product">
            <%@include file="productCard.jsp"%>
        </c:forEach>
    </c:if>
    <c:if test="${products.size() == 0 && !isEmpty}">
        <div class="noproducts-container">
            <h4><spring:message code="explore.noproductsfilter"/></h4>
            <div class="circle">
                <img src="<c:url value="/resources/images/logo.png"/>" height="200" width="200"
                     alt="Logo">
            </div>
            <div class="clean-filters">
                <div>
                    <a class="decision-button waves-effect waves-light btn" href="<c:url value="/explore"/>">
                        <spring:message code="explore.cleanfilters"/>
                    </a>
                </div>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
