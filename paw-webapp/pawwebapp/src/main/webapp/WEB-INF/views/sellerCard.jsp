<html>
  <body>
    <div class="card product-card" style="max-height: 320px">
      <a style="height:60%" href="<c:url value="/sellerPage/${seller.id}"/>">
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
        <div class="seller-card-container">
          <div class="card-title product-card-title" style="padding-top: 7px">
            <a href="<c:url value="/sellerPage/${seller.id}"/>" class="green-font">
              <c:out value="${seller.user.firstName}"/>
            </a>
          </div>
          <div>
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
              <a href="<c:url value="/setFav/${seller.id}/true"/>" style="float: right">
                <i class="material-icons star-icon">star_border</i>
              </a>
            </c:if>
          </div>
        </div>
        <div>
          <c:if test="${seller.area != null}">
            <div class="card-category">
              <i class="tiny material-icons">location_pin</i>
              <a href="<c:url value="/explore?areaId=${area.id}&sort=${sort}&direction=${direction}"/>">
                <c:out value="${seller.area.name}"/>
              </a>
            </div>
          </c:if>
        </div>
      </div>

    </div>
  </body>
</html>
