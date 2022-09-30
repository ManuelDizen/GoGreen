<%--
  Created by IntelliJ IDEA.
  User: Franco De Simone
  Date: 22/9/2022
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Explore Products</title>
</head>
<body>
<div class="explore-products">
    <c:if test="${isEmpty}">
        <h4><spring:message code="explore.noproducts"/></h4>
        <sec:authorize access="hasRole('SELLER')">
            <div><spring:message code="explore.noproducts.sellermsg"/></div>
            <div>
                <a class="decision-button waves-effect waves-light btn standard-button"
                   href="<c:url value="/createProduct"/>">
                    <spring:message code="explore.createproduct"/>
                </a>
            </div>
        </sec:authorize>
    </c:if>
    <c:if test="${products.size() != 0}">
        <c:forEach items="${products}" var="product">
            <div class="card product-card" style="margin:10px auto;">
                <a href="<c:url value="/product/${product.productId}"/>">
                    <div class="card-image">
                        <c:if test="${product.imageId != 0}">
                            <img style="border-radius: 10px 10px 0 0;" src="<c:url value="/image/${product.imageId}"/>">
                        </c:if>
                        <c:if test="${product.imageId == 0}">
                            <img style="border-radius: 10px 10px 0 0;" src="<c:url value="/resources/images/logo.png"/>">
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
                                <spring:message code="${category.name}"/>
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
<%--                    <div class="submit-button">--%>
<%--&lt;%&ndash;                        <a class="waves-effect waves-light btn standard-button"&ndash;%&gt;--%>
<%--&lt;%&ndash;                           href="<c:url value="/product/${product.productId}"/>"&ndash;%&gt;--%>
<%--&lt;%&ndash;                           style="text-align: center">&ndash;%&gt;--%>
<%--&lt;%&ndash;                            <spring:message code="explore.product.goto"/>&ndash;%&gt;--%>
<%--&lt;%&ndash;                        </a>&ndash;%&gt;--%>
<%--                    </div>--%>
                </div>
<%--                <div class="card-reveal" style="background-color: #1b5e20; color: #ADE28A;">--%>
<%--                    <span class="card-title product-card-title"><i class="material-icons right">close</i></span>--%>
<%--                    <div class="two-line">--%>
<%--                        <span class="card-title product-card-title"><c:out value="${product.name}"/></span>--%>
<%--                        <span class="card-title product-card-title"><c:out value="$${product.price}"/></span>--%>
<%--                    </div>--%>
<%--                    <p><c:out value="${product.description}"/></p>--%>
<%--                    <c:forEach items="${product.tagList}" var="ecotag">--%>
<%--                        <a class="${ecotag.color} white-text chip" href="<c:url value="/explore?name=&maxPrice=&${ecotag.path}=on"/>"/>--%>
<%--                        <i class="tiny material-icons">${ecotag.icon}</i>--%>
<%--                        <spring:message code="${ecotag.tag}"/>--%>
<%--                        </a>--%>
<%--                        <br>--%>
<%--                    </c:forEach>--%>
<%--                    <div class="submit-button">--%>
<%--                        <a class="waves-effect waves-light btn standard-button"--%>
<%--                           href="<c:url value="/product/${product.productId}"/>"--%>
<%--                           style="text-align: center">--%>
<%--                            <spring:message code="explore.product.goto"/>--%>
<%--                        </a>--%>
<%--                    </div>--%>
<%--                </div>--%>
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${products.size() == 0}">
        <div class="noproducts-container">
            <h4><spring:message code="explore.noproductsfilter"/></h4>
            <div class="circle">
                <img src="<c:url value="/resources/images/logo.png"/>" height="200" width="200"
                     alt="Logo">
            </div>
            <div style="margin-bottom: 15px">
                <div>
                    <a class="decision-button waves-effect waves-light btn" href="<c:url value="/explore"/>"><spring:message code="explore.cleanfilters"/></a>
                </div>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
