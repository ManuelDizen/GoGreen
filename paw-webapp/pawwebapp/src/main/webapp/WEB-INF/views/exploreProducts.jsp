<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Explore Products</title>
</head>
<body>
<div class="explore-products">
    <c:if test="${products.size() == 0}">
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
            <%--div class="card product-card z-depth-1">
                <a href="<c:url value="/product/${product.productId}"/>">
                    <div class="card-image">
                        <c:if test="${product.image.id != 0}">
                            <img src="<c:url value="/image/${product.image.id}"/>">
                        </c:if>
                        <c:if test="${product.image.id == 0}">
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
                                <a href="<c:url value="/explore?category=${category.id}&sort=${sort}&direction=${direction}"/>"><spring:message code="${category.name}"/></a>
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
            </div--%>
            <div class="product-card-2">
                <div class="product-card-2-img-container">
                    <c:choose>
                        <c:when test="${product.image.id != 0}">
                            <img class="product-card-2-img" src="<c:url value="/image/${product.image.id}"/>"/>
                        </c:when>
                        <c:otherwise>
                            <img class="product-card-2-img" src="<c:url value="/resources/images/logo.png"/>"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="product-card-2-info-container">
                    <a href="<c:url value="/product/${product.productId}"/>" class="card-title product-card-title"><c:out value="${product.name}"/></a>
                    <div class="card-price">
                        <spring:message code="explore.products.price"/><c:out value="${product.price}"/>
                    </div>
                    <div class="card-price vertical-align">
                        <i class="tiny material-icons icon-margins">category</i>
                        <c:forEach items="${categories}" var="category">
                            <c:if test="${category.id == product.categoryId}">
                                <a class="align-with-icon" href="<c:url value="/explore?category=${category.id}&sort=${sort}&direction=${direction}"/>"><spring:message code="${category.name}"/></a>
                            </c:if>
                        </c:forEach>
                    </div>
                    <div class="center ecotag-container">
                        <c:set var="count" value="0"/>
                        <c:forEach items="${product.tagList}" var="ecotag">
                            <a class="${ecotag.color} white-text chip eco_chip" href="<c:url value="/explore?strings=${ecotag.id}&sort=${sort}&direction=${direction}"/>">
                                <span style="margin-top: 5px;"><i class="tiny material-icons ecotag-icon">${ecotag.icon}</i></span>
                                <spring:message code="${ecotag.tag}"/>
                            </a>
                            <c:if test="${count lt 2}">
                            </c:if>
                            <%--c:if test="${count == 2}">
                                <br>
                                <div class="yellow-card black-text chip ">
                                    <i class="tiny material-icons">more_horiz</i>
                                </div>
                            </c:if--%>
                            <%--c:set var="count" value="${count + 1}"/--%>
                        </c:forEach>
                    </div>
                </div>
            </div>
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
