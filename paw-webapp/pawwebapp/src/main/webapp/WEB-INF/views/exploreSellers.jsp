<%--
  Created by IntelliJ IDEA.
  User: manuel
  Date: 5/11/22
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="header.jsp"%>
    <title>Explore sellers</title>
</head>

<body>
    <%@include file="navbar.jsp"%>
    <h3>Explore sellers woohoo</h3>
    <div class="row" style="width:100%;">
        <div class="col s4" style="height:500px;">

        </div>
        <div class="col s7" style="height:500px;">
            <c:forEach items="${sellers}" var="seller">
                <div>
                    <h4>
                        <spring:message code="fullname"
                        arguments="${seller.user.firstName}, ${seller.user.surname}"/>
                    </h4>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
