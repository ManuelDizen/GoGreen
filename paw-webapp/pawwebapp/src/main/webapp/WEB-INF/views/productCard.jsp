<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<div class="card product-card z-depth-1">
    <a style="height:50%" href="<c:url value="/product/${product.productId}"/>">
        <div class="product-card-image-container">
            <c:if test="${product.image.id != 0}">
                <img class="image-restrain" src="<c:url value="/image/${product.image.id}"/>">
            </c:if>
            <c:if test="${product.image.id == 0}">
                <img class="image-restrain" src="<c:url value="/resources/images/logo.png"/>">
            </c:if>
        </div>
    </a>
    <div class="card-content">
        <a href="<c:url value="/product/${product.productId}"/>"
           class="card-title product-card-title"><c:out value="${product.name}"/></a>
        <div class="card-price">
            <spring:message code="explore.products.price"/><c:out value="${product.price}"/>
        </div>
        <div class="card-category">
            <i class="tiny material-icons">category</i>
            <c:forEach items="${categories}" var="category">
                <c:if test="${category.id == product.categoryId}">
                    <a href="<c:url value="/explore?category=${category.id}&sort=${sort}&direction=${direction}"/>"><spring:message code="${category.name}"/></a>
                </c:if>
            </c:forEach>
        </div>
        <div class="product-card-tags">
            <c:forEach items="${product.tagList}" var="ecotag">
                <a class="${ecotag.color} white-text chip eco_chip" href="<c:url value="/explore?strings=${ecotag.id}&sort=${sort}&direction=${direction}"/>">
                    <i class="tiny material-icons">${ecotag.icon}</i>
                </a>
            </c:forEach>
        </div>
    </div>
</div>
</html>
