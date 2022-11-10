<html>
  <body>
    <div class="card product-card">
      <a style="height:50%" href="<c:url value="/sellerPage/${seller.id}"/>">
        <div class="seller-card-image-container">
          <c:if test="${seller.user.image != null}">
            <img class="image-restrain" src="<c:url value="/image/${seller.user.image.id}"/>">
          </c:if>
          <c:if test="${seller.user.image == null}">
            <img class="image-restrain" src="<c:url value="/resources/images/logo.png"/>">
          </c:if>
        </div>
      </a>
      <div class="card-content">
        <a href="<c:url value="/sellerPage/${seller.id}"/>"
           class="card-title product-card-title">
          <c:out value="${seller.user.firstName}"/>
        </a>
        <c:set var="isFav" value="0"/>
        <c:forEach items="${favIds}" var="sellerId">
          <c:if test="${sellerId == seller.id}">
            <a href="<c:url value="/setFav/${seller.id}/false"/>">
              <i class="material-icons star-icon">star</i>
            </a>
            <c:set var="isFav" value="1"/>
          </c:if>
        </c:forEach>
        <c:if test="${isFav == 0}">
          <a href="<c:url value="/setFav/${seller.id}/true"/>">
            <i class="material-icons star-icon">star_border</i>
          </a>
        </c:if>
      </div>

    </div>
  </body>
</html>
