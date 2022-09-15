<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 28/8/22
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="explore.title"/></title>
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="resources/images/logo.png"/>"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
</head>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="explore-title"><spring:message code="explore.title"/></div>
    <div class="explore-container">
        <div class="explore-filter">
            <div class="explore-filter-title"><spring:message code="explore.filterby"/></div>
            <c:url value="/explore" var="explore"/>
            <form action="${explore}" method="get" id="filter_form">
                <table>
                    <tr>
                        <!--<td><label path="name">Name</label></td>-->
                        <td class="filter-inputlabel"><spring:message code="explore.filterform.name"/></td>
                        <td><input name="name" type="text"/></td>
                    </tr>
                    <tr>
                        <td class="filter-inputlabel"><spring:message code="explore.filterform.category"/></td>
                        <td><input name="category" type="text"/><td>
                    </tr>
                    <tr>
                        <td class="filter-inputlabel">Ecotag</td>
                        <c:forEach items="${ecotagList}" var="ecotag">
                            <td><input name="${ecotag.path}" type="checkbox" id="ecotag">
                                <label for="ecotag">${ecotag.tag}</label> </td>
                        </c:forEach>
                    </tr>
                    <tr>
                        <!--<td><label path="price">Max price</label></td>-->
                        <td class="filter-inputlabel"><spring:message code="explore.filterform.maxprice"/></td>
                        <td><input name="maxPrice" type="number"/></td>
                    </tr>
                </table>
                <div style="display:flex;justify-content: space-around;margin-top:5vh;">
                    <button type="submit" class="waves-effect waves-light btn"><spring:message code="explore.filterform.submit"/></button>
                </div>
            </form>

        </div>
        <div class="explore-products">
            <c:if test="${isEmpty}">
                <div class="center">
                    <h4><spring:message code="explore.noproducts"/></h4>
                    <img style="width:30%; position:relative;" src="<c:url value="/resources/images/landingImage1.png"/>" alt="Sustainability for all!">
                    <h4><spring:message code="explore.comeback"/></h4>
                </div>
            </c:if>
            <c:forEach items="${products}" var="product">
                <div class="product-card-holder">
                    <!-- TODO: Me da toda la sensación que esta mal, pero el return default
                            para los products con columna null de imageId es 0.-->

                    <div class="explore-product-image-container">
                        <c:if test="${product.imageId != 0}">
                            <img src="<c:url value="/image/${product.imageId}"/>" alt="">
                        </c:if>
                    </div>
                    <div class="pccontainer">
                        <a class="pccard1" href="<c:url value="/product/${product.productId}"/>">
                            <h3>${product.name}</h3>
                            <p class="small">${product.description}</p>
                            <p class="small">
                                <spring:message code="explore.products.price"/>${product.price}
                            </p>
                            <c:forEach items="${product.tagList}" var="ecotag">
                                <div class="${ecotag.color} white-text chip">
                                    <i class="tiny material-icons">${ecotag.icon}</i>
                                        ${ecotag.tag}
                                </div>
                            </c:forEach>
                            <div class="go-corner" href="#">
                                <div class="go-arrow">
                                    →
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <%@ include file="footer.jsp"%>
</body>
</html>
